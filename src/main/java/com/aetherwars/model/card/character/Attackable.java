package com.aetherwars.model.card.character;

import com.aetherwars.model.card.CardException;
import com.aetherwars.model.player.Player;

public interface Attackable {
    void attackCharacter(SummonedCharacter c) throws CardException;
    void attackPlayer(Player p);
    void takeDamage(int damage);
}
