package com.example.customerservice;
import com.example.feign_api.Pojo.Order;
import org.apache.ibatis.annotations.*;
import com.example.feign_api.Message.Emit.Element.*;
import com.example.feign_api.Pojo.*;
import java.util.Date;

@Mapper
public interface CustomerMapper
{
    //----------------------------------user----------------------------------
    @Select("select * from user where id = #{id}")
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
    @Select("SELECT * FROM membership_level where id = #{ID}")
    MembershipLevel queryLevelByID(String ID);
    @Select("call queryDiscountByCustomerID(#{ID})")
    float queryDiscountByCustomerID(String ID);

    //----------------------------------product----------------------------------
    @Select("call queryAllDish()")
    Product[] queryAllDish();

    //----------------------------------coupon----------------------------------
    @Select("select id,type,amount,shop_id,shop_name from coupon " +
            "join (select coupon_id,shop_id from user_coupon where user_id = #{ID})a on a.coupon_id = id " +
            "join (select id,name as shop_name from shop)b on b.id = a.shop_id")
    Card[] queryCardByCustomerID(String ID);
    @Select("select amount from coupon where id = #{ID}")
    float queryCardMoneyByID(String ID);
    @Delete("delete from user_coupon where user_id = #{customerID} and coupon_id = #{cardID} and shop_id = #{shopID}")
    void deleteCustomerCard(String customerID, String cardID, String shopID);
    //----------------------------------order----------------------------------
    @Select("call queryOrderByCustomerID(#{ID})")
    Order[] queryOrderByCustomerID(String ID);
    @Select("select GenerateOrderID()")
    String queryNewOrderID();
    @Insert("call insertOrder(#{id},#{user},#{address},#{shop},#{总amount},#{备注},#{用券},#{特权})")
    void insertOrder(Order order);
    @Select("call queryTodayPrivilegeByUerID(#{ID})")
    boolean queryTodayPrivilegeByCustomerID(String ID);
    @Delete("delete from order where id = #{ID}")
    void deleteOrderByID(String ID);

    //---------------------------------order_product--------------------------------
    @Select("call queryOrderDishByOrderID(#{ID})")
    OrderDish[] queryOrderDishByOrderID(String ID);
    @Insert("call insertOrderDish(#{orderID},#{dishID},#{num},#{money})")
    void insertOrderDish(String orderID,String dishID,int num,float money);

    //----------------------------------shop----------------------------------
    @Select("call queryAvailableShop()")
    Shop[] queryAvailableShop();
    @Select("call queryShopByID(#{ID})")
    Shop queryShopByID(String ID);

    //---------------------------------shop_product---------------------------------
    @Select("call queryShopDishByShopID(#{shopID})")
    String[] queryShopDishByShopID(String shopID);

    //---------------------------------delivery_person---------------------------------
    @Select("select * from delivery_person where id = #{ID}")
    DeliveryPerson queryDeliverByID(String ID);

}
