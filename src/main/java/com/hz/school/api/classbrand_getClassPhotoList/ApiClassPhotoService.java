package com.hz.school.api.classbrand_getClassPhotoList;

import com.hz.school.model.ClassRing;
import com.hz.school.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/18.
 */
public class ApiClassPhotoService {
    public static List<ApiPhoto> generateList(List<ClassRing> dataList){
        List<ApiPhoto> apiList=new ArrayList<ApiPhoto>();
        for(ClassRing data:dataList){
            ApiPhoto api=new ApiPhoto();
            api.setCircleid(data.getId());
            api.setContent(data.getContent());
            api.setPariseCount(data.getPariseCount());
            api.setClassid(data.getClassInfo().getId());
            api.setPhotoList(generatePhotoList(data.getPhotos()));
            apiList.add(api);
        }
        return apiList;
    }
    private static List<ApiPhotoList> generatePhotoList(List<Photo> dataList){
        List<ApiPhotoList> apiList=new ArrayList<ApiPhotoList>();
        for(Photo data:dataList){
            ApiPhotoList api=new ApiPhotoList();
            api.setPhotoUrl(data.getUrl());
            apiList.add(api);
        }
        return apiList;
    }
}
