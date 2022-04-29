package com.aetherwars.model.card.character;

import com.aetherwars.model.card.spell.potion.Potion;
import com.aetherwars.model.card.spell.swap.Swap;
import com.aetherwars.model.player.Player;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.spell.Applicable;
import com.aetherwars.model.card.spell.Revertible;

import java.util.List;
import java.util.ArrayList;

public class SummonedCharacter extends Character implements Summonable, Attackable, Affectable {
    private int level;
    private int exp;
    private double currentHealth;
    private double currentAttack;
    private final List<Potion> potionSpells;
    private Swap swapSpell;

    private final int field; /* Position in field */
    private int isAvailableAttack; /* 0 is not available, 1 is available attack */

    public SummonedCharacter(int id) throws CardException {
        super(id);

        this.field = -1;
        this.level = 1;
        this.exp = 0;
        this.currentHealth = 0;
        this.currentAttack = 0;
        this.potionSpells = new ArrayList<>();
        this.swapSpell = null;
        this.isAvailableAttack = 0;
    }

    public SummonedCharacter(int id, String name, Type type, String description, String imagepath, double baseAttack, double baseHealth, int mana, double attackup, double healthup) throws CardException {
        super(id, name, type, description, imagepath, baseAttack, baseHealth, mana, attackup, healthup);

        this.field = -1;
        this.level = 1;
        this.exp = 0;
        this.currentHealth = baseHealth;
        this.currentAttack = baseAttack;
        this.potionSpells = new ArrayList<>();
        this.swapSpell = null;
        this.isAvailableAttack = 0;
    }

    public SummonedCharacter(Character C) throws CardException {
        super(C);

        this.field = 0;
        this.level = 1;
        this.exp = 0;
        this.currentHealth = C.baseHealth;
        this.currentAttack = C.baseAttack;
        this.potionSpells = new ArrayList<>();
        this.swapSpell = null;
        this.isAvailableAttack = 0;
    }

    public SummonedCharacter(Character C, int field) throws CardException {
        super(C);

        this.field = field;
        this.level = 1;
        this.exp = 0;
        this.currentHealth = C.baseHealth;
        this.currentAttack = C.baseAttack;
        this.potionSpells = new ArrayList<>();
        this.swapSpell = null;
        this.isAvailableAttack = 0;
    }

