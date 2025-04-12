package com.example.feign_api.Message.Emit;

import com.example.feign_api.Pojo.User;

public class CustomersMessage {
    User[] customers;

    public CustomersMessage(User[] customers) {
        this.customers = customers;
    }

    public User[] getCustomers() {
        return customers;
    }

    public void setCustomers(User[] customers) {
        this.customers = customers;
    }
}
