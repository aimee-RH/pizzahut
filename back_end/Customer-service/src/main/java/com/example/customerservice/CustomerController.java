package com.example.customerservice;



import com.example.feign_api.Message.Receive.*;
import com.example.feign_api.Message.Emit.*;
import com.example.feign_api.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //无参的构造方法
@RestController
@RequestMapping("/customer")
public class CustomerController
{
    @Autowired
    CustomerService customerService;

    //----------------------------------登录----------------------------------
    @PostMapping("/login")
    public PostMessage customerLogin(@RequestBody User customer) {
        return customerService.customerLogin(customer);
    }
    @PostMapping("/register")
    public PostMessage customerRegister(@RequestBody User customer){
        return customerService.customerRegister(customer);
    }
    @PostMapping("/alterPassword")
    public PostMessage customerAlterPassword(@RequestBody User customer) {
        return customerService.customerAlterPassword(customer);
    }
    @GetMapping("/nav/{ID}")
    public NavMessage customerNav(@PathVariable String ID){
        return customerService.customerNav(ID);
    }

    //----------------------------------点餐----------------------------------
    @GetMapping("/dish")
    public DishMessage searchDishes(){
        return customerService.searchDishes();
    }
    @GetMapping("/buyDishes/{ID}")
    public BuyDishesMessage searchBuyDishes(@PathVariable("ID") String ID){
        return customerService.searchBuyDishes(ID);
    }

    @GetMapping("/availableDishes/{ID}")
    public ShopDishesMessage searchShopDishes(@PathVariable String ID){
        return customerService.searchShopDishes(ID);
    }
    @PostMapping("/insertOrder")
    public PostMessage insertOrder(@RequestBody InsertOrderMessage iom){
        return customerService.insertOrder(iom);
    }

    //----------------------------------优惠券----------------------------------
    @GetMapping("/card/{ID}")
    public CardMessage searchCard(@PathVariable String ID){
        return customerService.searchCard(ID);
    }
    @PostMapping("/useCard")
    public PostMessage customerCard(@RequestBody UseCardMessage ucm){
        return customerService.customerCard(ucm);
    }

    //----------------------------------订单----------------------------------
    @GetMapping("/order/{ID}")
    public OrderMessage searchOrder(@PathVariable("ID") String ID){
        return customerService.searchOrder(ID);
    }

    @GetMapping("/orderDetail/{orderID}/{shopID}/{deliverID}")
    public OrderDetailMessage searchOrderDetail(@PathVariable("orderID") String orderID, @PathVariable("shopID") String shopID, @PathVariable("deliverID") String deliverID){
        return customerService.searchOrderDetail(orderID,shopID,deliverID);
    }
    @PostMapping("/deleteOrder")
    public PostMessage deleteOrder(@RequestBody SimpleMessage sm){
        return customerService.deleteOrder(sm);
    }

    //----------------------------------个人信息----------------------------------
    @GetMapping("/addresses/{ID}")
    public AddressesMessage searchAddresses(@PathVariable  String ID){
        return customerService.searchAddresses(ID);
    }
    @GetMapping("/info/{ID}")
    public CustomerMessage searchInfo(@PathVariable String ID){
        return customerService.searchInfo(ID);
    }
    @PostMapping("/alterBase")
    public PostMessage updateCustomerInfo(@RequestBody User customer){
        return customerService.updateCustomerInfo(customer);
    }
    @PostMapping("/alterAddr")
    public PostMessage updateAddress(@RequestBody UpdateAddressMessage uam){
        return customerService.updateAddress(uam);
    }

    //----------------------------------会员中心----------------------------------
    @GetMapping("/EXP/{ID}")
    public Date searchEXP(@PathVariable String ID){
        return customerService.searchEXP(ID);
    }
    @PostMapping("/upgrade")
    public PostMessage updateCustomerLevel(@RequestBody User customer){
        return customerService.updateCustomerLevel(customer);
    }
}
