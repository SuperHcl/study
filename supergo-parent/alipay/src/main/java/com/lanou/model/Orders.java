package com.lanou.model;

public class Orders {
    private Integer orderid;

    private String orderno;

    private Double paymany;

    private Integer status;

    public Orders(Integer orderid, String orderno, Double paymany, Integer status) {
        this.orderid = orderid;
        this.orderno = orderno;
        this.paymany = paymany;
        this.status = status;
    }

    public Orders() {
        super();
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public Double getPaymany() {
        return paymany;
    }

    public void setPaymany(Double paymany) {
        this.paymany = paymany;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}