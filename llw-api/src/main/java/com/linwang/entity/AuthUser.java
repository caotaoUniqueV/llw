package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class AuthUser extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.String username;
	private java.lang.String pwd;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date dateLogin;
	private java.lang.String ipLogin;
	private java.lang.String realname;
	private java.lang.String mobile;
	private java.lang.Boolean isLock;
   
    public java.lang.Integer getId() {
        return id;
    }
    public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.String getUsername() {
        return username;
    }
    public void setUsername(java.lang.String username) {
        this.username = username;
    }
    public java.lang.String getPwd() {
        return pwd;
    }
    public void setPwd(java.lang.String pwd) {
        this.pwd = pwd;
    }
    public java.util.Date getDateLogin() {
        return dateLogin;
    }
    public void setDateLogin(java.util.Date dateLogin) {
        this.dateLogin = dateLogin;
    }
    public java.lang.String getIpLogin() {
        return ipLogin;
    }
    public void setIpLogin(java.lang.String ipLogin) {
        this.ipLogin = ipLogin;
    }
    public java.lang.String getRealname() {
        return realname;
    }
    public void setRealname(java.lang.String realname) {
        this.realname = realname;
    }
    public java.lang.String getMobile() {
        return mobile;
    }
    public void setMobile(java.lang.String mobile) {
        this.mobile = mobile;
    }
    public java.lang.Boolean getIsLock() {
        return isLock;
    }
    public void setIsLock(java.lang.Boolean isLock) {
        this.isLock = isLock;
    }
}
