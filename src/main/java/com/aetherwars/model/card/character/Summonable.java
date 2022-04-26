package com.aetherwars.model.card.character;

import com.aetherwars.model.player.Player;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.spell.Activable;
import com.aetherwars.model.card.spell.Temporary;

import java.util.List;

public interface Summonable {
    int getLevel();
    int getExp();
    int getExpForNextLevel();
    void addExp(int exp);
    void levelUp();

    List<Temporary> getTemporary();
    void addActivable(Activable s) throws CardException;
    void decrementTemporaryDuration() throws CardException;

    void attackCharacter(SummonedCharacter c) throws CardException;
    void attackPlayer(Player p);
    void takeDamage(int damage);
}
