package com.xiaoli.basic.jackson.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaoli.basic.jackson.JacksonUtils;
import com.xiaoli.basic.jackson.test.filter.TestDataFilter;

public class JackJsonTest {

	public static void main(String[] args) {

		test1();
		testList();
		testSet();
		testArray();
		testMap();
		testMap2();
		testParse();
	}

	public static void test1() {
		TestData data = new TestData();
		data.setByteArr(new byte[10]);

		data.setMap(new HashMap<>());
		data.getMap().put("aa", "bbbb");
		data.getMap().put("bb", 1);
		data.setNumber1(10L);
		data.setStringList(new ArrayList<String>());
		data.getStringList().add("bbbbbb");
		data.setDate(new Date());
		data.setChilds(new ArrayList<TestData>());
		data.setDate2(LocalDateTime.now());
		data.getChilds().add(new TestData());

		String json = JacksonUtils.toJson(data);
		System.out.println(json);

		TestData data2 = JacksonUtils.parseObject(json, TestData.class);
		System.out.println(data2.getMap());

		String json2 = JacksonUtils.toJson(data, TestDataFilter.class);
		System.out.println(json2);
	}

	public static void testList() {

		List<User> list = new ArrayList<>();
		list.add(new User(1, "name1"));
		list.add(new User(1, "name1"));

		String json = JacksonUtils.toJson(list);
		System.out.println(json);

		List<User> list2 = JacksonUtils.parseList(json, User.class);

		System.out.println(list2.getClass());
		System.out.println(list2.get(0).getAge());

	}

	public static void testSet() {
		List<User> list = new ArrayList<>();
		list.add(new User(1, "aa"));
		list.add(new User(2, "aa"));

		String json = JacksonUtils.toJson(list);
		System.out.println(json);

		Set<User> list2 = JacksonUtils.parseSet(json, User.class);
		System.out.println(list2.getClass());
		System.out.println(list2.iterator().next().getName());
	}

	public static void testArray() {
		User[] list = new User[1];

		list[0] = new User(1, "aa");

		String json = JacksonUtils.toJson(list);
		System.out.println(json);

		User[] list2 = JacksonUtils.parseArray(json, User.class);
		System.out.println(list2.getClass());
		System.out.println(list2[0].getName());
	}

	public static void testMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("aaa", "bbbbbbbbb");
		map.put("bbb", true);
		map.put("c", new Date());
		map.put("1", new User(1, "2"));

		String json = JacksonUtils.toJson(map);
		System.out.println(json);

		Map<String, Object> map2 = JacksonUtils.parseMap(json);
		System.out.println(map2.getClass());
		System.out.println(map2);
		System.out.println(map2.get("1").getClass());
	}

	public static void testMap2() {
		Map<String, User> map = new HashMap<>();
		map.put("1", new User(1, "2"));

		String json = JacksonUtils.toJson(map);
		System.out.println(json);

		Map<String, User> map2 = JacksonUtils.parseMap(json, String.class, User.class);
		System.out.println(map2.getClass());
		System.out.println(map2);
		System.out.println(map2.get("1").getClass());
	}

	public static void testParse() {
		String json = "{aa:111}";
		JacksonUtils.parseObject(json, TestData.class);
		
		Result<User> result = new Result<>();
		result.setCode(1);
		result.setData(new User(1, "test"));
		
		String json2= JacksonUtils.toJson(result);
		Result<User> result2 = JacksonUtils.parseObject(json2, Result.class,User.class);
		System.out.println(result2.getData().getName());
	}


}
