package com.aetherwars.model.card;

import java.util.List;

public interface Summonable {
    int getLevel();
    int getExp();
    void addSpell(Spell s);
    List<Spell> getActiveSpells();
    void levelUp();
}
