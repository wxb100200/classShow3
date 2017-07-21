package com.hz.school.controller;

import com.hz.school.model.Student;
import com.hz.school.resource.LoginResource;
import com.hz.school.util.EbeanUtil;
import com.hz.school.util.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2017/6/24.
 */
@Controller
public class LoginController {
    private static Logger log= Logger.getLogger(LoginController.class);
    /**
     * 登录页面
     */
    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String login(ModelMap model){
        model.addAttribute("message","Hello Spring mvc Framework");
        return "login/login";
    }
    /**
     * 登录页面
     */
    /*@RequestMapping(value="/loginRequest",method= RequestMethod.POST)
    public String loginRequest(String name,String password){
        log.info("--->>>>name:"+name+",password:"+password);
        String message= LoginResource.findAccount(name,password);
        if(message.equals("success")){
            return "index";
        }else {
            return "login/login";
        }
    }*/
    @RequestMapping(value="/loginRequest",method= RequestMethod.POST)
    public String loginRequest(String name,String password,ModelMap model){
        log.info("--->>>>name:"+name+",password:"+password);
        Student s= EbeanUtil.find(Student.class,1l);
        model.addAttribute(s);
        return "index";
    }
    /**
     * 注册页面
     */
    @RequestMapping(value="/regist",method= RequestMethod.GET)
    public String regist(ModelMap model){
        model.addAttribute("message","Hello Spring mvc Framework");
        return "login/regist";
    }
}
