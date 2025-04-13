package com.example.feign_api.Message.Emit;

import com.example.feign_api.Pojo.DeliveryPerson;
import com.example.feign_api.Pojo.Shop;
import com.example.feign_api.Message.Emit.Element.OrderDish;

public class OrderDetailMessage {
    OrderDish[] list;
    Shop shop;
    DeliveryPerson deliver;

    public OrderDetailMessage(OrderDish[] list, DeliveryPerson deliver) {
        this.list = list;
        this.deliver = deliver;
    }

    public
    OrderDetailMessage(OrderDish[] list, Shop shop, DeliveryPerson deliver) {
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public DeliveryPerson getDeliver() {
        return deliver;
    }

    public void setDeliver(DeliveryPerson deliver) {
        this.deliver = deliver;
    }
}
