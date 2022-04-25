package com.aetherwars.model.card.spell;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.card.character.SummonedCharacter;
import com.aetherwars.model.card.CardException;

public interface Temporary extends Activable {
    int getDuration();
    void revert(SummonedCharacter c) throws CardException;
    void decrementDuration() throws CardException;
    void stackDuration(Card C) throws CardException;
}
