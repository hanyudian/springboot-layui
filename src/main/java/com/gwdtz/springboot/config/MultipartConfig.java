package com.gwdtz.springboot.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @program: springboot
 * @Date: 2021-4-15 12:46
 * @Author: Miss.Chenmf
 * @Description:
 */
@Configuration
public class MultipartConfig {
    @Bean
     public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory=new MultipartConfigFactory();
        factory.setMaxFileSize("100MB");
        factory.setMaxRequestSize("100MB");
        return factory.createMultipartConfig();
    }
}
