package com.aetherwars.model;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.card.CardException;

import java.util.List;
import java.util.ArrayList;

public class Deck {
    private final List<Card> buffer;

    public Deck() {
        this.buffer = new ArrayList<>();
    }

    public void addCard(Card c) {
        this.buffer.add(c);
    }

    public Card getCard() throws CardException {
        if (this.buffer.size() > 0) {
            return this.buffer.remove(0);
        } else {
            throw new CardException("Deck is empty!");
        }
    }

    public void printAll() {
        for (Card c : this.buffer) {
            System.out.println(c);
        }
    }
}
