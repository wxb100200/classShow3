package com.hz.school.api.classbrand_getTakeClassStuList;


import com.hz.school.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */
public class ApiTakeClassStuService {
    public static List<ApiTakeClass> generateList(List<GoClassCourse> goClassCourseList){
        List<ApiTakeClass> apiTakeClassList =new ArrayList<ApiTakeClass>();
        for(GoClassCourse goClassCourse:goClassCourseList){
            ApiTakeClass apiTakeClass =new ApiTakeClass();
            ClassRoom classRoom=goClassCourse.getClassRoom();
            ClassInfo classInfo=classRoom.getClassInfo();
            apiTakeClass.setClassid(classInfo.getId());
            apiTakeClass.setPicpath("");
            apiTakeClass.setClassNum(goClassCourse.getClassNum());
            apiTakeClass.setWeekday(goClassCourse.getWeekday());
            List<ApiStudent> apiStudentList=generateStudentList(goClassCourse.getGoClassStudents());
            if(!apiStudentList.isEmpty()){
                apiTakeClass.setStudents(apiStudentList);
            }
            apiTakeClassList.add(apiTakeClass);
        }
        return apiTakeClassList;
    }
    private static List<ApiStudent> generateStudentList(List<GoClassStudent> goClassStudents){
        List<ApiStudent> apiStudentList=new ArrayList<ApiStudent>();
        for(GoClassStudent goClassStudent:goClassStudents){
            Student student=goClassStudent.getStudent();
            if(student==null)continue;
            ApiStudent apiStudent=new ApiStudent();
            Card card=student.getCard();
            if(card!=null){
                apiStudent.setCard(card.getCardNo());
            }
            apiStudent.setName(student.getName());
            apiStudent.setOrder(student.getOrder().toString());
            apiStudent.setPicpath(student.getPicpath());
            apiStudentList.add(apiStudent);
        }
        return apiStudentList;
    }
}
