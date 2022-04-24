package com.aetherwars.model;

public class Player {
    private static final int MAX_HP = 80;
    private static final int MAX_MANA = 10;
    private static final int MIN_HP = 0;
    private static final int MIN_MANA = 0;

    private final String name;
    private final Deck deck;
    private int health;
    private int mana;

    public Player() {
        this.name = "";
        this.deck = new Deck();
        this.health = MAX_HP;
        this.mana = 1;
    }

    public Player(String name) {
        this.name = name;
        this.deck = new Deck();
        this.health = MAX_HP;
        this.mana = 1;
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
    }

    public int getMana() {
        return this.mana;
    }

    public boolean isValidHP(int health) {
        return this.health + health >= MIN_HP && this.health + health <= MAX_HP;
    }

    public boolean isValidMana(int mana) {
        return this.mana + mana >= MIN_MANA && this.mana + mana <= MAX_MANA;
    }

    public void fillMana() {

    }

    public void useMana(int mana) {

    }

    public void heal(int health) {

    }

    public void takeDamage(int damage) {

    }
}
