package com.hz.school.synchrony;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.text.json.JsonContext;
import com.hz.school.util.HttpUtil;
import com.hz.school.util.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/23.
 */
public class SynClassInfo {
    private static Logger log= Logger.getLogger(SynClassInfo.class);
    private static JsonContext jsonContext = Ebean.createJsonContext();
    /**
     * http://zjgbanpai.hzxjhs.com/weixt/api/classbrand_getBzrClassid?apiparams={"params":{"classroomid":"","accessTlisten":"ccesstlisten000001","SN":"587DA6830CE47FF2632D1EDFA0E302D4","card":"1967948798"}}
     */
    public static void main(String[] args){
        classbrand_getBzrClassid();

    }
    public static void classbrand_getBzrClassid(){
        String url="http://zjgbanpai.hzxjhs.com/weixt/api/classbrand_getBzrClassid";
        Map<String,String> map=new HashMap<String, String>();
        map.put("classroomid","");
        map.put("accessTlisten","ccesstlisten000001");
        map.put("SN","587DA6830CE47FF2632D1EDFA0E302D4");
        map.put("card","1967948798");
        String str=parseMap(map);
        Map<String,String> paramMap=new HashMap<String, String>();
        paramMap.put("apiparams","{\"params\":"+str+"}");
        try {
            String result= HttpUtil.post(url,paramMap);
            log.info("----------------->>>>>接口返回结果："+result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String parseMap(Map<String,String> map){
        StringBuilder result= new StringBuilder();
        for(String key:map.keySet()){
            result.append("\"").append(key).append("\"").append(":").append("\"").append(map.get(key)).append("\"").append(",");
        }
        if(result.length()>0){
            result.deleteCharAt(result.length()-1);
        }
        result.insert(0,"{");
        result.append("}");
        return result.toString();
    }
}
