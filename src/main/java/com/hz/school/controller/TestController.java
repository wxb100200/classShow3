package com.hz.school.controller;

import com.hz.school.util.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    private static Logger log=Logger.getLogger(TestController.class);

    /**
     * 访问首页
     */
    @RequestMapping(value="/",method= RequestMethod.GET)
    public String index(ModelMap model){
        model.addAttribute("message","Hello Spring mvc Framework");
        return "index";
    }
    @ResponseBody
    @RequestMapping(value = "/getString",method = RequestMethod.GET)
    public String getString(){
        return "wo shi String";
    }
}
