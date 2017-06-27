package com.hz.school.api.classbrand_getClassAttendanceInfo;

import com.hz.school.model.ClassAttendanceTime;
import com.hz.school.model.ClassAttendance;
import com.hz.school.model.ClassInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */
public class ApiClassAttendanceService {
    public static ApiClassAttendance generateData(ClassAttendance att){
        ApiClassAttendance api=new ApiClassAttendance();
        ClassInfo classInfo=att.getClassInfo();
        api.setClassid(classInfo.getId().toString());
        api.setAttCount(att.getAttCount());
        api.setAttLateCount(att.getAttLateCount());
        api.setAttTardyCount(att.getAttTardyCount());
        api.setAttLategradeRanking(att.getAttLategradeRanking());
        api.setAttLateSchoolRanking(att.getAttLateSchoolRanking());
        api.setAttTardyGradeRanking(att.getAttTardyGradeRanking());
        api.setAttTardySchoolRanking(att.getAttTardySchoolRanking());
        api.setAttTimeSet(generateAttTimeList(att.getAttTimeList()));
        return api;
    }
    private static List<ApiClassAttList> generateAttTimeList(List<ClassAttendanceTime> attList){
        List<ApiClassAttList> apiList=new ArrayList<ApiClassAttList>();
        for(ClassAttendanceTime att:attList){
            ApiClassAttList api=new ApiClassAttList();
            api.setConfigcode(att.getConfigcode());
            api.setConfigvalue(att.getConfigvalue());
            api.setConfigname(att.getConfigname());
            apiList.add(api);
        }
        return apiList;
    }
}
