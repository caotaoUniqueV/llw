package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class SysConfig extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.Integer type;//参数类型,预留
	private java.lang.Integer dateType;//0 文本字符串  1 开关 2 上传文件
	private java.lang.String key;//参数编号
	private java.lang.String value;//参数值
	private java.lang.String remark;//备注
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date creatAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateAt;
   
    public java.lang.Integer getId() {
        return id;
    }
    public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.Integer getType() {
        return type;
    }
    public void setType(java.lang.Integer type) {
        this.type = type;
    }
    public java.lang.Integer getDateType() {
        return dateType;
    }
    public void setDateType(java.lang.Integer dateType) {
        this.dateType = dateType;
    }
    public java.lang.String getKey() {
        return key;
    }
    public void setKey(java.lang.String key) {
        this.key = key;
    }
    public java.lang.String getValue() {
        return value;
    }
    public void setValue(java.lang.String value) {
        this.value = value;
    }
    public java.lang.String getRemark() {
        return remark;
    }
    public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }
    public java.util.Date getCreatAt() {
        return creatAt;
    }
    public void setCreatAt(java.util.Date creatAt) {
        this.creatAt = creatAt;
    }
    public java.util.Date getUpdateAt() {
        return updateAt;
    }
    public void setUpdateAt(java.util.Date updateAt) {
        this.updateAt = updateAt;
    }
}
