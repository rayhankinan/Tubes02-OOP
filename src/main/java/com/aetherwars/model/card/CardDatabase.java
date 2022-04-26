package com.aetherwars.model.card;

import com.aetherwars.model.card.character.Type;
import com.aetherwars.model.card.spell.level.Level;
import com.aetherwars.model.card.spell.morph.Morph;
import com.aetherwars.model.card.spell.potion.Potion;
import com.aetherwars.model.card.spell.swap.Swap;
import com.aetherwars.model.card.character.Character;
import com.aetherwars.util.CSVReader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class CardDatabase {
    /* TODO: Input data from resources automatically, without the need to initialize it in main program */

    private static final String CHARACTER_CSV_FILE_PATH = "data/character.csv";
    private static final String SPELL_POTION_FILE_PATH = "data/spell_ptn.csv";
    private static final String SPELL_LEVEL_FILE_PATH = "data/spell_lvl.csv";
    private static final String SPELL_MORPH_FILE_PATH = "data/spell_morph.csv";
    private static final String SPELL_SWAP_FILE_PATH = "data/spell_swap.csv";

    private static final List<Character> characterList = new ArrayList<>();
    private static final List<Potion> potionList = new ArrayList<>();
    private static final List<Level> levelList = new ArrayList<>();
    private static final List<Morph> morphList = new ArrayList<>();
    private static final List<Swap> swapList = new ArrayList<>();

    public static void initialize() throws IOException, URISyntaxException, CardException {
        File characterCSVFile = new File(Objects.requireNonNull(Character.class.getResource(CHARACTER_CSV_FILE_PATH)).toURI());
        CSVReader characterReader = new CSVReader(characterCSVFile, "\t");
        characterReader.setSkipHeader(true);
        List<String[]> characterRows = characterReader.read();
        for (String[] row : characterRows) {
            Character c = new Character(Integer.parseInt(row[0]), row[1], Type.valueOf(row[2]), row[3], row[4], Integer.parseInt(row[5]), Integer.parseInt(row[6]), Integer.parseInt(row[7]), Integer.parseInt(row[8]), Integer.parseInt(row[9]));
            CardDatabase.addCharacter(c);
        }

        File potionCSVFile = new File(Objects.requireNonNull(Potion.class.getResource(SPELL_POTION_FILE_PATH)).toURI());
        CSVReader potionReader = new CSVReader(potionCSVFile, "\t");
        potionReader.setSkipHeader(true);
        List<String[]> potionRows = potionReader.read();
        for (String[] row : potionRows) {
            Potion p = new Potion(Integer.parseInt(row[0]), row[1], row[2], row[3], Integer.parseInt(row[4]), Integer.parseInt(row[5]), Integer.parseInt(row[6]), Integer.parseInt(row[7]));
            CardDatabase.addPotion(p);
        }

        File levelCSVFile = new File(Objects.requireNonNull(Level.class.getResource(SPELL_LEVEL_FILE_PATH)).toURI());
        CSVReader levelReader = new CSVReader(levelCSVFile, "\t");
        levelReader.setSkipHeader(true);
        List<String[]> levelRows = levelReader.read();
        for (String[] row : levelRows) {
            Level l = new Level(Integer.parseInt(row[0]), row[1], row[2], row[3], Integer.parseInt(row[4]), Integer.parseInt(row[5]));
            CardDatabase.addLevel(l);
        }

        File morphCSVFile = new File(Objects.requireNonNull(Morph.class.getResource(SPELL_MORPH_FILE_PATH)).toURI());
        CSVReader morphReader = new CSVReader(morphCSVFile, "\t");
        morphReader.setSkipHeader(true);
        List<String[]> morphRows = morphReader.read();
        for (String[] row : morphRows) {
            Morph m = new Morph(Integer.parseInt(row[0]), row[1], row[2], row[3], Integer.parseInt(row[4]), Integer.parseInt(row[5]));
            CardDatabase.addMorph(m);
        }

        File swapCSVFile = new File(Objects.requireNonNull(Swap.class.getResource(SPELL_SWAP_FILE_PATH)).toURI());
        CSVReader swapReader = new CSVReader(swapCSVFile, "\t");
        swapReader.setSkipHeader(true);
        List<String[]> swapRows = swapReader.read();
        for (String[] row : swapRows) {
            Swap s = new Swap(Integer.parseInt(row[0]), row[1], row[2], row[3], Integer.parseInt(row[4]), Integer.parseInt(row[5]));
            CardDatabase.addSwap(s);
        }
    }

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
