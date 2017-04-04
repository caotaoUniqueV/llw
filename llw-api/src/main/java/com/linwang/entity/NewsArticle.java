package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class NewsArticle extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.Integer typeId;//类别主键ID
	private java.lang.String title;//标题
	private java.lang.String content;//内容
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date gmtCreate;//创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date gmtModify;//修改时间
	private java.lang.String status;//状态
	private java.lang.Integer sort;//排序
	private java.lang.String picture;//图片
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date gmtSend;//发布时间
	private java.lang.Integer mode;//模式：1列表，2单页
   
    public java.lang.Integer getId() {
        return id;
    }
    public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.Integer getTypeId() {
        return typeId;
    }
    public void setTypeId(java.lang.Integer typeId) {
        this.typeId = typeId;
    }
    public java.lang.String getTitle() {
        return title;
    }
    public void setTitle(java.lang.String title) {
        this.title = title;
    }
    public java.lang.String getContent() {
        return content;
    }
    public void setContent(java.lang.String content) {
        this.content = content;
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
    public java.lang.String getStatus() {
        return status;
    }
    public void setStatus(java.lang.String status) {
        this.status = status;
    }
    public java.lang.Integer getSort() {
        return sort;
    }
    public void setSort(java.lang.Integer sort) {
        this.sort = sort;
    }
    public java.lang.String getPicture() {
        return picture;
    }
    public void setPicture(java.lang.String picture) {
        this.picture = picture;
    }
    public java.util.Date getGmtSend() {
        return gmtSend;
    }
    public void setGmtSend(java.util.Date gmtSend) {
        this.gmtSend = gmtSend;
    }
    public java.lang.Integer getMode() {
        return mode;
    }
    public void setMode(java.lang.Integer mode) {
        this.mode = mode;
    }
}
