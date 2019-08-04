package com.shopping.base.foundation.result;

import com.shopping.base.foundation.form.PaginationForm;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 聚合+分页查询结果
 * @param <T> 结果类型
 */

/**
 * 示例
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AggregationResult<T> extends PaginationResult<T> {

	/**
	 * 聚合信息
	 */
	@Setter
	@Getter
	private List<Aggregation> aggs = new ArrayList<Aggregation>();

	public AggregationResult() {

	}

	public AggregationResult(Page<?> page, List<T> list, PaginationForm form) {
		super(page, list, form);
	}

	public void addAgg(Aggregation agg) {
		aggs.add(agg);
	}

	@Data
	public static class Aggregation {

		private String name;

		private String lable;

		private List<AggregationItem> items = new ArrayList<AggregationItem>();

		public void addItem(AggregationItem item) {
			this.items.add(item);
		}
	}

	@Data
	@EqualsAndHashCode(callSuper = false)
	public static class AggregationItem implements Serializable {
		/**
		 *
		 */
		private static final long serialVersionUID = 8555864780368215327L;

		/**
		 * 聚合名称
		 */
		@Setter
		@Getter
		private String name;
		/**
		 * 显示名称
		 */
		@Setter
		@Getter
		private String lable;
		/**
		 * 项目实际值
		 */
		@Setter
		@Getter
		private String value;
	}
}
