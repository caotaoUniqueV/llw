package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class UserThirdLogin extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.String platform;//QQ WEIXIN WEIBO ALIPAY
	private java.lang.Integer uid;
	private java.lang.String platformId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createdAt;
	private java.lang.String createdIp;
   
    public java.lang.Integer getId() {
        return id;
    }
    public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.String getPlatform() {
        return platform;
    }
    public void setPlatform(java.lang.String platform) {
        this.platform = platform;
    }
    public java.lang.Integer getUid() {
        return uid;
    }
    public void setUid(java.lang.Integer uid) {
        this.uid = uid;
    }
    public java.lang.String getPlatformId() {
        return platformId;
    }
    public void setPlatformId(java.lang.String platformId) {
        this.platformId = platformId;
    }
    public java.util.Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }
    public java.lang.String getCreatedIp() {
        return createdIp;
    }
    public void setCreatedIp(java.lang.String createdIp) {
        this.createdIp = createdIp;
    }
}
