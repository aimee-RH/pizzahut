package com.example.feign_api.Message.Emit.Element;

public class Card {
    float amount;
    String type,id,shop_id,shop_name;

    public float getamount() {
        return amount;
    }

    public void setamount(float amount) {
        this.amount = amount;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getshop_id() {
        return shop_id;
    }

    public void setshop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getshop_name() {
        return shop_name;
    }

    public void setshop_name(String shop_name) {
        this.shop_name = shop_name;
    }
}
