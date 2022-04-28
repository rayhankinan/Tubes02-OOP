package com.aetherwars.model.card.spell;

import com.aetherwars.model.card.CardException;

public interface Revertible extends Applicable {
    int getDuration();
    void decrementDuration() throws CardException;
    void stackDuration(Applicable s) throws CardException;
}
