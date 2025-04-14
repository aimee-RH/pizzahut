package com.example.feign_api.Message.Emit.Element;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //无参的构造方法
public class OrderDish {
    String dishName;
    int number;
    float amount;

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String name) {
        this.dishName = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
