package com.aetherwars.model.card.character;

import com.aetherwars.model.player.Player;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.spell.Activable;
import com.aetherwars.model.card.spell.Inactiveable;

import java.util.List;
import java.util.ArrayList;

public class SummonedCharacter extends Character implements Summonable {
    private int level;
    private int exp;
    private final List<Inactiveable> inactiveableSpells;

    public SummonedCharacter(int id) throws CardException {
        super(id);
        this.level = 1;
        this.exp = 0;
        this.inactiveableSpells = new ArrayList<>();
    }

    public SummonedCharacter(int id, String name, Type type, String description, String imagepath, int attack, int health, int mana, int attackup, int healthup) throws CardException {
        super(id, name, type, description, imagepath, attack, health, mana, attackup, healthup);
        this.level = 1;
        this.exp = 0;
        this.inactiveableSpells = new ArrayList<>();
    }

    public SummonedCharacter(Character C) throws CardException {
        super(C);
        this.level = 1;
        this.exp = 0;
        this.inactiveableSpells = new ArrayList<>();
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
        this.inactiveableSpells.clear();
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
    public int getExpForNextLevel() {
        return this.level * 2 - 1;
    }

    @Override
    public void addExp(int exp) {
        if (level < 10) {
            if (this.exp + exp < this.getExpForNextLevel()) {
                this.exp += exp;
            } else {
                this.levelUp();
                this.exp += (exp - this.getExpForNextLevel());
            }
        }
    }

    @Override
    public void levelUp() {
        if (level < 10) {
            this.level++;
            this.exp = 0;
            this.health += this.healthup;
            this.attack += this.attackup;
        }
    }

    @Override
    public List<Inactiveable> getTemporary() {
        return this.inactiveableSpells;
    }

    @Override
    public void addActivable(Activable s) throws CardException {
        if (s instanceof Inactiveable) {
            int index = this.inactiveableSpells.indexOf(s);

            if (index != -1) {
                this.inactiveableSpells.get(index).stackDuration(s);
            } else {
                s.apply(this);
                this.inactiveableSpells.add((Inactiveable) s);
            }

        } else {
            s.apply(this);
        }
    }

    @Override
    public void decrementTemporaryDuration() throws CardException {
        for (Inactiveable t : this.inactiveableSpells) {
            try {
                t.decrementDuration();
            } catch (CardException ce) {
                t.revert(this);
                this.inactiveableSpells.remove(t);
            }
        }
    }

    @Override
    public void attackCharacter(SummonedCharacter c) {
        TypeComparator typeComparator = new TypeComparator();

        if (typeComparator.compare(this.type, c.getType()) > 0) {
            c.takeDamage(this.attack * 2);
        } else if (typeComparator.compare(this.type, c.getType()) < 0) {
            c.takeDamage(this.attack / 2);
        } else {
            c.takeDamage(this.attack);
        }
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
}
