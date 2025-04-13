package com.example.shopservice;


import com.example.feign_api.Pojo.*;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ShopMapper {
    //----------------------------------Shop----------------------------------
    @Select("select * from shop where id = #{ID}")
    Shop queryShopByID(String ID);
    @Select("select count(*) from shop where id = #{ID}")
    boolean queryExistShopByID(String ID);
    @Select("select GenerateShopID()")
    String queryNewShopID();
    @Insert("insert into shop values(#{id},#{name},#{address},#{phone},#{password},#{open_time},#{close_time})")
    void insertShop(Shop shop);
    @Update("update shop set password = #{password} where id = #{id}")
    void updateShopPasswordByID(Shop shop);
    @Update("update shop set `name` = '${name}',`address` = '${address}', `phone` = '${phone}', " +
            "open_time = '${open_time}',close_time = '${close_time}' where `id` = #{id}")
    void updateShopInfoByID(Shop shop);

    //----------------------------------product----------------------------------
    @Insert("insert into shop_product values (#{shopID}, #{dishID})")
    void insertShopDishes(String shopID, String dishID);
    @Delete("delete from shop_product where shop_id = #{shopID} and product_id = #{dishID}")
    void deleteShopDishes(String shopID, String dishID);
    //----------------------------------coupon----------------------------------
    @Select("select * from coupon")
    Coupon[] queryAllCards();
    @Select("select * from user where id not in " +
            "(select user_id from user_coupon where coupon_id = #{cardID} and shop_id = #{shopID})")
    User[] queryNoCardCustomer(String cardID, String shopID);
    @Insert("insert into user_coupon values (#{CustomerID}, #{cardID}, #{shopID})")
    void insertCustomerCard(String CustomerID, String cardID, String shopID);
    //----------------------------------order----------------------------------
    @Select("select a.`id`,`order_time`,`user`,`user_name`,`address`,`delivery_person`,`delivery_person_name`,`total_amount`,`delivery_status`,`note`,`coupon_used`,`privilege` " +
            "from (select * from order where Shop = #{ID})a " +
            "join (select id,name as user_name from user)b on a.user = b.id " +
            "join (select id,name as delivery_person_name from delivery_person)c on a.delivery_person = c.id " +
            "order by `order_time` desc")
    OrderEntity[] queryOrderByShopID(String ID);

}
