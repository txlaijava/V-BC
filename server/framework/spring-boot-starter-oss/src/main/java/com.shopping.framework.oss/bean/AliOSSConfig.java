package com.shopping.framework.oss.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author hx
 * @date 2019/7/30 15:28
 */
@Data
@PropertySource("classpath:aliyun-oss.properties")
@Configuration
public class AliOSSConfig {
    @Value("${oss-aliyun.accessKey}")
    private  String accessKey;
    @Value("${oss-aliyun.secretKey}")
    private String  secretKey;
    @Value("${oss-aliyun.bucket}")
    private String bucket;
    @Value("${oss-aliyun.endPoint}")
    private String endPoint;
    @Value("${oss-aliyun.httpPath}")
    private String httpPath;
}
