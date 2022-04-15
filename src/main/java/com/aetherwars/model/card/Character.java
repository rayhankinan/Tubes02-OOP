package com.aetherwars.model.card;

public class Character extends Card {
    protected Type type;
    protected int health;
    protected int attack;
    protected int attackup;
    protected int healthup;

    public Character(int id, String name, Type type, String description, String imagepath, int attack, int health, int mana, int attackup, int healthup) {
        super(id, name, description, imagepath, mana);
        this.type = type;
        this.attack = attack;
        this.health = health;
        this.attackup = attackup;
        this.healthup = healthup;
    }

    public Type getType() {
        return this.type;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getHealth() {
        return this.health;
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
