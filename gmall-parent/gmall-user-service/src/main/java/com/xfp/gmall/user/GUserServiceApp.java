package com.xfp.gmall.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xfp.gmall.user.mapper")
public class GUserServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(GUserServiceApp.class,args);
    }
}
