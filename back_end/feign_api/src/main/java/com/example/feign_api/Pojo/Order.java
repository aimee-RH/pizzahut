package com.example.feign_api.Pojo;

import java.util.Date;

public class Order {
    private String id;
    private String user;
    private String address;
    private String shop;
    private String deliveryPerson;
    private String deliveryStatus;
    private String note;
    private String couponUsed;
    private String privilege;
    private Date orderTime;
    private Float totalAmount;

    public Order(String id, String user, String address, String shop, Float totalAmount, String note, String couponUsed, String privilege) {
        this.id = id;
        this.user = user;
        this.address = address;
        this.shop = shop;
        this.totalAmount = totalAmount;
        this.note = note;
        this.couponUsed = couponUsed;
        this.privilege = privilege;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(String deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCouponUsed() {
        return couponUsed;
    }

    public void setCouponUsed(String couponUsed) {
        this.couponUsed = couponUsed;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", address='" + address + '\'' +
                ", shop='" + shop + '\'' +
                ", deliveryPerson='" + deliveryPerson + '\'' +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                ", note='" + note + '\'' +
                ", couponUsed='" + couponUsed + '\'' +
                ", privilege='" + privilege + '\'' +
                ", orderTime=" + orderTime +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
