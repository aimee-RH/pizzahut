package com.example.feign_api.Message.Emit;

import com.example.feign_api.Message.Emit.Element.Order;

public class OrderMessage {
    Order[] orders;

    public OrderMessage(Order[] orders) {
        this.orders = orders;
    }

//    public OrderMessage(com.example.feign_api.Pojo.Order[] orders) {
//    }

    public Order[] getOrders() {
        return orders;
    }

    public void setOrders(Order[] orders) {
        this.orders = orders;
    }
}
