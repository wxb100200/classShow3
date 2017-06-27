package com.hz.school.api.classbrand_getClassStuScoreList;

import com.hz.school.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/18.
 */
public class ApiClassStuScoreService {
    public static List<ApiClassStudentScore> generateList(List<StudentGrade> dataList){
        List<ApiClassStudentScore> apiList=new ArrayList<ApiClassStudentScore>();
        for(StudentGrade data:dataList){
            ApiClassStudentScore api=new ApiClassStudentScore();
            ExamInfo examInfo=data.getExamInfo();
            api.setExamTitle(examInfo.getExamTitle());
            api.setExamid(examInfo.getId());
            api.setRanking(data.getRanking());
            api.setTeacherComment(data.getTeacherComment());
            Student student=data.getStudent();
            api.setStuid(student.getId());
            api.setClassid(student.getClassInfo().getId());
            api.setScoreList(generateStudentScoreList(data.getScoreList()));
            apiList.add(api);
        }
        return apiList;
    }
    private static List<ApiStudentScore> generateStudentScoreList(List<StudentScore> dataList){
        List<ApiStudentScore> apiList=new ArrayList<ApiStudentScore>();
        for(StudentScore data:dataList){
            ApiStudentScore api=new ApiStudentScore();
            CourseInfo courseInfo=data.getCourseInfo();
            api.setCourseid(courseInfo.getId());
            api.setCourse(courseInfo.getCourseName());
            api.setScore(data.getScore().toString());
            apiList.add(api);
        }
        return apiList;
    }
}
