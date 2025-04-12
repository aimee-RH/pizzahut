package com.example.feign_api.Message.Emit;

import com.example.feign_api.Pojo.coupon;

public class TemplateCardsMessage {
    coupon[] cards;

    public TemplateCardsMessage(coupon[] cards) {
        this.cards = cards;
    }

    public coupon[] getCards() {
        return cards;
    }

    public void setCards(coupon[] cards) {
        this.cards = cards;
    }
}
