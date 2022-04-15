package com.aetherwars.model.card;

public abstract class ActivatedSpell extends Spell implements Activable {
    protected boolean active;
    public ActivatedSpell(int id, String name, String description, String imagepath, int mana) {
        super(id, name, description, imagepath, mana);
        this.active = false;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }
    @Override
    public void apply(SummonedCharacter c) throws Exception {
        if (!this.active) {
            this.action(c);
            this.active = true;

        } else {
            throw new Exception(String.format("%s are already activated", this.name));
        }
    }
    public void revert(SummonedCharacter c) throws Exception {
        if (this.active) {
            this.counteraction(c);
            this.active = false;

        } else {
            throw new Exception(String.format("%s are already deactivated", this.name));
        }
    }
}
