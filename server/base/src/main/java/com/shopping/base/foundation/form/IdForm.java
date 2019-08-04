package com.shopping.base.foundation.form;

import lombok.Data;

/**
 * ID 查询 on 2017/6/9.
 */
@Data
public class IdForm {

	private Long id;

	public IdForm(Long id) {
		this.id = id;
	}

	public IdForm() {

	}
}
