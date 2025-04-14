package com.example.feign_api.Message.Emit.Element;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Order {
    private String id;
    private String user;
    private String userName;
    private String address;
    private String shop;
    private String shopName;
    private String deliveryPerson;
    private String deliveryPersonName;
    private String deliveryStatus;
    private String note;
    private String couponUsed;
    private String privilege;
    private Date orderTime;
    private Float totalAmount;
}
