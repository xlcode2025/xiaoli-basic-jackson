package com.xiaoli.basic.jackson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Json视图过滤声明注解类
 * 
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonViewFilter {
	/**
	 * 目标对象
	 */
	Class<?> value();
}
