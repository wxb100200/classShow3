package com.hz.school.controller;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.hz.school.api.ApiResult;
import com.hz.school.model.ClassBoard;
import com.hz.school.resource.PageRecordResult;
import com.hz.school.util.EbeanUtil;
import com.hz.school.util.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 班牌发布系统控制台
 */
@Controller
@RequestMapping(value="/issue",produces="text/html;charset=UTF-8")
public class IssueManageController {
    private static Logger log= Logger.getLogger(ApiController.class);

    @RequestMapping(value = "/issueManage",method = RequestMethod.GET)
    public String helloWorld(){
        return "issue/issueManage";
    }

    @ResponseBody
    @RequestMapping(value = "/findClassBoard",method = RequestMethod.GET)
    public String findClassBoard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<ClassBoard> classBoards= EbeanUtil.find(ClassBoard.class).select("name,ip").findList();
        return PageRecordResult.list(classBoards).toJson();
    }


}
