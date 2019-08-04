package com.shopping.framework.druid.configuration;

import java.lang.annotation.*;

/**
 * Multiple DataSource Aspect For Switch DataSource
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String value();
}
