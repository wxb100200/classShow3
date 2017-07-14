package com.hz.school.synchrony;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avaje.ebean.Ebean;
import com.hz.school.model.*;
import com.hz.school.util.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 教师：选修课学生信息之学生列表查询
 * 获取总课程表数据包括总行政课表和走班课表
 * refId表示：学期-第几周-班级id-星期几-第几节
 * refId表示：学期id-教室id-日期-星期几-第几节
 */
public class DynamicWeekCourse {
    private static Logger log= Logger.getLogger(DynamicWeekCourse.class);
    private static Map<String,TotalCourse> totalCourseMap= null;
    private static Map<String,TermInfo> termInfoMap=( Map<String,TermInfo>)EbeanUtil.find(TermInfo.class).where().select("id,termName").setMapKey("termName").findMap();
    private static Map<String,ClassInfo> classInfoMap= (Map<String,ClassInfo>) EbeanUtil.find(ClassInfo.class).where().select("id,className").setMapKey("className").findMap();
    private static Map<String,CourseInfo> courseInfoMap=(Map<String,CourseInfo>) EbeanUtil.find(CourseInfo.class).where().setMapKey("courseName").findMap();
    private static Map<String,Teacher> teacherMap=(Map<String,Teacher>) EbeanUtil.find(Teacher.class).where().select("id,name").setMapKey("name").findMap();
    private static String url="http://183.129.175.35:6690/xjzx/clientNcourse/clientClassAddressCourseAction!getDynamicWeekCourse";
    public static void main(String[] args){
        requestUrl();
    }
    public static void requestUrl(){
        totalCourseMap= (Map<String,TotalCourse>)EbeanUtil.find(TotalCourse.class).where().setMapKey("refId").findMap();
        try {
            String result= HttpUtil.post(url,null);
            log.info(result);
            log.info("---->>>>totalCourseMapSize:"+totalCourseMap.size());
            log.info("---->>>>classInfoMapSize:"+classInfoMap.size());
            log.info("---->>>>courseInfoMapSize:"+courseInfoMap.size());
            parseData(result);
        } catch (IOException e) {
            log.error("DynamicWeekCourse requestUrl error",e);
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
        String termName=jsonObject.getString("termName");//学期名
        String studySectionName=jsonObject.getString("studySectionName");
        log.info("------>>>>t:"+t+",monday:"+monday+",sunday:"+sunday+",termName"+termName+",studySectionName"+studySectionName);
        TermInfo termInfo=termInfoMap.get(termName);
        if(termInfo==null){
            log.error("--->>>>在学期表中没有找到对应的学期名  termName:"+termName);
            return;
        }
        TotalCourse temp=new TotalCourse();
        temp.setTermName(termName);
        temp.setWeekInfo(monday+"-"+sunday);
        String refId=formatNum(termInfo.getId());
        JSONArray jsonArray=jsonObject.getJSONArray("classCourse");
        for (Object obj : jsonArray) {
            if (obj == null) continue;
            String str = obj.toString();
            parseOneClass(str,temp,refId);
        }
    }
    private static void parseOneClass(String data,TotalCourse temp,String ref){
        JSONObject jsonObject=JSONObject.parseObject(data);
        String className=jsonObject.getString("className");
        ClassInfo classInfo=classInfoMap.get(className);
        if(classInfo==null){
            log.error("--->>>在班级信息表中没有找到班级名 className:"+className);
            return;
        }
        temp.setClassInfo(classInfo);
        String refId=ref+formatNum(classInfo.getId());
        JSONArray jsonArray=jsonObject.getJSONArray("courseWeek");
        List<TotalCourse> totalCourses=new ArrayList<TotalCourse>();
        for (Object obj : jsonArray) {
            if(obj==null)continue;
            String str=obj.toString();
            TotalCourse totalCourse=parseOneCourse(str,temp,refId);
            if(totalCourse!=null){
                totalCourses.add(totalCourse);
            }
        }
        try {
            Ebean.beginTransaction();
            Ebean.save(totalCourses);
            Ebean.commitTransaction();
        }catch (Exception e){
            Ebean.rollbackTransaction();
        }finally {
            Ebean.endTransaction();
        }
    }

    private static TotalCourse parseOneCourse(String data,TotalCourse temp,String ref){
        JSONObject jsonObject=JSONObject.parseObject(data);
        String classSection=jsonObject.getString("classSection");
        String courseName=jsonObject.getString("ncourseName");
        String section=jsonObject.getString("section");//节次1-12
        String teacherName=jsonObject.getString("tchName").trim();
        String week=jsonObject.getString("week");//星期1-7
        String type=jsonObject.getString("choosCourse");//星期1-7
        String day=jsonObject.getString("day");

        Long date=DateUtil.parseDate(day,"yyyy-MM-dd").getTime();
        String refId=ref+date+formatNum(Long.parseLong(week))+formatNum(Long.parseLong(section));
        temp.setRefId(refId);
        temp.setDate(date);
        temp.setWeekday(Integer.parseInt(week));
        if(type.equals("1")){
            temp.setType(1);
        }else{
            temp.setType(0);
        }
        int classNum=Integer.parseInt(section);
        temp.setClassNum(classNum);
        if(classNum<6){
            temp.setTimeInterval(1);
        }else{
            temp.setTimeInterval(2);
        }
        temp.setCourseName(courseName);
        temp.setCourse(courseInfoMap.get(courseName));
        temp.setTeacherName(teacherName);
        temp.setTeacher(teacherMap.get(teacherName));
        return generateTotalCourse(temp);
    }
    private static TotalCourse generateTotalCourse(TotalCourse temp){
        String refId=temp.getRefId();
        log.info("----->>>>>refId:"+refId);
        if(totalCourseMap.containsKey(refId)){
            TotalCourse totalCourse=totalCourseMap.get(refId);
            String oldCourseName=temp.getCourseName();
            String oldTeacherName=temp.getTeacherName();
            if(!oldCourseName.equals(temp.getCourseName())||!oldTeacherName.equals(temp.getTeacherName())){
                totalCourse.setCourseName(temp.getCourseName());
                totalCourse.setCourse(temp.getCourse());
                totalCourse.setTeacherName(temp.getTeacherName());
                totalCourse.setTeacher(temp.getTeacher());
                return totalCourse;
            }else{
                return null;
            }
        }
        TotalCourse totalCourse=new TotalCourse();
        totalCourse.setRefId(temp.getRefId());
        totalCourse.setTermName(temp.getTermName());
        totalCourse.setClassNum(temp.getClassNum());
        totalCourse.setClassInfo(temp.getClassInfo());
        totalCourse.setCourseName(temp.getCourseName());
        totalCourse.setCourse(temp.getCourse());
        totalCourse.setTeacherName(temp.getTeacherName());
        totalCourse.setTeacher(temp.getTeacher());
        totalCourse.setTimeInterval(temp.getTimeInterval());
        totalCourse.setWeekday(temp.getWeekday());
        totalCourse.setDate(temp.getDate());
        totalCourse.setWeekInfo(temp.getWeekInfo());
        totalCourse.setType(temp.getType());
        return totalCourse;

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
