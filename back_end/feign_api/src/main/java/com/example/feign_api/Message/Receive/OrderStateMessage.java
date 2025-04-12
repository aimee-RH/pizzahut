package com.example.feign_api.Message.Receive;

public class OrderStateMessage {
    String orderID, deliverID;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getDeliverID() {
        return deliverID;
    }

    public void setDeliverID(String deliverID) {
        this.deliverID = deliverID;
    }
}
