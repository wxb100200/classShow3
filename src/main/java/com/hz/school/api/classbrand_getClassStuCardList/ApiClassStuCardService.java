package com.hz.school.api.classbrand_getClassStuCardList;

import com.hz.school.model.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/18.
 */
public class ApiClassStuCardService {
    public static ApiClassCardInfo generateData(ClassInfo data){
        ApiClassCardInfo api=new ApiClassCardInfo();
        api.setStuInfo(generateStuInfoList(data.getStudents()));
        api.setTeacherInfo(generateTeacherInfoList(data.getTeachers()));
        return api;
    }
    private static List<ApiCardInfo> generateStuInfoList(List<Student> dataList){
        List<ApiCardInfo> apiList=new ArrayList<ApiCardInfo>();
        for(Student data:dataList){
            ApiCardInfo api=new ApiCardInfo();
            Card card=data.getCard();
            if(card!=null){
                api.setBalance(card.getBalance().toString());
                api.setCard(card.getCardNo());
            }
            api.setStuid(data.getId());
            api.setStuname(data.getName());
            apiList.add(api);
        }
        return apiList;
    }
    private static List<ApiTeacher> generateTeacherInfoList(List<Teacher> dataList){
        List<ApiTeacher> apiList=new ArrayList<ApiTeacher>();
        for(Teacher data:dataList){
            ApiTeacher api=new ApiTeacher();
            Card card=data.getCard();
            if(card!=null){
                api.setBalance(card.getBalance().toString());
                api.setCard(card.getCardNo());
            }
            api.setIconpath(data.getIconpath());
            api.setTeacherid(data.getId());
            api.setTeacherName(data.getName());
            apiList.add(api);
        }
        return apiList;
    }
}
