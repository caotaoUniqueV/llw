package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class ActivityLevelPrize extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.Integer activityInfoId;//活动ID
	private java.lang.Integer couponModuId;//礼品券模板ID
	private java.lang.String description;//描述
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date gmtCreate;//创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date gmtModify;//修改时间
	private java.lang.String name;//名称
	private java.lang.Integer level;//名次
   
    public java.lang.Integer getId() {
        return id;
    }
    public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.Integer getActivityInfoId() {
        return activityInfoId;
    }
    public void setActivityInfoId(java.lang.Integer activityInfoId) {
        this.activityInfoId = activityInfoId;
    }
    public java.lang.Integer getCouponModuId() {
        return couponModuId;
    }
    public void setCouponModuId(java.lang.Integer couponModuId) {
        this.couponModuId = couponModuId;
    }
    public java.lang.String getDescription() {
        return description;
    }
    public void setDescription(java.lang.String description) {
        this.description = description;
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
    public java.lang.String getName() {
        return name;
    }
    public void setName(java.lang.String name) {
        this.name = name;
    }
    public java.lang.Integer getLevel() {
        return level;
    }
    public void setLevel(java.lang.Integer level) {
        this.level = level;
    }
}
