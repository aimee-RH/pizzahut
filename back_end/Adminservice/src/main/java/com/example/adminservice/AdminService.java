package com.example.adminservice;

import com.example.feign_api.Message.Emit.*;
import com.example.feign_api.Message.Receive.SimpleMessage;
import com.example.feign_api.Pojo.Product;
import com.example.feign_api.Pojo.Admin;
import com.example.feign_api.Pojo.OrderEntity;
import com.example.feign_api.clients.CustomerClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class AdminService {

    @Autowired
    CustomerClients customerClients;
    @Autowired
    AdminMapper adminMapper;

    //----------------------------------登录----------------------------------
    public PostMessage adminLogin(Admin administrator) {

        Admin Account = adminMapper.queryAdminByID(administrator.getAccount());
        if (Account == null)
            return new PostMessage(0, "账号输入错误");
        else if (!Account.getPassword().equals(administrator.getPassword()))
            return new PostMessage(0, "密码输入错误");
        else
            return new PostMessage(1, "登录成功");
    }
    public PostMessage adminRegister(Admin administrator){
        String newID = null;
        try{
            newID = adminMapper.queryNewAdminID();
            administrator.setAccount(newID);
            adminMapper.insertAdmin(administrator);
        }
        catch (Exception e){
            return new PostMessage(0,e.getMessage());
        }
        return new PostMessage(1,newID);
    }
    public PostMessage adminAlterPassword(Admin administrator){
        try {
            if(!adminMapper.queryExistAdminByID(administrator.getAccount())){
                return new PostMessage(0, "账号不存在");
            }
            adminMapper.updateAdminPasswordByID(administrator);
        }
        catch (Exception e){
            return new PostMessage(0, e.getMessage());
        }
        return new PostMessage(1,"密码修改成功");
    }
    public String adminNav(String ID) {
        return adminMapper.queryAdminByID(ID).getName();
    }

    //----------------------------------菜品管理----------------------------------
    public DishMessage searchDish() {
        return customerClients.searchDishes();
    }
    public PostMessage insertDish(Product dish){
        String newID = null;
        try {
            newID = adminMapper.queryNewDishID();
            adminMapper.insertDish(newID, dish.getName(), dish.getCategory(), dish.getOriginalPrice(), dish.getDescription());
        }
        catch (Exception e){
            return new PostMessage(0,e.getMessage());
        }
        return new PostMessage(1,"菜品添加成功");
    }
    public PostMessage deleteDish(String ID){
        try{
            adminMapper.deleteDishByID(ID);
        }
        catch (Exception e){
            return new PostMessage(0,e.getMessage());
        }
        return new PostMessage(1,"删除成功");
    }
    public PostMessage updateDish(Product dish){
        try {
            adminMapper.updateDishByID(dish.getId(), dish.getName(), dish.getCategory(), dish.getOriginalPrice(), dish.getDescription());
        }
        catch (Exception e){
            return new PostMessage(0,e.getMessage());
        }
        return new PostMessage(1,"编辑菜品成功");
    }

    //----------------------------------订单管理----------------------------------
    public OrderMessage searchAllOrder() {return new OrderMessage(adminMapper.queryAllOrder());}
    public DeliversMessage searchAllDelivers(){return new DeliversMessage(adminMapper.queryAllDelivers());}
    public PostMessage updateOrder(OrderEntity Order){
        try{
            adminMapper.updateOrderByID(Order.getId(), Order.getDeliveryPerson(), Order.getDeliveryStatus());
        }
        catch (Exception e){
            return new PostMessage(0,e.getMessage());
        }
        return new PostMessage(1,"订单更新成功");
    }
    public PostMessage deleteOrder(String ID){
        try{
            customerClients.deleteOrder(ID);
        }
        catch (Exception e){
            return new PostMessage(0, e.getMessage());
        }
        return new PostMessage(1,"删除成功");
    }

    //----------------------------------数据管理----------------------------------
    public BackupMessage searchBackups(){
        return new BackupMessage(adminMapper.queryAllBackup());
    }
    public PostMessage backupDatabase(SimpleMessage sm) throws IOException, InterruptedException {
        if (adminMapper.queryExistBackupByPath(sm.getData())) {
            return new PostMessage(0, "该路径名已存在");
        }
        adminMapper.insertBackup(sm.getData());
        String path = "backup/" + sm.getData() + ".sql";

        ProcessBuilder processBuilder =
                new ProcessBuilder("mysqldump", "-u", "root", "-pf25012501", "pizzahut", "--result-file", path);
        Process process = processBuilder.start();

        int exitCode = process.waitFor();
        if (exitCode == 0) {
            return new PostMessage(1, "备份成功");
        } else {
            return new PostMessage(0, "备份失败");
        }
    }
    public PostMessage restoreDatabase(SimpleMessage sm) throws IOException, InterruptedException {
        String name = adminMapper.queryBackupByID(sm.getData());
        String path = "backup/" + name + ".sql";

        ProcessBuilder processBuilder = new ProcessBuilder("mysql", "-u", "root", "-pf25012501", "pizzahut");
        File inputFile = new File(path);
        processBuilder.redirectInput(inputFile);
        Process process = processBuilder.start();

        int exitCode = process.waitFor();
        if (exitCode == 0) {
            return new PostMessage(1, "恢复成功");
        } else {
            return new PostMessage(0, "恢复失败");
        }
    }
}
