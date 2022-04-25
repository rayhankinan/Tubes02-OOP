package com.aetherwars.model;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.card.CardDatabase;

import java.util.List;
import java.util.ArrayList;

public class Deck {
    private final List<Card> buffer;

    public Deck() {
        this.buffer = new ArrayList<>();
    }

    public void addCard(int id) {

    }

    public Card getCard() {
        return this.buffer.remove(0);
    }
}
