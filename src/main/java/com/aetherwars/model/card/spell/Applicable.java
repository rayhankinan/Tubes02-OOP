package com.aetherwars.model.card.spell;

import com.aetherwars.model.card.character.SummonedCharacter;
import com.aetherwars.model.card.CardException;

public interface Applicable {
    void apply(SummonedCharacter c) throws CardException;
}
