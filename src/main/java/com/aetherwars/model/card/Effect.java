package com.aetherwars.model.card;

public interface Effect {
    void action(SummonedCharacter c);
    void counteraction(SummonedCharacter c);
}