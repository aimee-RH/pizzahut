package com.example.feign_api.Message.Receive;

public class DealShopDishesMessage {
    String shopID, dishID;

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getDishID() {
        return dishID;
    }

    public void setDishID(String dishID) {
        this.dishID = dishID;
    }
}
