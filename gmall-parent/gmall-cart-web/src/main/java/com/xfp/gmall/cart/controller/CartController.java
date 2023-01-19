package com.xfp.gmall.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.xfp.gmall.cart.annoations.LoginRequired;
import com.xfp.gmall.cart.bean.OmsCartItem;
import com.xfp.gmall.cart.service.CartItemService;
import com.xfp.gmall.manager.bean.PmsSkuInfo;
import com.xfp.gmall.manager.service.SkuService;
import com.xfp.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class CartController {

    @Reference
    private SkuService skuService;
    @Reference
    private CartItemService cartItemService;
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/addToCart")
    @LoginRequired(loginSuccess = false)
    public String addToCart(HttpServletRequest request, HttpServletResponse response
            , String skuId, int quantity) throws Exception {
        PmsSkuInfo skuInfo = skuService.findSkuInfoById(skuId);
        List<OmsCartItem> omsCartItems = new ArrayList<>();
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setProductSkuId(skuId);
        omsCartItem.setPrice(skuInfo.getPrice());
        omsCartItem.setCreateDate(new Date());
        omsCartItem.setProductCategoryId(skuInfo.getCatalog3Id());
        omsCartItem.setDeleteStatus(0);
        omsCartItem.setQuantity(quantity);
        omsCartItem.setProductName(skuInfo.getSkuName());
        omsCartItem.setProductPic(skuInfo.getSkuDefaultImg());
        omsCartItem.setIsChecked("1");
        String memberId = "1";
        String memberId1 = (String) request.getAttribute("memberId");
        if (StringUtils.isBlank(memberId1)) {
            Cookie[] cookies = request.getCookies();
            Cookie cookie1 = null;
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    String name = cookie.getName();
                    if ("cartListCookie".equals(name)) {
                        cookie1 = cookie;
                    }
                }
            } else {
                cookie1 = new Cookie("cartListCookie", "");
            }
            String cookie1Value = cookie1.getValue();
            if (StringUtils.isBlank(cookie1Value)) {
                omsCartItems.add(omsCartItem);
                cookie1.setMaxAge(60 * 60 * 24 * 3);
                cookie1.setDomain("localhost");
                cookie1.setPath("/");
                cookie1.setValue(URLEncoder.encode(JSON.toJSONString(omsCartItems), "UTF-8"));
                response.addCookie(cookie1);
            } else {
                String cookieStr = URLDecoder.decode(cookie1.getValue(), "UTF-8");
                omsCartItems = JSON.parseArray(cookieStr, OmsCartItem.class);
                for (OmsCartItem cartItem : omsCartItems) {
                    if (omsCartItem.getProductSkuId().equals(cartItem.getProductSkuId())) {
                        cartItem.setQuantity(cartItem.getQuantity() + omsCartItem.getQuantity());
                        cartItem.setPrice(cartItem.getPrice());
                        cartItem.setModifyDate(new Date());
                    } else {
                        omsCartItems.add(omsCartItem);
                    }
                }
                cookie1.setMaxAge(60 * 60 * 24 * 3);
                cookie1.setDomain("localhost");
                cookie1.setValue(URLEncoder.encode(JSON.toJSONString(omsCartItems), "UTF-8"));
                cookie1.setPath("/");
                response.addCookie(cookie1);
            }
        } else {
            OmsCartItem omsCartFromDb = cartItemService.getOmsCartItemByMemberIdAndSkuId(memberId, skuId);
            if (omsCartFromDb == null) {
                omsCartItem.setCreateDate(new Date());
                omsCartItem.setMemberNickname("admin1");
                omsCartItem.setMemberId(memberId);
                omsCartItem.setProductId(skuInfo.getSpuId());
                omsCartItems.add(omsCartItem);
                cartItemService.saveCartItemToDB(omsCartItem);
            } else {
                omsCartFromDb.setPrice(omsCartFromDb.getPrice());
                omsCartFromDb.setQuantity(omsCartFromDb.getQuantity() + omsCartItem.getQuantity());
                omsCartFromDb.setModifyDate(new Date());
                omsCartFromDb.setProductId(skuInfo.getSpuId());
                omsCartItems.add(omsCartFromDb);
                cartItemService.updateCartItemBySkuId(omsCartFromDb);
            }
            Jedis jedis = redisUtil.getJedis();
            String mapKey = "user:" + memberId + ":cart";
            Map<String, String> map = new HashMap<>();
            for (OmsCartItem cartItem : omsCartItems) {
                String cartStr = JSON.toJSONString(cartItem);
                map.put(cartItem.getProductSkuId(), cartStr);
            }
            try {
                jedis.hmset(mapKey, map);
            } catch (Exception e) {
                e.printStackTrace();
                jedis.close();
            } finally {
                jedis.close();
            }
        }
        return "redirect:/success.html";
    }

    @RequestMapping("/cartList")
    @LoginRequired(loginSuccess = false)
    public String cartList(HttpServletResponse response, HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
        List<OmsCartItem> omsCartItems = new ArrayList<>();
        String memberId = "1";
        Cookie cookie = new Cookie("cartListCookie", "");
        if (StringUtils.isNotBlank(memberId)) {
            omsCartItems = cartItemService.getCartItemByMemberId(memberId);
            if (omsCartItems!=null&&omsCartItems.size() > 0) {
                for (OmsCartItem omsCartItem : omsCartItems) {
                    omsCartItem.setTotalPrice(omsCartItem.getPrice().multiply(new BigDecimal(omsCartItem.getQuantity())).doubleValue());
                }
            }
        } else {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie1 : cookies) {
                    if (cookie1.getName().equalsIgnoreCase(cookie.getName())) {
                        cookie = cookie1;
                    }
                }
            }
            String cookieValue = cookie.getValue();
            if (StringUtils.isNotBlank(cookieValue)) {
                omsCartItems = JSON.parseArray(URLDecoder.decode(cookieValue, "UTF-8"), OmsCartItem.class);
            } else {
                cookie.setValue(URLEncoder.encode("", "UTF-8"));
                response.addCookie(cookie);
            }
        }
        BigDecimal totalAmount=new BigDecimal("0");
        if(omsCartItems.size()>0){
            totalAmount= getCartItemTotalAmount(omsCartItems);
        }
        modelMap.put("cartList", omsCartItems);
        modelMap.put("totalAmount",totalAmount);
        return "cartList";
    }

    private BigDecimal getCartItemTotalAmount(List<OmsCartItem> omsCartItems) {
        BigDecimal totalAmount=new BigDecimal("0");
        for (OmsCartItem omsCartItem : omsCartItems) {
            if(omsCartItem.getIsChecked().equals("1")){
                totalAmount=totalAmount.add(new BigDecimal(+omsCartItem.getTotalPrice()));
            }
        }
        return totalAmount;
    }

    @RequestMapping("/checkCart")
    @LoginRequired(loginSuccess = false)
    public String checkCart(String skuId,String isChecked,HttpServletResponse response,
                            HttpServletRequest request,ModelMap modelMap)
    {
        List<OmsCartItem> omsCartItems=new ArrayList<>();
        String memberId="1";
        OmsCartItem omsCartItem=new OmsCartItem();
        omsCartItem.setMemberId(memberId);
        omsCartItem.setProductSkuId(skuId);
        omsCartItem.setIsChecked(isChecked);
        cartItemService.checkCart(omsCartItem);
        List<OmsCartItem> cartItemByMemberId = cartItemService.getCartItemByMemberId(memberId);
        if(cartItemByMemberId!=null&&cartItemByMemberId.size()>0){
            cartItemService.flushCartItemCache(cartItemByMemberId);
            for (OmsCartItem cartItem : cartItemByMemberId) {
                cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getQuantity())).doubleValue());
                omsCartItems.add(cartItem);
            }
        }
        BigDecimal totalAmount=new BigDecimal("0");
        if(omsCartItems.size()>0){
            totalAmount= getCartItemTotalAmount(omsCartItems);
        }
        modelMap.put("cartList",omsCartItems);
        modelMap.put("totalAmount",totalAmount);
        return "cartListInner";
    }
    @RequestMapping("/toTrade")
    @LoginRequired(loginSuccess = true)
    public String toTrade(HttpServletRequest request,HttpServletResponse response,ModelMap map){
        //这个地方不适用toString方法了 因为获取的如果是空null的话
        //调用这个方法会报空指针异常
        String memberId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");
        return "toTrade";
    }




}
