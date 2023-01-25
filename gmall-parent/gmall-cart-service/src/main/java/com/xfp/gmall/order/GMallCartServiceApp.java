package com.xfp.gmall.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.xfp.gmall.cart.mapper")
public class GMallCartServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(GMallCartServiceApp.class,args);
    }
}
