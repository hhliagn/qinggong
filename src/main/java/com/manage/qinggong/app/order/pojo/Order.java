package com.manage.qinggong.app.order.pojo;

import java.util.Date;

public class Order {
    private Integer orderId;

    private Integer orderType;

    private Long orderCount;

    private String orderUserName;

    private Integer orderUserAge;

    private String orderUserPhone;

    private String orderParty;

    private Date orderDate;

    private String orderDateStr;

    private String orderTime;

    private Integer status;

    private Date createTime;

    private Long passed;

    public String getOrderDateStr() {
        return orderDateStr;
    }

    public void setOrderDateStr(String orderDateStr) {
        this.orderDateStr = orderDateStr;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }

    public String getOrderUserName() {
        return orderUserName;
    }

    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName == null ? null : orderUserName.trim();
    }

    public Integer getOrderUserAge() {
        return orderUserAge;
    }

    public void setOrderUserAge(Integer orderUserAge) {
        this.orderUserAge = orderUserAge;
    }

    public String getOrderUserPhone() {
        return orderUserPhone;
    }

    public void setOrderUserPhone(String orderUserPhone) {
        this.orderUserPhone = orderUserPhone == null ? null : orderUserPhone.trim();
    }

    public String getOrderParty() {
        return orderParty;
    }

    public void setOrderParty(String orderParty) {
        this.orderParty = orderParty == null ? null : orderParty.trim();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime == null ? null : orderTime.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getPassed() {
        return passed;
    }

    public void setPassed(Long passed) {
        this.passed = passed;
    }
}