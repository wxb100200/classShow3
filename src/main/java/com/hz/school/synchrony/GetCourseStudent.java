package com.hz.school.synchrony;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avaje.ebean.Ebean;
import com.hz.school.model.ClassRoom;
import com.hz.school.model.CourseStudent;
import com.hz.school.model.Student;
import com.hz.school.util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 获取课表数据对应的学生
 * refId表示：学期-第几周-教室id-星期几-第几节
 */
public class GetCourseStudent {
    private static Logger log= Logger.getLogger(GetCourseStudent.class);
    private static String url="http://183.129.175.35:6690/xjzx/clientNcourse/clientClassAddressCourseAction!getCourseStudent";
    private static Map<String,CourseStudent> courseStudentMap=null;
    private static Map<String,ClassRoom> classRoomMap=(Map<String,ClassRoom>)EbeanUtil.find(ClassRoom.class).where().select("id,classRoomName").setMapKey("classRoomName").findMap();
    private static Map<String,Student> studentMap=(Map<String,Student>)EbeanUtil.find(Student.class).where().setMapKey("stuNo").findMap();
    public static void main(String[] args){
        requestUrl();
    }
    public static void requestUrl(){
       courseStudentMap= (Map<String,CourseStudent>)EbeanUtil.find(CourseStudent.class).where().setMapKey("refId").findMap();
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
        int numWeek=judgeWeekNum(monday);//第几周
        String termNum=termName.substring(termName.indexOf("(")+1,termName.indexOf(")"));//第几学期
        CourseStudent temp=new CourseStudent();
        temp.setTermName(termName);
        String refId=termNum+"-"+numWeek+"-";
        JSONArray jsonArray=jsonObject.getJSONArray("classCourse");
        for(Object obj:jsonArray){
            if (obj == null) continue;
            String str = obj.toString();
            log.info("---->>>"+str);
            parseOneClass(str,temp,refId);
        }
    }
    private static void parseOneClass(String data,CourseStudent temp,String ref){
        JSONObject jsonObject=JSONObject.parseObject(data);
        String classRoomName=jsonObject.getString("className");

        ClassRoom classRoom=classRoomMap.get(classRoomName);
        temp.setClassRoomName(classRoomName);
        temp.setClassRoom(classRoom);
        String refId=ref+classRoom.getId()+"-";
        JSONArray jsonArray=jsonObject.getJSONArray("courseWeek");
        for(Object obj:jsonArray){
            if(obj==null)continue;
            String str=obj.toString();
            log.info("--->>>>>:"+str);
            parseOneDay(str,temp,refId);
        }
    }
    private static void parseOneDay(String data,CourseStudent temp,String ref){
        JSONObject jsonObject=JSONObject.parseObject(data);
        String classSection=jsonObject.getString("classSection");
        String section=jsonObject.getString("section");//第几节
        String week=jsonObject.getString("week");//星期几
        String day=jsonObject.getString("day");//日期

        String refId=ref+week+"-"+section+"-";

        temp.setDate(DateUtil.parseDate(day,"yyyy-MM-dd").getTime());
        temp.setWeekday(Integer.parseInt(week));
        temp.setClassNum(Integer.parseInt(section));//第几节

        JSONArray jsonArray=jsonObject.getJSONArray("stuArray");
        List<CourseStudent> courseStudentList=new ArrayList<CourseStudent>();
        for(Object obj:jsonArray){
            if(obj==null)continue;
            String str=obj.toString();
            log.info("---->>>>oneStudent:"+str);
            CourseStudent courseStudent=parseOneStudent(str,temp,refId);
            if(courseStudent!=null){
                courseStudentList.add(courseStudent);
            }
        }
        try {
            Ebean.beginTransaction();
            Ebean.save(courseStudentList);
            Ebean.commitTransaction();
        }catch (Exception e){
            Ebean.rollbackTransaction();
        }finally {
            Ebean.endTransaction();
        }
    }
    private static CourseStudent parseOneStudent(String data,CourseStudent temp,String ref){
        JSONObject jsonObject=JSONObject.parseObject(data);
        String stuNO=jsonObject.getString("stuNO");
        String stuNO1=jsonObject.getString("stuNO1");
        String stuName=jsonObject.getString("stuName");//学生姓名

        Student student=studentMap.get(stuNO);
        temp.setRefId(ref+stuNO);
        temp.setStuNo(stuNO);
        temp.setStuNo1(stuNO1);
        temp.setName(stuName);
        temp.setStudent(student);

        if(courseStudentMap.containsKey(temp.getRefId()))
            return null;
        CourseStudent courseStudent=new CourseStudent();
        courseStudent.setRefId(temp.getRefId());
        courseStudent.setTermName(temp.getTermName());
        courseStudent.setClassRoomName(temp.getClassRoomName());
        courseStudent.setClassRoom(temp.getClassRoom());
        courseStudent.setDate(temp.getDate());
        courseStudent.setWeekday(temp.getWeekday());
        courseStudent.setClassNum(temp.getClassNum());
        courseStudent.setName(temp.getName());
        courseStudent.setStuNo(temp.getStuNo());
        courseStudent.setStuNo1(temp.getStuNo1());
        courseStudent.setStudent(student);
        return courseStudent;


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
}
