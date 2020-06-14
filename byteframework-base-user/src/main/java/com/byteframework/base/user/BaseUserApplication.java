package com.byteframework.base.user;

import com.byteframework.base.user.config.UniqueNameGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(nameGenerator = UniqueNameGenerator.class, value = {"com.byteframework.base.user.*", "com.byteframework.psi.*"})
@MapperScan(nameGenerator = UniqueNameGenerator.class, basePackages = {"com.byteframework.base.user.mapper","com.byteframework.psi.mapper"})
public class BaseUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseUserApplication.class, args);
    }

}
