package com.example.feign_api.clients;

import com.example.feign_api.Message.Emit.DishMessage;
import com.example.feign_api.Message.Emit.OrderDetailMessage;
import com.example.feign_api.Message.Emit.PostMessage;
import com.example.feign_api.Message.Emit.ShopDishesMessage;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value="Customerservice")
public interface CustomerClients {

    //----------------管理员-----------------------------------

    @GetMapping("/customer/{id}")
    PostMessage deleteOrder(@PathVariable("id")  String id);

    //----------------门店--------------------------------------

    @GetMapping("/customer/dish")//一定需要无参构造函数！！
    DishMessage searchDishes();

    @GetMapping("/customer/availableDishes/{ID}")
    ShopDishesMessage searchShopDishes(@PathVariable("ID") String ID);

    @GetMapping("/orderDetail/{orderID}/{shopID}/{deliverID}")
    OrderDetailMessage searchOrderDetail(@PathVariable("orderID") String orderID, @PathVariable("shopID") String shopID,
                                         @PathVariable("deliverID") String deliverID);

}
