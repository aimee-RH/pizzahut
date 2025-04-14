package com.example.customerservice;
import com.example.feign_api.Message.Emit.Element.Order;
import org.apache.ibatis.annotations.*;
import com.example.feign_api.Message.Emit.Element.*;
import com.example.feign_api.Pojo.*;

import java.time.LocalTime;
import java.util.Date;

@Mapper
public interface CustomerMapper
{
    //----------------------------------user----------------------------------
    @Select("SELECT * FROM user WHERE id = #{id}")
    @Results({
            @Result(property = "membershipLevel", column = "membership_level"),
            @Result(property = "membershipExpiry", column = "membership_expiry")
    })
    User queryCustomerByID(@Param("id") String id);

    @Select("SELECT * FROM user WHERE id = #{id} AND password = #{password}")
    User queryCustomerByIDAndPassword(@Param("id") String id, @Param("password") String password);

    @Select("select count(*) from user where id = #{ID}")
    boolean queryExistCustomerByID(String ID);
    @Select("select GenerateCustomerID()")
    String queryNewCustomerID();
    @Insert("insert into user values(#{id},#{name},#{gender},#{phone},1,now(),#{password})")
    void insertCustomer(User customer);
    @Update("update user set password = #{password} where id = #{id}")
    void updateCustomerPasswordByID(User customer);
    @Update("update user set membership_level = #{membership_level} ,membership_expiry = DATE_ADD(NOW(), INTERVAL +1 year) where id = #{id}")
    void updateCustomerLevelByID(User customer);
    @Update("update user set `name` = '${name}', `gender` = '${gender}', `phone` = #{phone} where `id` = #{id}")
    void updateCustomerInfoByID(User customer);
    @Select("select membership_expiry from user where id = #{ID}")
    Date queryEXPByID(String ID);

    //----------------------------------address----------------------------------
    @Select("call queryAddressByID(#{ID})")
    String[] queryAddressesByID(String ID);
    @Delete("delete FROM user_address where user_id = #{ID}")
    void deleteAddressByID(String ID);
    @Insert("insert into user_address values (#{ID},#{addr})")
    void insertAddress(String ID,String addr);

    //---------------------------------membership_level---------------------------------



    @Select("SELECT * FROM membership_level WHERE id = #{id}")
    MembershipLevel queryLevelByID(@Param("id") String id);

    @Select("call queryDiscountByCustomerID(#{ID})")
    float queryDiscountByCustomerID(String ID);

    //----------------------------------product----------------------------------
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "category", column = "category"),
            @Result(property = "originalPrice", column = "original_price"),
            @Result(property = "description", column = "description")
    })
    @Select("CALL queryAllDish()")
    Product[] queryAllDish();


    @Select("select coupon.id as id, type, amount, shop_id, shop_name " +
            "from coupon " +
            "join (select coupon_id, shop_id from user_coupon where user_id = #{ID}) a " +
            "on a.coupon_id = coupon.id " +
            "join (select id, name as shop_name from shop) b " +
            "on b.id = a.shop_id")
    Card[] queryCardByCustomerID(String ID);



    @Select("select amount from coupon where id = #{ID}")
    float queryCardMoneyByID(String ID);
    @Delete("delete from user_coupon where user_id = #{customerID} and coupon_id = #{cardID} and shop_id = #{shopID}")
    void deleteCustomerCard(String customerID, String cardID, String shopID);
    //----------------------------------order----------------------------------
//    @Select("call queryOrderByCustomerID(#{ID})")
//    Order[] queryOrderByCustomerID(String ID);

    @Select("CALL queryOrderByCustomerID(#{ID})")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "orderTime", column = "order_time"),
            @Result(property = "user", column = "user_id"),
            @Result(property = "address", column = "address"),
            @Result(property = "shop", column = "shop_id"), // 👈 一定要有
            @Result(property = "deliveryPersonName", column = "delivery_person_name"),
            @Result(property = "shopName", column = "shop_name"),
            @Result(property = "deliveryPerson", column = "delivery_person"),
            @Result(property = "totalAmount", column = "total_amount"),
            @Result(property = "deliveryStatus", column = "delivery_status"),
            @Result(property = "note", column = "note"),
            @Result(property = "couponUsed", column = "coupon_used"),
            @Result(property = "privilege", column = "privilege")
    })
    Order[] queryOrderByCustomerID(String ID);

    @Select("select GenerateOrderID()")
    String queryNewOrderID();
    @Insert("call insertOrder(#{id},#{user},#{address},#{shop},#{totalAmount},#{note},#{couponUsed},#{privilege})")
    void insertOrder(OrderEntity order);
    @Select("call queryTodayPrivilegeByUserID(#{ID})")
    boolean queryTodayPrivilegeByCustomerID(String ID);
    @Delete("delete from order where id = #{ID}")
    void deleteOrderByID(String ID);

    //---------------------------------order_product--------------------------------
    @Select("call queryOrderDishByOrderID(#{ID})")
    @Results({
            @Result(property = "dishName", column = "dish_name"),
            @Result(property = "number", column = "number"),
            @Result(property = "price", column = "price")
    })
    OrderDish[] queryOrderDishByOrderID(String ID);

    @Insert("call insertOrderDish(#{orderID},#{dishID},#{num},#{money})")
    void insertOrderDish(String orderID,String dishID,int num,float money);

    //----------------------------------shop----------------------------------
    @Select("CALL queryAvailableShop(#{now})")
    Shop[] queryAvailableShop(LocalTime now);

    @Select("call queryShopByID(#{ID})")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "address", column = "address"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "openTime", column = "open_time"),
            @Result(property = "closeTime", column = "close_time")
    })

    Shop queryShopByID(String ID);
    //---------------------------------shop_product---------------------------------
    @Select("call queryShopDishByShopID(#{shopID})")
    String[] queryShopDishByShopID(String shopID);

    //---------------------------------delivery_person---------------------------------
    @Select("select * from delivery_person where id = #{ID}")
    DeliveryPerson queryDeliverByID(String ID);

}
