package com.shopping.framework.swagger.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("springbootcamp.swagger")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwaggerConfigurationProperties {

  String title = "APPLICATION.NAME";

  String version = "APPLICATION.VERSION";

  boolean redirect = false;

}
