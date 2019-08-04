/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.data.aggregate;

/**
 *
 * @version 1.0 created at 2017年6月23日 下午6:49:38
 *
 */
public enum AggregateType {

	/**
	 * 不
	 */
	NONE,
	/**
	 * 求和
	 */
	SUM,
	/**
	 * 计数
	 */
	COUNT,
	/**
	 * 最大值
	 */
	MAX,
	/**
	 * 最小值
	 */
	MIN,
	/**
	 * 求平均值
	 */
	AVG
}
