package com.hz.school.synchrony;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hz.school.util.HttpUtil;
import com.hz.school.util.Logger;

import java.io.IOException;

/**
 * 教师：选修课学生信息之学生列表查询
 * 获取总行政课程表数据
 */
public class getDynamicWeekCourse {
    private static Logger log= Logger.getLogger(getDynamicWeekCourse.class);
    private static String url="http://183.129.175.35:6690/xjzx/clientNcourse/clientClassAddressCourseAction!getDynamicWeekCourse";
    public static void main(String[] args){
        requestUrl();
    }
    public static void requestUrl(){
        try {
            String result= HttpUtil.post(url,null);
            log.info(result);
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
        String terName=jsonObject.getString("terName");
        String studySectionName=jsonObject.getString("studySectionName");
        log.info("------>>>>t:"+t+",monday:"+monday+",sunday:"+sunday+",terName"+terName+",studySectionName"+studySectionName);
        String refId=terName+"="+monday+"="+sunday+"=";
        JSONArray jsonArray=jsonObject.getJSONArray("classCourse");
        for(int i=0;i<jsonArray.size();i++){
            Object obj=jsonArray.get(i);
            if(obj==null)continue;
            String str=obj.toString();
            log.info("--->>>str:"+str);
            parseOneClass(str,refId);
        }
    }
    private static void parseOneClass(String data,String refId){
        JSONObject jsonObject=JSONObject.parseObject(data);
        String className=jsonObject.getString("className");
        JSONArray jsonArray=jsonObject.getJSONArray("courseWeek");
        for(int i=0;i<jsonArray.size();i++){
            Object obj=jsonArray.get(i);
            log.info("--->>>obj:"+obj.toString());
            parseOneCourse(obj.toString());
        }
    }
    private static void parseOneCourse(String data){
        JSONObject jsonObject=JSONObject.parseObject(data);
        String classSection=jsonObject.getString("classSection");
        String ncourseName=jsonObject.getString("ncourseName");
        String section=jsonObject.getString("section");
        String tchName=jsonObject.getString("tchName");
        String week=jsonObject.getString("week");
    }
}
