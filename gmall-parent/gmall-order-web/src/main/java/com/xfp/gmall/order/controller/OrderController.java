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
    public String toTrade(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        String memberId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");
        //每一个购物车项信息对应一个订单项信息
        //从缓存里面把信息取出来
        //使用ModelMap进行封装订单项信息就可以
        //这个地方需要生成一个交易码
        return "trade";
    }
    @RequestMapping("/submitOrder")
    public String submitOrder(String receiveAddressId,String totalAmount,HttpServletRequest request,HttpServletResponse response) {
    String memberId= (String) request.getAttribute("memberId");
    String nickname= (String) request.getAttribute("nickname");
        /*
        一个结算页面生成的一个交易码只能在提交订单时使用一次
        进行页面交易码和redis里面交易码进行比对
        比对成功可以进行订单的提交,失败则不能进行订单的提交
         */
        //从缓存里面把购物车信息查询出来
        //将购物车信息进行清空
        //进行封装成订单项和订单详情项
        //进行写入到数据库
        return "";
    }
}
