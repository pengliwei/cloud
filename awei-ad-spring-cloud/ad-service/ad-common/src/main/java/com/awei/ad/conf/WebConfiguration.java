package com.awei.ad.conf;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author ：penglw
 * @date ：2020/6/9 18:26
 */
public class WebConfiguration implements WebMvcConfigurer{

    /**
     * 配置统一的java实体对象转换为json对象
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
