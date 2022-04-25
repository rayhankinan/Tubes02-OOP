package com.aetherwars.model.card;

import com.aetherwars.model.card.spell.Level;
import com.aetherwars.model.card.spell.Morph;
import com.aetherwars.model.card.spell.Potion;
import com.aetherwars.model.card.spell.Swap;
import com.aetherwars.model.card.character.Character;

import java.util.List;
import java.util.ArrayList;

public class CardDatabase {
    private static final List<Character> characterList = new ArrayList<>();
    private static final List<Potion> potionList = new ArrayList<>();
    private static final List<Level> levelList = new ArrayList<>();
    private static final List<Morph> morphList = new ArrayList<>();
    private static final List<Swap> swapList = new ArrayList<>();

    public static Character getCharacter(int id) throws CardException {
        int index = CardDatabase.characterList.indexOf(new Character(id));

        if (index == -1) {
            throw new CardException("Character not found!");
        } else {
            return CardDatabase.characterList.get(index);
        }
    }

    public static void addCharacter(Character c) throws CardException {
        int index = CardDatabase.characterList.indexOf(c);

        if (index == -1) {
            CardDatabase.characterList.add(c);
        } else {
            throw new CardException("Character already added!");
        }
    }

    public static Potion getPotion(int id) throws CardException {
        int index = CardDatabase.potionList.indexOf(new Potion(id));

        if (index == -1) {
            throw new CardException("Potion spell not found!");
        } else {
            return CardDatabase.potionList.get(index);
        }
    }

    public static void addPotion(Potion p) throws CardException {
        int index = CardDatabase.potionList.indexOf(p);

        if (index == -1) {
            CardDatabase.potionList.add(p);
        } else {
            throw new CardException("Potion spell already added!");
        }
    }

    public static Level getLevel(int id) throws CardException {
        int index = CardDatabase.levelList.indexOf(new Level(id));

        if (index == -1) {
            throw new CardException("Level spell not found!");
        } else {
            return CardDatabase.levelList.get(index);
        }
    }

    public static void addLevel(Level l) throws CardException {
        int index = CardDatabase.levelList.indexOf(l);

        if (index == -1) {
            CardDatabase.levelList.add(l);
        } else {
            throw new CardException("Level spell already added!");
        }
    }

    public static Morph getMorph(int id) throws CardException {
        int index = CardDatabase.morphList.indexOf(new Morph(id));

        if (index == -1) {
            throw new CardException("Morph spell not found!");
        } else {
            return CardDatabase.morphList.get(index);
        }
    }

    public static void addMorph(Morph m) throws CardException {
        int index = CardDatabase.morphList.indexOf(m);

        if (index == -1) {
            CardDatabase.morphList.add(m);
        } else {
            throw new CardException("Morph spell already added!");
        }
    }

    public static Swap getSwap(int id) throws CardException {
        int index = CardDatabase.swapList.indexOf(new Swap(id));

        if (index == -1) {
            throw new CardException("Swap spell not found!");
        } else {
            return CardDatabase.swapList.get(index);
        }
    }

    public static void addSwap(Swap s) throws CardException {
        int index = CardDatabase.swapList.indexOf(s);

        if (index == -1) {
            CardDatabase.swapList.add(s);
        } else {
            throw new CardException("Swap spell already added!");
        }
    }

    public static void printAll() {
        for (Character c : CardDatabase.characterList) {
            System.out.println(c);
        }

        for (Potion p : CardDatabase.potionList) {
            System.out.println(p);
        }

        for (Level l : CardDatabase.levelList) {
            System.out.println(l);
        }

        for (Morph m : CardDatabase.morphList) {
            System.out.println(m);
        }

        for (Swap s : CardDatabase.swapList) {
            System.out.println(s);
        }
    }
}
