package com.aetherwars.model.card.spell;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.card.character.SummonedCharacter;
import com.aetherwars.model.card.CardException;

public class Potion extends Spell implements Temporary {
    //TODO: implement temporary health and temporary attack
    private final int attack;
    private final int health;
    private int duration;
    private boolean active;

    public Potion(int id) {
        super(id);
        this.attack = 0;
        this.health = 0;
        this.duration = 0;
    }

    public Potion(int id, String name, String description, String imagepath, int attack, int health, int mana, int duration) {
        super(id, name, description, imagepath, mana);
        this.attack = attack;
        this.health = health;
        this.duration = duration;
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
            c.addAttack(this.attack);
            c.addHealth(this.health);
            this.active = true;
        }
    }

    @Override
    public void revert(SummonedCharacter c) throws CardException {
        if (!this.active) {
            throw new CardException("Spell is already inactivated!");
        } else {
            c.subtractAttack(this.attack);
            c.subtractHealth(this.health);
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
        if (C instanceof Potion) {
            this.duration += ((Potion) C).getDuration();
        } else {
            throw new CardException(String.format("Invalid operation between %s and %s!", this.getClass().getSimpleName(), C.getClass().getSimpleName()));
        }
    }

    @Override
    public String toString() {
        return String.format("Id: %d\nName: %s\nDescription: %s\nImagepath: %s\nAttack: %d\nHealth: %d\nMana: %d\nDuration: %d", this.id, this.name, this.description, this.imagepath, this.attack, this.health, this.mana, this.duration);
    }
}
