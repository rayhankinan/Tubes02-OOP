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

    public static Card getCard(int id) throws CardException {
        if (id >= Character.MIN_ID && id <= Character.MAX_ID) {
            return CardDatabase.getCharacter(id);
        } else if (id >= Potion.MIN_ID && id <= Potion.MAX_ID) {
            return CardDatabase.getPotion(id);
        } else if (id >= Swap.MIN_ID && id <= Swap.MAX_ID) {
            return CardDatabase.getSwap(id);
        } else if (id >= Morph.MIN_ID && id <= Morph.MAX_ID) {
            return CardDatabase.getMorph(id);
        } else if (id >= Level.MIN_ID && id <= Level.MAX_ID) {
            return CardDatabase.getLevel(id);
        } else {
            throw new CardException("Card not found!");
        }
    }

    public static void addCard(Card c) throws CardException {
        if (c instanceof Character) {
            CardDatabase.addCharacter((Character) c);
        } else if (c instanceof Potion) {
            CardDatabase.addPotion((Potion) c);
        } else if (c instanceof Swap) {
            CardDatabase.addSwap((Swap) c);
        } else if (c instanceof Morph) {
            CardDatabase.addMorph((Morph) c);
        } else if (c instanceof Level) {
            CardDatabase.addLevel((Level) c);
        } else {
            throw new CardException("Type is not recognized!");
        }
    }

    public static void printAll() {
        for (Character c : CardDatabase.characterList) {
            System.out.println(c);
        }

        for (Potion p : CardDatabase.potionList) {
            System.out.println(p);
        }

        for (Swap s : CardDatabase.swapList) {
            System.out.println(s);
        }

        for (Morph m : CardDatabase.morphList) {
            System.out.println(m);
        }

        for (Level l : CardDatabase.levelList) {
            System.out.println(l);
        }
    }
}
