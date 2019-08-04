package com.shopping.framework.sms.conf;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 类描述：极光消息配置
 */
@Data
@Component
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties(prefix = "jg")
@PropertySource("classpath:sms.properties")
public class JpushConfig {

    private String AppKey;

    private String AppSecret;

    private String AppKey_seller;

    private String AppSecret_seller;
}
