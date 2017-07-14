package com.hz.school.synchrony;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avaje.ebean.Ebean;
import com.hz.school.model.*;
import com.hz.school.util.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 获取课表数据对应的学生
 * refId表示：学期id(4)-教室id(4)-日期(13)-星期几(4)-第几节(4)
 */
public class GetCourseStudent {
    private static Logger log= Logger.getLogger(GetCourseStudent.class);
    private static String url="http://183.129.175.35:6690/xjzx/clientNcourse/clientClassAddressCourseAction!getCourseStudent";
    private static Map<String,GoClassCourse> goClassCourseMap=null;
    private static Map<String,TermInfo> termInfoMap=( Map<String,TermInfo>)EbeanUtil.find(TermInfo.class).where().select("id,termName").setMapKey("termName").findMap();
    private static Map<String,ClassRoom> classRoomMap=(Map<String,ClassRoom>) EbeanUtil.find(ClassRoom.class).where().select("id,classRoomName").setMapKey("classRoomName").findMap();
    private static Map<String,Student> studentMap=(Map<String,Student>)EbeanUtil.find(Student.class).where().setMapKey("stuNo").findMap();
    public static void main(String[] args){
        requestUrl();
    }
    public static void requestUrl(){
        goClassCourseMap= (Map<String,GoClassCourse>)EbeanUtil.find(GoClassCourse.class).where().setMapKey("refId").findMap();
        try {
            String result= HttpUtil.post(url,null);
            parseData(result);
        } catch (IOException e) {
            log.error("GetCourseStudent requestUrl error ",e);
        }
    }
    private static void parseData(String data){
        JSONObject jsonObject=JSONObject.parseObject(data);
        String t=jsonObject.getString("T");
        if(t.equals("F")){
            log.error("---->>>getDynamicWeekCourse parseData url请求获取数据失败");
            return;
        }
        String monday=jsonObject.getString("monday");
        String sunday=jsonObject.getString("sunday");
        String termName=jsonObject.getString("termName");
        String studySectionName=jsonObject.getString("studySectionName");
        log.info("------>>>>t:"+t+",monday:"+monday+",sunday:"+sunday+",termName"+termName+",studySectionName"+studySectionName);
        TermInfo termInfo=termInfoMap.get(termName);
        if(termInfo==null){
            log.error("--->>>>在学期表中没有找到对应的学期名  termName:"+termName);
            return;
        }
        GoClassCourse temp=new GoClassCourse();
        temp.setTermName(termName);
        String refId=formatNum(termInfo.getId());
        JSONArray jsonArray=jsonObject.getJSONArray("classCourse");
        for(Object obj:jsonArray){
            if (obj == null) continue;
            String str = obj.toString();
            log.info("---->>>"+str);
            parseOneClass(str,temp,refId);
        }
    }
    private static void parseOneClass(String data,GoClassCourse temp,String ref){
        JSONObject jsonObject=JSONObject.parseObject(data);
        String classRoomName=jsonObject.getString("className");
        ClassRoom classRoom=classRoomMap.get(classRoomName);
        if(classRoomName.contains("教学楼")){
            classRoom=classRoomMap.get(classRoomName.replace("教学楼","博识楼"));
        }
        if(classRoom==null){
            log.error("---->>>>>在教室信息表中没有找到对应的教室名称 classRoomName:"+classRoomName);
            return;
        }
        temp.setClassRoomName(classRoomName);
        temp.setClassRoom(classRoom);
        String refId=ref+formatNum(classRoom.getId());
        JSONArray jsonArray=jsonObject.getJSONArray("courseWeek");
        for(Object obj:jsonArray){
            if(obj==null)continue;
            String str=obj.toString();
            log.info("--->>>>>:"+str);
            parseOneDay(str,temp,refId);
        }
    }
    private static void parseOneDay(String data,GoClassCourse temp,String ref) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        String classSection = jsonObject.getString("classSection");
        String section = jsonObject.getString("section");//第几节
        String week = jsonObject.getString("week");//星期几
        String day = jsonObject.getString("day");//日期
        Long date=DateUtil.parseDate(day,"yyyy-MM-dd").getTime();
        String refId = ref +date+ formatNum(Long.parseLong(week))+formatNum(Long.parseLong(section));
        temp.setRefId(refId);
        temp.setDate(date);
        temp.setWeekday(Integer.parseInt(week));
        temp.setClassNum(Integer.parseInt(section));//第几节

