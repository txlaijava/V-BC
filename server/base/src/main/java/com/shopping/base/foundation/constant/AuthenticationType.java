package com.shopping.base.foundation.constant;

/**
 *
 * @since 2017-10-26 认证用户类型
 */
public enum AuthenticationType {

	/**
	 * 管理员
	 */
	ADMIN("admin"),
	/**
	 * 客户
	 */
	CUSTOMER("customer"),
	/**
	 *匿名用户
	 */
	ANONYMOUS("anonymous");

	private String value;

	AuthenticationType(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public boolean is(String value) {
		return this.value.equals(value);
	}
}
