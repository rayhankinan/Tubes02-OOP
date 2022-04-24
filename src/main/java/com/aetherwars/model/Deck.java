package com.aetherwars.model;

import com.aetherwars.model.card.Card;

import java.util.List;
import java.util.ArrayList;

public class Deck {
    private final List<Card> buffer;

    public Deck() {
        /* TODO */
        this.buffer = new ArrayList<>();
    }

    public void addCard(Card c) {
        /* TODO */
    }

    public Card getCard() {
        return this.buffer.remove(0);
    }
}
