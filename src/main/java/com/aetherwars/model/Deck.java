package com.aetherwars.model;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.character.Character;
import com.aetherwars.model.card.spell.Level;
import com.aetherwars.model.card.spell.Morph;
import com.aetherwars.model.card.spell.Potion;
import com.aetherwars.model.card.spell.Swap;

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
            if (c instanceof Character) {
                System.out.println(c);
            }
            if (c instanceof Level) {
                System.out.println(c);
            }
            if (c instanceof Morph) {
                System.out.println(c);
            }
            if (c instanceof Potion) {
                System.out.println(c);
            }
            if (c instanceof Swap) {
                System.out.println(c);
            }
        }
    }
}
