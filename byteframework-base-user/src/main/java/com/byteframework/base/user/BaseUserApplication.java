package com.byteframework.base.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.byteframework.base.user.mapper"})
public class BaseUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseUserApplication.class, args);
    }

}
