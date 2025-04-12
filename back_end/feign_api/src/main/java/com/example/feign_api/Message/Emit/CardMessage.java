package com.example.feign_api.Message.Emit;
import com.example.feign_api.Message.Emit.Element.Card;

public class CardMessage {
    Card[] cards;

    public CardMessage(Card[] cards) {
        this.cards = cards;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }
}

