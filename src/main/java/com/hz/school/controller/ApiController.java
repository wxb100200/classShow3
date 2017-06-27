package com.hz.school.controller;

import com.alibaba.fastjson.JSONObject;
import com.hz.school.api.ApiResult;
import com.hz.school.api.base_getToken.ApiAccessToken;
import com.hz.school.api.base_getToken.ApiAccessTokenService;
import com.hz.school.api.classbrand_getBzrClassid.ApiClassInfo;
import com.hz.school.api.classbrand_getBzrClassid.ApiGetBzrClassService;
import com.hz.school.api.classbrand_getClassCoursLeist.ApiClassCourse;
import com.hz.school.api.classbrand_getClassCoursLeist.ApiClassCourseService;
import com.hz.school.api.classbrand_getWeather.ApiWeather;
import com.hz.school.api.classbrand_getWeather.ApiWeatherService;
import com.hz.school.model.AccessToken;
import com.hz.school.model.ClassInfo;
import com.hz.school.model.GoClassCourse;
import com.hz.school.model.TotalCourse;
import com.hz.school.util.DateUtil;
import com.hz.school.util.EbeanUtil;
import com.hz.school.util.Logger;
import com.hz.school.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 班牌接口
 */
@Controller
@RequestMapping(value="/api",produces="text/html;charset=UTF-8")
public class ApiController {
    private static Logger log= Logger.getLogger(ApiController.class);
    /**
     * 一、获取AccessTlisten令牌接口（延后）
     */
    @ResponseBody
    @RequestMapping(value="/base_getTlisten",method= RequestMethod.POST)
    public String baseGetTlisten(HttpServletRequest request){
        String apiparams=request.getParameter("apiparams");
        JSONObject jApiparams= JSONObject.parseObject(apiparams);
        String params=jApiparams.getString("params");
        JSONObject jParams=JSONObject.parseObject(params);
        String appId=jParams.getString("appId");
        String appSecret=jParams.getString("appSecret");
        log.info("----->>>>>>baseGetTlisten appId:"+appId+",appSecret:"+appSecret);
        AccessToken accessToken= EbeanUtil.find(AccessToken.class).where().eq("appId",appId).eq("appSecret",appSecret).findUnique();
        ApiAccessToken apiAccessToken= ApiAccessTokenService.generateData(accessToken);
        return ApiResult.single("base_getTlisten",apiAccessToken).toSingleJson();
    }
    /**
     * 通过
     *  二、 设备测试接口网络及获取当前时间情况
     * 1、主要测试电子班牌和微校通网络是否畅通
     */
    @ResponseBody
    @RequestMapping(value="/classbrand_getServerTime",method= RequestMethod.POST)
    public String ServerTime(HttpServletRequest request){
        String apiparams=request.getParameter("apiparams");
        log.info("apiparams="+apiparams);
        JSONObject jApiparams= JSONObject.parseObject(apiparams);
        String params=jApiparams.getString("params");
        JSONObject jParams=JSONObject.parseObject(params);
        String init=jParams.getString("init");
        log.info("----->>>>>>ServerTime init:"+init);
        String currentTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        if(StringUtil.isEmpty(init)){
            log.error("网络异常");
            return "{\"api\":\"classbrand_getServerTime\",\"data\":\"\",\"ret\":{\"code\":\"400 \",\"msg\":\"网络异常\"},\"v\":\"\"}";
        }
        return "{\"api\":\"classbrand_getServerTime\",\"data\":\""+currentTime+"\",\"ret\":{\"code\":\"200\",\"msg\":\"\"},\"v\":\"\"}";
    }
    /**
     * 三、 设备获取天气情况情况
     * 1、主要测试电子班牌和微校通网络是否畅通
     * apiparams={"params":{"cityname":"杭州","classid":"2958","accessTlisten":"ccesstlisten000001"}}
     */
    @ResponseBody
    @RequestMapping(value="/classbrand_getWeather",method= RequestMethod.POST)
    public String Weather(HttpServletRequest request) {
        String apiparams=request.getParameter("apiparams");
        log.info("apiparams="+apiparams);
        JSONObject jApiparams= JSONObject.parseObject(apiparams);
        String params=jApiparams.getString("params");
        JSONObject jParams=JSONObject.parseObject(params);
        String accessTlisten=jParams.getString("accessTlisten");//令牌
        String cityname=jParams.getString("cityname");//城市名称
        String classid=jParams.getString("classid");//班级id
        log.info("----->>>>>>Weather accessTlisten:"+accessTlisten+",cityname:"+cityname+",classid:"+classid);
        try {
            ApiWeather apiWeather= ApiWeatherService.generateData("101210101");
            return ApiResult.single("classbrand_getWeather",apiWeather).toJson();
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResult.error("classbrand_getWeather","500","ApiWeather todayWeather error").toJson();
        }
    }
    /**
     *四、 设备与班级关联接口
     * http://zjgbanpai.hzxjhs.com/weixt/api/classbrand_getBzrClassid
     * apiparams={"params":{"classroomid":"","accessTlisten":"ccesstlisten000001","SN":"587DA6830CE47FF2632D1EDFA0E302D4","card":"1967948798"}}
     */
    @ResponseBody
    @RequestMapping(value="/classbrand_getBzrClassid",method= RequestMethod.POST)
    public String BzrClassid(HttpServletRequest request) {
        String apiparams=request.getParameter("apiparams");
        log.info("apiparams="+apiparams);
        JSONObject jApiparams= JSONObject.parseObject(apiparams);
        String params=jApiparams.getString("params");
        JSONObject jParams=JSONObject.parseObject(params);
        String accessTlisten=jParams.getString("accessTlisten");//令牌
        String card=jParams.getString("card");//班主任卡号
        String SN=jParams.getString("SN");//CB电子班牌唯一设备号
        log.info("----->>>>>>BzrClassid accessTlisten:"+accessTlisten+",card:"+card+",SN:"+SN);
        ClassInfo classInfo=EbeanUtil.find(ClassInfo.class).where().eq("headmaster.card.cardNo",card).findUnique();
        ApiClassInfo apiClassInfo= ApiGetBzrClassService.generateData(classInfo);
        System.out.println("{\"api\":\"classbrand_getBzrClassid\",\"data\":{\"class\":{"+ApiResult.objectToJson(apiClassInfo)+"},\"classSelection\":null},\"ret\":{\"code\":\"200\",\"msg\":\"\"},\"v\":\"\"}".trim());
        return "{\"api\":\"classbrand_getBzrClassid\",\"data\":{\"class\":{"+ApiResult.objectToJson(apiClassInfo)+"},\"classSelection\":null},\"ret\":{\"code\":\"200\",\"msg\":\"\"},\"v\":\"\"}".trim();
    }




