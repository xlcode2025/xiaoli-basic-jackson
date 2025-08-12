package com.xiaoli.basic.jackson.test;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestData implements Cloneable {
	private Long number1 = 0L;
	private Integer number2;
	private List<String> stringList;
	private List<TestData> childs;
	private Map<String, Object> map;
	private byte[] byteArr;
	private Date date;
	private LocalDateTime date2;

	public Long getNumber1() {
		return number1;
	}

	public void setNumber1(Long number1) {
		this.number1 = number1;
	}

	public Integer getNumber2() {
		return number2;
	}

	public void setNumber2(Integer number2) {
		this.number2 = number2;
	}

	public List<String> getStringList() {
		return stringList;
	}

	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public byte[] getByteArr() {
		return byteArr;
	}

	public void setByteArr(byte[] byteArr) {
		this.byteArr = byteArr;
	}

	public List<TestData> getChilds() {
		return childs;
	}

	public void setChilds(List<TestData> childs) {
		this.childs = childs;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public TestData clone() {
		return this;
	}

	public LocalDateTime getDate2() {
		return date2;
	}

	public void setDate2(LocalDateTime date2) {
		this.date2 = date2;
	}
}
