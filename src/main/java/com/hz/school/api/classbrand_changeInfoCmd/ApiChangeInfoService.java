package com.hz.school.api.classbrand_changeInfoCmd;

import com.hz.school.model.ChangeInfo;
import com.hz.school.model.ClassInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */
public class ApiChangeInfoService {
    public static List<ApiChangeInfo> generateList(List<ChangeInfo> changeInfoList){
        List<ApiChangeInfo> apiList=new ArrayList<ApiChangeInfo>();
        for(ChangeInfo info:changeInfoList){
            ApiChangeInfo api=new ApiChangeInfo();
            api.setCampuscode(info.getCampuscode());
            ClassInfo classInfo=info.getClassInfo();
            api.setClassid(classInfo.getId().toString());
            api.setApiMethod(info.getApiMethod());
            apiList.add(api);
        }
        return apiList;
    }
}
