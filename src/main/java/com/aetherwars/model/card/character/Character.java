package com.aetherwars.model.card.character;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.card.CardException;

public class Character extends Card {
    public static final int MIN_ID = 1;
    public static final int MAX_ID = 99;

    protected Type type;
    protected double baseHealth;
    protected double baseAttack;
    protected double attackup;
    protected double healthup;

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

    public Character(int id, String name, Type type, String description, String imagepath, double baseAttack, double baseHealth, int mana, double attackup, double healthup) throws CardException {
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

    public double getBaseAttack() {
        return this.baseAttack;
    }

    public double getBaseHealth() {
        return this.baseHealth;
    }

    public double getAttackup() {
        return this.attackup;
    }

    public double getHealthup() {
        return this.healthup;
    }

    @Override
    public String toString() {
        return String.format("Id: %d\nName: %s\nType: %s\nDescription: %s\nImagepath: %s\nAttack: %.1f\nHealth: %.1f\nMana: %d\nAttack Up: %.1f\nHealth Up: %.1f", this.id, this.name, this.type, this.description, this.imagepath, this.baseAttack, this.baseHealth, this.mana, this.attackup, this.healthup);
    }
}
