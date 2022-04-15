package com.aetherwars.model.card;

public class Potion extends Spell implements Temporal {
    private final int attack;
    private final int health;
    private int duration;

    public Potion(int id, String name, String description, String imagepath, int attack, int health, int mana, int duration) {
        super(id, name, description, imagepath, mana);
        this.attack = attack;
        this.health = health;
        this.duration = duration;
    }

    @Override
    public void action(SummonedCharacter c) {
        c.addAttack(this.attack);
        c.addHealth(this.health);
    }

    @Override
    public void counteraction(SummonedCharacter c) {
        c.subtractAttack(this.attack);
        c.subtractHealth(this.health);
    }

    @Override
    public int getDuration() {
        return this.duration;
    }

    @Override
    public void decrementDuration() throws Exception {
        if (this.duration > 0) {
            this.duration--;
        } else {
            throw new Exception("Duration can't be less than 0!");
        }
    }

    @Override
    public void stackDuration(Card C) throws Exception {
        if (C instanceof Potion) {
            this.duration += ((Potion) C).getDuration();
        } else {
            throw new Exception(String.format("Invalid operation between %s and %s!", this.getClass().getSimpleName(), C.getClass().getSimpleName()));
        }
    }

    @Override
    public String toString() {
        return String.format("Id: %d\nName: %s\nDescription: %s\nImagepath: %s\nAttack: %d\nHealth: %d\nMana: %d\nDuration: %d", this.id, this.name, this.description, this.imagepath, this.attack, this.health, this.mana, this.duration);
    }
}
