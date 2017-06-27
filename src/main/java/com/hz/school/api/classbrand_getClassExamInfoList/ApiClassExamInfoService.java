package com.hz.school.api.classbrand_getClassExamInfoList;

import com.hz.school.model.ExamCourse;
import com.hz.school.model.ExamInfo;
import com.hz.school.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/18.
 */
public class ApiClassExamInfoService {
    public static List<ApiExamInfo> generateList(List<ExamInfo> examInfoList){
        List<ApiExamInfo> apiList=new ArrayList<ApiExamInfo>();
        for(ExamInfo examInfo:examInfoList){
            ApiExamInfo api=new ApiExamInfo();
            api.setExamTitle(examInfo.getExamTitle());
            api.setExamid(examInfo.getId());
            Long examTime=examInfo.getExamTime();
            api.setExamTime(DateUtil.formatDate(examTime));
            api.setClassid(examInfo.getClassInfo().getId());
            api.setCourseList(generateCourseList(examInfo.getCourseList()));
            apiList.add(api);
        }
        return apiList;
    }
    private static List<ApiExamCourse> generateCourseList(List<ExamCourse> examCourseList){
        List<ApiExamCourse> apiLst=new ArrayList<ApiExamCourse>();
        for(ExamCourse examCourse:examCourseList){
            ApiExamCourse api=new ApiExamCourse();
            api.setCourse(examCourse.getCourse());
            apiLst.add(api);
        }
        return apiLst;
    }
}
