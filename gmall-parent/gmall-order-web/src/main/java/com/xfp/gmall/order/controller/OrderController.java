package com.xfp.gmall.order.controller;

import com.xfp.gmall.order.annoations.LoginRequired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class OrderController {
    @RequestMapping("/toTrade")
    @LoginRequired(loginSuccess = true)
    public String toTrade(HttpServletRequest request, HttpServletResponse response, ModelMap map){
        //这个地方不适用toString方法了 因为获取的如果是空null的话
        //调用这个方法会报空指针异常
        String memberId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");
        return "toTrade";
    }
}
