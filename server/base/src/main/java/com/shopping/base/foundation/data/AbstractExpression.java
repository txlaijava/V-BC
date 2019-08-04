/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.data;

import org.springframework.data.mongodb.core.query.Criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 查询表达式，包括JPA和MongoDB
 *
 * @version 1.0 created at 2017年5月26日 下午1:08:37
 *
 */
public abstract class AbstractExpression {

	protected Object value;

	public AbstractExpression(Object value) {
		this.value = value;
	}

	public abstract Predicate buildJpaPredicate(CriteriaBuilder cb, Root<?> root, Object expression);

	public abstract Criteria buildMongoCriteria(Object expression);

}
