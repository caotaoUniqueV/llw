package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;

public class CoreUser extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.String state;
	private java.lang.String nickname;
   
    public java.lang.Integer getId() {
        return id;
    }
    public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.String getState() {
        return state;
    }
    public void setState(java.lang.String state) {
        this.state = state;
    }
    public java.lang.String getNickname() {
        return nickname;
    }
    public void setNickname(java.lang.String nickname) {
        this.nickname = nickname;
    }
}
