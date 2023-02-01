package com.xfp.gmall.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.xfp.gmall.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {

    @Autowired
    private AlipayClient alipayClient;

    @RequestMapping("/wx/submit")
    public String wx(String totalAmount,String orderId,HttpServletRequest request, ModelMap map){
        return null;
    }
    @RequestMapping("/alipay/submit")
    @ResponseBody
    public String alipay(String totalAmount,String orderId,HttpServletRequest request, ModelMap map){
        String form=null;
        //利用支付宝客户端生成表单页面
        AlipayTradePagePayRequest alipayRequest=new AlipayTradePagePayRequest();
        Map<String,Object> map1=new HashMap<>();
        map1.put("out_trade_no",orderId);
        map1.put("totalAmount",totalAmount);
        map1.put("project_code","FAST_INSTANT_TRADE_PAY");
        map1.put("subject","礼物购买");
        String bizStr = JSON.toJSONString(map1);
        alipayRequest.setBizContent(bizStr);
        //同步回调地址
        alipayRequest.setReturnUrl(AlipayConfig.return_payment_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_payment_url);
        //异步回调地址
        try {
            form= alipayClient.pageExecute(alipayRequest).getBody();
            System.out.println(form);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    @RequestMapping("/index")
    public String index(ModelMap map){
        map.put("nickName","王五");
        map.put("orderId","202201022196732");
        map.put("totalAmount","1450.00");
        return "index";
    }
}
