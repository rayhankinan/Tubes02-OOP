package com.aetherwars.model.card.character;

import com.aetherwars.model.card.spell.potion.Potion;
import com.aetherwars.model.card.spell.swap.Swap;
import com.aetherwars.model.player.Player;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.spell.Applicable;
import com.aetherwars.model.card.spell.Revertable;

import java.rmi.activation.Activatable;
import java.util.List;
import java.util.ArrayList;

public class SummonedCharacter extends Character implements Summonable, Attackable, Affectable {
    private int level;
    private int exp;
    private int currentHealth;
    private int currentAttack;
    private int tempHealth;
    private int tempAttack;
    private final List<Revertable> revertableSpells;

    public SummonedCharacter(int id) throws CardException {
        super(id);

        this.level = 1;
        this.exp = 0;
        this.currentHealth = 0;
        this.currentAttack = 0;
        this.tempHealth = 0;
        this.tempAttack = 0;
        this.revertableSpells = new ArrayList<>();
    }

    public SummonedCharacter(int id, String name, Type type, String description, String imagepath, int baseAttack, int baseHealth, int mana, int attackup, int healthup) throws CardException {
        super(id, name, type, description, imagepath, baseAttack, baseHealth, mana, attackup, healthup);

        this.level = 1;
        this.exp = 0;
        this.currentHealth = baseHealth;
        this.currentAttack = baseAttack;
        this.tempHealth = 0;
        this.tempAttack = 0;
        this.revertableSpells = new ArrayList<>();
    }

    public SummonedCharacter(Character C) throws CardException {
        super(C);

        this.level = 1;
        this.exp = 0;
        this.currentHealth = C.baseHealth;
        this.currentAttack = C.baseAttack;
        this.tempHealth = 0;
        this.tempAttack = 0;
        this.revertableSpells = new ArrayList<>();
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
    public int getCurrentHealth() {
        return this.currentHealth;
    }

    @Override
    public int getCurrentAttack() {
        return this.currentAttack;
    }

    @Override
    public void addExp(int exp) throws CardException {
        if (level < 10) {
            if (this.exp + exp < this.getExpForNextLevel()) {
                this.exp += exp;
            } else {
                this.levelUp();
                this.exp += (exp - this.getExpForNextLevel());
            }
        } else {
            throw new CardException("Character level is already maxed out!");
        }
    }

    @Override
    public void levelUp() throws CardException {
        if (level < 10) {
            this.level++;
            this.exp = 0;
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
    public void attackCharacter(SummonedCharacter c) {
        TypeComparator typeComparator = new TypeComparator();

        if (typeComparator.compare(this.type, c.getType()) > 0) {
            c.takeDamage(Math.max(this.currentAttack + this.tempAttack, 0) * 2);
        } else if (typeComparator.compare(this.type, c.getType()) < 0) {
            c.takeDamage(Math.max(this.currentAttack + this.tempAttack, 0) / 2);
        } else {
            c.takeDamage(Math.max(this.currentAttack + this.tempAttack, 0));
        }
    }

    @Override
    public void attackPlayer(Player p) {
        p.takeDamage(Math.max(this.currentAttack + this.tempAttack, 0));
    }

    @Override
    public void takeDamage(int damage) {
        if (this.tempHealth > damage) {
            this.tempHealth -= damage;
        } else {
            this.tempHealth = 0;
            this.currentHealth = Math.max(this.currentHealth + this.tempHealth - damage, 0);
        }
    }

    @Override
    public int getTempHealth() {
        return this.tempHealth;
    }

    @Override
    public int getTempAttack() {
        return this.tempAttack;
    }

    @Override
    public int getTotalHealth() {
        return Math.max(this.currentHealth + this.tempHealth, 0);
    }

    @Override
    public int getTotalAttack() {
        return Math.max(this.currentAttack + this.tempAttack, 0);
    }

    @Override
    public List<Revertable> getTemporary() {
        return this.revertableSpells;
    }

    @Override
    public void addActivable(Applicable s) throws CardException {
        if (s instanceof Swap) {
            Revertable currentSwap = this.revertableSpells.stream().filter((r) -> r instanceof Swap).findFirst().orElse(null);

            if (currentSwap != null) {
                currentSwap.stackDuration(s);
            } else {
                s.apply(this);
                this.revertableSpells.add((Revertable) s);
            }

        } else if (s instanceof Potion) {
            int index = this.revertableSpells.indexOf(s);

            if (index != -1) {
                this.revertableSpells.get(index).stackDuration(s);
            } else {
                s.apply(this);
                this.revertableSpells.add((Revertable) s);
            }

        } else {
            s.apply(this);
        }
    }

    @Override
    public void decrementTemporaryDuration() throws CardException {
        for (Revertable t : this.revertableSpells) {
            try {
                t.decrementDuration();
            } catch (CardException ce) {
                t.revert(this);
                this.revertableSpells.remove(t);
            }
        }
    }

    @Override
    public void swapHealthAttack() {
        int temp;

        temp = this.currentHealth;
        this.currentHealth = this.currentAttack;
        this.currentAttack = temp;

        temp = this.tempHealth;
        this.tempHealth = this.tempAttack;
        this.tempAttack = temp;
    }

    @Override
    public void addTempHealth(int tempHealth) {
        this.tempHealth += tempHealth;
    }

    @Override
    public void addTempAttack(int tempAttack) {
        this.tempAttack += tempAttack;
    }

    @Override
    public void subtractTempHealth(int tempHealth) {
        this.tempHealth -= tempHealth;
    }

    @Override
    public void subtractTempAttack(int tempAttack) {
        this.tempAttack -= tempAttack;
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
        this.currentHealth = newCharacter.getBaseHealth();
        this.currentAttack = newCharacter.getBaseAttack();
        this.tempHealth = 0;
        this.tempAttack = 0;
        this.revertableSpells.clear();
    }
}
