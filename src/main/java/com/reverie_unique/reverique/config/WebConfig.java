package com.reverie_unique.reverique.config;

import com.reverie_unique.reverique.interceptor.ResponseInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public ResponseInterceptor responseInterceptor() {
        return new ResponseInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(responseInterceptor())
                .addPathPatterns("/**"); // 모든 경로에 대해 인터셉터 적용
    }
}