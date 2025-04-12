package com.example.feign_api.Message.Emit;

import com.example.feign_api.Pojo.DeliveryPerson;


public class DeliversMessage {
    DeliveryPerson[] delivers;

    public DeliversMessage(DeliveryPerson[] delivers) {
        this.delivers = delivers;
    }

    public DeliveryPerson[] getDelivers() {
        return delivers;
    }

    public void setDelivers(DeliveryPerson[] delivers) {
        this.delivers = delivers;
    }
}
