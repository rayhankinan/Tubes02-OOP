package com.aetherwars.model.card;

public class Spell extends Card implements Effect {
    public Spell(int id, String name, String description, String imagepath, int mana) {
        super(id, name, description, imagepath, mana);
    }

    @Override
    public void action(SummonedCharacter c) {
        /* EMPTY */
    }

    @Override
    public void counteraction(SummonedCharacter c) {
        /* EMPTY */
    }
}
