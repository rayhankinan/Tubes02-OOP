package com.aetherwars.model.card.spell.swap;

import com.aetherwars.model.card.character.SummonedCharacter;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.spell.Applicable;
import com.aetherwars.model.card.spell.Spell;
import com.aetherwars.model.card.spell.Revertible;

public class Swap extends Spell implements Revertible, Cloneable {
    public static final int MIN_ID = 201;
    public static final int MAX_ID = 299;

    private int duration;

    public Swap(int id) throws CardException {
        super(id);

        if (id < Swap.MIN_ID || id > Swap.MAX_ID) {
            throw new CardException("Id is invalid");
        } else {
            this.duration = 0;
        }
    }

    public Swap(int id, String name, String description, String imagepath, int duration, int mana) throws CardException {
        super(id, name, description, imagepath, mana);

        if (id < Swap.MIN_ID || id > Swap.MAX_ID) {
            throw new CardException("Id is invalid");
        } else {
            this.duration = duration;
        }
    }

    @Override
    public void apply(SummonedCharacter c) throws CardException {
        c.setSwap(this);
    }

    @Override
    public int getDuration() {
        return this.duration;
    }

    @Override
    public void decrementDuration() throws CardException {
        if (this.duration > 0) {
            this.duration--;
        } else {
            throw new CardException("Duration can't be less than 0!");
        }
    }

    @Override
    public void stackDuration(Applicable s) throws CardException {
        if (s instanceof Swap) {
            this.duration += ((Swap) s).getDuration();
        } else {
            throw new CardException(String.format("Invalid operation between %s and %s!", this.getClass().getSimpleName(), s.getClass().getSimpleName()));
        }
    }

    @Override
    public String toString() {
        return String.format("Id: %d\nName: %s\nDescription: %s\nImagepath: %s\nMana: %d\nDuration: %d", this.id, this.name, this.description, this.imagepath, this.mana, this.duration);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
