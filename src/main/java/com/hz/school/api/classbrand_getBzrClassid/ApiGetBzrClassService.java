package com.hz.school.api.classbrand_getBzrClassid;

import com.hz.school.model.*;
import com.hz.school.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */
public class ApiGetBzrClassService {
    private static Logger log= Logger.getLogger(ApiGetBzrClassService.class);
    public static ApiClassInfo generateData(ClassInfo data){
        if(data==null){
            log.error("----->>>>>>>classInfo data is null");
            return null;
        }
        ApiClassInfo apiData=new ApiClassInfo();
        apiData.setCampusid(data.getCampusid());
        apiData.setClassInfo(data.getClassInfo());
        apiData.setClassName(data.getClassName());
        ClassRoom classRoom=data.getClassRoom();
        apiData.setClassRoomName(classRoom.getClassRoomName());
        apiData.setClassRoomid(classRoom.getId().toString());
        apiData.setClassTimeList(generateClassTimeList(data.getClassTimeList()));
        apiData.setClassid(data.getId().toString());
        apiData.setCommittee(generateCommittee(data.getCommitteeList()));
        Teacher headmaster=data.getHeadmaster();
        apiData.setHeadmasterId(headmaster.getId());
        apiData.setHeadmasterName(headmaster.getName());
        apiData.setXqbm(generateXqbm(data.getXqbm()));
        return apiData;
    }
    private static List<ApiClassTime> generateClassTimeList(List<ClassTime> classTimeList){
        List<ApiClassTime> apiList=new ArrayList<ApiClassTime>();
        for(ClassTime classTime:classTimeList){
            ApiClassTime api=new ApiClassTime();
            api.setClassNum(classTime.getClassNum());
            api.setStartTime(classTime.getStartTime());
            api.setEmdTime(classTime.getEndTime());
            apiList.add(api);
        }
        return apiList;
    }
    private static List<ApiCommittee> generateCommittee(List<Committee> committees){
        List<ApiCommittee> apiList=new ArrayList<ApiCommittee>();
        for(Committee committee:committees){
            ApiCommittee api=new ApiCommittee();
            api.setCommitteeCode(committee.getCommitteeCode());
            api.setCommitteeName(committee.getCommitteeName());
            Student student=committee.getStudent();
            api.setStuid(student.getId());
            api.setStuName(student.getName());
            apiList.add(api);
        }
        return apiList;
    }
    private static ApiXqbm generateXqbm(Xqbm xqbm){
        ApiXqbm api=new ApiXqbm();
        api.setId(xqbm.getId());
        api.setCampusid(xqbm.getCampusid());
        api.setCampusid_ch(xqbm.getCampusid_ch());
        api.setCurrentxq(xqbm.getCurrentxq());
        api.setOrgcode(xqbm.getOrgcode());
        api.setRemark(xqbm.getRemark());
        api.setWeekbegin(xqbm.getWeekbegin());
        api.setWeeknum(xqbm.getWeeknum());
        api.setXnbm(xqbm.getXnbm());
        api.setXqbm(xqbm.getXqbm());
        api.setXqgb(xqbm.getXqgb());
        api.setXqmc(xqbm.getXqmc());
        return api;
    }
}
