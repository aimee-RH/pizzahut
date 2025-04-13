package com.example.feign_api.Message.Receive;

import com.example.feign_api.Pojo.OrderEntity;
import com.example.feign_api.Message.Receive.Element.Payment;

public class InsertOrderMessage {
    Payment[] paymentList;
    OrderEntity orderList;

    public Payment[] getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(Payment[] paymentList) {
        this.paymentList = paymentList;
    }

    public OrderEntity getOrderList() {
        return orderList;
    }

    public void setOrderList(OrderEntity orderList) {
        this.orderList = orderList;
    }
}
