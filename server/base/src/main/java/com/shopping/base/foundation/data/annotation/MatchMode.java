package com.shopping.base.foundation.data.annotation;

/**
 *
 *
 *
 */
public enum MatchMode {

	/**
	 * 相等
	 */
	EQ,
	/**
	 * 包含 like '%xxxx%'
	 */
	CONTAINS,
	/**
	 * 以*开始
	 */
	START,
	/**
	 * 以*结束
	 */
	END,
	/**
	 * 在xx到之间
	 */
	BETWEEN,
	/**
	 * in查询
	 */
	IN,
	/**
	 * 大于等于
	 */
	GE,
	/**
	 * 小于等于
	 */
	LE,
	/**
	 * 大于
	 */
	GT,
	/**
	 * 小于
	 */
	LT,
	/**
	 * 不等于
	 */
	NE,
	/**
	 * 小于等于
	 */
	NIN,
	/**
	 * 不为NULL
	 */
	ISNULL
}
