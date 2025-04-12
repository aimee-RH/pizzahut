package com.example.feign_api.Message.Emit;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //无参的构造方法
public class ShopDishesMessage {
    String[] availableDishes;

    public ShopDishesMessage(String[] availableDishes) {
        this.availableDishes = availableDishes;
    }

    public String[] getAvailableDishes() {
        return availableDishes;
    }

    public void setAvailableDishes(String[] availableDishes) {
        this.availableDishes = availableDishes;
    }

}
