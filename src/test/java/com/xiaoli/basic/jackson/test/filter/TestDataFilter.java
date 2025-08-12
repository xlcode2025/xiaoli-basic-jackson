package com.xiaoli.basic.jackson.test.filter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiaoli.basic.jackson.JsonViewFilter;
import com.xiaoli.basic.jackson.test.TestData;

/**
 * TJSON视图过滤
 *
 */
@JsonViewFilter(TestData.class)
// 过滤属性
@JsonIgnoreProperties(value = { "number1" })
public interface TestDataFilter {
	// 字段改名
	@JsonProperty("date2")
	Long getDate();
}
