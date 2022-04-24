package com.aetherwars.model.card.character;

import com.aetherwars.model.card.spell.Spell;

import java.util.List;
import java.util.ArrayList;

public class SummonedCharacter extends Character implements Summonable {
    private int level;
    private int exp;
    private final List<Spell> activeSpells;

    public SummonedCharacter(int id, String name, Type type, String description, String imagepath, int attack, int health, int mana, int attackup, int healthup, int level, int exp) {
        super(id, name, type, description, imagepath, attack, health, mana, attackup, healthup);
        this.level = level;
        this.exp = exp;
        this.activeSpells = new ArrayList<>();
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getExp() {
        return this.exp;
    }

    @Override
    public void addSpell(Spell s) {
        this.activeSpells.add(s);
    }

    @Override
    public List<Spell> getActiveSpells() {
        return this.activeSpells;
    }

    @Override
    public void levelUp() {
        if (level < 9) {
            this.level++;
            this.exp = 0;
            this.health += this.healthup;
            this.attack += this.attackup;
        }
    }
}
