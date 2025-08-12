package com.xiaoli.basic.jackson;

import java.io.OutputStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.xiaoli.basic.jackson.utils.ObjectMapperUtils;
import com.xiaoli.basic.jackson.utils.Utils;

/**
 * 基于Jackson JSON工具类。<br/>
 * 支持：
 * <p>
 * 1)过滤null值属性;
 * </p>
 * <p>
 * 2)日期格式化;
 * </p>
 * <p>
 * 3)可以通过{@link com.xiaoli.basic.jackson.JsonViewFilterManager}维护过滤视图
 * </p>
 * 
 * <b>注意：</b>解析时，不支持泛型里再套泛型
 * 
 *
 */
public class JacksonUtils {
	private static final String DATE_FM = "yyyy-MM-dd HH:mm:ss";

	private static JavaTimeModule timeModule = new JavaTimeModule(); // java.time序列化支持

	private static ObjectMapper mapper = getFilterMapper();
	private static ObjectMapper mapper2 = getFilterMapper(false);
	private static TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<Map<String, Object>>() {
	};

	private JacksonUtils() {
		// nothing to do
	}

	/**
	 * 支持视图过滤。 See {@link com.xiaoli.basic.jackson.JsonViewFilterManager}
	 * 
	 */
	public static ObjectMapper getFilterMapper() {
		return getFilterMapper(true);
	}

	/**
	 * 支持视图过滤。 See {@link com.xiaoli.basic.jackson.JsonViewFilterManager}
	 * 
	 * @param formatDate
	 *            是否启用"yyyy-MM-dd HH:mm:ss"格式化日期
	 */
	public static ObjectMapper getFilterMapper(boolean formatDate) {
		ObjectMapper mapper = ObjectMapperUtils.create();
		if (formatDate) {
			setDateFormat(mapper);
		}
		mapper.registerModule(timeModule); // java.time序列化支持
		return mapper;
	}

	/**
	 * @param formatDate
	 *            是否启用"yyyy-MM-dd HH:mm:ss"格式化日期
	 * @return
	 */
	public static ObjectMapper getMapper(boolean formatDate) {
		return initMapper(new ObjectMapper(), formatDate);
	}

	public static ObjectMapper initMapper(ObjectMapper mapper) {
		return ObjectMapperUtils.initMapper(mapper);
	}

	/**
	 * 
	 * @param mapper
	 * @param formatDate
	 * @param formatDate
	 *            是否启用"yyyy-MM-dd HH:mm:ss"格式化日期
	 * @return
	 */
	public static ObjectMapper initMapper(ObjectMapper mapper, boolean formatDate) {
		initMapper(mapper);
		if (formatDate) {
			setDateFormat(mapper);
		}
		return mapper;
	}

