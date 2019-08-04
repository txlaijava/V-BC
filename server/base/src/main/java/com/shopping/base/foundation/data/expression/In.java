/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.data.expression;


import com.shopping.base.foundation.data.AbstractExpression;
import com.shopping.base.foundation.data.QueryFormHelper;
import org.springframework.data.mongodb.core.query.Criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Array;

/**
 *
 * @version 1.0 created at 2017年5月26日 下午1:08:21
 *
 */
public class In extends AbstractExpression {

	public <T> In(T value) {
		super(value);
	}

	@Override
	public Predicate buildJpaPredicate(CriteriaBuilder builder, Root<?> root, Object expression) {
		Expression<Object> path = QueryFormHelper.getPath(root, expression.toString());
		CriteriaBuilder.In<Object> predicate = builder.in(path);
		int length = Array.getLength(value);
		for (int i = 0; i < length; i++) {
			predicate.value(Array.get(value, i));
		}
		return predicate;
	}

	@Override
	public Criteria buildMongoCriteria(Object expression) {
		String path = expression.toString();
		Object[] values = (Object[]) value;
		return Criteria.where(path).in(values);
	}
}
