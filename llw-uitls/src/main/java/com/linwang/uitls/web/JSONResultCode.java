package com.linwang.uitls.web;

import java.io.Serializable;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class JSONResultCode implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private Object data;

    public JSONResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JSONResultCode(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    
    public JSONResultCode(Object data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }
    
    public JSONResultCode (Enum<?> e) {
    	try {
    		this.code = Integer.parseInt(BeanUtils.getProperty(e, "code"));
    		this.msg = BeanUtils.getProperty(e, "msg");
		} catch (Exception e2) {
			this.code = -1;
	        this.msg = "错误的Enun类型，必须包含 code，msg属性";
		}
    }
    
    public JSONResultCode (Enum<?> e, Object data) {
    	this.data = data;
    	try {
    		this.code = Integer.parseInt(BeanUtils.getProperty(e, "code"));
    		this.msg = BeanUtils.getProperty(e, "msg");
    	} catch (Exception e2) {
    		this.code = -1;
    		this.msg = "错误的Enun类型，必须包含 code，msg属性";
    	}
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
