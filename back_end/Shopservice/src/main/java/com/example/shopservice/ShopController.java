package com.example.shopservice;

import com.example.feign_api.Message.Emit.Element.*;
import com.example.feign_api.Message.Receive.*;
import com.example.feign_api.Message.Emit.*;
import com.example.feign_api.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    ShopService shopService;

    //----------------------------------登录----------------------------------
    @PostMapping("/login")
    public PostMessage shopLogin(@RequestBody Shop shop) {return shopService.shopLogin(shop);}

    @PostMapping("/register")
    public PostMessage shopRegister(@RequestBody Shop shop){return shopService.shopRegister(shop);}

    @PostMapping("/alterPassword")
    public PostMessage shopAlterPassword(@RequestBody Shop shop) {return shopService.shopAlterPassword(shop);}

    @GetMapping("/nav/{ID}")
    public String shopNav(@PathVariable String ID){return shopService.shopNav(ID);}

    //----------------------------------菜品管理----------------------------------
    @GetMapping("/dish")
    public DishMessage searchDishes(){
        return shopService.searchDishes();
    }

    @GetMapping("/availableDishes/{ID}")
    public ShopDishesMessage searchShopDishes(@PathVariable String ID){
        return shopService.searchShopDishes(ID);
    }

    @PostMapping("/addDish")
    public PostMessage insertShopDishes(@RequestBody DealShopDishesMessage dsdm){
        return shopService.insertShopDishes(dsdm);
    }

    @PostMapping("/removeDish")
    public PostMessage deleteShopDishes(@RequestBody DealShopDishesMessage dsdm){
        return shopService.deleteShopDishes(dsdm);
    }

    //----------------------------------优惠券发放----------------------------------
    @GetMapping("/card")
    public TemplateCardsMessage displayAllCards(){return shopService.searchCards();}

    @GetMapping("/noCardCustomer/{cardID}/{shopID}")
    public CustomersMessage displayNoCardUser(@PathVariable String cardID, @PathVariable String shopID){
        return shopService.searchNoCardCustomer(cardID,shopID);
    }

    @PostMapping("/distributeCard")
    public PostMessage distributeCard(@RequestBody CustomerCardMessage ucm){return shopService.insertCustomerCard(ucm);}

    //----------------------------------订单列表----------------------------------

    @GetMapping("/order/{ID}")
    public OrderMessage searchOrder(@PathVariable String ID){
        return shopService.searchOrder(ID);
    }

    @GetMapping("/orderDetail/{orderID}/{deliverID}")
    public OrderDetailMessage searchOrderDetail(@PathVariable String orderID, @PathVariable String deliverID){
        return shopService.searchOrderDetail(orderID,deliverID);
    }
    //----------------------------------shop信息----------------------------------
    @GetMapping("/info/{ID}")
    public Shop searchInfo(@PathVariable String ID){
        return shopService.searchInfo(ID);
    }
    @PostMapping("/alterInfo")
    public PostMessage updateShopInfo(@RequestBody Shop shop){
        return shopService.updateShopInfo(shop);
    }
}
