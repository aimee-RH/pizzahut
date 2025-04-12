package com.example.feign_api.Message.Receive;

public class UseCardMessage {
    String CustomerID, cardID, firstDish, secondDish, address, shop, note;

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        this.CustomerID = customerID;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getFirstDish() {
        return firstDish;
    }

    public void setFirstDish(String firstDish) {
        this.firstDish = firstDish;
    }

    public String getSecondDish() {
        return secondDish;
    }

    public void setSecondDish(String secondDish) {
        this.secondDish = secondDish;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
