package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;

public class AuthRole extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.String name;
	private java.lang.String profile;
   
    public java.lang.Integer getId() {
        return id;
    }
    public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.String getName() {
        return name;
    }
    public void setName(java.lang.String name) {
        this.name = name;
    }
    public java.lang.String getProfile() {
        return profile;
    }
    public void setProfile(java.lang.String profile) {
        this.profile = profile;
    }
}
