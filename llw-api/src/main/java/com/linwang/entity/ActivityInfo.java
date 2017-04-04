package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class ActivityInfo extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.String title;//标题
	private java.lang.String description;//描述
	private java.lang.Integer totalPeople;//总人数
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date gmtEnrollStart;//报名开始时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date gmtEnrollEnd;//报名截止时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date gmtCreate;//创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date gmtModify;//修改时间
	private java.lang.String picture;//活动图片
	private java.lang.Integer state;//状态
   
    public java.lang.Integer getId() {
        return id;
    }
    public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.String getTitle() {
        return title;
    }
    public void setTitle(java.lang.String title) {
        this.title = title;
    }
    public java.lang.String getDescription() {
        return description;
    }
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    public java.lang.Integer getTotalPeople() {
        return totalPeople;
    }
    public void setTotalPeople(java.lang.Integer totalPeople) {
        this.totalPeople = totalPeople;
    }
    public java.util.Date getGmtEnrollStart() {
        return gmtEnrollStart;
    }
    public void setGmtEnrollStart(java.util.Date gmtEnrollStart) {
        this.gmtEnrollStart = gmtEnrollStart;
    }
    public java.util.Date getGmtEnrollEnd() {
        return gmtEnrollEnd;
    }
    public void setGmtEnrollEnd(java.util.Date gmtEnrollEnd) {
        this.gmtEnrollEnd = gmtEnrollEnd;
    }
    public java.util.Date getGmtCreate() {
        return gmtCreate;
    }
    public void setGmtCreate(java.util.Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    public java.util.Date getGmtModify() {
        return gmtModify;
    }
    public void setGmtModify(java.util.Date gmtModify) {
        this.gmtModify = gmtModify;
    }
    public java.lang.String getPicture() {
        return picture;
    }
    public void setPicture(java.lang.String picture) {
        this.picture = picture;
    }
    public java.lang.Integer getState() {
        return state;
    }
    public void setState(java.lang.Integer state) {
        this.state = state;
    }
}
