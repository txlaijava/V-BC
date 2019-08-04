package com.shopping.framework.token.config;

import com.shopping.framework.token.authorization.resolvers.CacheUserMethodArgumentResolver;
import com.shopping.framework.token.authorization.resolvers.CurrentUserMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 配置类，增加自定义拦截器和解析器
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public CacheUserMethodArgumentResolver cacheUserMethodArgumentResolver(){
        return new CacheUserMethodArgumentResolver();
    }

    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver(){
        return new CurrentUserMethodArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(cacheUserMethodArgumentResolver());
        argumentResolvers.add(currentUserMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }
}
