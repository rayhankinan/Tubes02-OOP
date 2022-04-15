package com.aetherwars.model.card;

public class Swap extends Spell implements Temporal {
    private int duration;

    public Swap(int id, String name, String description, String imagepath, int duration, int mana) {
        super(id, name, description, imagepath, mana);
        this.duration = duration;
    }

    @Override
    public void action(SummonedCharacter c) {
        c.swapAttackHealth();
    }

    @Override
    public void counteraction(SummonedCharacter c) {
        c.swapAttackHealth();
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
        return String.format("Id: %d\nName: %s\nDescription: %s\nImagepath: %s\nMana: %d\nDuration: %d\n", this.id, this.name, this.description, this.imagepath, this.mana, this.duration);
    }
}
