package com.aetherwars.model.card.character;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.card.CardException;

public class Character extends Card {
    public static final int MIN_ID = 1;
    public static final int MAX_ID = 99;

    protected Type type;
    protected int health;
    protected int attack;
    protected int attackup;
    protected int healthup;

    public Character(int id) throws CardException {
        super(id);

        if (id < Character.MIN_ID || id > Character.MAX_ID) {
            throw new CardException("Id is invalid!");
        } else {
            this.type = Type.OVERWORLD;
            this.attack = 0;
            this.health = 0;
            this.attackup = 0;
            this.healthup = 0;
        }
    }

    public Character(int id, String name, Type type, String description, String imagepath, int attack, int health, int mana, int attackup, int healthup) throws CardException {
        super(id, name, description, imagepath, mana);

        if (id < Character.MIN_ID || id > Character.MAX_ID) {
            throw new CardException("Id is invalid!");
        } else {
            this.type = type;
            this.attack = attack;
            this.health = health;
            this.attackup = attackup;
            this.healthup = healthup;
        }
    }

    public Type getType() {
        return this.type;
    }

    public int getAttack() {
        return this.attack;
    }

    public void addAttack(int attack) {
        if (this.attack + attack >= 0) {
            this.attack += attack;
        } else {
            this.attack = 0;
        }
    }

    public void subtractAttack(int attack) {
        if (this.attack - attack >= 0) {
            this.attack -= attack;
        } else {
            this.attack = 0;
        }
    }

    public int getHealth() {
        return this.health;
    }

    public void addHealth(int health) {
        if (this.health + health >= 0) {
            this.health += health;
        } else {
            this.health = 0;
        }
    }

    public void subtractHealth(int health) {
        if (this.health - health >= 0) {
            this.health -= health;
        } else {
            this.health = 0;
        }
    }

    public void swapAttackHealth() {
        int temp = this.health;
        this.health = this.attack;
        this.attack = temp;
    }

    public int getAttackup() {
        return this.attackup;
    }

    public int getHealthup() {
        return this.healthup;
    }

    @Override
    public String toString() {
        return String.format("Id: %d\nName: %s\nType: %s\nDescription: %s\nImagepath: %s\nAttack: %d\nHealth: %d\nMana: %d\nAttack Up: %d\nHealth Up: %d", this.id, this.name, this.type, this.description, this.imagepath, this.attack, this.health, this.mana, this.attackup, this.healthup);
    }
}
