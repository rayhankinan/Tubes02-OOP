package com.aetherwars.model.card.spell.morph;

import com.aetherwars.model.card.character.SummonedCharacter;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.spell.Activable;
import com.aetherwars.model.card.spell.Spell;

public class Morph extends Spell implements Activable {
    public static final int MIN_ID = 301;
    public static final int MAX_ID = 399;

    private final int targetid;
    private boolean active;

    public Morph(int id) throws CardException {
        super(id);

        if (id < Morph.MIN_ID || id > Morph.MAX_ID) {
            throw new CardException("Id is invalid");
        } else {
            this.targetid = 0;
            this.active = false;
        }
    }

    public Morph(int id, String name, String description, String imagepath, int targetid, int mana) throws CardException {
        super(id, name, description, imagepath, mana);

        if (id < Morph.MIN_ID || id > Morph.MAX_ID) {
            throw new CardException("Id is invalid");
        } else {
            this.targetid = targetid;
            this.active = false;
        }
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
