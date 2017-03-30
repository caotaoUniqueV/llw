package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class SysLog extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.String moduleType;//所属模块
	private java.lang.String oprateType;//操作类型
	private java.lang.String name;
	private java.lang.String uri;
	private java.lang.String msg;
	private java.lang.Integer adminId;
	private java.lang.String ip;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date dateAdd;
   
    public java.lang.Integer getId() {
        return id;
    }
    public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.String getModuleType() {
        return moduleType;
    }
    public void setModuleType(java.lang.String moduleType) {
        this.moduleType = moduleType;
    }
    public java.lang.String getOprateType() {
        return oprateType;
    }
    public void setOprateType(java.lang.String oprateType) {
        this.oprateType = oprateType;
    }
    public java.lang.String getName() {
        return name;
    }
    public void setName(java.lang.String name) {
        this.name = name;
    }
    public java.lang.String getUri() {
        return uri;
    }
    public void setUri(java.lang.String uri) {
        this.uri = uri;
    }
    public java.lang.String getMsg() {
        return msg;
    }
    public void setMsg(java.lang.String msg) {
        this.msg = msg;
    }
    public java.lang.Integer getAdminId() {
        return adminId;
    }
    public void setAdminId(java.lang.Integer adminId) {
        this.adminId = adminId;
    }
    public java.lang.String getIp() {
        return ip;
    }
    public void setIp(java.lang.String ip) {
        this.ip = ip;
    }
    public java.util.Date getDateAdd() {
        return dateAdd;
    }
    public void setDateAdd(java.util.Date dateAdd) {
        this.dateAdd = dateAdd;
    }
}
