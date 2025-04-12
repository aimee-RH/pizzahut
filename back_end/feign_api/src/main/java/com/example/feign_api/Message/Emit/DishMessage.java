package com.example.feign_api.Message.Emit;

import com.example.feign_api.Pojo.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //无参的构造方法

public class DishMessage{
    Product[] dishes;

    public DishMessage(Product[] dishes) {
        this.dishes = dishes;
    }

    public Product[] getDishes() {
        return dishes;
    }

    public void setDishes(Product[] dishes) {
        this.dishes = dishes;
    }
}

