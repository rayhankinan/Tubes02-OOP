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
    private int field;
    private int level;
    private int exp;
    private int currentHealth;
    private int currentAttack;
    private final List<Potion> potionSpells;
    private Swap swapSpell;
    // 0 is not available, 1 is available attack
    private int isAvailableAttack;

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

    public SummonedCharacter(int id, String name, Type type, String description, String imagepath, int baseAttack, int baseHealth, int mana, int attackup, int healthup) throws CardException {
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
    public int getBattleAvailability() {
        return this.isAvailableAttack;
    }

    @Override
    public void setBattleAvailability(int battleAvailability) {
        this.isAvailableAttack = battleAvailability;
    }

    @Override
    public void attackCharacter(SummonedCharacter c) {
        TypeComparator typeComparator = new TypeComparator();

        if (typeComparator.compare(this.type, c.getType()) > 0) {
            c.takeDamage(Math.max(this.currentAttack + this.getTempAttack(), 0) * 2);
        } else if (typeComparator.compare(this.type, c.getType()) < 0) {
            c.takeDamage(Math.max(this.currentAttack + this.getTempAttack(), 0) / 2);
        } else {
            c.takeDamage(Math.max(this.currentAttack + this.getTempAttack(), 0));
        }
    }

    @Override
    public void attackPlayer(Player p) {
        p.takeDamage(Math.max(this.currentAttack + this.getTempAttack(), 0));
    }

    @Override
    public void takeDamage(int damage) {
        for (Potion p : this.potionSpells) {
            int initialTempHealth = p.getTempHealth();
            p.setTempHealth(Math.max(initialTempHealth - damage, 0));
            damage = Math.max(damage - initialTempHealth, 0);
        }

        this.currentHealth = Math.max(this.currentHealth - damage, 0);
    }

    @Override
    public int getTempHealth() {
        int result = 0;

        for (Potion p : this.potionSpells) {
            result += p.getTempHealth();
        }

        return result;
    }

    @Override
    public int getTempAttack() {
        int result = 0;

        for (Potion p : this.potionSpells) {
            result += p.getTempAttack();
        }

        return result;
    }

    @Override
    public int getTotalHealth() {
        return Math.max(this.currentHealth + this.getTempHealth(), 0);
    }

    @Override
    public int getTotalAttack() {
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
    public void decrementTemporaryDuration() throws CardException {
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
        int temp;

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
        this.currentHealth = 0;
        this.currentAttack = 0;
        this.potionSpells.clear();
        this.swapSpell = null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(String.format("Id: %d\nName: %s\nType: %s\nDescription: %s\nImagepath: %s\nAttack: %d\nHealth: %d\nMana: %d\nAttack Up: %d\nHealth Up: %d\nLevel: %d\nExp: %d\nCurrent Attack: %d\nCurrent Health: %d\nTemp Attack: %d\nTemp Health: %d", this.id, this.name, this.type, this.description, this.imagepath, this.baseAttack, this.baseHealth, this.mana, this.attackup, this.healthup, this.level, this.exp, this.currentAttack, this.currentHealth, this.getTempAttack(), this.getTempHealth()));

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


