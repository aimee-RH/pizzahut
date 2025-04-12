package com.example.feign_api.Message.Emit;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //无参的构造方法
public class AddressesMessage {
    String[] addresses;

    public AddressesMessage(String[] addresses) {
        this.addresses = addresses;
    }

    public String[] getAddresses() {
        return addresses;
    }

    public void setAddresses(String[] addresses) {
        this.addresses = addresses;
    }
}
