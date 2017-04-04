package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class SysLog extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.String moduleType;//所属模块
	private java.lang.String oprateType;//操作类型
	private java.lang.String name;//当前权限
	private java.lang.String uri;//相关链接
	private java.lang.String msg;//操作记录
	private java.lang.String adminName;//管理员
	private java.lang.String ip;//IP地址
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date dateAdd;//操作时间
   
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
    public java.lang.String getAdminName() {
        return adminName;
    }
    public void setAdminName(java.lang.String adminName) {
        this.adminName = adminName;
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