        List<BaseEntity> entityList=new ArrayList<BaseEntity>();
        GoClassCourse goClassCourse=generateGoClassCourse(temp,entityList);

        JSONArray jsonArray=jsonObject.getJSONArray("stuArray");
        for(Object obj:jsonArray){
            if(obj==null)continue;
            String str=obj.toString();
            log.info("---->>>>oneStudent:"+str);
            GoClassStudent goClassStudent=generateGoClassStudent(str,goClassCourse);
            if(goClassStudent!=null) {
                entityList.add(goClassStudent);
            }
        }
        try {
            Ebean.beginTransaction();
            Ebean.save(entityList);
            Ebean.commitTransaction();
        }catch (Exception e){
            Ebean.rollbackTransaction();
        }finally {
            Ebean.endTransaction();
        }
    }
    private static GoClassStudent generateGoClassStudent(String data,GoClassCourse goClassCourse){
        JSONObject jsonObject=JSONObject.parseObject(data);
        String stuNO=jsonObject.getString("stuNO");
        String stuNO1=jsonObject.getString("stuNO1");
        String stuName=jsonObject.getString("stuName");//学生姓名

        Map<String,GoClassStudent> goClassStudentMap=(Map<String,GoClassStudent>)EbeanUtil.find(GoClassStudent.class).where()
                .eq("goClassCourse.id",goClassCourse.getId()).setMapKey("stuNo").findMap();
        if(goClassStudentMap.containsKey(stuNO)){
            return null;
        }
        GoClassStudent goClassStudent=new GoClassStudent();
        goClassStudent.setGoClassCourse(goClassCourse);
        goClassStudent.setStudent(studentMap.get(stuNO));
        goClassStudent.setName(stuName);
        goClassStudent.setStuNo(stuNO);
        goClassStudent.setStuNo1(stuNO1);
        return goClassStudent;
    }
    private static GoClassCourse generateGoClassCourse(GoClassCourse temp,List<BaseEntity> entityList){
        String refId=temp.getRefId();
        GoClassCourse goClassCourse=null;
        if(goClassCourseMap.containsKey(refId)){
            goClassCourse=goClassCourseMap.get(refId);
        }else{
            goClassCourse=new GoClassCourse();
            goClassCourse.setRefId(temp.getRefId());
            goClassCourse.setTermName(temp.getTermName());
            goClassCourse.setClassRoomName(temp.getClassRoomName());
            goClassCourse.setClassRoom(temp.getClassRoom());
            goClassCourse.setDate(temp.getDate());
            goClassCourse.setWeekday(temp.getWeekday());
            goClassCourse.setClassNum(temp.getClassNum());

            entityList.add(goClassCourse);
        }
        return goClassCourse;
    }
    private static Integer judgeWeekNum(String dateStr){
        Date date= DateUtil.parseDate(dateStr,"yyyy-MM-dd");
        int num=DateUtil.weekOfYear(date);
        String oneTermStartTime= SSMConfig.getProperty("oneTermStartTime");
        String twoTermStartTime= SSMConfig.getProperty("twoTermStartTime");
        Date oneDate=DateUtil.parseDate(oneTermStartTime,"yyyy-MM-dd");
        Date twoDate=DateUtil.parseDate(twoTermStartTime,"yyyy-MM-dd");
        int oneNum=DateUtil.weekOfYear(oneDate);
        int twoNum=DateUtil.weekOfYear(twoDate);
        if(num<=twoNum){
            return num-oneNum+1;
        }else {
            return num-twoNum+1;
        }
    }
    private static String formatNum(Long num){
        DecimalFormat df=new DecimalFormat("0000");
        return df.format(num);
    }
}
