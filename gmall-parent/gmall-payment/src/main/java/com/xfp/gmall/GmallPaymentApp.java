package com.xfp.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.xfp.gmall.mapper")
public class GmallPaymentApp {
    public static void main(String[] args) {
        SpringApplication.run(GmallPaymentApp.class,args);
    }
}
