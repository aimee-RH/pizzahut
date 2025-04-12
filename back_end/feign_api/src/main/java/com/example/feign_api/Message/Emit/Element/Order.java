package com.example.feign_api.Message.Emit.Element;

import java.util.Date;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //无参的构造方法
public class Order {
    String id, user, user_name, address, shop, shop_name, delivery_person, delivery_person_name, delivery_status, note, coupon_used, privilege;
    Date order_time;
    Float total_amount;

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getuser() {
        return user;
    }

    public void setuser(String user) {
        this.user = user;
    }

    public String getuser_name() {
        return user_name;
    }

    public void setuser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public String getshop() {
        return shop;
    }

    public void setshop(String shop) {
        this.shop = shop;
    }

    public String getshop_name() {
        return shop_name;
    }

    public void setshop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getdelivery_person() {
        return delivery_person;
    }

    public void setdelivery_person(String delivery_person) {
        this.delivery_person = delivery_person;
    }

    public String getdelivery_person_name() {
        return delivery_person_name;
    }

    public void setdelivery_person_name(String delivery_person_name) {
        this.delivery_person_name = delivery_person_name;
    }

    public String getdelivery_status() {
        return delivery_status;
    }

    public void setdelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public String getnote() {
        return note;
    }

    public void setnote(String note) {
        this.note = note;
    }

    public String getcoupon_used() {
        return coupon_used;
    }

    public void setcoupon_used(String coupon_used) {
        this.coupon_used = coupon_used;
    }

    public String getprivilege() {
        return privilege;
    }

    public void setprivilege(String privilege) {
        this.privilege = privilege;
    }

    public Date getorder_time() {
        return order_time;
    }

    public void setorder_time(Date order_time) {
        this.order_time = order_time;
    }

    public Float gettotal_amount() {
        return total_amount;
    }

    public void settotal_amount(Float total_amount) {
        this.total_amount = total_amount;
    }
}
