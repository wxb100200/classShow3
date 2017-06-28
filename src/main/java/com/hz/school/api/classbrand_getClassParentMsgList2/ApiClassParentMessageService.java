package com.hz.school.api.classbrand_getClassParentMsgList2;

import com.hz.school.model.Message;
import com.hz.school.model.MessageParent;
import com.hz.school.model.Student;
import com.hz.school.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */
public class ApiClassParentMessageService {
    public static List<ApiClassParentMessage> generateList(List<MessageParent> dataList){
        List<ApiClassParentMessage> apiList=new ArrayList<ApiClassParentMessage>();
        for(MessageParent data:dataList){
            ApiClassParentMessage api=new ApiClassParentMessage();
            api.setMsgid(data.getId());
            Message message=data.getMessage();
            api.setContenttype(message.getContentType());
            api.setContent(message.getContent());
            Student student=data.getStudent();
            api.setClassid(student.getClassInfo().getId());
            Long publishDate=message.getPublishdate();
            api.setPublishdate(DateUtil.formatDateToSecond(publishDate));
            apiList.add(api);
        }
        return apiList;
    }
}
