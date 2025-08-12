package com.xiaoli.basic.jackson;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据视图过滤器管理器
 * 
 *
 */
public class JsonViewFilterManager {
	private static final Map<Class<?>, Class<?>> MAP = new HashMap<Class<?>, Class<?>>();

	private JsonViewFilterManager() {
		// nothing to do
	}

	public static boolean isEmpty() {
		return MAP.isEmpty();
	}

	/**
	 * 添加过滤器
	 * 
	 * @param targetClass
	 * @param filter
	 *            属性过滤器.
	 *            见{@link com.fasterxml.jackson.annotation.JsonIgnoreProperties}、{@link com.fasterxml.jackson.annotation.JsonProperty}用法
	 */
	public static void addFilter(Class<?> targetClass, Class<?> filter) {
		MAP.put(targetClass, filter);
	}

	public static Class<?> getFilter(Class<?> targetClass) {
		return MAP.get(targetClass);
	}

	public static Map<Class<?>, Class<?>> getAll() {
		return new HashMap<>(MAP);
	}
}
