package com.aetherwars.model.card.character;

import com.aetherwars.model.card.spell.Spell;

import java.util.List;

public interface Summonable {
    int getLevel();
    int getExp();
    void addSpell(Spell s);
    List<Spell> getActiveSpells();
    void levelUp();
}
