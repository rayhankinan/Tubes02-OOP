package com.aetherwars.model.card.character;

import com.aetherwars.model.card.spell.Activable;

import java.util.List;

public interface Summonable {
    int getLevel();
    int getExp();
    void addActivable(Activable s);
    List<Activable> getActivable();
    void levelUp();
}
