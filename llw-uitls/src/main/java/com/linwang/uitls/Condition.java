package com.linwang.uitls;

import java.util.List;

public class Condition {
	private String sql;
	private Object[] object;

	public Condition() {

	}

	public Condition(String sql) {
		this.sql = sql;
	}

	public Condition(String sql, Object[] object) {
		this.sql = sql;
		this.object = object;
	}
	
	public Condition(String sql, List<?> objectList){
		this.sql = sql;
		this.object = objectList.toArray();
	}
	
	public Object[] getObject() {
		return object;
	}

	public void setObject(Object[] object) {
		this.object = object;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
