package com.aetherwars.model.card.character;

import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.spell.Applicable;
import com.aetherwars.model.card.spell.potion.Potion;
import com.aetherwars.model.card.spell.swap.Swap;

import java.util.List;

public interface Affectable {
    double getTempHealth();
    double getTempAttack();
    double getTotalHealth();
    double getTotalAttack();
    List<Potion> getTemporary();
    void addPotion(Potion p) throws CardException;
    void deletePotion(Potion p) throws CardException;
    void addActivable(Applicable s) throws CardException;
    void decrementTemporaryDuration() throws CardException;
    void setSwap(Swap s) throws CardException;
    void morph(int id) throws CardException;
}
