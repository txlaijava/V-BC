/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 将对象转换成Map
 *
 * @version 1.0 created at 2017年5月22日 上午11:20:59
 *
 */
public class MapBuilder<K, V> {

	private Map<K, V> map = new HashMap<>();

	public MapBuilder<K, V> put(K key, V value) {
		map.put(key, value);
		return this;
	}

	public static <K, V> MapBuilder<K, V> create(Class<K> keyClass) {
		return new MapBuilder<K, V>();
	}

	public static <V> MapBuilder<Object, V> create() {
		return new MapBuilder<Object, V>();
	}

	public Map<K, V> build() {
		return new HashMap<>(map);
	}
}
