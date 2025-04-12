package com.example.shopservice;

import com.example.feign_api.Message.Receive.*;
import com.example.feign_api.Message.Emit.*;
import com.example.feign_api.Pojo.*;
import com.example.feign_api.clients.CustomerClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {

    @Autowired
    CustomerClients customerClients;
    @Autowired
    ShopMapper shopMapper;

    //----------------------------------登录----------------------------------
    public PostMessage shopLogin(Shop shop) {
        Shop Account = shopMapper.queryShopByID(shop.getAccount());
        if (Account == null)
            return new PostMessage(0, "account输入错误");
        else if (!Account.getPassword().equals(shop.getPassword()))
            return new PostMessage(0, "password输入错误");
        else
            return new PostMessage(1, "登录成功");
    }
    public PostMessage shopRegister(Shop shop){
        String newID = null;
        try{
            newID = shopMapper.queryNewShopID();
            shop.setAccount(newID);
            shopMapper.insertShop(shop);
        }
        catch (Exception e){
            return new PostMessage(0,e.getMessage());
        }
        return new PostMessage(1,newID);
    }
    public PostMessage shopAlterPassword(Shop shop){
        try {
            if(!shopMapper.queryExistShopByID(shop.getAccount())){
                return new PostMessage(0, "account不存在");
            }
            shopMapper.updateShopPasswordByID(shop);
        }
        catch (Exception e){
            return new PostMessage(0, e.getMessage());
        }
        return new PostMessage(1,"password修改成功");
    }
    public String shopNav(String ID) {
        return shopMapper.queryShopByID(ID).getName();
    }
    //----------------------------------菜品管理----------------------------------
    public DishMessage searchDishes() {
        return customerClients.searchDishes();
    }
    public ShopDishesMessage searchShopDishes(String ID) {
        return customerClients.searchShopDishes(ID);
    }
    public PostMessage insertShopDishes(DealShopDishesMessage dsdm){
        try{
            shopMapper.insertShopDishes(dsdm.getShopID(), dsdm.getDishID());
        }
        catch (Exception e){
            return new PostMessage(0, e.getMessage());
        }
        return new PostMessage(1,"上架成功");
    }
    public PostMessage deleteShopDishes(DealShopDishesMessage dsdm){
        try{
            shopMapper.deleteShopDishes(dsdm.getShopID(), dsdm.getDishID());
        }
        catch (Exception e){
            return new PostMessage(0, e.getMessage());
        }
        return new PostMessage(1,"下架成功");
    }
    //----------------------------------优惠券发放----------------------------------
    public TemplateCardsMessage searchCards(){
        return new TemplateCardsMessage(shopMapper.queryAllCards());
    }
    public CustomersMessage searchNoCardCustomer(String cardID, String shopID){
        return new CustomersMessage(shopMapper.queryNoCardCustomer(cardID,shopID));
    }
    public PostMessage insertCustomerCard(CustomerCardMessage ucm){
        try{
            shopMapper.insertCustomerCard(ucm.getCustomerID(), ucm.getCardID(), ucm.getShopID());
        }
        catch (Exception e){
            return new PostMessage(0,e.getMessage());
        }
        return new PostMessage(1,"优惠券发放成功");
    }
    //----------------------------------订单列表----------------------------------
    public OrderMessage searchOrder(String id) {
        return new OrderMessage(shopMapper.queryOrderByShopID(id));
    }
    public OrderDetailMessage searchOrderDetail(String orderID, String deliverID) {
        return customerClients.searchOrderDetail(orderID,"0",deliverID);
    }
    //----------------------------------shop信息----------------------------------
    public Shop searchInfo(String ID){
        return shopMapper.queryShopByID(ID);
    }
    public PostMessage updateShopInfo(Shop shop){
        try{
            shopMapper.updateShopInfoByID(shop);
        }
        catch (Exception e){
            return new PostMessage(0, e.getMessage());
        }
        return new PostMessage(1,"修改成功");
    }
}
