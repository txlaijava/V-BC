package com.shopping.base.foundation.result;


import com.shopping.base.foundation.view.BeanView;
import com.shopping.base.foundation.view.BeanViewUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 查询结果

 * @param <T> 结果类型
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryResult<T> extends ActionResult<List<T>> {

	@Setter(value = AccessLevel.NONE)
	private String orderBy;

	public QueryResult(List<T> result, String orderBy) {
		super();
		this.setCode(0);
		this.setMsg("ok");
		this.setData(result);
		this.orderBy = orderBy;
	}

	public QueryResult() {

	}

	public QueryResult<T> treeSorted(String keyField, String parentKeyField, Object rootValue) {
		try {
			List<T> list = getData();
			if (list.size() <= 0) {
				return this;
			}
			Class<?> itemClass = list.get(0).getClass();
			PropertyDescriptor keyProp = BeanUtils.getPropertyDescriptor(itemClass, keyField);
			PropertyDescriptor parentKeyProp = BeanUtils.getPropertyDescriptor(itemClass, parentKeyField);
			List<T> newList = new ArrayList<>(list.size());
			addTreeNode(newList, list, rootValue, keyProp, parentKeyProp);
			setData(newList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	private void addTreeNode(List<T> newList, List<T> list, Object rootValue, PropertyDescriptor keyProp,
			PropertyDescriptor parentKeyProp) throws ReflectiveOperationException {
		for (T item : list) {
			Object parentId = parentKeyProp.getReadMethod().invoke(item);
			if (!rootValue.equals(parentId)) {
				continue;
			}
			newList.add(item);
			Object id = keyProp.getReadMethod().invoke(item);
			addTreeNode(newList, list, id, keyProp, parentKeyProp);
		}
	}

	public <V extends BeanView<T>> QueryResult<V> getView(Class<V> viewClass) {
		List<V> viewList = BeanViewUtils.getList(this.getData(), viewClass);
		String orderBy = this.getOrderBy();
		return new QueryResult<>(viewList, orderBy);
	}

	public QueryResult<T> filter(Predicate<T> predicate) {
		List<T> content = getData().stream().filter(predicate).collect(Collectors.toList());
		return new QueryResult<>(content, orderBy);
	}

	public void forEach(Consumer<T> action) {
		getData().stream().forEach(action);
	}

	public <R> List<R> mapToList(Function<T, R> mapper) {
		return getData().stream().map(mapper).collect(Collectors.toList());
	}

	public <R> List<R> mapDistinctToList(Function<T, R> mapper) {
		return getData().stream().map(mapper).distinct().collect(Collectors.toList());
	}

	public void sort(Comparator<? super T> comparator) {
		Collections.sort(getData(), comparator);
	}

	public <R> QueryResult<R> mapToResult(Function<T, R> mapper) {
		List<R> content = getData().stream().map(mapper).collect(Collectors.toList());
		return new QueryResult<>(content, orderBy);
	}
}
