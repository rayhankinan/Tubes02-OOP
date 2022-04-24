package com.aetherwars.model.card.spell;

import com.aetherwars.model.card.character.SummonedCharacter;

public interface Permanently {
    boolean isActive();
    void apply(SummonedCharacter c) throws Exception;
}
