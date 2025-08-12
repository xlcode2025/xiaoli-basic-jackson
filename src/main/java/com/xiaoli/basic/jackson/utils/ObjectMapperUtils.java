package com.xiaoli.basic.jackson.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.xiaoli.basic.jackson.JsonViewFilterManager;

public class ObjectMapperUtils {
	
	public static ObjectMapper create() {
		 return create(true, false);
	}
	
	public static ObjectMapper create(boolean ignoreNullValue) {
		 return create(ignoreNullValue, false);
	}

	/**
	 * 
	 * @param ignoreNullObject
	 *            过滤对象的null属性.
	 * @param strictCheckJson
	 *            严格检查json串
	 * 
	 */
	public static ObjectMapper create(boolean ignoreNullValue, boolean strictCheckJson) {
		ObjectMapper mapper = JsonMapper.builder().enable(MapperFeature.PROPAGATE_TRANSIENT_MARKER).build();  
		
		// 过滤对象的null属性.
		if (ignoreNullValue) {
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
		// 忽略多余的字段
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	


		if (!strictCheckJson) {
			// 允许单引号
			mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
			// 允许无引号包括的字段
			mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		}
		bindFilterManager(mapper);
		return mapper;
	}
	
	@SuppressWarnings("deprecation")
	public static ObjectMapper initMapper(ObjectMapper mapper) {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// 忽略多余的字段
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 允许单引号
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		// 允许无引号包括的字段
		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.enable(MapperFeature.PROPAGATE_TRANSIENT_MARKER);
		return mapper;
	}

	/**
	 * 绑定视图过滤器{@link com.xiaoli.basic.jackson.JsonViewFilterManager}
	 */
	public static void bindFilterManager(ObjectMapper objectMapper) {
		objectMapper.setMixIns(JsonViewFilterManager.getAll());
	}
}
