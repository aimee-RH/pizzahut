package com.example.feign_api.Message.Emit.Element;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //无参的构造方法
public class OrderDish {
    String name;
    int number;

    float amount;

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public int getnumber() {
        return number;
    }

    public void setnumber(int number) {
        this.number = number;
    }

    public float getamount() {
        return amount;
    }

    public void setamount(float amount) {
        this.amount = amount;
    }
}
