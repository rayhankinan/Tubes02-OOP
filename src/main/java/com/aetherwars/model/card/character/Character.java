package com.aetherwars.model.card.character;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.card.CardException;

public class Character extends Card {
    public static final int MIN_ID = 1;
    public static final int MAX_ID = 99;

    protected Type type;
    protected int baseHealth;
    protected int baseAttack;
    protected int attackup;
    protected int healthup;

    public Character(int id) throws CardException {
        super(id);

        if (id < Character.MIN_ID || id > Character.MAX_ID) {
            throw new CardException("Id is invalid!");
        } else {
            this.type = Type.OVERWORLD;
            this.baseAttack = 0;
            this.baseHealth = 0;
            this.attackup = 0;
            this.healthup = 0;
        }
    }

    public Character(int id, String name, Type type, String description, String imagepath, int baseAttack, int baseHealth, int mana, int attackup, int healthup) throws CardException {
        super(id, name, description, imagepath, mana);

        if (id < Character.MIN_ID || id > Character.MAX_ID) {
            throw new CardException("Id is invalid!");
        } else {
            this.type = type;
            this.baseAttack = baseAttack;
            this.baseHealth = baseHealth;
            this.attackup = attackup;
            this.healthup = healthup;
        }
    }

    public Character(Character C) throws CardException {
        super(C);

        if (C.getId() < Character.MIN_ID || C.getId() > Character.MAX_ID) {
            throw new CardException("Id is invalid!");
        } else {
            this.type = C.type;
            this.baseAttack = C.baseAttack;
            this.baseHealth = C.baseHealth;
            this.attackup = C.attackup;
            this.healthup = C.healthup;
        }
    }

    public Type getType() {
        return this.type;
    }

    public int getBaseAttack() {
        return this.baseAttack;
    }

    public void addBaseAttack(int attack) {
        if (this.baseAttack + attack >= 0) {
            this.baseAttack += attack;
        } else {
            this.baseAttack = 0;
        }
    }

    public void subtractBaseAttack(int attack) {
        if (this.baseAttack - attack >= 0) {
            this.baseAttack -= attack;
        } else {
            this.baseAttack = 0;
        }
    }

    public int getBaseHealth() {
        return this.baseHealth;
    }

    public void addBaseHealth(int health) {
        if (this.baseHealth + health >= 0) {
            this.baseHealth += health;
        } else {
            this.baseHealth = 0;
        }
    }

    public void subtractBaseHealth(int health) {
        if (this.baseHealth - health >= 0) {
            this.baseHealth -= health;
        } else {
            this.baseHealth = 0;
        }
    }

    public int getAttackup() {
        return this.attackup;
    }

    public int getHealthup() {
        return this.healthup;
    }

    @Override
    public String toString() {
        return String.format("Id: %d\nName: %s\nType: %s\nDescription: %s\nImagepath: %s\nAttack: %d\nHealth: %d\nMana: %d\nAttack Up: %d\nHealth Up: %d", this.id, this.name, this.type, this.description, this.imagepath, this.baseAttack, this.baseHealth, this.mana, this.attackup, this.healthup);
    }
}
