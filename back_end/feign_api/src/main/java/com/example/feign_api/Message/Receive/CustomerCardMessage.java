package com.example.feign_api.Message.Receive;

public class CustomerCardMessage {
    String CustomerID,cardID,shopID;

    public CustomerCardMessage(String customerID, String cardID, String shopID) {
        CustomerID = customerID;
        this.cardID = cardID;
        this.shopID = shopID;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }
}
