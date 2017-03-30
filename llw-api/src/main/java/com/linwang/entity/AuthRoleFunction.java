package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;

public class AuthRoleFunction extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.Integer roleId;
	private java.lang.Integer functionId;
   
    public java.lang.Integer getId() {
        return id;
    }
    public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.Integer getRoleId() {
        return roleId;
    }
    public void setRoleId(java.lang.Integer roleId) {
        this.roleId = roleId;
    }
    public java.lang.Integer getFunctionId() {
        return functionId;
    }
    public void setFunctionId(java.lang.Integer functionId) {
        this.functionId = functionId;
    }
}
