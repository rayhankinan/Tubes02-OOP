package com.aetherwars.model.card.spell.swap;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.card.character.SummonedCharacter;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.spell.Spell;
import com.aetherwars.model.card.spell.Temporary;

public class Swap extends Spell implements Temporary {
    public static final int MIN_ID = 201;
    public static final int MAX_ID = 299;

    private int duration;
    private boolean active;

    public Swap(int id) throws CardException {
        super(id);

        if (id < Swap.MIN_ID || id > Swap.MAX_ID) {
            throw new CardException("Id is invalid");
        } else {
            this.duration = 0;
            this.active = false;
        }
    }

    public Swap(int id, String name, String description, String imagepath, int duration, int mana) throws CardException {
        super(id, name, description, imagepath, mana);

        if (id < Swap.MIN_ID || id > Swap.MAX_ID) {
            throw new CardException("Id is invalid");
        } else {
            this.duration = duration;
            this.active = false;
        }
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void apply(SummonedCharacter c) throws CardException {
        if (this.active) {
            throw new CardException("Spell is already activated!");
        } else {
            c.swapAttackHealth();
            this.active = true;
        }
    }

    @Override
    public void revert(SummonedCharacter c) throws CardException {
        if (!this.active) {
            throw new CardException("Spell is already inactivated!");
        } else {
            c.swapAttackHealth();
            this.active = false;
        }
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
    public void stackDuration(Card C) throws CardException {
        if (C instanceof Swap) {
            this.duration += ((Swap) C).getDuration();
        } else {
            throw new CardException(String.format("Invalid operation between %s and %s!", this.getClass().getSimpleName(), C.getClass().getSimpleName()));
        }
    }

    @Override
    public String toString() {
        return String.format("Id: %d\nName: %s\nDescription: %s\nImagepath: %s\nMana: %d\nDuration: %d", this.id, this.name, this.description, this.imagepath, this.mana, this.duration);
    }
}
