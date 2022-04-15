package com.aetherwars.model.card.spell;

import com.aetherwars.model.card.character.SummonedCharacter;

public class Level extends Spell {
    private final int level;

    public Level(int id, String name, String description, String imagepath, int mana, int level) {
        super(id, name, description, imagepath, mana);
        this.level = level;
    }

    @Override
    public void action(SummonedCharacter c) {
        int N = this.level;
        while (N > 0) {
            c.levelUp();
            N--;
        }
    }

    @Override
    public void counteraction(SummonedCharacter c) {
        /* EMPTY */
    }

    @Override
    public String toString() {
        return String.format("Id: %d\nName: %s\nDescription: %s\nImagepath: %s\nMana: %d\nLevel: %d", this.id, this.name, this.description, this.imagepath, this.mana, this.level);
    }
}
