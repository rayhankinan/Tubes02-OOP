package com.aetherwars.model.card.spell;

import com.aetherwars.model.card.character.SummonedCharacter;
import com.aetherwars.model.card.CardException;

public class Morph extends Spell implements Activable {
    private final int targetid;
    private boolean active;

    public Morph(int id) {
        super(id);
        this.targetid = 0;
        this.active = false;
    }

    public Morph(int id, String name, String description, String imagepath, int targetid, int mana) {
        super(id, name, description, imagepath, mana);
        this.targetid = targetid;
        this.active = false;
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
            c.morph(this.targetid);
            this.active = true;
        }
    }

    @Override
    public String toString() {
        return String.format("Id: %d\nName: %s\nDescription: %s\nImagepath: %s\nTarget Id: %d\nMana: %d", this.id, this.name, this.description, this.imagepath, this.targetid, this.mana);
    }
}
