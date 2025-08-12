package com.xiaoli.basic.jackson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JavaType管理类，有缓存
 * 
 *
 */
public abstract class JavaTypeManger {
	private static final Map<String, JavaType> CACHE = new HashMap<>();

	/**
	 * 
	 * @param <T> 目标类型
	 * @param mapper
	 * @param type 目标类型
	 * @param parameterTypes 泛型参数类型
	 * @return
	 */
	public static <T> JavaType parametric(ObjectMapper mapper, Class<T> type, Class<?>... parameterTypes) {
		String key = "obj_" + type.getName();
		JavaType javaType = CACHE.get(key);
		if (javaType == null) {
			javaType = mapper.getTypeFactory().constructParametricType(type, parameterTypes);
			CACHE.put(key, javaType);
		}
		return javaType;
	}

	public static <T> JavaType list(ObjectMapper mapper, Class<T> type) {
		String key = "list_" + type.getName();
		JavaType javaType = CACHE.get(key);
		if (javaType == null) {
			javaType = mapper.getTypeFactory().constructCollectionType(List.class, type);
			CACHE.put(key, javaType);
		}
		return javaType;
	}

	public static <T> JavaType sets(ObjectMapper mapper, Class<T> type) {
		String key = "sets_" + type.getName();
		JavaType javaType = CACHE.get(key);
		if (javaType == null) {
			javaType = mapper.getTypeFactory().constructCollectionType(Set.class, type);
			CACHE.put(key, javaType);
		}
		return javaType;
	}

	public static <T> JavaType array(ObjectMapper mapper, Class<T> type) {
		String key = "array_" + type.getName();
		JavaType javaType = CACHE.get(key);
		if (javaType == null) {
			javaType = mapper.getTypeFactory().constructArrayType(type);
			CACHE.put(key, javaType);
		}
		return javaType;
	}

	public static <K, V> JavaType map(ObjectMapper mapper, Class<K> keyType, Class<V> valueType) {
		String key = "map_" + keyType.getName() + "_" + valueType.getName();
		JavaType javaType = CACHE.get(key);
		if (javaType == null) {
			javaType = mapper.getTypeFactory().constructMapType(Map.class, keyType, valueType);
			CACHE.put(key, javaType);
		}
		return javaType;
	}
}
