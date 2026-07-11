package com.ctgu.alexapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ctgu.alexapi.mapper")
public class AlexApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlexApiApplication.class, args);
    }
}
