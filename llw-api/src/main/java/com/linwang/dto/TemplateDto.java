package com.linwang.dto;

public class TemplateDto implements java.io.Serializable {
	private String lowerAttribute;//小写属性
	private String type;
	private String capitalAttribute;//大写属性
	private String comment;
	private String columnName;
	private String onColumnName;
	public String getOnColumnName() {
		return onColumnName;
	}
	public void setOnColumnName(String onColumnName) {
		this.onColumnName = onColumnName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getLowerAttribute() {
		return lowerAttribute;
	}
	public void setLowerAttribute(String lowerAttribute) {
		this.lowerAttribute = lowerAttribute;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCapitalAttribute() {
		return capitalAttribute;
	}
	public void setCapitalAttribute(String capitalAttribute) {
		this.capitalAttribute = capitalAttribute;
	}
	
	
}
