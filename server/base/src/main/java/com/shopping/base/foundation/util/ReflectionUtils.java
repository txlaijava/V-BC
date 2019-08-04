/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @version 1.0 created at 2017年4月22日 下午2:52:11
 *
 */
public class ReflectionUtils {

	public static Field[] getAllDeclaredFields(Class<?> beanClass) {
		Map<String, Field> fieldMap = getFieldMap(beanClass);
		Field[] array = new Field[fieldMap.size()];
		fieldMap.values().toArray(array);
		return array;
	}

	public static Field findDeclaredField(Class<?> beanClass, String name) {
		Map<String, Field> fieldMap = getFieldMap(beanClass);
		return fieldMap.get(name);
	}

	public static PropertyDescriptor[] getProperties(Class<?> beanClass) {
		Map<String, PropertyDescriptor> propertyMap = getPropertyMap(beanClass);
		PropertyDescriptor[] array = new PropertyDescriptor[propertyMap.size()];
		propertyMap.values().toArray(array);
		return array;
	}

	public static PropertyDescriptor findProperty(Class<?> beanClass, String name) {
		Map<String, PropertyDescriptor> propertyMap = getPropertyMap(beanClass);
		return propertyMap.get(name);
	}

	public static Class<?> getUnboxedType(Class<?> type) {
		if (type == Character.class) {
			return char.class;
		}
		if (type == Boolean.class) {
			return boolean.class;
		}
		if (type == Double.class) {
			return double.class;
		}
		if (type == Double.class) {
			return double.class;
		}
		if (type == Long.class) {
			return long.class;
		}
		if (type == Integer.class) {
			return int.class;
		}
		if (type == Short.class) {
			return short.class;
		}
		if (type == Byte.class) {
			return byte.class;
		}
		return type;
	}

	public static boolean isComplexType(Class<?> type) {
		if (type.isPrimitive()) {
			return false;
		}
		if (getUnboxedType(type).isPrimitive()) {
			return false;
		}
		if (type == String.class) {
			return false;
		}
		if (type == Date.class) {
			return false;
		}
		if (type.isArray()) {
			type = type.getComponentType();
			if (type != null) {
				return isComplexType((Class<?>) type);
			}
			return false;
		}
		return true;
	}

	///////////////////////////////// 私有方法
	///////////////////////////////// ///////////////////////////////////////

	private static ConcurrentHashMap<Class<?>, Map<String, Field>> fieldMapPool = new ConcurrentHashMap<>();

	private static ConcurrentHashMap<Class<?>, Map<String, PropertyDescriptor>> propertyMapPool = new ConcurrentHashMap<>();

	private static Map<String, Field> getFieldMap(Class<?> beanClass) {
		Map<String, Field> fieldMap = fieldMapPool.get(beanClass);
		if (fieldMap != null) {
			return fieldMap;
		}
		fieldMap = new HashMap<String, Field>();
		addFields(beanClass, fieldMap);
		fieldMapPool.put(beanClass, fieldMap);
		return fieldMap;
	}

	private static Map<String, PropertyDescriptor> getPropertyMap(Class<?> beanClass) {
		Map<String, PropertyDescriptor> propertyMap = propertyMapPool.get(beanClass);
		if (propertyMap != null) {
			return propertyMap;
		}
		propertyMap = new HashMap<String, PropertyDescriptor>();
		addProperties(beanClass, propertyMap);
		propertyMapPool.put(beanClass, propertyMap);
		return propertyMap;
	}

	private static void addFields(Class<?> beanClass, Map<String, Field> fieldMap) {
		for (Field field : beanClass.getDeclaredFields()) {
			if (!fieldMap.containsKey(field.getName())) {
				fieldMap.put(field.getName(), field);
			}
		}
		Class<?> superclass = beanClass.getSuperclass();
		if (superclass != null && superclass != Object.class) {
			addFields(superclass, fieldMap);
		}
	}

	private static void addProperties(Class<?> beanClass, Map<String, PropertyDescriptor> propertyMap) {
		for (Method method : beanClass.getMethods()) {
			String methodName = method.getName();
			if (!methodName.startsWith("get") && !methodName.startsWith("set") && !methodName.startsWith("is")) {
				continue;
			}
			String upperCaseName = null;
			if (methodName.startsWith("is")) {
				upperCaseName = methodName.substring(2);
			} else {
				upperCaseName = methodName.substring(3);
			}
			if ("Class".equals(upperCaseName)) {
				continue;
			}
			char[] chars = upperCaseName.toCharArray();
			chars[0] = Character.toLowerCase(chars[0]);
			String propertyName = new String(chars);
			try {
				PropertyDescriptor property = propertyMap.get(propertyName);
				if (property == null) {
					property = new PropertyDescriptor(propertyName, beanClass, null, null);
					propertyMap.put(propertyName, property);
				}
				if (methodName.startsWith("get") || methodName.startsWith("is")) {
					if (property.getReadMethod() == null) {
						property.setReadMethod(method);
					}
				} else {
					if (property.getWriteMethod() == null) {
						property.setWriteMethod(method);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		Class<?> superclass = beanClass.getSuperclass();
		if (superclass != null && superclass != Object.class) {
			addProperties(superclass, propertyMap);
		}
	}

}