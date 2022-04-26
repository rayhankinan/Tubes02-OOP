package com.aetherwars.model.card.character;

import com.aetherwars.model.Player;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.spell.Activable;

import java.util.List;

public interface Summonable {
    int getLevel();
    int getExp();
    void addActivable(Activable s);
    List<Activable> getActivable();
    void activateEffects();
    void attackCharacter(SummonedCharacter c) throws CardException;
    void attackPlayer(Player p);
    void takeDamage(int damage);
    void levelUp();
}
