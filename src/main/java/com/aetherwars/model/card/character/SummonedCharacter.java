package com.aetherwars.model.card.character;

import com.aetherwars.model.Player;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.spell.Activable;
import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.spell.Temporary;

import java.util.List;
import java.util.ArrayList;

public class SummonedCharacter extends Character implements Summonable {
    private int level;
    private int exp;
    private final List<Activable> activeSpells;

    public SummonedCharacter(int id, String name, Type type, String description, String imagepath, int attack, int health, int mana, int attackup, int healthup, int level, int exp) {
        super(id, name, type, description, imagepath, attack, health, mana, attackup, healthup);
        this.level = level;
        this.exp = exp;
        this.activeSpells = new ArrayList<>();
    }

    public void morph(int id) throws CardException {
        Character newCharacter = CardDatabase.getCharacter(id);

        this.id = newCharacter.getId();
        this.name = newCharacter.getName();
        this.type = newCharacter.getType();
        this.description = newCharacter.getDescription();
        this.imagepath = newCharacter.getImagepath();
        this.attack = newCharacter.getAttack();
        this.health = newCharacter.getHealth();
        this.mana = newCharacter.getMana();
        this.attackup = newCharacter.getAttackup();
        this.healthup = newCharacter.getHealthup();
        this.level = 1;
        this.exp = 0;
        this.activeSpells.clear();
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
    public void addActivable(Activable s) {
        this.activeSpells.add(s);
    }

    @Override
    public List<Activable> getActivable() {
        return this.activeSpells;
    }

    @Override
    public void activateEffects() {
        for (Activable s : this.activeSpells) {
            if (s instanceof Temporary) {
                /* TODO */
            } else {
                /* TODO */
            }
        }
    }

    @Override
    public void attackCharacter(SummonedCharacter c) {
        c.takeDamage(this.attack);
    }

    @Override
    public void attackPlayer(Player p) {
        p.takeDamage(this.attack);
    }

    @Override
    public void takeDamage(int damage) {
        if (this.health - damage < 0) {
            this.health = 0;
        } else {
            this.health -= damage;
        }
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
