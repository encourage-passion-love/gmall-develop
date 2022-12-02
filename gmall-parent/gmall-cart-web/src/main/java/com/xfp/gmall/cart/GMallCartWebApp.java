package com.xfp.gmall.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.xfp.gmall")
public class GMallCartWebApp {
    public static void main(String[] args) {
        SpringApplication.run(GMallCartWebApp.class,args);
    }
}