	public static void setDateFormat(ObjectMapper mapper, String fm) {
		mapper.setDateFormat(new SimpleDateFormat(fm));
		JavaTimeModule module = new JavaTimeModule();  
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(fm);  
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));  
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));  
        mapper.registerModule(module);  
	}

	public static void setDateFormat(ObjectMapper mapper) {
		setDateFormat(mapper, DATE_FM);
	}

	/**
	 * 转json串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 转json字节(UTF-8)
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] toJsonBytes(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			return mapper.writeValueAsBytes(obj);
		} catch (JsonProcessingException e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 转json串
	 * 
	 * @param obj
	 * @param formatDate
	 *            是否格式化日期，如果true，日期转为字符串输出
	 * @return
	 */
	public static String toJson(Object obj, boolean formatDate) {
		if (obj == null) {
			return null;
		}
		try {
			if (formatDate) {
				return mapper.writeValueAsString(obj);
			} else {
				return mapper2.writeValueAsString(obj);
			}
		} catch (JsonProcessingException e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 转json字节(UTF-8)
	 * 
	 * @param obj
	 * @param formatDate
	 *            是否格式化日期，如果true，日期转为字符串输出
	 * @return
	 */
	public static byte[] toJsonBytes(Object obj, boolean formatDate) {
		if (obj == null) {
			return null;
		}
		try {
			if (formatDate) {
				return mapper.writeValueAsBytes(obj);
			} else {
				return mapper2.writeValueAsBytes(obj);
			}
		} catch (JsonProcessingException e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 转json串
	 * 
	 * @param obj
	 * @param propertyFilter
	 *            属性过滤器.
	 *            见{@link com.fasterxml.jackson.annotation.JsonIgnoreProperties}、{@link com.fasterxml.jackson.annotation.JsonProperty}用法
	 * @return
	 */
	public static String toJson(Object obj, Class<?> propertyFilter) {
		if (obj == null) {
			return null;
		}
		ObjectMapper mapper = getMapper(true);
		mapper.addMixIn(obj.getClass(), propertyFilter);
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 转json字节 (UTF-8)
	 * 
	 * @param obj
	 * @param propertyFilter
	 *            属性过滤器.
	 *            见{@link com.fasterxml.jackson.annotation.JsonIgnoreProperties}、{@link com.fasterxml.jackson.annotation.JsonProperty}用法
	 * @return
	 */
	public static byte[] toJsonBytes(Object obj, Class<?> propertyFilter) {
		if (obj == null) {
			return null;
		}
		ObjectMapper mapper = getMapper(true);
		mapper.addMixIn(obj.getClass(), propertyFilter);
		try {
			return mapper.writeValueAsBytes(obj);
		} catch (JsonProcessingException e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析为对象
	 * 
	 * @param <T>
	 * @param s
	 * @param clazz
	 *            目标类型, 不能含有泛型；若含有泛型，See
	 *            {@link #parseObject(String, Class, Class...)}
	 * @return
	 */
	public static <T> T parseObject(String s, Class<T> type) {
		if (Utils.isEmpty(s)) {
			return null;
		}
		try {
			return mapper.readValue(s, type);
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析为对象。只支持二级泛型，不支持三级泛型
	 * 
	 * @param <T>
	 *            目标类型
	 * @param s
	 * @param type
	 *            目标类型
	 * @param parameterTypes
	 *            泛型参数类型
	 * @return
	 */
	public static <T> T parseObject(String s, Class<T> type, Class<?>... parameterTypes) {
		if (Utils.isEmpty(s)) {
			return null;
		}
		try {
			return mapper.readValue(s, mapper.getTypeFactory().constructParametricType(type, parameterTypes));
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析为对象。多级泛型
	 * 
	 * @param <T>
	 *            目标类型
	 * @param s
	 * @param type
	 *            目标类型
	 * @param parameterTypes
	 *            泛型参数类型.
	 * @see {@link TypeFactory#constructParametricType(Class, JavaType...)}
	 * @since 2.2.0
	 * @return
	 */
	public static <T> T parseObject(String s, Class<T> type, JavaType... parameterTypes) {
		if (Utils.isEmpty(s)) {
			return null;
		}
		try {
			return mapper.readValue(s, mapper.getTypeFactory().constructParametricType(type, parameterTypes));
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析为对象
	 * 
	 * @param <T>
	 * @param data
	 *            (UTF-8)
	 * @param type
	 *            目标类型, 不能含有泛型；若含有泛型
	 * @return
	 */
	public static <T> T parseObject(byte[] data, Class<T> type) {
		if (Utils.isEmpty(data)) {
			return null;
		}
		try {
			return mapper.readValue(data, type);
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析为对象。只支持二级泛型，不支持三级泛型
	 * 
	 * @param <T>
	 * @param data
	 * @param type
	 *            目标类型
	 * @param parameterTypes
	 *            泛型参数类型
	 * @return
	 */
	public static <T> T parseObject(byte[] data, Class<T> type, Class<?>... parameterTypes) {
		if (Utils.isEmpty(data)) {
			return null;
		}
		try {
			return mapper.readValue(data, mapper.getTypeFactory().constructParametricType(type, parameterTypes));
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析为对象。多级泛型
	 * 
	 * @param <T>
	 * @param data
	 * @param type
	 *            目标类型
	 * @param parameterTypes
	 *            泛型参数类型
	 * @since 2.2.0
	 * @return
	 */
	public static <T> T parseObject(byte[] data, Class<T> type, JavaType... parameterTypes) {
		if (Utils.isEmpty(data)) {
			return null;
		}
		try {
			return mapper.readValue(data, mapper.getTypeFactory().constructParametricType(type, parameterTypes));
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析为list
	 * 
	 * @param <T>
	 *            泛型类型。
	 * @param s
	 * @param type
	 *            泛型类型。不支持再嵌套泛型
	 * @return
	 */
	public static <T> List<T> parseList(String s, Class<T> type) {
		if (Utils.isEmpty(s)) {
			return null;
		}
		try {
			return mapper.readValue(s, JavaTypeManger.list(mapper, type));
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析为list
	 * 
	 * @param <T>
	 * @param data
	 * @param type
	 *            泛型类型。不支持再嵌套泛型
	 * @return
	 */
	public static <T> List<T> parseList(byte[] data, Class<T> type) {
		if (Utils.isEmpty(data)) {
			return null;
		}
		try {
			return mapper.readValue(data, JavaTypeManger.list(mapper, type));
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析为Set
	 * 
	 * @param <T>
	 * @param s
	 * @param type
	 *            泛型类型。不支持再嵌套泛型
	 * @return
	 */
	public static <T> Set<T> parseSet(String s, Class<T> type) {
		if (Utils.isEmpty(s)) {
			return null;
		}
		try {
			return mapper.readValue(s, JavaTypeManger.sets(mapper, type));
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析为Set
	 * 
	 * @param <T>
	 * @param s
	 * @param type
	 *            泛型类型。不支持再嵌套泛型
	 * @return
	 */
	public static <T> Set<T> parseSet(byte[] data, Class<T> type) {
		if (Utils.isEmpty(data)) {
			return null;
		}
		try {
			return mapper.readValue(data, JavaTypeManger.sets(mapper, type));
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析为数组
	 * 
	 * @param <T>
	 * @param s
	 * @param type
	 *            泛型类型。不支持再嵌套泛型
	 * @return
	 */
	public static <T> T[] parseArray(String s, Class<T> type) {
		if (Utils.isEmpty(s)) {
			return null;
		}
		try {
			return mapper.readValue(s, JavaTypeManger.array(mapper, type));
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析为数组
	 * 
	 * @param <T>
	 * @param s
	 * @param type
	 *            泛型类型。不支持再嵌套泛型
	 * @return
	 */
	public static <T> T[] parseArray(byte[] data, Class<T> type) {
		if (Utils.isEmpty(data)) {
			return null;
		}
		try {
			return mapper.readValue(data, JavaTypeManger.array(mapper, type));
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析为一般Map
	 * 
	 * @param s
	 * @return
	 */
	public static Map<String, Object> parseMap(String s) {
		if (Utils.isEmpty(s)) {
			return null;
		}
		try {
			return mapper.readValue(s, MAP_TYPE);
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析为Map
	 * 
	 * @param <K>
	 *            key类型
	 * @param <V>
	 *            value类型
	 * @param s
	 * @param keyType
	 * @param valueType
	 * @return
	 */
	public static <K, V> Map<K, V> parseMap(String s, Class<K> keyType, Class<V> valueType) {
		if (Utils.isEmpty(s)) {
			return null;
		}
		try {
			return mapper.readValue(s, JavaTypeManger.map(mapper, keyType, valueType));
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析为Map
	 * 
	 * @param <K>
	 *            key类型
	 * @param <V>
	 *            value类型
	 * @param s
	 * @param keyType
	 * @param valueType
	 *            Value类型。不支持再嵌套泛型
	 * @return
	 */
	public static <K, V> Map<K, V> parseMap(byte[] data, Class<K> keyType, Class<V> valueType) {
		if (Utils.isEmpty(data)) {
			return null;
		}
		try {
			return mapper.readValue(data, JavaTypeManger.map(mapper, keyType, valueType));
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析
	 */
	public static <T> T readValue(String s, JavaType valueType) {
		if (Utils.isEmpty(s)) {
			return null;
		}
		try {
			return mapper.readValue(s, valueType);
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析
	 */
	public static <T> T readValue(byte[] data, JavaType valueType) {
		if (Utils.isEmpty(data)) {
			return null;
		}
		try {
			return mapper.readValue(data, valueType);
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析
	 */
	public static <T> T readValue(String s, TypeReference<T> valueTypeRef) {
		if (Utils.isEmpty(s)) {
			return null;
		}
		try {
			return mapper.readValue(s, valueTypeRef);
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 解析
	 */
	public static <T> T readValue(byte[] data, TypeReference<T> valueTypeRef) {
		if (Utils.isEmpty(data)) {
			return null;
		}
		try {
			return mapper.readValue(data, valueTypeRef);
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 写
	 */
	public static void writeValue(OutputStream out, Object value) {
		try {
			mapper.writeValue(out, value);
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	/**
	 * 写
	 */
	public static void writeValue(Writer w, Object value) {
		try {
			mapper.writeValue(w, value);
		} catch (Exception e) {
			throw new JsonException(e);
		}
	}

	public static TypeFactory getTypeFactory() {
		return mapper.getTypeFactory();
	}
}
