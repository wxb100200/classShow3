package com.hz.school.http.servlet;

import com.alibaba.fastjson.JSONObject;
import com.hz.school.api.ApiResult;
import com.hz.school.api.classbrand_getTakeClassStuList.ApiTakeClass;
import com.hz.school.api.classbrand_getTakeClassStuList.ApiTakeClassStuService;
import com.hz.school.http.GenericServlet;
import com.hz.school.http.Request;
import com.hz.school.http.Response;
import com.hz.school.model.GoClassCourse;
import com.hz.school.util.EbeanUtil;
import com.hz.school.util.Logger;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
public class GetTakeClassStuList extends GenericServlet {
    private static Logger log= Logger.getLogger(GetTakeClassStuList.class);
    public void doGet(Request request, Response response) {

    }

    public void doPost(Request request, Response response) {
        String apiparams=request.getParameter("apiparams");
        log.info("apiparams="+apiparams);
        JSONObject jApiparams= JSONObject.parseObject(apiparams);
        String params=jApiparams.getString("params");
        JSONObject jParams=JSONObject.parseObject(params);
        String classid=jParams.getString("classid");
        String accessToken=jParams.getString("accessToken");
        log.info("----->>>>>>getClassCourseList classid:"+classid+",accessToken:"+accessToken);
        List<GoClassCourse> goClassCourseList= EbeanUtil.find(GoClassCourse.class).where().eq("classRoom.classInfo.id",classid)
                .findList();
        List<ApiTakeClass> apiTakeClassList = ApiTakeClassStuService.generateList(goClassCourseList);
        String result= ApiResult.list("classbrand_getClassCourseList", apiTakeClassList).toJson();
        response.print(result);
        System.out.println(result);
    }
}
