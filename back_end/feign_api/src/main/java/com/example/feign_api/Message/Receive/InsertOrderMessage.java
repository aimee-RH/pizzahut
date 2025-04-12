package com.example.feign_api.Message.Receive;

import com.example.feign_api.Pojo.Order;
import com.example.feign_api.Message.Receive.Element.Payment;

public class InsertOrderMessage {
    Payment[] paymentList;
    Order orderList;

    public Payment[] getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(Payment[] paymentList) {
        this.paymentList = paymentList;
    }

    public Order getOrderList() {
        return orderList;
    }

    public void setOrderList(Order orderList) {
        this.orderList = orderList;
    }
}
