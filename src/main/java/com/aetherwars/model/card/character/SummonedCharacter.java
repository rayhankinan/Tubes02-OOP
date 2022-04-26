package com.aetherwars.model.card.character;

import com.aetherwars.model.player.Player;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.spell.Activable;
import com.aetherwars.model.card.spell.Temporary;

import java.util.List;
import java.util.ArrayList;

public class SummonedCharacter extends Character implements Summonable {
    private int level;
    private int exp;
    // TODO: Exp per level (buat tau kapan harus level up)
    private int expNeeded;

    private final List<Activable> activeSpells;

    public SummonedCharacter(int id, String name, Type type, String description, String imagepath, int attack, int health, int mana, int attackup, int healthup, int level, int exp) throws CardException {
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
        /* TODO: stack duration */
    }

    @Override
    public List<Activable> getActivable() {
        return this.activeSpells;
    }

    @Override
    public void activateEffects() {
        for (Activable s : this.activeSpells) {
            if (s instanceof Temporary) {
                try {
                    s.apply(this);

                } catch (CardException ce1) {
                    /* Already activated */
                    try {
                        ((Temporary) s).decrementDuration();

                    } catch (CardException ce2) {
                        /* Duration is 0 */
                        try {
                            ((Temporary) s).revert(this);
                            this.activeSpells.remove(s);

                        } catch (CardException ce3) {
                            ce3.printStackTrace();
                        }
                    }
                }

            } else {
                try {
                    s.apply(this);
                    this.activeSpells.remove(s);

                } catch (CardException ce1) {
                    ce1.printStackTrace();
                }
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
