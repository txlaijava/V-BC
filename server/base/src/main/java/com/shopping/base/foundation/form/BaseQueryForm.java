/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.form;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.LockModeType;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询表单 封装
 * 
 *
 *
 */
public abstract class BaseQueryForm {

	/**
	 * 模糊查找关键字
	 */
	@Getter
	@Setter
	private String searchText;

	/**
	 * 关键字所关联的字段
	 */
	@Getter
	private String[] searchFields;

	/**
	 * 只读锁
	 */
	@Getter
	private LockModeType lockMode = LockModeType.NONE;

	/**
	 * 排序方式
	 */
	@Getter
	@Setter
	private String orderBy;

	protected Map<String, String> orderMappings = new HashMap<String, String>();

	/**
	 * 设置 模糊查找关键字所关联的字段
	 * @param searchFields
	 */
	public void setSearchFields(String... searchFields) {
		this.searchFields = searchFields;
	}

	/**
	 * 获取排序信息
	 * @return
	 */
	public String getOrderMapping() {
		String mapping = orderBy;
		if (mapping != null) {
			mapping = orderMappings.get(mapping);
		}
		return mapping == null ? orderBy : mapping;
	}

	/**
	 * 使用悲观锁
	 */
	public void lock() {
		this.lockMode = LockModeType.PESSIMISTIC_WRITE;
	}
	/**
	 * 设置用锁方式
	 */
	public void lock(LockModeType lockMode) {
		this.lockMode = lockMode;
	}

}
