package com.refactoring.rekall.config;

import com.refactoring.rekall.interceptor.AdminInterceptor;
import com.refactoring.rekall.interceptor.AuthLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Interceptor implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**", "/notice/**")
                .excludePathPatterns("/resources/**","/img/**","/js/**","/upImg/**", "/myLib/**", "/css/**","/no");

        // admin인 경우 들어가도록 설정되어있지만 혹시 모르니 session  상  admin / 저장이 admin 인지 확인하도록 설정
        
        registry.addInterceptor(new AuthLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**","/join/**","/community/**","/product/**", "/main",
                                    "/resources/**","/img/**","/js/**","/upImg/**", "/myLib/**", "/css/**", "/");

        //전체 페이지에 대하여 session이 있을 경우에 들어갈 수 있는 페이지 설정
    }

}
