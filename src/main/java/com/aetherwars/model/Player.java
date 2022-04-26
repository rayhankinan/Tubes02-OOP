package com.aetherwars.model;

import com.aetherwars.model.card.Card;

public class Player {
    private static final int MAX_HP = 80;
    private static final int MAX_MANA = 10;
    private static final int MIN_HP = 0;
    private static final int MIN_MANA = 0;

    private final String name;
    private int health;
    private int mana;
    private int turn;

    public Player(String name) {
        this.name = name;
        this.health = MAX_HP;
        this.mana = 1;
        this.turn = 1;
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

    public void useMana(int mana) throws Exception {
        if (this.mana - mana < MIN_MANA) {
            throw new Exception("Mana is not sufficient!");
        } else {
            this.mana -= mana;
        }
    }

    public void heal(int health) {
        if (this.health + health > MAX_HP) {
            this.health = MAX_HP;
        } else {
            this.health += health;
        }
    }

    public void takeDamage(int damage) {
        if (this.health - damage < MIN_HP) {
            this.health = 0;
        } else {
            this.health -= damage;
        }
    }

    public void takeTurn() {
        /* TODO: turn in player */
        this.turn++;
    }
}
