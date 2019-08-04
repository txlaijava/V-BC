package com.shopping.framework.context.config;

import org.springframework.context.ApplicationContext;

/**
 * 类描述: 注入 applicationContext
 *
 * @author GuoFuJun
 * @date 2018/10/25 下午2:20
 */
public class ApplicationUtil {
    protected static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static ApplicationUtil init(ApplicationContext app) {
        applicationContext = app;
        return new ApplicationUtil();
    }
}
