package com.aetherwars.model.card.character;

import com.aetherwars.model.card.CardException;

public interface Summonable {
    int getField();
    int getLevel();
    int getExp();
    int getExpForNextLevel() throws CardException;
    int getCurrentHealth();
    int getCurrentAttack();
    void addExp(int exp) throws CardException;
    void resetExp();
    void levelUp() throws CardException;
    void levelDown() throws CardException;
    int getBattleAvailability();
    void setBattleAvailability(int battleAvailability);
}
