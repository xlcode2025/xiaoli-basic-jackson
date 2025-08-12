package com.xiaoli.basic.jackson.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 
 * 工具类。注意：内部用。外面不要引用
 *
 */
public class AssertUtils {

	private AssertUtils() {
		// nothing to do
	}

	/**
	 * 断言判断
	 * 
	 * @param expression 表达式
	 * @param errMsg 错误消息
	 */
	public static void check(boolean expression, String errMsg) {
		if (!expression) {
			throw new IllegalArgumentException(errMsg);
		}
	}

	/**
	 * 不为null
	 * 
	 * @param obj
	 * @param paramName 参数名
	 */
	public static void notNull(Object obj, String paramName) {
		if (obj == null) {
			throw new IllegalArgumentException(paramName + " not all null.");
		}
	}

	/**
	 * 不为空或null
	 * 
	 * @param s
	 * @param errMsg 错误信息
	 */
	public static void notEmpty(String s, String errMsg) {
		if (Utils.isEmpty(s)) {
			throw new IllegalArgumentException(errMsg);
		}
	}

	/**
	 * 不为空或null
	 * 
	 * @param errMsg 错误信息
	 */
	public static void notEmpty(Collection<?> list, String errMsg) {
		if (Utils.isEmpty(list)) {
			throw new IllegalArgumentException(errMsg);
		}
	}

	/**
	 * 不为空或null
	 * 
	 * @param errMsg 错误信息
	 */
	public static <T> void notEmpty(T[] arr, String errMsg) {
		if (Utils.isEmpty(arr)) {
			throw new IllegalArgumentException(errMsg);
		}
	}

	/**
	 * 不为空或null
	 * 
	 * @param errMsg 错误信息
	 */
	public static void notEmpty(Map<?, ?> map, String errMsg) {
		if (Utils.isEmpty(map)) {
			throw new IllegalArgumentException(errMsg);
		}
	}

	public static void noNullElements(Object[] array, String errMsg) {
		if (array == null) {
			return;
		}
		for (Object element : array) {
			if (element == null) {
				throw new IllegalArgumentException(errMsg);
			}
		}
	}

	public static <T> void noNullElements(Collection<T> list, String errMsg) {
		if (list == null) {
			return;
		}
		for (T element : list) {
			if (element == null) {
				throw new IllegalArgumentException(errMsg);
			}
		}
	}

	/**
	 * 值是否在数组
	 */
	@SuppressWarnings("unchecked")
	public static <T> void in(T source, String errMsg, T... targets) {
		for (T item : targets) {
			if (source.equals(item)) {
				return;
			}
		}
		throw new IllegalArgumentException(errMsg);
	}

	/**
	 * 值是否在数组
	 */
	public static <T> void in(T source, T[] targets, String errMsg) {
		in(source, errMsg, targets);
	}

	/**
	 * 值是否在数组
	 */
	public static <T> void in(T source, Collection<T> targets, String errMsg) {
		for (T item : targets) {
			if (source.equals(item)) {
				return;
			}
		}
		throw new IllegalArgumentException(errMsg);
	}
}
