package com.linwang.statusCode;

public enum BaseStatusCode {
	
	SUCCESS(0, "success"),

    ERROR(-1, "服务器内部错误"),
    
    ERROR_JSP(300, "本系统已禁止JSP页面访问"),

    ERROR_403(403, "禁止访问"),
    ERROR_404(404, "资源不存在"),
    ERROR_405(405, "Request method not supported"),
    
    ERROR_DATABASE(501, "数据库错误"),
    ATOMIC(502, "并发异常，请重试"),

    REQUIRE_PARAM(600, "缺少参数"),
    REQUIRE_TOKEN(601, "无权操作:缺少 token"),
    SIGN_ERROR(602, "签名错误"),
    MERCHANT_KEY_ERROR(603, "商户秘钥错误"),
    
    DATA_EMPTY(700, "暂无数据"),
    DATA_NOT_OPEN(701, "该功能暂未开通"),
    DATA_NOT_ENOUGH(702, "资源余额不足"),
    
    MERCHANT_NOT_EXISTS(800, "商户不存在"),
    MERCHANT_DISABLE(801, "商户禁用"),
    
    TOKEN_ERROR_CODE(900, "临时凭证错误"),
    TOKEN_ERROR(901, "token错误"),
    ;

    private int code;
    private String msg;
    private Object data;

    BaseStatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    BaseStatusCode(int code, String msg, Object data) {
    	this.code = code;
    	this.msg = msg;
    	this.data = data;
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
