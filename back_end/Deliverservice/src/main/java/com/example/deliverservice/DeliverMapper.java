package com.example.deliverservice;

import com.example.feign_api.Pojo.*;
import com.example.feign_api.Pojo.OrderEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface DeliverMapper {
    //----------------------------------DeliveryPerson----------------------------------
    @Select("select * from delivery_person where account = #{ID}")
    DeliveryPerson queryDeliverByID(String ID);
    @Select("select count(*) from delivery_person where account = #{ID}")
    boolean queryExistDeliverByID(String ID);
    @Select("select GenerateDeliverID()")
    String queryNewDeliverID();
    @Insert("insert into delivery_person values(#{account},#{name},#{gender},#{phone},#{password})")
    void insertDeliver(DeliveryPerson deliver);
    @Update("update delivery_person set password = #{password} where account = #{account}")
    void updateDeliverPasswordByID(DeliveryPerson deliver);
    @Update("update delivery_person set `name` = '${name}',`gender` = '${gender}',`phone` = '${phone}' where `account` = #{account}")
    void updateDeliverInfoByID(DeliveryPerson deliver);

    //----------------------------------order----------------------------------
    @Select("select a.`id`,`order_time`,`user`,`user_name`,`address`,`shop`,`shop_name`,`total_amount`,`delivery_status`,`note`,`coupon_used`,`privilege` " +
            "from (select * from order where delivery_status = 'Not Delivered')a " +
            "join (select account,name as user_name from user)b on a.user = b.account " +
            "join (select account,name as shop_name from shop)c on a.shop = c.account " +
            "order by `order_time` desc")
    OrderEntity[] queryUndeliveredOrder();
    @Select("select a.`id`,`order_time`,`user`,`user_name`,`address`,`shop`,`shop_name`,`total_amount`,`delivery_status`,`note`,`coupon_used`,`privilege` " +
            "from (select * from order where delivery_person = #{ID})a " +
            "join (select account,name as user_name from user)b on a.user = b.account " +
            "join (select account,name as shop_name from shop)c on a.shop = c.account " +
            "order by `order_time` desc")
    OrderEntity[] queryOrderByDeliverID(String ID);

    @Update("update order set delivery_person = #{deliverID}, delivery_status = 'In Delivery' where id = #{ID}")
    void updateOrderDeliver(String ID,String deliverID);

    @Update("update order set delivery_status = 'Delivered' where id = #{ID}")
    void updateOrderState(String ID);
}
