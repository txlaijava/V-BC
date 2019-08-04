/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.exception;

/**
 *
 * @version 1.0 created at 2017年4月19日 下午9:26:20
 *
 */
public class InterruptReturnException extends RuntimeException {

	private static final long serialVersionUID = -4749912019992317139L;

	private Object value;

	public InterruptReturnException(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}
}
