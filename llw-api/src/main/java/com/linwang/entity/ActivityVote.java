package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class ActivityVote extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.Integer activityInfoId;//活动ID
	private java.lang.String name;//名称
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date gmtVoteStart;//投票起始时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date gmtVoteEnd;//投票截止时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date gmtCreate;//创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date gmtModify;//修改时间
	private java.lang.Integer sort;//排序
	private java.lang.Integer modu;//模式（1单选，2多选）
   
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
    public java.lang.String getName() {
        return name;
    }
    public void setName(java.lang.String name) {
        this.name = name;
    }
    public java.util.Date getGmtVoteStart() {
        return gmtVoteStart;
    }
    public void setGmtVoteStart(java.util.Date gmtVoteStart) {
        this.gmtVoteStart = gmtVoteStart;
    }
    public java.util.Date getGmtVoteEnd() {
        return gmtVoteEnd;
    }
    public void setGmtVoteEnd(java.util.Date gmtVoteEnd) {
        this.gmtVoteEnd = gmtVoteEnd;
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
    public java.lang.Integer getSort() {
        return sort;
    }
    public void setSort(java.lang.Integer sort) {
        this.sort = sort;
    }
    public java.lang.Integer getModu() {
        return modu;
    }
    public void setModu(java.lang.Integer modu) {
        this.modu = modu;
    }
}
