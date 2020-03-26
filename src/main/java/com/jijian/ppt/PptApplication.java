package com.jijian.ppt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jijian.ppt.mapper")
public class PptApplication {

    public static void main(String[] args) {
        SpringApplication.run(PptApplication.class, args);
    }

}
