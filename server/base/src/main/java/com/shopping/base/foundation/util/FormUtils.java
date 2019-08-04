/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Form对象工作类
 *
 * @version 1.0 created at 2017年6月5日 下午8:36:44
 *
 */
public class FormUtils {

	/**
	 * 两个对象之间属性值拷贝
	 * @param form 源对象
	 * @param target 目标对象
	 */

	@SuppressWarnings("unchecked")
	public static <F, T, I> void copyProperties(Object form, Object target) {
		try {
			Class<?> targetClass = target.getClass();
			PropertyDescriptor[] properties = BeanUtils.getPropertyDescriptors(form.getClass());
			for (PropertyDescriptor readPd : properties) {
				Method readMethod = readPd.getReadMethod();
				if (readMethod == null) {
					continue;
				}
				String propertyName = readPd.getName();
				PropertyDescriptor targetPd = BeanUtils.getPropertyDescriptor(targetClass, propertyName);
				if (targetPd == null) {
					continue;
				}
				Method writeMethod = targetPd.getWriteMethod();
				if (writeMethod == null) {
					continue;
				}
				Object value = readMethod.invoke(form);
				if (targetPd.getPropertyType().isAssignableFrom(List.class)) {
					Field mappedByField = null;
					Field field = targetClass.getDeclaredField(propertyName);
					OneToMany oneToMany = field.getAnnotation(OneToMany.class);
					OrderBy orderBy = field.getAnnotation(OrderBy.class);
					Class<I> itemClass = getListItemType(field);
					if (itemClass.isAnnotationPresent(Entity.class)) {
						List<String> keyPropNameList = getEntityKeyNames(itemClass);
						if (oneToMany != null && !StringUtils.isEmpty(oneToMany.mappedBy())) {
							mappedByField = itemClass.getDeclaredField(oneToMany.mappedBy());
							if (mappedByField != null) {
								keyPropNameList.remove(mappedByField.getName());
								JoinColumn joinColumn = mappedByField.getAnnotation(JoinColumn.class);
								if (joinColumn != null) {
									keyPropNameList.remove(joinColumn.name());
								}
							}
						}
						String[] keyPropNames = new String[keyPropNameList.size()];
						keyPropNameList.toArray(keyPropNames);
						List<I> items = (List<I>) targetPd.getReadMethod().invoke(target);
						mergeToBeans((List<?>) value, items, itemClass, keyPropNames);
						// 排序
						if (orderBy != null) {
							String sortName = orderBy.value();
							int order = 1;
							for (I item : items) {
								Field sortField = itemClass.getDeclaredField(sortName);
								sortField.setAccessible(true);
								sortField.set(item, order++);
							}
						}
						if (mappedByField != null) {
							JoinColumn joinColumn = mappedByField.getAnnotation(JoinColumn.class);
							if (joinColumn != null && (joinColumn.insertable() || joinColumn.updatable())) {
								mappedByField.setAccessible(true);
								for (I item : items) {
									mappedByField.set(item, target);
								}
							}
						}
						List<Field> createDateFieldList = getEntityCreateDate(itemClass);
						if (!createDateFieldList.isEmpty()) {
							for (I item : items) {
								for (Field createDateField : createDateFieldList) {
									createDateField.setAccessible(true);
									Object date = createDateField.get(item);
									if (date == null) {
										createDateField.set(item, new Date());
									}
								}
							}
						}
					}
				} else {
					if (value == null) {
						if (ReflectionUtils.isComplexType(writeMethod.getParameterTypes()[0])) {
							writeMethod.invoke(target, value);
						}
					} else {
						writeMethod.invoke(target, value);
					}

				}
			}
			List<Field> createDateFieldList = getEntityCreateDate(targetClass);
			if (!createDateFieldList.isEmpty()) {
				for (Field createDateField : createDateFieldList) {
					createDateField.setAccessible(true);
					Object date = createDateField.get(target);
					if (date == null) {
						createDateField.set(target, new Date());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	private static <T, F> void mergeToBeans(List<F> forms, List<T> items, Class<T> itemClass, String... keyPropNames) {
		if (forms == null) {
			return;
		}
		PropertyDescriptor[] itemProps = new PropertyDescriptor[keyPropNames.length];
		PropertyDescriptor[] formProps = new PropertyDescriptor[keyPropNames.length];
		for (int i = 0; i < keyPropNames.length; i++) {
			String propName = keyPropNames[i];
			if (forms.size() > 0) {
				formProps[i] = BeanUtils.getPropertyDescriptor(forms.get(0).getClass(), propName);
			}
			itemProps[i] = BeanUtils.getPropertyDescriptor(itemClass, propName);
		}
		try {
			for (int i = 0; i < items.size(); i++) {
				Object item = items.get(i);
				Object form = findForm(forms, item, formProps, itemProps);
				// 当前列表中不存在
				if (form == null) {
					items.remove(i);
					i--;
				} else {
					copyProperties(form, item);
					forms.remove(form);
				}
			}
			for (F form : forms) {
				T item = findItem(items, form, formProps, itemProps);
				if (item == null) {
					item = itemClass.newInstance();
				}
				copyProperties(form, item);
				items.add(item);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> getListItemType(Field field) {
		return (Class<T>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
	}

	private static List<String> getEntityKeyNames(Class<?> entityClass) {
		List<String> keyProperties = new ArrayList<>(2);
		for (Field field : entityClass.getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class)) {
				keyProperties.add(field.getName());
			}
		}
		return keyProperties;
	}

	private static List<Field> getEntityCreateDate(Class<?> entityClass) {
		List<Field> fieldList = new ArrayList<>(1);
		for (Field field : entityClass.getDeclaredFields()) {
			if (field.isAnnotationPresent(CreatedDate.class)) {
				fieldList.add(field);
			}
		}
		return fieldList;
	}

	private static <T, F> F findForm(List<F> forms, T item, PropertyDescriptor[] formProps,
			PropertyDescriptor[] itemProps) throws ReflectiveOperationException {
		for (int i = 0; i < forms.size(); i++) {
			F form = forms.get(i);
			if (isEqual(form, item, formProps, itemProps)) {
				return form;
			}
		}
		return null;
	}

	private static <T, F> T findItem(List<T> items, F form, PropertyDescriptor[] formProps,
			PropertyDescriptor[] itemProps) throws ReflectiveOperationException {
		for (int i = 0; i < items.size(); i++) {
			T item = items.get(i);
			if (isEqual(form, item, formProps, itemProps)) {
				return item;
			}
		}
		return null;
	}

	private static <T, F> boolean isEqual(F form, T item, PropertyDescriptor[] formProps,
			PropertyDescriptor[] itemProps) throws ReflectiveOperationException {
		for (int i = 0; i < formProps.length; i++) {
			PropertyDescriptor formProp = formProps[i];
			if (formProp == null) {
				return false;
			}
			PropertyDescriptor itemProp = itemProps[i];
			Object fval = formProp.getReadMethod().invoke(form);
			Object ival = itemProp.getReadMethod().invoke(item);
			if (fval == null) {
				if (ival != null) {
					return false;
				}
			} else if (!fval.equals(ival)) {
				return false;
			}
		}
		return true;
	}

}
