package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class UserRecharge extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.Integer uid;
	private java.lang.String orderNo;//充值流水编号
	private java.lang.String payGate;//WEIXIN ALIPAY NETBANK
	private java.lang.String payGateTradeId;//第三方支付返回订单号
	private java.math.BigDecimal money;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date dateAdd;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date dateUpdate;
	private java.lang.Integer status;//0 未支付 1 已支付 2 失败
	private java.lang.String remark;
   
    public java.lang.Integer getId() {
        return id;
    }
    public void setId(java.lang.Integer id) {
        this.id = id;
    }
    public java.lang.Integer getUid() {
        return uid;
    }
    public void setUid(java.lang.Integer uid) {
        this.uid = uid;
    }
    public java.lang.String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(java.lang.String orderNo) {
        this.orderNo = orderNo;
    }
    public java.lang.String getPayGate() {
        return payGate;
    }
    public void setPayGate(java.lang.String payGate) {
        this.payGate = payGate;
    }
    public java.lang.String getPayGateTradeId() {
        return payGateTradeId;
    }
    public void setPayGateTradeId(java.lang.String payGateTradeId) {
        this.payGateTradeId = payGateTradeId;
    }
    public java.math.BigDecimal getMoney() {
        return money;
    }
    public void setMoney(java.math.BigDecimal money) {
        this.money = money;
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
    public java.lang.Integer getStatus() {
        return status;
    }
    public void setStatus(java.lang.Integer status) {
        this.status = status;
    }
    public java.lang.String getRemark() {
        return remark;
    }
    public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }
}
