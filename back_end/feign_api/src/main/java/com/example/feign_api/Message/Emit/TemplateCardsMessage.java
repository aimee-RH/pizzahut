package com.example.feign_api.Message.Emit;

import com.example.feign_api.Pojo.Coupon;

public class TemplateCardsMessage {
    Coupon[] cards;

    public TemplateCardsMessage(Coupon[] cards) {
        this.cards = cards;
    }

    public Coupon[] getCards() {
        return cards;
    }

    public void setCards(Coupon[] cards) {
        this.cards = cards;
    }
}
