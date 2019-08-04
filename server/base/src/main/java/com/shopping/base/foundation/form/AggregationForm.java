package com.shopping.base.foundation.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 聚合查询表单
 * @author chendaoxing
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AggregationForm extends PaginationForm {

	/**
	 * 筛选项 {"brand":['10023','10989'],"class":["1","345"]}
	 * 
	 * brand和class之间是and查询 brand内部多个值之间是or查询
	 */
	@Getter
	@Setter
	private Map<String, Object> filters;

	public void addFilter(String name, Object value) {
		if (filters == null) {
			filters = new HashMap<>();
		}
		filters.put(name, value);
	}

}
