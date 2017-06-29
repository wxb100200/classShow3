package com.hz.school.api.classbrand_getClassSchoolMsgList;

import com.hz.school.model.ClassInfo;
import com.hz.school.model.Message;
import com.hz.school.model.MessageSchool;
import com.hz.school.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/19.
 */
public class ApiClassSchoolMsgService {
    public static List<ApiClassSchoolMsg> generateList(List<MessageSchool> dataList){
        List<ApiClassSchoolMsg> apiList=new ArrayList<ApiClassSchoolMsg>();
        for(MessageSchool data:dataList){
            ApiClassSchoolMsg api=new ApiClassSchoolMsg();
            api.setMsgid(data.getId());
            api.setTitle(data.getTitle());
            Message message=data.getMessage();
            api.setContenttype(message.getContentType());
            api.setContent(message.getContent());
            ClassInfo classInfo=data.getClassInfo();
            api.setClassid(classInfo.getId());
            Long publishDate=message.getPublishdate();
            api.setPublishdate(DateUtil.formatDateTime(publishDate));
            apiList.add(api);
        }
        return apiList;
    }
}
