package com.shopping.base.annotation;

import java.lang.annotation.*;

@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Title {
	public abstract String value();
}