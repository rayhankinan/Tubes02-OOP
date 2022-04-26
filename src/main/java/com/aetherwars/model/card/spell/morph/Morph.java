package com.aetherwars.model.card.spell.morph;

import com.aetherwars.model.card.character.SummonedCharacter;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.spell.Applicable;
import com.aetherwars.model.card.spell.Spell;

public class Morph extends Spell implements Applicable {
    public static final int MIN_ID = 301;
    public static final int MAX_ID = 399;

    private final int targetid;

    public Morph(int id) throws CardException {
        super(id);

        if (id < Morph.MIN_ID || id > Morph.MAX_ID) {
            throw new CardException("Id is invalid");
        } else {
            this.targetid = 0;
        }
    }

    public Morph(int id, String name, String description, String imagepath, int targetid, int mana) throws CardException {
        super(id, name, description, imagepath, mana);

        if (id < Morph.MIN_ID || id > Morph.MAX_ID) {
            throw new CardException("Id is invalid");
        } else {
            this.targetid = targetid;
        }
    }

    @Override
    public void apply(SummonedCharacter c) throws CardException {
        c.morph(this.targetid);
    }

    @Override
    public String toString() {
        return String.format("Id: %d\nName: %s\nDescription: %s\nImagepath: %s\nTarget Id: %d\nMana: %d", this.id, this.name, this.description, this.imagepath, this.targetid, this.mana);
    }
}
