package com.aetherwars.model.card;

public interface Temporal extends Effect {
    int getDuration();
    void decrementDuration() throws Exception;
    void stackDuration(Card C) throws Exception;
}
