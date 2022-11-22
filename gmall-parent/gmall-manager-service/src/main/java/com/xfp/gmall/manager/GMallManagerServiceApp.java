package com.xfp.gmall.manager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xfp.gmall.manager.mapper")
public class GMallManagerServiceApp {
    public static void main(String[] args) {
        //测试Git
        SpringApplication.run(GMallManagerServiceApp.class,args);
    }
}
