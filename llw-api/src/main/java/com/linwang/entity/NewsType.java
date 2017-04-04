package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;

public class NewsType extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.String name;//名称
	private java.lang.Integer pid;//父ID
	private java.lang.Integer sort;//顺序
	private java.lang.String hasDisplay;//是否显示
	private java.lang.String imgUrl;//图片
	private java.lang.String description;//描述
   
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
    public java.lang.Integer getPid() {
        return pid;
    }
    public void setPid(java.lang.Integer pid) {
        this.pid = pid;
    }
    public java.lang.Integer getSort() {
        return sort;
    }
    public void setSort(java.lang.Integer sort) {
        this.sort = sort;
    }
    public java.lang.String getHasDisplay() {
        return hasDisplay;
    }
    public void setHasDisplay(java.lang.String hasDisplay) {
        this.hasDisplay = hasDisplay;
    }
    public java.lang.String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(java.lang.String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public java.lang.String getDescription() {
        return description;
    }
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
}
