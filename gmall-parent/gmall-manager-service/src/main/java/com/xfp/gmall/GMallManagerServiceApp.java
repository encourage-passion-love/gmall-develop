package com.xfp.gmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xfp.gmall.manager.mapper")
public class GMallManagerServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(GMallManagerServiceApp.class,args);
    }
}
