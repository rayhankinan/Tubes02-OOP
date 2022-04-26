package com.aetherwars.model.card.character;

import com.aetherwars.model.card.CardException;

public interface Summonable {
    int getLevel();
    int getExp();
    int getExpForNextLevel();
    int getCurrentHealth();
    int getCurrentAttack();
    void addExp(int exp) throws CardException;
    void levelUp() throws CardException;
    void levelDown() throws CardException;
}
