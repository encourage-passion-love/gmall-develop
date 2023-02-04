package com.xfp.gmall.entity;

public class Payment {


    private String orderSn;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    @Override
    public String toString() {
        return "Payment{" +
                "orderSn='" + orderSn + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
