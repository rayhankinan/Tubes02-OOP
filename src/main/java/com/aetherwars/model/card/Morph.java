package com.aetherwars.model.card;

public class Morph extends ActivatedSpell {
    private final int targetid;

    public Morph(int id, String name, String description, String imagepath, int targetid, int mana) {
        super(id, name, description, imagepath, mana);
        this.targetid = targetid;
    }

    @Override
    public void action(SummonedCharacter c) {
        /* IMPLEMENTASIKAN NANTI */
    }

    @Override
    public void counteraction(SummonedCharacter c) {
        /* IMPLEMENTASIKAN NANTI */
    }

    @Override
    public String toString() {
        return String.format("Id: %d\nName: %s\nDescription: %s\nImagepath: %s\nTarget Id: %d\nMana: %d", this.id, this.name, this.description, this.imagepath, this.targetid, this.mana);
    }
}
