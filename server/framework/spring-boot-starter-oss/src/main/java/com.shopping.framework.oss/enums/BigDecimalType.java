package com.shopping.framework.oss.enums;

/**
 * @Author 没有用户名
 * @Date 2017/8/25
 * @Description
 */

public enum BigDecimalType {
	ROUND_HALF_UP(4),
	ROUND_HALF_DOWN(5);

	private final int value;

	private BigDecimalType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}

