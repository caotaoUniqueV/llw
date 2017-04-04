package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class UserShippingAddress extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.Integer userId;
	private java.lang.Boolean isDefault;//是否默认收货地址
	private java.lang.Integer province;
	private java.lang.Integer city;
	private java.lang.Integer area;
	private java.lang.String linkMan;//联系人
	private java.lang.String address;//详细地址
	private java.lang.String mobile;//收件人手机号
	private java.lang.String phone;//收件人座机号
	private java.lang.String company;//企业名称
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date dateAdd;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date dateUpdate;
   
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
    public java.lang.Boolean getIsDefault() {
        return isDefault;
    }
    public void setIsDefault(java.lang.Boolean isDefault) {
        this.isDefault = isDefault;
    }
    public java.lang.Integer getProvince() {
        return province;
    }
    public void setProvince(java.lang.Integer province) {
        this.province = province;
    }
    public java.lang.Integer getCity() {
        return city;
    }
    public void setCity(java.lang.Integer city) {
        this.city = city;
    }
    public java.lang.Integer getArea() {
        return area;
    }
    public void setArea(java.lang.Integer area) {
        this.area = area;
    }
    public java.lang.String getLinkMan() {
        return linkMan;
    }
    public void setLinkMan(java.lang.String linkMan) {
        this.linkMan = linkMan;
    }
    public java.lang.String getAddress() {
        return address;
    }
    public void setAddress(java.lang.String address) {
        this.address = address;
    }
    public java.lang.String getMobile() {
        return mobile;
    }
    public void setMobile(java.lang.String mobile) {
        this.mobile = mobile;
    }
    public java.lang.String getPhone() {
        return phone;
    }
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }
    public java.lang.String getCompany() {
        return company;
    }
    public void setCompany(java.lang.String company) {
        this.company = company;
    }
    public java.util.Date getDateAdd() {
        return dateAdd;
    }
    public void setDateAdd(java.util.Date dateAdd) {
        this.dateAdd = dateAdd;
    }
    public java.util.Date getDateUpdate() {
        return dateUpdate;
    }
    public void setDateUpdate(java.util.Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
