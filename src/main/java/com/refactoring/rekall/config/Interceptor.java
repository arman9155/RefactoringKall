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
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/resources/**","/img/**","/js/**","/upImg/**", "/myLib/**", "/css/**","/no");
        
        registry.addInterceptor(new AuthLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**","/join/**","/community/**","/product/**", "/main","/message/*","/close","/favicon.ico",
                                    "/resources/**","/img/**","/js/**","/upImg/**", "/myLib/**", "/css/**", "/",
                                    "https://kapi.kakao.com/**","/kakao/**");

    }

}
