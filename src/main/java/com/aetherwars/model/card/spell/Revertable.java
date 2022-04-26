package com.aetherwars.model.card.spell;

import com.aetherwars.model.card.character.SummonedCharacter;
import com.aetherwars.model.card.CardException;

public interface Revertable extends Applicable {
    boolean isActive();
    int getDuration();
    void revert(SummonedCharacter c) throws CardException;
    void decrementDuration() throws CardException;
    void stackDuration(Applicable s) throws CardException;
}
