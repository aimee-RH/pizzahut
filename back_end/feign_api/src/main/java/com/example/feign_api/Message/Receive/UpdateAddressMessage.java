package com.example.feign_api.Message.Receive;

public class UpdateAddressMessage {
    String id;
    String addresses[];

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getAddresses() {
        return addresses;
    }

    public void setAddresses(String[] addresses) {
        this.addresses = addresses;
    }
}
