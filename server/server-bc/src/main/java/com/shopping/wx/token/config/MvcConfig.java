package com.shopping.wx.token.config;


import com.shopping.wx.token.authorization.resolvers.CurrentBcUserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 配置类，增加自定义拦截器和解析器
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private CurrentBcUserMethodArgumentResolver currentBcUserMethodArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        argumentResolvers.add(currentBcUserMethodArgumentResolver);

    }
}
