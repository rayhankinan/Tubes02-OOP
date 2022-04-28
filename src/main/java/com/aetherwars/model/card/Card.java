package com.aetherwars.model.card;

import com.aetherwars.model.card.character.Character; /* DELETE THIS LATER */
import com.aetherwars.model.card.character.SummonedCharacter; /* DELETE THIS LATER */
import com.aetherwars.model.card.spell.level.Level; /* DELETE THIS LATER */
import com.aetherwars.model.card.spell.morph.Morph; /* DELETE THIS LATER */
import com.aetherwars.model.card.spell.potion.Potion; /* DELETE THIS LATER */
import com.aetherwars.model.card.spell.swap.Swap; /* DELETE THIS LATER */

public class Card {
    protected int id;
    protected String name;
    protected String description;
    protected String imagepath;
    protected int mana;

    public Card(int id) {
        this.id = id;
        this.name = "";
        this.description = "";
        this.imagepath = "";
        this.mana = 0;
    }

    public Card(int id, String name, String description, String imagepath, int mana) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagepath = imagepath;
        this.mana = mana;
    }

    public Card(Card C) {
        this.id = C.id;
        this.name = C.name;
        this.description = C.description;
        this.imagepath = C.imagepath;
        this.mana = C.mana;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImagepath() {
        return this.imagepath;
    }

    public int getMana() {
        return this.mana;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Card) {
            Card c = (Card) o;
            return this.getId() == c.getId();

        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            CardDatabase.initialize();

            System.out.println("Initialize Character:");
            Character character = CardDatabase.getCharacter(1);
            SummonedCharacter summonedCharacter = new SummonedCharacter(character);
            System.out.println(summonedCharacter);
            System.out.println();

            System.out.println("Add Potion Again:");
            Potion potion1 = CardDatabase.getPotion(102);
            summonedCharacter.addActivable(potion1);
            System.out.println(summonedCharacter);
            System.out.println();

            System.out.println("Decrement Duration:");
            summonedCharacter.decrementTemporaryDuration();
            System.out.println(summonedCharacter);
            System.out.println();

            System.out.println("Add Potion Again:");
            Potion potion2 = CardDatabase.getPotion(108);
            summonedCharacter.addActivable(potion2);
            System.out.println(summonedCharacter);
            System.out.println();

            System.out.println("Decrement Duration:");
            summonedCharacter.decrementTemporaryDuration();
            System.out.println(summonedCharacter);
            System.out.println();

            System.out.println("Add Level:");
            Level level = CardDatabase.getLevel(401);
            summonedCharacter.addActivable(level);
            System.out.println(summonedCharacter);
            System.out.println();

            System.out.println("Decrement Duration:");
            summonedCharacter.decrementTemporaryDuration();
            System.out.println(summonedCharacter);
            System.out.println();

            System.out.println("Initialize Other Character:");
            Character otherCharacter = CardDatabase.getCharacter(1);
            SummonedCharacter summonedOtherCharacter = new SummonedCharacter(otherCharacter);
            System.out.println(summonedOtherCharacter);
            System.out.println();

            System.out.println("Character Attacked by Other Character:");
            summonedOtherCharacter.attackCharacter(summonedCharacter);
            System.out.println(summonedCharacter);
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
