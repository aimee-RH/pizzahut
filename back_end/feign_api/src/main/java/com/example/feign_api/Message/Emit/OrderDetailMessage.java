package com.example.feign_api.Message.Emit;

import com.example.feign_api.Pojo.delivery_person;
import com.example.feign_api.Pojo.shop;
import com.example.feign_api.Message.Emit.Element.OrderDish;

public class OrderDetailMessage {
    OrderDish[] list;
    shop shop;
    delivery_person deliver;

    public OrderDetailMessage(OrderDish[] list, delivery_person deliver) {
        this.list = list;
        this.deliver = deliver;
    }

    public OrderDetailMessage(OrderDish[] list, shop shop, delivery_person deliver) {
        this.list = list;
        this.shop = shop;
        this.deliver = deliver;
    }

    public OrderDish[] getList() {
        return list;
    }

    public void setList(OrderDish[] list) {
        this.list = list;
    }

    public shop getShop() {
        return shop;
    }

    public void setShop(shop shop) {
        this.shop = shop;
    }

    public delivery_person getDeliver() {
        return deliver;
    }

    public void setDeliver(delivery_person deliver) {
        this.deliver = deliver;
    }
}
