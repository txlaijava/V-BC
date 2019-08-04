/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.data.expression;


import com.shopping.base.foundation.data.AbstractExpression;
import com.shopping.base.foundation.data.QueryFormHelper;
import org.springframework.data.mongodb.core.query.Criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @version 1.0 created at 2017年5月26日 下午1:08:21
 *
 */
public class NotEqual extends AbstractExpression {

	public NotEqual(Object value) {
		super(value);
	}

	@Override
	public Predicate buildJpaPredicate(CriteriaBuilder builder, Root<?> root, Object expression) {
		return builder.notEqual(QueryFormHelper.getPath(root, expression.toString()), value);
	}

	@Override
	public Criteria buildMongoCriteria(Object expression) {
		String path = expression.toString();
		return Criteria.where(path).ne(value);
	}
}
