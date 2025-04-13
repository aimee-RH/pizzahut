package com.example.adminservice;

import com.example.feign_api.Message.Receive.*;
import com.example.feign_api.Message.Emit.*;
import com.example.feign_api.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController

@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    //----------------------------------登录----------------------------------
    @PostMapping("/login")
    public PostMessage adminLogin(@RequestBody Admin administrator) {return adminService.adminLogin(administrator);}
    @PostMapping("/register")
    public PostMessage adminRegister(@RequestBody Admin administrator){return adminService.adminRegister(administrator);}
    @PostMapping("/alterPassword")
    public PostMessage alterPassword(@RequestBody Admin administrator) {return adminService.adminAlterPassword(administrator);}
    @GetMapping("/nav/{ID}")
    public String adminNav(@PathVariable String ID){return adminService.adminNav(ID);}

    //----------------------------------菜品管理----------------------------------
    @GetMapping("/dish")
    public DishMessage displayDish(){return adminService.searchDish();}
    @PostMapping("/insertDish")
    public PostMessage addDish(@RequestBody Product dish){return adminService.insertDish(dish);}
    @GetMapping("/deleteDish/{ID}")
    public PostMessage removeDish(@PathVariable String ID){return adminService.deleteDish(ID);}
    @PostMapping("/editDish")
    public PostMessage alterDish(@RequestBody Product dish){return adminService.updateDish(dish);}

    //----------------------------------订单管理----------------------------------
    @GetMapping("/product")
    public OrderMessage displayAllproduct(){return adminService.searchAllOrder();}
    @GetMapping("/deliveries")
    public DeliversMessage displayAllDelivers(){return adminService.searchAllDelivers();}
    @PostMapping("/updateproduct")
    public PostMessage alterproduct(@RequestBody OrderEntity order){return adminService.updateOrder(order);}
    @GetMapping("/deleteproduct/{ID}")
    public PostMessage removeproduct(@PathVariable String ID){return adminService.deleteOrder(ID);}

    //----------------------------------数据管理----------------------------------
    @GetMapping("/backups")
    public BackupMessage searchBackups(){
        return adminService.searchBackups();
    }
    @PostMapping("/backup")
    public PostMessage backupDatabase(@RequestBody SimpleMessage sm) throws IOException, InterruptedException {
        return adminService.backupDatabase(sm);
    }
    @PostMapping("/restore")
    public PostMessage restoreDatabase(@RequestBody SimpleMessage sm) throws IOException, InterruptedException {
        return adminService.restoreDatabase(sm);
    }

}
