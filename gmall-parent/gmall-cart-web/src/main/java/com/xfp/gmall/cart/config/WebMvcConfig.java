package com.xfp.gmall.cart.config;

import com.xfp.gmall.cart.interceptors.HandlerInterceptorCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private HandlerInterceptorCart handlerInterceptorCart;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerInterceptorCart).addPathPatterns("/**").excludePathPatterns("/error");
        super.addInterceptors(registry);
    }
}
