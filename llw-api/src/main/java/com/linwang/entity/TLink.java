package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;

public class TLink extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.String name;//名称
	private java.lang.String target;//打开方式
	private java.lang.String position;//位置
	private java.lang.String description;//描述
	private java.lang.Integer sort;//顺序
	private java.lang.String url;//链接地址
   
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
    public java.lang.String getTarget() {
        return target;
    }
    public void setTarget(java.lang.String target) {
        this.target = target;
    }
    public java.lang.String getPosition() {
        return position;
    }
    public void setPosition(java.lang.String position) {
        this.position = position;
    }
    public java.lang.String getDescription() {
        return description;
    }
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    public java.lang.Integer getSort() {
        return sort;
    }
    public void setSort(java.lang.Integer sort) {
        this.sort = sort;
    }
    public java.lang.String getUrl() {
        return url;
    }
    public void setUrl(java.lang.String url) {
        this.url = url;
    }
}
