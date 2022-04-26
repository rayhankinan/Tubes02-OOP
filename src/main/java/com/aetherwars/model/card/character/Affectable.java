package com.aetherwars.model.card.character;

import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.spell.Applicable;
import com.aetherwars.model.card.spell.Revertable;

import java.util.List;

public interface Affectable {
    int getTempHealth();
    int getTempAttack();
    int getTotalHealth();
    int getTotalAttack();
    List<Revertable> getTemporary();
    void addActivable(Applicable s) throws CardException;
    void decrementTemporaryDuration() throws CardException;
    void swapHealthAttack();
    void addTempHealth(int tempHealth);
    void addTempAttack(int tempAttack);
    void subtractTempHealth(int tempHealth);
    void subtractTempAttack(int tempAttack);
    void morph(int id) throws CardException;
}
