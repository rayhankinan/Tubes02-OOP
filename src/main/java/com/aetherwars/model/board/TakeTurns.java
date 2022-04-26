package com.aetherwars.model.board;

import com.aetherwars.model.deck.DeckException;

public interface TakeTurns {
    void switchTurn() throws DeckException;
    void nextPhase();
}
