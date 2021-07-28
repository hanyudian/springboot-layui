package com.gwdtz.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.gwdtz.springboot.dao")
@EnableScheduling
public class SpringbootApplication  {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

}
