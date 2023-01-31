package com.xfp.gmall.config;

import com.xfp.gmall.interceptors.HandlerInterceptorOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private HandlerInterceptorOrder handlerInterceptorOrder;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerInterceptorOrder).addPathPatterns("/**").excludePathPatterns("/error");
        super.addInterceptors(registry);
    }
}
