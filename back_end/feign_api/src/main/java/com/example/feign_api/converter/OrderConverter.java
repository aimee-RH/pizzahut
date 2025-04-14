package com.example.feign_api.converter;

import com.example.feign_api.Pojo.OrderEntity;
import com.example.feign_api.Message.Emit.Element.Order;

public class OrderConverter {

    public static Order convert(OrderEntity entity) {
        Order order = new Order();
        order.setId(entity.getId());
        order.setOrderTime(entity.getOrderTime());
        order.setUser(entity.getUser());
        order.setAddress(entity.getAddress());
        order.setShop(entity.getShop());
        order.setShopName(entity.getShopName()); // ✅ 关键补充！
        order.setDeliveryPersonName(entity.getDeliveryPersonName());
        order.setDeliveryPerson(entity.getDeliveryPerson());
        order.setTotalAmount(entity.getTotalAmount());
        order.setDeliveryStatus(entity.getDeliveryStatus());
        order.setNote(entity.getNote());
        order.setCouponUsed(entity.getCouponUsed());
        order.setPrivilege(entity.getPrivilege());
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