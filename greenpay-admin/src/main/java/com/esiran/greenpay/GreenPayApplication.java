package com.esiran.greenpay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.esiran.greenpay.**.mapper")
public class GreenPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GreenPayApplication.class,args);
    }
}
