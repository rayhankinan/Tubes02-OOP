package com.aetherwars.model.card.spell;

import com.aetherwars.model.card.Card;

public class Spell extends Card  {
    public Spell(int id) {
        super(id);
    }

    public Spell(int id, String name, String description, String imagepath, int mana) {
        super(id, name, description, imagepath, mana);
    }
}
