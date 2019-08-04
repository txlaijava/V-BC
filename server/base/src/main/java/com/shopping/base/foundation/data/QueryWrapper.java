/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * 查询包装
 * 
 *
 *
 */
public interface QueryWrapper {

	public void wrap(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder, boolean sort);
}
