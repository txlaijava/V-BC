package com.shopping.base.foundation.view;

import com.shopping.base.foundation.util.ReflectionUtils;
import lombok.Data;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
public class BeanView<T> implements Serializable {

	public String[] ignoreFields;

	@SuppressWarnings("unchecked")
	public void transfer(T bean) {
		List<String> ignores = this.getIgnoreFields()!=null ? Arrays.asList(this.getIgnoreFields()) : null;
		Class<?> viewClass = this.getClass();
		Class<?> beanClass = bean.getClass();
		Field[] viewFields = ReflectionUtils.getAllDeclaredFields(viewClass);
		for (Field viewField : viewFields) {
			try {
				String name = viewField.getName();
				if (ignores!=null && ignores.contains(name)) {
					continue;
				}
				viewField.setAccessible(true);
				PropertyDescriptor beanProperty = ReflectionUtils.findProperty(beanClass, name);
				if (beanProperty == null) {
					continue;
				}
				if (beanProperty.getReadMethod() == null) {
					throw new RuntimeException("实体[" + beanClass.getName() + "],字段[" + name + "]没有getter");
				}
				Class<?> fieldType = viewField.getType();
				Object value = beanProperty.getReadMethod().invoke(bean);
				if (value == null) {
					continue;
				}
				if (BeanView.class.isAssignableFrom(fieldType)) {
					BeanView<Object> view = (BeanView<Object>) fieldType.newInstance();
					view.transfer(value);
					value = view;
				} else if (Collection.class.isAssignableFrom(fieldType)) {
					ParameterizedType genericType = (ParameterizedType) viewField.getGenericType();
					Class<?> cildViewClass = (Class<?>) genericType.getActualTypeArguments()[0];
					if (BeanView.class.isAssignableFrom(cildViewClass)) {
						Collection<BeanView<?>> viewList = new ArrayList<>();
						for (Object item : (Collection<?>) value) {
							BeanView<Object> view = (BeanView<Object>) cildViewClass.newInstance();
							view.transfer(item);
							viewList.add(view);
						}
						value = viewList;
					}
				}
				viewField.set(this, value);
			} catch (Exception e) {

				e.printStackTrace();
				continue;

				// throw new RuntimeException(
				// "视图[" + viewClass.getName() + "],字段[" + viewField.getName() + "]赋值失败:" +
				// e.getMessage());
			}
		}
	}



}