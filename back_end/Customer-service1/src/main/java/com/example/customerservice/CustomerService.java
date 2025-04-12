package com.example.customerservice;

import com.example.feign_api.Message.Receive.*;
import com.example.feign_api.Message.Emit.*;
import com.example.feign_api.Pojo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //无参的构造方法
@Service
public class CustomerService {
    @Autowired
    CustomerMapper customerMapper;

    //----------------------------------登录----------------------------------
    public PostMessage customerLogin(User customer) {

        User user = customerMapper.queryCustomerByID(customer.getId());
        System.out.println("登录参数: " + customer.getId() + " / " + customer.getPassword());
        User user2 = customerMapper.queryCustomerByIDAndPassword(customer.getId(), customer.getPassword());
        System.out.println("数据库查出: " + user);
        if (user == null)
            return new PostMessage(0, "id输入错误");
        else if (!user.getPassword().equals(customer.getPassword()))
            return new PostMessage(0, "password输入错误");
        else
            return new PostMessage(1, "登录成功");
    }


    public PostMessage customerRegister(User customer){
        try {
            customer.setId(customerMapper.queryNewCustomerID());
            customerMapper.insertCustomer(customer);
        }
        catch (Exception e){
            return new PostMessage(0,e.getMessage());
        }
        return new PostMessage(1,customer.getId());
    }
    public PostMessage customerAlterPassword(User customer){
        try {
            if(!customerMapper.queryExistCustomerByID(customer.getId())){
                return new PostMessage(0, "id不存在");
            }
            customerMapper.updateCustomerPasswordByID(customer);
        }
        catch (Exception e){
            return new PostMessage(0, e.getMessage());
        }
        return new PostMessage(1,"password修改成功");
    }
    public NavMessage customerNav(String ID) {
        User User = customerMapper.queryCustomerByID(ID);
        MembershipLevel level = customerMapper.queryLevelByID(User.getMembershipLevel());
        return new NavMessage(User.getName(),User.getGender(), level.getId(), level.getLevel());
    }

    //----------------------------------点餐----------------------------------
    public DishMessage searchDishes() {
        return new DishMessage(customerMapper.queryAllDish());
    }
    public BuyDishesMessage searchBuyDishes(String ID) {
        return new BuyDishesMessage(customerMapper.queryAvailableShop(),
                customerMapper.queryDiscountByCustomerID(ID),customerMapper.queryTodayPrivilegeByCustomerID(ID));
    }
    public AddressesMessage searchAddresses(String ID){
        return new AddressesMessage(customerMapper.queryAddressesByID(ID));
    }
    public ShopDishesMessage searchShopDishes(String ID) {
        return new ShopDishesMessage(customerMapper.queryShopDishByShopID(ID));
    }
    public PostMessage insertOrder(InsertOrderMessage iom) {
        try {
            iom.getOrderList().setId(customerMapper.queryNewOrderID());
            customerMapper.insertOrder(iom.getOrderList());
            for (int j = 0; j < iom.getPaymentList().length; j++)
                customerMapper.insertOrderDish(iom.getOrderList().getId(), iom.getPaymentList()[j].getId(), iom.getPaymentList()[j].getNum(),iom.getPaymentList()[j].getMoney());
        }
        catch (Exception e){
            return new PostMessage(0, e.getMessage());
        }
        return new PostMessage(1, "添加订单成功");
    }

    //----------------------------------优惠券----------------------------------
    public CardMessage searchCard(String ID) {
        return new CardMessage(customerMapper.queryCardByCustomerID(ID));
    }
    public PostMessage customerCard(UseCardMessage ucm){
        try {
            String newID = customerMapper.queryNewOrderID();
            float money = customerMapper.queryCardMoneyByID(ucm.getCardID());
            Order order = new Order(newID,ucm.getCustomerID(), ucm.getAddress(),
                    ucm.getShop(), money, ucm.getNote(),"是","否");
            customerMapper.insertOrder(order);
            if(ucm.getCardID() != "3"){
                customerMapper.insertOrderDish(newID, ucm.getFirstDish(), 1,money/2);
                customerMapper.insertOrderDish(newID, ucm.getSecondDish(), 1,money/2);
            }
            else{
                customerMapper.insertOrderDish(newID, ucm.getFirstDish(), 1,money);
            }

            customerMapper.deleteCustomerCard(ucm.getCustomerID(), ucm.getCardID(), ucm.getShop());
        }
        catch (Exception e){
            return new PostMessage(0, e.getMessage());
        }
        return new PostMessage(1, "优惠券使用成功");
    }

    //----------------------------------订单----------------------------------
    public OrderMessage searchOrder(String ID) {
        return new OrderMessage(customerMapper.queryOrderByCustomerID(ID));
    }
    public OrderDetailMessage searchOrderDetail(String orderID, String shopID, String deliverID){
        if(shopID != "0")
            return new OrderDetailMessage(customerMapper.queryOrderDishByOrderID(orderID),
                    customerMapper.queryShopByID(shopID),customerMapper.queryDeliverByID(deliverID));
        return new OrderDetailMessage(customerMapper.queryOrderDishByOrderID(orderID),customerMapper.queryDeliverByID(deliverID));
    }
    public PostMessage deleteOrder(SimpleMessage sm){
        System.out.println(sm.getData());
        try{
            customerMapper.deleteOrderByID(sm.getData());
        }
        catch (Exception e){
            return new PostMessage(0, e.getMessage());
        }
        return new PostMessage(1,"订单删除成功");
    }

    //----------------------------------个人信息----------------------------------
    public CustomerMessage searchInfo(String ID) {
        CustomerMessage resp = new CustomerMessage();
        User User = customerMapper.queryCustomerByID(ID);
        String[] Address = customerMapper.queryAddressesByID(ID);

        resp.setName(User.getName());
        resp.setSex(User.getGender());
        resp.setLevel(User.getMembershipLevel());
        resp.setPhone(User.getPhone());
        resp.setAddress(Address);

        return resp;
    }
    public PostMessage updateCustomerInfo(User customer){
        try{
            customerMapper.updateCustomerInfoByID(customer);
        }
        catch (Exception e){
            return new PostMessage(0,e.getMessage());
        }
        return new PostMessage(1,"user信息修改成功");
    }
    public PostMessage updateAddress(UpdateAddressMessage uam){
        try{
            customerMapper.deleteAddressByID(uam.getId());
            for (String addr:uam.getAddresses()) {
                customerMapper.insertAddress(uam.getId(),addr);
            }
        }
        catch (Exception e){
            return new PostMessage(0,e.getMessage());
        }
        return new PostMessage(1,"地址修改成功");
    }

    //----------------------------------会员中心----------------------------------
    public Date searchEXP(String ID){
        return customerMapper.queryEXPByID(ID);
    }
    public PostMessage updateCustomerLevel(User customer){
        try {
            customerMapper.updateCustomerLevelByID(customer);
        }
        catch (Exception e){
            return new PostMessage(0, e.getMessage());
        }
        return new PostMessage(1, "升级成功");
    }





}
