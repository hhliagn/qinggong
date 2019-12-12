package com.manage.qinggong.pc.place.pojo;

import java.util.Date;

public class OrderPeriod {
    private Integer id;

    private String orderTime;

    private Long orderLimit;

    private Integer status;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime == null ? null : orderTime.trim();
    }

    public Long getOrderLimit() {
        return orderLimit;
    }

    public void setOrderLimit(Long orderLimit) {
        this.orderLimit = orderLimit;
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
}