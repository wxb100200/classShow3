package com.hz.school.api.classbrand_classCircleParise;

import com.hz.school.model.ClassRing;
import com.hz.school.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/18.
 */
public class ApiClassCirclePariseService {
    public static ApiClassCircleParise generateData(ClassRing data){
        ApiClassCircleParise api=new ApiClassCircleParise();
        api.setCircleid(data.getId());
        return api;
    }
}
