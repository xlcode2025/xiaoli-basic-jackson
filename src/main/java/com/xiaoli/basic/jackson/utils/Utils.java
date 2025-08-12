package com.xiaoli.basic.jackson.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 
 * 工具类。注意：内部用。外面不要引用
 *
 */
public class Utils {
	private Utils() {
		// nothing to do
	}
	
	public static boolean isEmpty(Collection<?> list) {
		return list == null || list.isEmpty();
	}

	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	public static boolean isEmpty(String s) {
		return s == null || s.isEmpty();
	}

	public static <T> boolean isEmpty(T[] arr) {
		return (arr == null || arr.length == 0);
	}

	public static <T> boolean isEmpty(byte[] arr) {
		return (arr == null || arr.length == 0);
	}
	
	public static void addValues(Set<String> sets, String... values) {
		if (isEmpty(values)) {
			return;
		}
		for (String p : values) {
			if (p == null) {
				continue;
			}
			sets.add(p);
		}
	}
}
