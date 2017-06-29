package com.hz.school.api.classbrand_getClassStuAttendanceList;

import com.hz.school.model.ClassInfo;
import com.hz.school.model.Student;
import com.hz.school.model.StudentAttendance;
import com.hz.school.model.StudentAttendanceDetail;
import com.hz.school.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */
public class ApiStuAttendanceService {
    public static List<ApiStudentAttendance> generateList(List<StudentAttendance> attList){
        List<ApiStudentAttendance> apiList=new ArrayList<ApiStudentAttendance>();
        for(StudentAttendance att:attList){
            ApiStudentAttendance api=new ApiStudentAttendance();
            Student student=att.getStudent();
            api.setStuid(student.getId().toString());
            ClassInfo classInfo=student.getClassInfo();
            api.setClassid(classInfo.getId().toString());
            api.setAttCount(att.getAttCount());
            api.setAttLateCount(att.getAttLateCount());
            api.setAttTardyCount(att.getAttTardyCount());
            api.setAttList(generateAttList(att.getAttDetailList()));
            apiList.add(api);
        }
        return apiList;
    }
    private static List<ApiStudentAtt> generateAttList(List<StudentAttendanceDetail> attList){
        List<ApiStudentAtt> apiList=new ArrayList<ApiStudentAtt>();
        for(StudentAttendanceDetail detail:attList){
            ApiStudentAtt api=new ApiStudentAtt();
            Long attTime=detail.getAttTime();
            api.setAttTime(DateUtil.formatDateTime(attTime));
            api.setParentWord(detail.getParentWord());
            apiList.add(api);
        }
        return apiList;
    }
}
