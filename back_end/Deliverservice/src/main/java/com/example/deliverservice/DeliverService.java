package com.example.deliverservice;

import com.example.feign_api.Message.Receive.*;
import com.example.feign_api.Message.Emit.*;
import com.example.feign_api.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliverService {

    @Autowired
    DeliverMapper deliverMapper;

    //----------------------------------登录----------------------------------
    public PostMessage deliverLogin(delivery_person deliver) {
        delivery_person Account = deliverMapper.queryDeliverByID(deliver.get_account());
        if (Account == null)
            return new PostMessage(0, "账号输入错误");
        else if (!Account.get_password().equals(deliver.get_password()))
            return new PostMessage(0, "密码输入错误");
        else
            return new PostMessage(1, "登录成功");
    }
    public PostMessage deliverRegister(delivery_person deliver){
        String newID = null;
        try{
            newID = deliverMapper.queryNewDeliverID();
            deliver.set_account(newID);
            deliverMapper.insertDeliver(deliver);
        }
        catch (Exception e){
            return new PostMessage(0,e.getMessage());
        }
        return new PostMessage(1,newID);
    }
    public PostMessage deliverAlterPassword(delivery_person deliver){
        try {
            if(!deliverMapper.queryExistDeliverByID(deliver.get_account())){
                return new PostMessage(0, "账号不存在");
            }
            deliverMapper.updateDeliverPasswordByID(deliver);
        }
        catch (Exception e){
            return new PostMessage(0, e.getMessage());
        }
        return new PostMessage(1,"密码修改成功");
    }
    public String deliverNav(String ID) {
        return deliverMapper.queryDeliverByID(ID).get_name();
    }
    //----------------------------------接单列表----------------------------------
    public OrderMessage searchOrder() {
        return new OrderMessage(deliverMapper.queryUndeliveredOrder());
    }
    public PostMessage receiveOrder(OrderStateMessage osm){
        try {
            deliverMapper.updateOrderDeliver(osm.getOrderID(),osm.getDeliverID());
        }
        catch (Exception e){
            return new PostMessage(0, e.getMessage());
        }
        return new PostMessage(1,"接单成功");
    }

    //----------------------------------我的接单----------------------------------
    public OrderMessage searchHistory(String id) {
        return new OrderMessage(deliverMapper.queryOrderByDeliverID(id));
    }
    public PostMessage updateOrder(OrderStateMessage osm){
        try {
            deliverMapper.updateOrderState(osm.getOrderID());
        }
        catch (Exception e){
            return new PostMessage(0, e.getMessage());
        }
        return new PostMessage(1,"订单更新成功");
    }

    //----------------------------------配送员信息----------------------------------
    public delivery_person searchInfo(String ID){
        return deliverMapper.queryDeliverByID(ID);
    }
    public PostMessage updateDeliverInfo(delivery_person deliver){
        try{
            deliverMapper.updateDeliverInfoByID(deliver);
        }
        catch (Exception e){
            return new PostMessage(0, e.getMessage());
        }
        return new PostMessage(1,"修改成功");
    }
}
