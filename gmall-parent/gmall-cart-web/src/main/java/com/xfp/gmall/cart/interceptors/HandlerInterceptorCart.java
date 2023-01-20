package com.xfp.gmall.cart.interceptors;

import com.xfp.gmall.cart.annoations.LoginRequired;
import com.xfp.gmall.cart.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HandlerInterceptorCart extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //编写业务逻辑代码
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginRequired annotation = handlerMethod.getMethodAnnotation(LoginRequired.class);
        if (annotation == null) {
            return true;
        }
        Cookie[] cookies = request.getCookies();
        Cookie cookie = new Cookie("oldToken", "");
        for (Cookie cookie1 : cookies) {
            if (cookie1.getName().equals("oldToken")) {
                cookie = cookie1;
            }
        }
        String token = "";
        String oldToken = cookie.getValue();
        if (StringUtils.isNotBlank(oldToken)) {
            token = oldToken;
        }
        String newToken = request.getParameter("token");
        if (StringUtils.isNotBlank(newToken)) {
            token = newToken;
        }
        //下面判断是否登录过系统
        String success="";
        if(StringUtils.isNotBlank(token)){
            success = HttpClientUtil.doGet("http://localhost:8085/verify?token=" + token);
        }

        boolean loginSuccess = annotation.loginSuccess();
        if (loginSuccess) {
            if (!success.equals("success")) {
                //踢回用户中心进行登录
                StringBuffer requestURL = request.getRequestURL();
                response.sendRedirect("http://localhost:8085/index?ReturnUrl="+requestURL);
                return false;
            }
             //验证通过
            request.setAttribute("memberId", "1");
            request.setAttribute("nickname", "admin");
            if(StringUtils.isNotBlank(token)){
                cookie.setMaxAge(60*60*2);
                cookie.setValue(token);//进行cookie的覆盖
                response.addCookie(cookie);
            }
        } else {
            //验证没有通过也可以进行使用
            if (!success.equals("success")) {
                request.setAttribute("memberId", "1");
                request.setAttribute("nickname", "admin");
            }
            if(StringUtils.isNotBlank(token)){
                cookie.setMaxAge(60*60*2);
                cookie.setValue(token);//进行cookie的覆盖
                response.addCookie(cookie);
            }
        }
        return true;
    }
}
