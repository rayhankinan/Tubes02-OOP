package com.aetherwars.model.board;

import com.aetherwars.model.card.CardException;

public interface TakeTurns {
    void switchTurn() throws CardException;
    void nextPhase();
}
