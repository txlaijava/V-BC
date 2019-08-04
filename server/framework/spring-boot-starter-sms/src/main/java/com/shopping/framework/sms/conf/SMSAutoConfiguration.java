package com.shopping.framework.sms.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DayuConfig.class)
public class SMSAutoConfiguration {

    @Autowired
    private DayuConfig dayuConfig;

    @Bean
    public String configSource() {
        String app_key = dayuConfig.getAppname();
        return app_key;
    }
}
