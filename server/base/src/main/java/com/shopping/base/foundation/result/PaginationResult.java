package com.shopping.base.foundation.result;


import com.shopping.base.foundation.form.PaginationForm;
import com.shopping.base.foundation.view.BeanView;
import com.shopping.base.foundation.view.BeanViewUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 分页查询结果

 * @param <T> 结果类型
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PaginationResult<T> extends QueryResult<T> {

	@Setter(value = AccessLevel.NONE)
	private int pageSize;

	@Setter(value = AccessLevel.NONE)
	private int currentPage;

	@Setter(value = AccessLevel.NONE)
	private long total;

	@Setter(value = AccessLevel.NONE)
	private int pages;

	public PaginationResult(long total, int pageSize, int currentPage, List<T> list, String orderBy) {
		super(list, orderBy);
		this.total = total;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.pages = (int) Math.ceil((double) total / pageSize);
	}

	public PaginationResult(PaginationResult<?> result, List<T> list) {
		super(list, result.getOrderBy());
		this.total = result.total;
		this.pageSize = result.pageSize;
		this.currentPage = result.currentPage;
		this.pages = result.pages;
	}

	public PaginationResult(PaginationResult<T> result) {
		super(result.getData(), result.getOrderBy());
		this.total = result.total;
		this.pageSize = result.pageSize;
		this.currentPage = result.currentPage;
		this.pages = result.pages;
	}

	public PaginationResult(Page<?> page, List<T> list, PaginationForm form) {
		super(list, form.getOrderBy());
		this.total = page.getTotalElements();
		this.pageSize = form.getPageSize();
		this.currentPage = form.getCurrentPage();
		this.pages = (int) Math.ceil((double) total / pageSize);
	}

	public PaginationResult(Page<T> page, PaginationForm form) {
		this(page, page.getContent(), form);
	}

	public PaginationResult() {

	}

	@Override
	public <V extends BeanView<T>> QueryResult<V> getView(Class<V> viewClass) {
		List<V> viewList = BeanViewUtils.getList(this.getData(), viewClass);
		long total = this.total;
		int pageSize = this.pageSize;
		int currentPage = this.currentPage;
		String orderBy = this.getOrderBy();
		return new PaginationResult<>(total, pageSize, currentPage, viewList, orderBy);
	}

	@Override
	public PaginationResult<T> filter(Predicate<T> predicate) {
		List<T> content = getData().stream().filter(predicate).collect(Collectors.toList());
		return new PaginationResult<>(this, content);
	}

	@Override
	public <R> QueryResult<R> mapToResult(Function<T, R> mapper) {
		List<R> content = getData().stream().map(mapper).collect(Collectors.toList());
		return new PaginationResult<>(this, content);
	}
}
