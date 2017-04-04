package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;

public class CommonCityData extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.String name;
	private java.lang.String pinyin;
	private java.lang.String jianpin;
	private java.lang.String firstLetter;
	private java.lang.Integer depth;//1 为省级 2为市级 3为区级
	private java.lang.Integer parentId;
   
    public java.lang.Integer getId() {
        return id;
    }
    public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.String getName() {
        return name;
    }
    public void setName(java.lang.String name) {
        this.name = name;
    }
    public java.lang.String getPinyin() {
        return pinyin;
    }
    public void setPinyin(java.lang.String pinyin) {
        this.pinyin = pinyin;
    }
    public java.lang.String getJianpin() {
        return jianpin;
    }
    public void setJianpin(java.lang.String jianpin) {
        this.jianpin = jianpin;
    }
    public java.lang.String getFirstLetter() {
        return firstLetter;
    }
    public void setFirstLetter(java.lang.String firstLetter) {
        this.firstLetter = firstLetter;
    }
    public java.lang.Integer getDepth() {
        return depth;
    }
    public void setDepth(java.lang.Integer depth) {
        this.depth = depth;
    }
    public java.lang.Integer getParentId() {
        return parentId;
    }
    public void setParentId(java.lang.Integer parentId) {
        this.parentId = parentId;
    }
}
