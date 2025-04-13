package com.example.deliverservice;

import com.example.feign_api.Message.Receive.*;
import com.example.feign_api.Message.Emit.*;
import com.example.feign_api.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/deliver")
public class DeliverController {
    @Autowired
    DeliverService deliverService;

    //----------------------------------登录----------------------------------
    @PostMapping("/login")
    public PostMessage deliverLogin(@RequestBody DeliveryPerson deliver) {return deliverService.deliverLogin(deliver);}

    @PostMapping("/register")
    public PostMessage deliverRegister(@RequestBody DeliveryPerson deliver){return deliverService.deliverRegister(deliver);}

    @PostMapping("/alterPassword")
    public PostMessage deliverAlterPassword(@RequestBody DeliveryPerson deliver) {return deliverService.deliverAlterPassword(deliver);}

    @GetMapping("/nav/{ID}")
    public String deliverNav(@PathVariable String ID){return deliverService.deliverNav(ID);}
    //----------------------------------接单列表----------------------------------
    @GetMapping("/order")
    public OrderMessage searchOrder(){
        return deliverService.searchOrder();
    }
    @PostMapping("/receiveOrder")
    public PostMessage receiveOrder(@RequestBody OrderStateMessage osm){
        return deliverService.receiveOrder(osm);
    }
    //----------------------------------我的接单----------------------------------
    @GetMapping("/history/{ID}")
    public OrderMessage searchHistory(@PathVariable String ID){
        return deliverService.searchHistory(ID);
    }
    @PostMapping("/updateOrder")
    public PostMessage updateOrder(@RequestBody OrderStateMessage osm){
        return deliverService.updateOrder(osm);
    }
    //----------------------------------配送员信息----------------------------------
    @GetMapping("/info/{ID}")
    public DeliveryPerson searchInfo(@PathVariable String ID){
        return deliverService.searchInfo(ID);
    }
    @PostMapping("/alterInfo")
    public PostMessage updateDeliverInfo(@RequestBody DeliveryPerson deliver){
        return deliverService.updateDeliverInfo(deliver);
    }
}
