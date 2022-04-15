package com.aetherwars.model.card.spell;

import com.aetherwars.model.card.character.SummonedCharacter;

public interface Effect {
    void action(SummonedCharacter c);
    void counteraction(SummonedCharacter c);
}