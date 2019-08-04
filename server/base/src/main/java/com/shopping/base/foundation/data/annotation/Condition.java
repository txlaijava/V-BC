package com.shopping.base.foundation.data.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * 条件注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { FIELD })
public @interface Condition {

	/**
	 * 查询路径 如 name  或 parent.name
	 * @return
	 */
	String path() default "";

	/**
	 * 匹配模式
	 * @see MatchMode
	 * @return
	 */
	MatchMode match() default MatchMode.EQ;

	/**
	 * 数据类型
	 * @return
	 */
	Class<?> type() default Object.class;

	/**
	 * 用于时间处理。表示此日期最早时间 如2017-11-09 00:00:00
	 * @return
	 */
	boolean minTime() default false;
	/**
	 * 用于时间处理。表示此日期最晚时间 如2017-11-09 23:59:59 999
	 * @return
	 */
	boolean maxTime() default false;
}
