package com.hz.school.http.servlet;

import com.alibaba.fastjson.JSONObject;
import com.hz.school.api.ApiResult;
import com.hz.school.api.classbrand_getClassCoursLeist.ApiClassCourse;
import com.hz.school.api.classbrand_getClassCoursLeist.ApiClassCourseService;
import com.hz.school.http.GenericServlet;
import com.hz.school.http.Request;
import com.hz.school.http.Response;
import com.hz.school.model.TotalCourse;
import com.hz.school.util.DateUtil;
import com.hz.school.util.EbeanUtil;
import com.hz.school.util.Logger;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
public class GetClassCourseList extends GenericServlet {
    private static Logger log= Logger.getLogger(GetClassCourseList.class);
    public void doGet(Request request, Response response) {

    }

    public void doPost(Request request, Response response) {
        String apiparams=request.getParameter("apiparams");
        log.info("apiparams="+apiparams);
        JSONObject jApiparams= JSONObject.parseObject(apiparams);
        String params=jApiparams.getString("params");
        JSONObject jParams=JSONObject.parseObject(params);
        String classroomid=jParams.getString("classroomid");
        String term=jParams.getString("term");
        log.info("----->>>>>>getClassCourseList classroomid:"+classroomid+",term:"+term);

        List<TotalCourse> totalCourseList = null;
        try {
            totalCourseList = EbeanUtil.find(TotalCourse.class).where().
                    eq("classInfo.classRoom.id",classroomid).ge("date", DateUtil.getCurrentMonday()).lt("date",DateUtil.getCurrentNextMonday()).findList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<ApiClassCourse> apiClassCourseList = ApiClassCourseService.generateList(totalCourseList);
        String result=ApiResult.list("classbrand_getClassCourseList", apiClassCourseList).toJson();
        response.print(result);
        System.out.println(result);
    }
}
