package com.hz.school.synchrony;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avaje.ebean.Ebean;
import com.hz.school.model.ClassInfo;
import com.hz.school.model.CourseInfo;
import com.hz.school.model.Teacher;
import com.hz.school.model.TotalCourse;
import com.hz.school.util.*;

import java.io.IOException;
import java.util.*;

/**
 * 教师：选修课学生信息之学生列表查询
 * 获取总行政课程表数据
 * refId表示：学期-第几周-班级id-星期几-第几节
 */
public class DynamicWeekCourse {
    private static Logger log= Logger.getLogger(DynamicWeekCourse.class);
    private static Map<String,TotalCourse> totalCourseMap= (Map<String,TotalCourse>)EbeanUtil.find(TotalCourse.class).where().setMapKey("refId").findMap();
    private static Map<String,ClassInfo> classInfoMap= (Map<String,ClassInfo>) EbeanUtil.find(ClassInfo.class).where().setMapKey("className").findMap();
    private static Map<String,CourseInfo> courseInfoMap=(Map<String,CourseInfo>) EbeanUtil.find(CourseInfo.class).where().setMapKey("courseName").findMap();
    private static Map<String,Teacher> teacherMap=(Map<String,Teacher>) EbeanUtil.find(Teacher.class).where().setMapKey("name").findMap();
    private static String url="http://183.129.175.35:6690/xjzx/clientNcourse/clientClassAddressCourseAction!getDynamicWeekCourse";
    public static void main(String[] args){
        requestUrl();
    }
    public static void requestUrl(){
        try {
            String result= HttpUtil.post(url,null);
            log.info(result);
            String encode=StringUtil.getEncoding(result);
            log.info("---------->>>>>>>>"+encode);
            parseData(result);
        } catch (IOException e) {
            e.printStackTrace();
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
        TotalCourse temp=new TotalCourse();
        temp.setTermName(termName);
        temp.setNumWeek(numWeek);
        temp.setWeekInfo(monday+"-"+sunday);
        String refId=termNum+"-"+numWeek+"-";
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
        String classId=className.replaceAll("\\D+", "");
        String refId=ref+classId+"-";
        temp.setClassInfo(classInfo);
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

    private static TotalCourse parseOneCourse(String data,TotalCourse temp,String refId){
        JSONObject jsonObject=JSONObject.parseObject(data);
        String classSection=jsonObject.getString("classSection");
        String courseName=jsonObject.getString("ncourseName");
        String section=jsonObject.getString("section");//节次1-12
        String teacherName=jsonObject.getString("tchName");
        String week=jsonObject.getString("week");//星期1-7

        temp.setRefId(refId+week+"-"+section);
        temp.setWeekday(Integer.parseInt(week));
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
        totalCourse.setNumWeek(temp.getNumWeek());
        totalCourse.setWeekInfo(temp.getWeekInfo());
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
}
