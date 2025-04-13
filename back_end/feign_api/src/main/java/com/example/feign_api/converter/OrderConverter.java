package com.example.feign_api.converter;

import com.example.feign_api.Pojo.OrderEntity;
import com.example.feign_api.Message.Emit.Element.Order;

public class OrderConverter {

    public static Order convert(OrderEntity entity) {
        Order order = new Order();
        order.setid(entity.getId());
        order.setorder_time(entity.getOrderTime());
        order.setuser(entity.getUser());
        //order.setuser_name(entity.getUserName());
        order.setaddress(entity.getAddress());
        order.setshop(entity.getShop());
        //order.setshop_name(entity.getShopName());
        order.setdelivery_person(entity.getDeliveryPerson());
        //order.setdelivery_person_name(entity.getDeliveryPersonName());
        order.settotal_amount(entity.getTotalAmount());
        order.setdelivery_status(entity.getDeliveryStatus());
        order.setnote(entity.getNote());
        order.setcoupon_used(entity.getCouponUsed());
        order.setprivilege(entity.getPrivilege());
        return order;
    }

    public static Order[] convertList(OrderEntity[] entities) {
        Order[] orders = new Order[entities.length];
        for (int i = 0; i < entities.length; i++) {
            orders[i] = convert(entities[i]);
        }
        return orders;
    }
}