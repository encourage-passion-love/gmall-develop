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
        String memberId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");
        //每一个购物车项信息对应一个订单项信息
        //从缓存里面把信息取出来
        //使用ModelMap进行封装订单项信息就可以
        return "trade";
    }
}
