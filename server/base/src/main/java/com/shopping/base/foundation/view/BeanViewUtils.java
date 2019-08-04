package com.shopping.base.foundation.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class BeanViewUtils {

	public static <V extends BeanView<T>, T> V getView(T bean, Class<V> viewClass) {
		V view;
		try {
			view = viewClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("创建视图[" + viewClass.getName() + "]失败:" + e.getMessage());
		}
		view.transfer(bean);
		return view;
	}

	public static <V extends BeanView<T>, T> V getView(T bean, Class<V> viewClass,String[] ignoreFields) {
		V view;
		try {
			view = viewClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("创建视图[" + viewClass.getName() + "]失败:" + e.getMessage());
		}
		view.setIgnoreFields(ignoreFields);
		view.transfer(bean);
		return view;
	}

	public static <V extends BeanView<T>, T> List<V> getList(Collection<T> collection, Class<V> viewClass) {
		List<V> viewList = new ArrayList<V>();
		for (T item : collection) {
			V view = getView(item, viewClass);
			viewList.add(view);
		}
		return viewList;
	}

	public static <V extends BeanView<T>, T> List<V> getList(Stream<T> collection, Class<V> viewClass) {
		return collection.map(item -> {
			return getView(item, viewClass);
		}).collect(Collectors.toList());
	}
}
