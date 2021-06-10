package com.bingchunmoli.admintemplate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author BingChunMoLi
 */
@SpringBootApplication
@MapperScan("com.bingchunmoli.admintemplate.mapper")
public class AdminTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminTemplateApplication.class, args);
    }

}
