package com.shopping.framework.swagger.configuration;


import com.shopping.framework.swagger.properties.SwaggerConfigurationProperties;
import com.fasterxml.classmate.TypeResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.not;
import static springfox.documentation.builders.PathSelectors.regex;

@Slf4j
@EnableSwagger2
@EnableConfigurationProperties(SwaggerConfigurationProperties.class)
public class SwaggerConfiguration {

    private final SwaggerConfigurationProperties properties;

    public SwaggerConfiguration(final SwaggerConfigurationProperties properties) {
        this.properties = properties;

        log.info("using springfox.swagger2 with properties='{}'", properties);
    }

    @Bean
    public Docket docket(final TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(not(regex("/error.*")))
                .build()
                .pathMapping("/")
                .apiInfo(new ApiInfoBuilder()
                                .title(properties.getTitle())
                                .version(properties.getVersion())
                                .build()
                );
    }

    @Bean
    public WebMvcConfigurerAdapter forwardToIndex() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                if (properties.isRedirect()) {
                    registry.addViewController("/").setViewName("redirect:/swagger-ui.html");
                }
            }
        };
    }


}
