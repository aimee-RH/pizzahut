package com.example.adminservice;


import com.example.feign_api.Message.Emit.Element.*;
import com.example.feign_api.Message.Emit.Element.Order;
import com.example.feign_api.Message.Receive.*;
import com.example.feign_api.Message.Emit.*;
import com.example.feign_api.Pojo.*;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminMapper {
    //----------------------------------admin----------------------------------
    @Select("select * from admin where account = #{ID}")
    Admin queryAdminByID(String ID);
    @Select("select count(*) from admin where account = #{ID}")
    boolean queryExistAdminByID(String ID);
    @Select("select GenerateAdminID()")
    String queryNewAdminID();
    @Insert("insert into admin values(#{account},#{name},#{password})")
    void insertAdmin(Admin admin);
    @Update("update admin set password = #{password} where account = #{account}")
    void updateAdminPasswordByID(Admin admin);

    //----------------------------------product----------------------------------
    @Select("select GenerateDishID()")
    String queryNewDishID();
    @Insert("insert into product values (#{ID},#{name},#{type},#{price},#{describe})")
    void insertDish(String ID,String name,String type,float price,String describe);
    @Delete("delete from product where id = #{ID}")
    void deleteDishByID(String ID);
    @Update("update product set name = #{name}, category = #{type}, original_price = #{price}, description = #{describe} where id = #{ID}")
    void updateDishByID(String ID, String name, String type, float price, String describe);

    //----------------------------------order----------------------------------
    @Select("select a.`id`,`order_time`,`user`,`user_name`,`address`,`shop`,`shop_name`,`delivery_person`,`delivery_person_name`,`total_amount`,`delivery_status`,`note`,`coupon_used`,`privilege` " +
            "from (select * from order)a " +
            "join (select account,name as user_name from user)b on a.user = b.account " +
            "join (select account,name as shop_name from shop)c on a.shop = c.account " +
            "join (select account,name as delivery_person_name from delivery_person)d on a.delivery_person = d.account " +
            "order by `order_time` desc")
    Order[] queryAllOrder();
    @Update("update order set delivery_person = #{deliver}, delivery_status = #{state} where id = #{ID}")
    void updateOrderByID(String ID, String deliver, String state);

    //----------------------------------delivery_person----------------------------------
    @Select("select * from delivery_person")
    DeliveryPerson[] queryAllDelivers();

    //----------------------------------数据备份----------------------------------
    @Select("select * from backup_log order by order_time desc")
    BackupLog[] queryAllBackup();
    @Select("select count(*) from backup_log where file_name = #{path}")
    boolean queryExistBackupByPath(String path);
    @Insert("insert into backup_log values (GenerateBackupID(),now(),#{path})")
    void insertBackup(String path);
    @Select("select file_name from backup_log where id = #{ID}")
    String queryBackupByID(String ID);

}
