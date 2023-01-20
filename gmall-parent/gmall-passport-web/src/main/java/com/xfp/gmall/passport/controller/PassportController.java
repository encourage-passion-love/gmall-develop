package com.xfp.gmall.passport.controller;

import com.xfp.gmall.passport.service.UserService;
import com.xfp.gmall.user.bean.UmsMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PassportController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public String login(UmsMember umsMember){
        //调用用户服务验明用户名和密码
        return "token";
    }
    @RequestMapping("/verify")
    @ResponseBody
    public String verify(String token){
        //调用用户服务验明用户名和密码
        return "success";
    }

    @RequestMapping("/index")
    public String index(String ReturnUrl, ModelMap map){
        map.put("ReturnUrl",ReturnUrl);
        return "index";
    }
}
