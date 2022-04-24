package com.aetherwars.model.card.spell;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.card.character.SummonedCharacter;

public interface Temporary extends Permanently {
    int getDuration();
    void revert(SummonedCharacter c) throws Exception;
    void decrementDuration() throws Exception;
    void stackDuration(Card C) throws Exception;
}
