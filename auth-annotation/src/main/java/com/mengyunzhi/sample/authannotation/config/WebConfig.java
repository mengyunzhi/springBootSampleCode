package com.mengyunzhi.sample.authannotation.config;

import com.mengyunzhi.sample.authannotation.interceptor.BasicAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhangxishuo on 2019-01-23
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private BasicAuthInterceptor basicAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthInterceptor).addPathPatterns("/**");
    }
}
