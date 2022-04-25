package com.aetherwars.model.card.spell;

import com.aetherwars.model.card.character.SummonedCharacter;
import com.aetherwars.model.card.CardException;

public interface Activable {
    boolean isActive();
    void apply(SummonedCharacter c) throws CardException;
}
