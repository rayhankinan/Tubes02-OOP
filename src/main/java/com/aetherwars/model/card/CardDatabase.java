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

    public static Character getCharacter(int id) {
        int index = CardDatabase.characterList.indexOf(new Character(id));
        return CardDatabase.characterList.get(index);
    }

    public static void addCharacter(Character c) {
        CardDatabase.characterList.add(c);
    }

    public static Potion getPotion(int id) {
        int index = CardDatabase.potionList.indexOf(new Potion(id));
        return CardDatabase.potionList.get(index);
    }

    public static void addPotion(Potion p) {
        CardDatabase.potionList.add(p);
    }

    public static Level getLevel(int id) {
        int index = CardDatabase.levelList.indexOf(new Level(id));
        return CardDatabase.levelList.get(index);
    }

    public static void addLevel(Level l) {
        CardDatabase.levelList.add(l);
    }

    public static Morph getMorph(int id) {
        int index = CardDatabase.morphList.indexOf(new Morph(id));
        return CardDatabase.morphList.get(index);
    }

    public static void addMorph(Morph m) {
        CardDatabase.morphList.add(m);
    }

    public static Swap getSwap(int id) {
        int index = CardDatabase.swapList.indexOf(new Swap(id));
        return CardDatabase.swapList.get(index);
    }

    public static void addSwap(Swap s) {
        CardDatabase.swapList.add(s);
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
