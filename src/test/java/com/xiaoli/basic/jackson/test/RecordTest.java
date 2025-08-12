package com.xiaoli.basic.jackson.test;

import com.xiaoli.basic.jackson.JacksonUtils;

public class RecordTest {

	public static void main(String[] args) {
		Data data = new Data(1, "test");
		System.out.println(JacksonUtils.toJson(data));
		

	}
	
	private static record Data(int id, String name){
		
	}

}
