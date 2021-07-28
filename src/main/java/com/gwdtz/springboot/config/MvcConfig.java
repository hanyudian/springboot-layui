package com.gwdtz.springboot.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-1-11 15:36
 * @Author: Miss Chenmf
 * @Description:解决IE浏览器 @ResponseBody返回json的时候提示下载问题
 */
@Configuration
public class MvcConfig extends SpringBootServletInitializer {
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(){
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter=new FastJsonHttpMessageConverter();
        List<MediaType> fastMediaTypes=new ArrayList<MediaType>();
        MediaType media=new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"));
        fastMediaTypes.add(media);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        HttpMessageConverter<?> converter=fastJsonHttpMessageConverter;
        return new HttpMessageConverters(converter);
    }
}