    /**
     *十九、班级课表接口：classbrand_getClassCourseList（班级课程表信息）
     * 入参：
     * 教室ID（classroomid）、学期（term）
     */
    @ResponseBody
    @RequestMapping(value="/classbrand_getClassCourseList",method= RequestMethod.POST)
    public String getClassCourseList(HttpServletRequest request) {
        String apiparams=request.getParameter("apiparams");
        log.info("apiparams="+apiparams);
        JSONObject jApiparams= JSONObject.parseObject(apiparams);
        String params=jApiparams.getString("params");
        JSONObject jParams=JSONObject.parseObject(params);
        String classroomid=jParams.getString("classroomid");
        String term=jParams.getString("term");
        log.info("----->>>>>>getClassCourseList classroomid:"+classroomid+",term:"+term);
        int currentWeek=0;
        try {
            currentWeek=judgeWeek();
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("--->>>>获取当前第几周失败。。。",e);
        }
        List<TotalCourse> totalCourseList=EbeanUtil.find(TotalCourse.class).where().
                eq("classInfo.classRoom.id",classroomid).eq("numWeek",currentWeek).findList();//根据当前时间获取第几周的课表
        List<GoClassCourse> goClassCourseList=EbeanUtil.find(GoClassCourse.class).where().eq("classRoom.id",classroomid).findList();

        List<ApiClassCourse> apiClassCourseList = ApiClassCourseService.generateList(totalCourseList,goClassCourseList);
        return ApiResult.list("classbrand_getClassCourseList", apiClassCourseList).toJson();
    }
    /**
     * 根据系统当前时间判断第几周
     */
    private static int judgeWeek() throws ParseException {
        Date date= DateUtil.getCurrent();
        int yearWeek=DateUtil.weekOfYear(date);

        String startDate="2017-02-27";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = format.parse(startDate);
        int yearWeek2=DateUtil.weekOfYear(date2);
        return yearWeek-yearWeek2;
    }
}
