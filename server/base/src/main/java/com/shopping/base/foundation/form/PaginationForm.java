package com.shopping.base.foundation.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

/**
 * 分页查询表单
 * 
 *
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PaginationForm extends BaseQueryForm implements Serializable {

	/**
	 * 当前页
	 */
	private int currentPage = 1;

	/**
	 * 分页大小
	 */
	private int pageSize = 10;

	public Pageable buildPageRequest() {
		return new PageRequest(currentPage - 1, pageSize);
	}

}
