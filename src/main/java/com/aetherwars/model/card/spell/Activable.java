package com.aetherwars.model.card.spell;

import com.aetherwars.model.card.character.SummonedCharacter;

public interface Activable {
    boolean isActive();
    void apply(SummonedCharacter c) throws Exception;
}
