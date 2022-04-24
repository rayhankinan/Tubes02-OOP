package com.aetherwars.model;

import com.aetherwars.model.card.Card;

import java.util.List;
import java.util.ArrayList;

public class Deck {
    private final List<Card> buffer;

    public Deck() {
        this.buffer = new ArrayList<>(); // GANTI INI DENGAN RANDOMIZER
    }
}
