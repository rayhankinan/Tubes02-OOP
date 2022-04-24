package com.aetherwars.model.card.spell;

import com.aetherwars.model.card.character.SummonedCharacter;

public class Level extends Spell {
    private final int level;
    private boolean active;

    public Level(int id) {
        super(id);
        this.level = 0;
        this.active = false;
    }

    public Level(int id, String name, String description, String imagepath, int mana, int level) {
        super(id, name, description, imagepath, mana);
        this.level = level;
        this.active = false;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void apply(SummonedCharacter c) throws Exception {
        if (this.active) {
            throw new Exception("Spell is already activated!");
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
