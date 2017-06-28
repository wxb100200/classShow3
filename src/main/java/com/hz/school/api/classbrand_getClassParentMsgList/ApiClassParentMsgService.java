package com.hz.school.api.classbrand_getClassParentMsgList;

import com.hz.school.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/18.
 */
public class ApiClassParentMsgService {
    public static List<ApiClassParentMsg> generateList(List<MessageParent> dataList){
        List<ApiClassParentMsg> apiList=new ArrayList<ApiClassParentMsg>();
        for(MessageParent data:dataList){
            ApiClassParentMsg api=new ApiClassParentMsg();
            Student student=data.getStudent();
            ClassInfo classInfo=student.getClassInfo();
            api.setClassid(classInfo.getId());
            Message message=data.getMessage();
            api.setContent(message.getContent());
            api.setContenttype(message.getContentType());
            api.setMsgid(data.getId());
            api.setStuid(student.getId());
            api.setPhotoList(generatePhotoList(data.getPhotoList()));
            apiList.add(api);
        }
        return apiList;
    }
    private static List<ApiMsgPhoto> generatePhotoList(List<Photo> dataList){
        List<ApiMsgPhoto> apiList=new ArrayList<ApiMsgPhoto>();
        for(Photo data:dataList){
            ApiMsgPhoto api=new ApiMsgPhoto();
            api.setPath(data.getUrl());
            apiList.add(api);
        }
        return apiList;
    }
}
