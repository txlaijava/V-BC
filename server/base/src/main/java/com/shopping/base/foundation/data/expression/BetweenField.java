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
public class BetweenField extends AbstractExpression {

	public BetweenField(String field1, String field2) {
		super(new String[] { field1, field2 });
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Predicate buildJpaPredicate(CriteriaBuilder cb, Root root, Object expression) {
		String field1 = (String) Array.get(value, 0);
		String field2 = (String) Array.get(value, 1);
		Expression<Comparable> path1 = QueryFormHelper.getPath(root, field1);
		Expression<Comparable> path2 = QueryFormHelper.getPath(root, field2);
		Expression<Comparable> parameter = cb.literal((Comparable) expression);
		return cb.between(parameter, path1, path2);
	}

	@Override
	public Criteria buildMongoCriteria(Object expression) {
		String field1 = (String) Array.get(value, 0);
		String field2 = (String) Array.get(value, 1);
		Object value = expression;
		return Criteria.where(field1).lte(value).and(field2).gte(value);
	}
}
