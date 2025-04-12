package com.example.feign_api.Message.Emit;
import com.example.feign_api.Pojo.Shop;

public class BuyDishesMessage {
    Shop[] shops;
    float discount;
    boolean canPrivilege;

    public BuyDishesMessage(Shop[] shops, float discount, boolean usePrivilege) {
        this.shops = shops;
        this.discount = discount;
        this.canPrivilege = !usePrivilege;
    }

    public Shop[] getShops() {
        return shops;
    }

    public void setShops(Shop[] shops) {
        this.shops = shops;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public boolean isCanPrivilege() {
        return canPrivilege;
    }

    public void setCanPrivilege(boolean canPrivilege) {
        this.canPrivilege = canPrivilege;
    }
}