    @Override
    public int getField() {
        return this.field;
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
    public int getExpForNextLevel() throws CardException {
        if (this.level < 10) {
            return this.level * 2 - 1;
        } else {
            throw new CardException("Character level is already maxed out!");
        }
    }

    @Override
    public double getCurrentHealth() {
        return this.currentHealth;
    }

    @Override
    public double getCurrentAttack() {
        return this.currentAttack;
    }

    @Override
    public void addExp(int exp) throws CardException {
        int totalExp = this.exp + exp;

        while (totalExp >= this.getExpForNextLevel()) {
            totalExp -= this.getExpForNextLevel();
            this.levelUp();
        }

        this.exp = totalExp;
    }

    @Override
    public void resetExp() {
        this.exp = 0;
    }

    @Override
    public void levelUp() throws CardException {
        if (level < 10) {
            this.level++;
            this.baseHealth += this.healthup;
            this.baseAttack += this.attackup;
            this.currentHealth = this.baseHealth;
            this.currentAttack = this.baseAttack;

        } else {
            throw new CardException("Character level is already maxed out!");
        }
    }

    @Override
    public void levelDown() throws CardException {
        if (level > 0) {
            this.level--;
            this.exp = 0;
            this.baseHealth -= this.healthup;
            this.baseAttack -= this.attackup;
            this.currentHealth = Math.min(this.currentHealth, this.baseHealth);
            this.currentAttack = Math.min(this.currentAttack, this.baseAttack);

        } else {
            throw new CardException("Character level is already 0!");
        }
    }

    @Override
    public int getBattleAvailability() {
        return this.isAvailableAttack;
    }

    @Override
    public void setBattleAvailability(int battleAvailability) {
        this.isAvailableAttack = battleAvailability;
    }

    @Override
    public void attackCharacter(SummonedCharacter c) throws CardException {
        TypeComparator typeComparator = new TypeComparator();

        if (typeComparator.compare(this.type, c.getType()) > 0) {
            c.takeDamage(Math.max(this.currentAttack + this.getTempAttack(), 0) * 2);
        } else if (typeComparator.compare(this.type, c.getType()) < 0) {
            c.takeDamage(Math.max(this.currentAttack + this.getTempAttack(), 0) / 2);
        } else {
            c.takeDamage(Math.max(this.currentAttack + this.getTempAttack(), 0));
        }

        if (typeComparator.compare(c.getType(), this.type) > 0) {
            this.takeDamage(Math.max(c.currentAttack + c.getTempAttack(), 0) * 2);
        } else if (typeComparator.compare(this.type, c.getType()) < 0) {
            this.takeDamage(Math.max(c.currentAttack + c.getTempAttack(), 0) / 2);
        } else {
            this.takeDamage(Math.max(c.currentAttack + c.getTempAttack(), 0));
        }

        if (c.getTotalHealth() <= 0) {
            this.addExp(c.getLevel());
        }

        if (this.getTotalHealth() <= 0) {
            c.addExp(this.getLevel());
        }
    }

    @Override
    public void attackPlayer(Player p) {
        p.takeDamage(Math.max(this.currentAttack + this.getTempAttack(), 0.0));
    }

    @Override
    public void takeDamage(double damage) {
        for (Potion p : this.potionSpells) {
            double initialTempHealth = p.getTempHealth();
            p.setTempHealth(Math.max(initialTempHealth - damage, 0));
            damage = Math.max(damage - initialTempHealth, 0);
        }

        this.currentHealth = Math.max(this.currentHealth - damage, 0);
    }

    @Override
    public double getTempHealth() {
        double result = 0;

        for (Potion p : this.potionSpells) {
            result += p.getTempHealth();
        }

        return result;
    }

    @Override
    public double getTempAttack() {
        double result = 0;

        for (Potion p : this.potionSpells) {
            result += p.getTempAttack();
        }

        return result;
    }

    @Override
    public double getTotalHealth() {
        return Math.max(this.currentHealth + this.getTempHealth(), 0);
    }

    @Override
    public double getTotalAttack() {
        return Math.max(this.currentAttack + this.getTempAttack(), 0);
    }

    @Override
    public List<Potion> getTemporary() {
        return this.potionSpells;
    }

    @Override
    public void addPotion(Potion p) throws CardException {
        int index = this.potionSpells.indexOf(p);

        if (index != -1) {
            this.potionSpells.get(index).stackDuration(p);
        } else {
            this.potionSpells.add(p);
        }
    }

    @Override
    public void deletePotion(Potion p) throws CardException {
        int index = this.potionSpells.indexOf(p);

        if (index != -1) {
            this.potionSpells.remove(p);
        } else {
            throw new CardException("Potion does not exist!");
        }
    }

    @Override
    public void addActivable(Applicable s) throws CardException {
        s.apply(this);
    }

    @Override
    public void decrementTemporaryDuration() {
        List<Potion> toRemove = new ArrayList<>();

        for (Potion p : this.potionSpells) {
            try {
                p.decrementDuration();
            } catch (CardException ce) {
                toRemove.add(p);
            }
        }

        for (Potion p : toRemove) {
            this.potionSpells.remove(p);
        }

        if (this.swapSpell != null) {
            try {
                this.swapSpell.decrementDuration();
            } catch (CardException e) {
                this.swapHealthAttack();
                this.swapSpell = null;
            }
        }
    }

    public void swapHealthAttack() {
        double temp;

        temp = this.currentHealth;
        this.currentHealth = this.currentAttack;
        this.currentAttack = temp;

        for (Potion p : this.potionSpells) {
            p.swapAttackHealth();
        }
    }

    @Override
    public void setSwap(Swap s) throws CardException {
        if (this.swapSpell != null) {
            this.swapSpell.stackDuration(s);
        } else {
            this.swapHealthAttack();
            this.swapSpell = s;
        }
    }

    @Override
    public void morph(int id) throws CardException {
        Character newCharacter = CardDatabase.getCharacter(id);

        this.id = newCharacter.getId();
        this.name = newCharacter.getName();
        this.type = newCharacter.getType();
        this.description = newCharacter.getDescription();
        this.imagepath = newCharacter.getImagepath();
        this.baseHealth = newCharacter.getBaseHealth();
        this.baseAttack = newCharacter.getBaseAttack();
        this.mana = newCharacter.getMana();
        this.healthup = newCharacter.getHealthup();
        this.attackup = newCharacter.getAttackup();

        this.level = 1;
        this.exp = 0;
        this.currentHealth = this.baseHealth;
        this.currentAttack = this.baseAttack;
        this.potionSpells.clear();
        this.swapSpell = null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(String.format("Id: %d\nName: %s\nType: %s\nDescription: %s\nImagepath: %s\nAttack: %.1f\nHealth: %.1f\nMana: %d\nAttack Up: %.1f\nHealth Up: %.1f\nLevel: %d\nExp: %d\nCurrent Attack: %.1f\nCurrent Health: %.1f\nTemp Attack: %.1f\nTemp Health: %.1f", this.id, this.name, this.type, this.description, this.imagepath, this.baseAttack, this.baseHealth, this.mana, this.attackup, this.healthup, this.level, this.exp, this.currentAttack, this.currentHealth, this.getTempAttack(), this.getTempHealth()));

        if (this.potionSpells.size() > 0) {
            stringBuilder.append("\nACTIVE POTIONS:");
            for (Revertible r : this.potionSpells) {
                stringBuilder.append("\n");
                stringBuilder.append(r);
            }

        }
        if (this.swapSpell != null) {
            stringBuilder.append("\nACTIVE SWAP:");
            stringBuilder.append("\n");
            stringBuilder.append(this.swapSpell);
        }

        return stringBuilder.toString();
    }
}