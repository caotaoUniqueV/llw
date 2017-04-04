package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class SysLogSms extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.Integer userId;//接收用户ID
	private java.lang.String mobile;//标题
	private java.lang.Boolean isSuccess;//是否成功
	private java.lang.String content;//发送内容
	private java.lang.String result;//发送结果信息
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date dateAdd;//添加时间
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
    public java.lang.String getMobile() {
        return mobile;
    }
    public void setMobile(java.lang.String mobile) {
        this.mobile = mobile;
    }
    public java.lang.Boolean getIsSuccess() {
        return isSuccess;
    }
    public void setIsSuccess(java.lang.Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
    public java.lang.String getContent() {
        return content;
    }
    public void setContent(java.lang.String content) {
        this.content = content;
    }
    public java.lang.String getResult() {
        return result;
    }
    public void setResult(java.lang.String result) {
        this.result = result;
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
