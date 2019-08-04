package com.shopping.framework.sms.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 类描述：阿里大鱼配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "alidayu")
@PropertySource("classpath:sms.properties")
public class DayuConfig {

    private String app_url;

    private String app_key;

    private String app_secret;

    private String sms_type;

    private String sign_name;

    private String appname;
}
