package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class CouponRecord extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.Integer userId;//用户ID
	private java.lang.Integer couponModuId;//礼品券模板ID
	private java.lang.String name;//名称
	private java.math.BigDecimal amount;//金额
	private java.lang.Boolean hasPercent;//百分比（0否，1是）
	private java.math.BigDecimal percent;//比率（%）
	private java.lang.String description;//描述
	private java.lang.Integer state;//状态（0未使用,1已使用）
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date gmtCreate;//创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date gmtModify;//修改时间
   
    public java.lang.Integer getId() {
        return id;
    }
    public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.Integer getUserId() {
        return userId;
    }
    public void setUserId(java.lang.Integer userId) {
        this.userId = userId;
    }
    public java.lang.Integer getCouponModuId() {
        return couponModuId;
    }
    public void setCouponModuId(java.lang.Integer couponModuId) {
        this.couponModuId = couponModuId;
    }
    public java.lang.String getName() {
        return name;
    }
    public void setName(java.lang.String name) {
        this.name = name;
    }
    public java.math.BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }
    public java.lang.Boolean getHasPercent() {
        return hasPercent;
    }
    public void setHasPercent(java.lang.Boolean hasPercent) {
        this.hasPercent = hasPercent;
    }
    public java.math.BigDecimal getPercent() {
        return percent;
    }
    public void setPercent(java.math.BigDecimal percent) {
        this.percent = percent;
    }
    public java.lang.String getDescription() {
        return description;
    }
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    public java.lang.Integer getState() {
        return state;
    }
    public void setState(java.lang.Integer state) {
        this.state = state;
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
}
