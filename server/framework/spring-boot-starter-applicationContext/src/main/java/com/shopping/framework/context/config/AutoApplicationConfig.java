package com.shopping.framework.context.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类描述: 自动注入 applicationContext
 *
 * @author GuoFuJun
 * @date 2018/10/25 下午2:20
 */
@Configuration
public class AutoApplicationConfig {

    @Bean
    public ApplicationUtil app(@Autowired ApplicationContext applicationContext) {
        return ApplicationUtil.init(applicationContext);
    }


}
