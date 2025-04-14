package com.example.deliverservice;

import com.example.feign_api.Message.Receive.*;
import com.example.feign_api.Message.Emit.*;
import com.example.feign_api.Pojo.*;
import com.example.feign_api.converter.OrderConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.feign_api.Message.Emit.Element.Order;

@Service
public class DeliverService {

    @Autowired
    DeliverMapper deliverMapper;

    //----------------------------------登录----------------------------------
    public PostMessage deliverLogin(DeliveryPerson deliver) {
        DeliveryPerson Account = deliverMapper.queryDeliverByID(deliver.getId());
        if (Account == null)
            return new PostMessage(0, "账号输入错误");
        else if (!Account.getPassword().equals(deliver.getPassword()))
            return new PostMessage(0, "密码输入错误");
        else
            return new PostMessage(1, "登录成功");
    }
    public PostMessage deliverRegister(DeliveryPerson deliver){
        String newID = null;
        try{
            newID = deliverMapper.queryNewDeliverID();
            deliver.setId(newID);
            deliverMapper.insertDeliver(deliver);
        }
        catch (Exception e){
            return new PostMessage(0,e.getMessage());
        }
        return new PostMessage(1,newID);
    }
    public PostMessage deliverAlterPassword(DeliveryPerson deliver){
        try {
            if(!deliverMapper.queryExistDeliverByID(deliver.getId())){
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
        return deliverMapper.queryDeliverByID(ID).getName();
    }
    //----------------------------------接单列表----------------------------------
    public OrderMessage searchOrder() {
        return new OrderMessage(OrderConverter.convertList(deliverMapper.queryUndeliveredOrder()));
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
        return new OrderMessage(OrderConverter.convertList(deliverMapper.queryOrderByDeliverID(id)));
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
    public DeliveryPerson searchInfo(String ID){
        return deliverMapper.queryDeliverByID(ID);
    }
    public PostMessage updateDeliverInfo(DeliveryPerson deliver){
        try{
            deliverMapper.updateDeliverInfoByID(deliver);
        }
        catch (Exception e){
            return new PostMessage(0, e.getMessage());
        }
        return new PostMessage(1,"修改成功");
    }
}
