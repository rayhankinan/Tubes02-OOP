package com.aetherwars.model.card;

import java.util.List;

public interface Summonable {
    int getLevel();
    int getExp();
    void addSpell(ActivatedSpell s);
    List<ActivatedSpell> getActiveSpells();
    void levelUp();
    void levelDown();
}
