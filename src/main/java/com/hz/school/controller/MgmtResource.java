package com.hz.school.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hz.school.util.CryptoUtil3;
import com.hz.school.util.Logger;
import com.hz.school.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value="/mgmt",produces="text/html;charset=UTF-8")
public class MgmtResource {
    private static Logger log = Logger.getLogger(MgmtResource.class);
    private static Gson gson = new GsonBuilder().create();

    public static boolean checkToken(String token){
        if (token == null || token.trim().length() == 0) {
            return false;
        }
        log.info("token: " + token);
        try {
            token = token.trim();
            int i = token.indexOf('x');
            if (i < 0) {
                log.info("token error: i < 0: " + token);
                return false;
            }
            String time = token.substring(0, i);
            token = CryptoUtil3.unescapeBase64(token.substring(i + 1));
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
            Date d = format.parse(time);
            if (!token.equalsIgnoreCase(CryptoUtil3.encrypt(time))) {
                log.info("decrypt error: " + token);
                return false;
            }

            // 必须在5分钟范围
            boolean tokenValid = Math.abs(d.getTime() - System.currentTimeMillis()) <= 5 * 60 * 1000;
            log.info("token valid?  " + tokenValid + " : " + token );
            return tokenValid;

        } catch (Throwable t) {
            log.error("error parse token : " + token, t);
        }
        return false;
    }
    private static void secure(HttpServletRequest request) {
        String name = "_tk";

        // 从cookie取tk

        for(Cookie ck : request.getCookies()){
            if(name.equalsIgnoreCase(ck.getName())){
                log.info("found cookie token: " + ck.getValue());
                if(checkToken(ck.getValue())){
                    return;
                }
            }
        }

        // 从header取tk
        String token = request.getHeader(name);
        log.info("token: " + token);
        if (token != null && checkToken(token)) {
            log.info("token ok: " + token);
            return;
        }else{
            log.info("token expires: " + token);
        }

        throw new RuntimeException("Auth Exception");
    }
    /**
     * 获取系统信息
     */
    @ResponseBody
    @RequestMapping(value="/cluster/system",method= RequestMethod.GET)
    public static String systemInfo(HttpServletRequest request) {
        secure(request);
        List<Param> list = new ArrayList<Param>();

        list.add(new Param("ip", ip()));
        list.add(new Param("now","" + new SimpleDateFormat("yyyyMMddHHmm").format(new Date())));
        list.add(new Param("now1","" + new Date()));
        list.add(new Param("now2","" + System.currentTimeMillis()));
        Properties props = System.getProperties(); // 系统属性

        Map<String,String> env = System.getenv();

        for(Object key : props.keySet()){
            list.add(new Param(key.toString(), props.getProperty(key.toString())));
        }

        for(Map.Entry<String,String> e : env.entrySet()){
            list.add(new Param(e.getKey(),e.getValue()));
        }

        return gson.toJson(list);
    }
    // 增加一个公开的time服务, 不需要登录
    @ResponseBody
    @RequestMapping(value="/time",method= RequestMethod.GET)
    public static String time(HttpServletRequest request) {
        List<Param> list = new ArrayList<Param>();

        list.add(new Param("ip", ip()));
        list.add(new Param("now","" + new SimpleDateFormat("yyyyMMddHHmm").format(new Date())));
        return gson.toJson(list);
    }
    /**
     * 列出目录下的所有文件
     */
    @ResponseBody
    @RequestMapping(value="/cluster/listFile",method= RequestMethod.GET)
    public static String clusterListFile(HttpServletRequest request) {
        String dir=request.getParameter("dir");
        secure(request);
        File[] files ;
        List<FileInfo> list = new ArrayList<FileInfo>();

        if(StringUtil.isEmpty(dir)){
            files = File.listRoots();
        }else{
            File d = new File(dir);
            if(!d.exists()){
                return "" + dir + " does not exist!";
            }
            if(!d.isDirectory()){
                return "" + dir + " is not directory!";
            }else{
                if(d.getParentFile()!=null){
                    FileInfo parent = new FileInfo(d.getParentFile());
                    parent.name="..";
                    list.add(parent);
                }
            }
            files = d.listFiles();
            sort(files);
        }

        if(files==null){
            return "{}";
        }


        for(File f: files){
            list.add(new FileInfo(f));
        }
        return gson.toJson(list);
    }
    /**
     * 获取文件
     */
//    @ResponseBody
    @RequestMapping(value="/cluster/getFile",method= RequestMethod.GET)
    public static InputStream clusterGetFile( HttpServletRequest request) {
        secure(request);
        String file=request.getParameter("file");
        try{
            return new FileInputStream(file);
        }catch(IOException e){
            return new ByteArrayInputStream( ("Can not open File: " + file + "\n" + e).getBytes());
        }
    }
    private static void sort(File[] files){
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File file, File file2) {
                if (file.isDirectory() == file2.isDirectory()) {
                    return file.getName().compareTo(file2.getName());
                }
                return file.isDirectory() ? -1 : 1;
            }
        });
    }
    /**
     * 文件信息辅助类
     */
    public static class FileInfo {
        String name;
        String path;
        String realPath="";
        long date;
        long length;

        boolean directory = false;

        FileInfo(File f){
            name = f.getName();
            path = f.getPath();
            try {
                realPath = f.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            date = f.lastModified();
            length = f.length();
            directory = f.isDirectory();
        }

        public boolean isDirectory() {
            return directory;
        }

        public String getName() {
            return name;
        }

        public String getPath() {
            return path;
        }

        public long getDate() {
            return date;
        }

        public long getLength() {
            return length;
        }

        public String getRealPath() {
            return realPath;
        }

    }
    private static class Param{
        String key;
        String value;

        Param(String k, String v){
            key = k;
            value = v;
        }
        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public static List<Param> create(String... params){
            List<Param> list = new ArrayList<Param>();

            if(params.length%2!=0){
                throw new RuntimeException("params should be even.");
            }

            for(int i=0; i<params.length; i+=2){
                list.add(new Param(params[i],params[i+1]));
            }

            return list;
        }

        public static Map<String,String> toMap(List<Param> params){
            Map<String,String> m = new HashMap<String, String>();
            for(Param p : params){
                m.put(p.getKey(),p.getValue());
            }
            return m;
        }
    }
    private static String ip(){
        try{
            InetAddress addr = InetAddress.getLocalHost();
            String ip=addr.getHostAddress().toString(); //获取本机ip
            String hostName=addr.getHostName().toString(); //获取本机计算机名称
//            System.out.println("本机IP："+ip+"\n本机名称:"+hostName);
            Properties props=System.getProperties();
//            System.out.println("操作系统的名称："+props.getProperty("os.name"));
//            System.out.println("操作系统的版本："+props.getProperty("os.version"));
            return "本机IP："+ip+"\n本机名称:"+hostName+"操作系统的名称："+props.getProperty("os.name")+"操作系统的版本："+props.getProperty("os.version");
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
