package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class ConfigNoticeTemple extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.String nid;//编码，与notice_type组合起来唯一
	private java.lang.Integer noticeType;//通知类型:1-sms,2-email,3-message
	private java.lang.Boolean isEnable;//是否启用
	private java.lang.String name;//名称
	private java.lang.String remark;//备注
	private java.lang.Integer type;//发送类型：1-系统通知，2-用户通知
	private java.lang.String titleTemplet;//标题的freemarker模板
	private java.lang.String templet;//内容的freemarker模板
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date dateAdd;//添加时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date dateUpdate;//更新时间
   
    public java.lang.Integer getId() {
        return id;
    }
    public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.String getNid() {
        return nid;
    }
    public void setNid(java.lang.String nid) {
        this.nid = nid;
    }
    public java.lang.Integer getNoticeType() {
        return noticeType;
    }
    public void setNoticeType(java.lang.Integer noticeType) {
        this.noticeType = noticeType;
    }
    public java.lang.Boolean getIsEnable() {
        return isEnable;
    }
    public void setIsEnable(java.lang.Boolean isEnable) {
        this.isEnable = isEnable;
    }
    public java.lang.String getName() {
        return name;
    }
    public void setName(java.lang.String name) {
        this.name = name;
    }
    public java.lang.String getRemark() {
        return remark;
    }
    public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }
    public java.lang.Integer getType() {
        return type;
    }
    public void setType(java.lang.Integer type) {
        this.type = type;
    }
    public java.lang.String getTitleTemplet() {
        return titleTemplet;
    }
    public void setTitleTemplet(java.lang.String titleTemplet) {
        this.titleTemplet = titleTemplet;
    }
    public java.lang.String getTemplet() {
        return templet;
    }
    public void setTemplet(java.lang.String templet) {
        this.templet = templet;
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
