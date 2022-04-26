package com.aetherwars.model.card.spell;

import com.aetherwars.model.card.character.Character;
import com.aetherwars.model.card.character.SummonedCharacter;
import com.aetherwars.model.card.CardException;

public class Level extends Spell implements Activable {
    public static final int MIN_ID = 401;
    public static final int MAX_ID = 499;

    private final int level;
    private boolean active;

    public Level(int id) throws CardException {
        super(id);

        if (id < Level.MIN_ID || id > Level.MAX_ID) {
            throw new CardException("Id is invalid");
        } else {
            this.level = 0;
            this.active = false;
        }
    }

    public Level(int id, String name, String description, String imagepath, int mana, int level) throws CardException {
        super(id, name, description, imagepath, mana);

        if (id < Level.MIN_ID || id > Level.MAX_ID) {
            throw new CardException("Id is invalid");
        } else {
            this.level = level;
            this.active = false;
        }
    }

    public int getLevel() {
        return level;
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
            int N = this.level;
            while (N > 0) {
                c.levelUp();
                N--;
            }
            this.active = true;
        }
    }

    @Override
    public String toString() {
        return String.format("Id: %d\nName: %s\nDescription: %s\nImagepath: %s\nMana: %d\nLevel: %d", this.id, this.name, this.description, this.imagepath, this.mana, this.level);
    }
}
