/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.data;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;

/**
 * 用于查询排表达式
 *
 *
 */
class OrderImpl implements Order {

	private final Expression<?> expression;
	private boolean ascending;

	public OrderImpl(Expression<?> expression) {
		this(expression, true);
	}

	public OrderImpl(Expression<?> expression, boolean ascending) {
		this.expression = expression;
		this.ascending = ascending;
	}

	@Override
	public Order reverse() {

		ascending = !ascending;
		return this;
	}

	@Override
	public boolean isAscending() {
		return ascending;
	}

	@Override
	public Expression<?> getExpression() {
		return expression;
	}
}
