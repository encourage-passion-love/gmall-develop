package com.xfp.gmall.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.bind.v2.TODO;
import com.xfp.gmall.manager.bean.PmsSkuInfo;
import com.xfp.gmall.manager.service.SkuService;
import com.xfp.gmall.user.bean.OmsCartItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CartController {

    @Reference
    private SkuService skuService;

    @RequestMapping("/addToCart")
    public String addToCart(HttpServletRequest request, HttpServletResponse response
            , String skuId, int quantity) throws Exception {
        PmsSkuInfo skuInfo = skuService.findSkuInfoById(skuId);
        OmsCartItem omsCartItem = new OmsCartItem();
        List<OmsCartItem> omsCartItems = new ArrayList<>();
        omsCartItem.setProductSkuId(skuId);
        omsCartItem.setPrice(skuInfo.getPrice());
        omsCartItem.setCreateDate(new Date());
        omsCartItem.setProductCategoryId(skuInfo.getCatalog3Id());
        omsCartItem.setDeleteStatus(0);
        omsCartItem.setQuantity(quantity);
        omsCartItem.setProductName(skuInfo.getSkuName());
        omsCartItem.setProductPic(skuInfo.getSkuDefaultImg());
        /*
         TODO:
          String memberId = "";这个用户是否登录后面还需要判断
         */
        Cookie[] cookies = request.getCookies();
        Cookie cookie1 = null;
        if(cookies!=null&&cookies.length>0){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("cartListCookie".equals(name)) {
                    cookie1 = cookie;
                }
            }
        }
        else {
            cookie1=new Cookie("cartListCookie","");
        }
        String cookie1Value = cookie1.getValue();
        if (StringUtils.isBlank(cookie1Value)) {
            omsCartItems.add(omsCartItem);
            cookie1.setMaxAge(60 * 60 * 24 * 3);
            cookie1.setDomain("localhost");
            cookie1.setPath("/");
            cookie1.setValue(URLEncoder.encode(JSON.toJSONString(omsCartItems),"UTF-8"));
            response.addCookie(cookie1);
        } else {
            String cookieStr = URLDecoder.decode(cookie1.getValue(),"UTF-8");
            omsCartItems = JSON.parseArray(cookieStr, OmsCartItem.class);
            for (OmsCartItem cartItem : omsCartItems) {
                if (omsCartItem.getProductSkuId().equals(cartItem.getProductSkuId())) {
                    cartItem.setQuantity(cartItem.getQuantity() + omsCartItem.getQuantity());
                    cartItem.setPrice(cartItem.getPrice().add(omsCartItem.getPrice()));
                    cartItem.setModifyDate(new Date());
                } else {
                    omsCartItems.add(omsCartItem);
                }
            }
            cookie1.setMaxAge(60 * 60 * 24 * 3);
            cookie1.setDomain("localhost");
            cookie1.setValue(URLEncoder.encode(JSON.toJSONString(omsCartItems),"UTF-8"));
            cookie1.setPath("/");
            response.addCookie(cookie1);
        }
        return "redirect:/success.html";
    }


}
