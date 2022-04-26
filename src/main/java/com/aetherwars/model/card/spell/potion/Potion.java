package com.aetherwars.model.card.spell.potion;

import com.aetherwars.model.card.character.SummonedCharacter;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.spell.Applicable;
import com.aetherwars.model.card.spell.Spell;
import com.aetherwars.model.card.spell.Revertable;

public class Potion extends Spell implements Revertable {
    public static final int MIN_ID = 101;
    public static final int MAX_ID = 199;

    private final int tempAttack;
    private final int tempHealth;
    private int duration;
    private boolean active;

    public Potion(int id) throws CardException {
        super(id);

        if (id < Potion.MIN_ID || id > Potion.MAX_ID) {
            throw new CardException("Id is invalid");
        } else {
            this.tempAttack = 0;
            this.tempHealth = 0;
            this.duration = 0;
        }
    }

    public Potion(int id, String name, String description, String imagepath, int tempAttack, int tempHealth, int mana, int duration) throws CardException {
        super(id, name, description, imagepath, mana);

        if (id < Potion.MIN_ID || id > Potion.MAX_ID) {
            throw new CardException("Id is invalid");
        } else {
            this.tempAttack = tempAttack;
            this.tempHealth = tempHealth;
            this.duration = duration;
        }
    }

    public int getTempAttack() {
        return this.tempAttack;
    }

    public int getTempHealth() {
        return this.tempHealth;
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
            c.addTempAttack(this.tempAttack);
            c.addTempHealth(this.tempHealth);
            this.active = true;
        }
    }

    @Override
    public void revert(SummonedCharacter c) throws CardException {
        if (!this.active) {
            throw new CardException("Spell is already inactivated!");
        } else {
            c.subtractTempAttack(this.tempAttack);
            c.subtractTempHealth(this.tempHealth);
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
    public void stackDuration(Applicable s) throws CardException {
        if (s instanceof Potion) {
            this.duration += ((Potion) s).getDuration();
        } else {
            throw new CardException(String.format("Invalid operation between %s and %s!", this.getClass().getSimpleName(), s.getClass().getSimpleName()));
        }
    }

    @Override
    public String toString() {
        return String.format("Id: %d\nName: %s\nDescription: %s\nImagepath: %s\nAttack: %d\nHealth: %d\nMana: %d\nDuration: %d", this.id, this.name, this.description, this.imagepath, this.tempAttack, this.tempHealth, this.mana, this.duration);
    }
}
