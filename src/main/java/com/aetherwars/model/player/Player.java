package com.aetherwars.model.player;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.CardException;

import com.aetherwars.model.card.spell.Applicable;
import com.aetherwars.model.card.spell.Spell;
import com.aetherwars.model.deck.Deck;
import com.aetherwars.model.card.character.SummonedCharacter;
import com.aetherwars.model.card.character.Character;
import com.aetherwars.model.deck.DeckException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private static final double MAX_HP = 80;
    private static final int MAX_MANA = 10;
    private static final double MIN_HP = 0;
    private static final int MIN_MANA = 0;
    private static final int MAX_CARD_IN_HAND = 5;
    private static final int MAX_CARD_IN_BOARD = 5;

    private final SummonedCharacter[] characterFieldCards = new SummonedCharacter[5];
    private final List<Card> onHand;
    private final Deck deck;
    private final String name;

    private double health;
    private int mana;
    private int round;

    public Player(String playerName, String deckFilename) throws IOException, URISyntaxException, CardException {
        this.name = playerName;
        this.health = MAX_HP;
        this.mana = 1;
        this.round = 1;
        this.onHand = new ArrayList<>(MAX_CARD_IN_HAND);
        this.deck = new Deck(deckFilename);

        for (int i = 0; i < MAX_CARD_IN_BOARD; i++) {
            this.characterFieldCards[i] = null;
        }
    }

    public String getName() {
        return this.name;
    }

    public double getHealth() {
        return this.health;
    }

    public double getMaxHp() {
        return this.MAX_HP;
    }

    public int getMana() {
        return this.mana;
    }

    public List<Card> getOnHand() {
        return this.onHand;
    }

    public Deck getDeck() {
        return this.deck;
    }

    public void useMana(int mana) throws PlayerException {
        if (this.mana - mana < MIN_MANA) {
            throw new PlayerException("Mana is not sufficient!");
        } else {
            this.mana -= mana;
        }
    }

    public int getRound() {
        return this.round;
    }

    public void addRound() {
        this.round++;
    }

    public void takeDamage(double damage) {
        if (this.health - damage < MIN_HP) {
            this.health = 0;
        } else {
            this.health -= damage;
        }
    }

    public void drawCard(Card choice, List<Card> remain) throws DeckException {
//        List<Card> threeCards = this.deck.getThreeCards();
        //TODO: buat choice sesuai dengan pilihan user dari UI
//        int choice = new Random().nextInt(threeCards.size()

        /* jika kartu di tangan sudah lebih dari 5 maka buang 1 kartu acak */

        if (this.onHand.size() >= MAX_CARD_IN_HAND) {
            Random r = new Random();
            int idx = r.nextInt(this.onHand.size());
            this.onHand.remove(idx);
        }

        this.onHand.add(choice);
        this.deck.returnCardsToDeck(remain);
    }

    public SummonedCharacter[] getCharacterFieldCards() { return this.characterFieldCards; }

    public Card getField(int field) {
        return characterFieldCards[field];
    }

    public boolean isFieldEmpty() {

        for (SummonedCharacter characterFieldCard : this.characterFieldCards) {
            if (characterFieldCard != null) {
                return false;
            }
        }

        return true;
    }

    public Card getHand(int hand) {
        if (hand < this.onHand.size()) {
            return onHand.get(hand);
        }
        return null;
    }

    public void addToField(int field, Card card) throws CardException, PlayerException {
        if (this.getMana() >= card.getMana()){
            if (card instanceof Character) {
                SummonedCharacter fieldCard = new SummonedCharacter((Character) card, field);
                characterFieldCards[field] = fieldCard;
                discardCardOnHand(card);
                this.useMana(card.getMana());

            } else if (card instanceof Spell) {
                characterFieldCards[field].addActivable((Applicable) card);
                // ILANGIN KLO ABIS KENA SPELL MALAH MODAR
                if (characterFieldCards[field].getTotalHealth() <= 0){
                    discardCharacterFieldCards(field);
                }
                discardCardOnHand(card);
                this.useMana(card.getMana());

            } else {
                throw new CardException("Class is not recognized!");
            }
        } else {
            throw new CardException("Mana tidak cukup!");
        }
    }

    public void addToFieldOpponent(Player opponent, int field, Card card) throws CardException, PlayerException {
        if (this.getMana() >= card.getMana()){
            if (card instanceof Spell) {
                System.out.println(field);
                for (int i=0; i < 5; i++){
                    System.out.println(opponent.characterFieldCards[i]);
                }
                opponent.characterFieldCards[field].addActivable((Applicable) card);
                System.out.println("YOYOYO");
                // ILANGIN KLO ABIS KENA SPELL MALAH MODAR
                if (opponent.characterFieldCards[field].getTotalHealth() <= 0){
                    opponent.discardCharacterFieldCards(field);
                }
                this.discardCardOnHand(card);
                this.useMana(card.getMana());

            } else {
                throw new CardException("Class is not recognized!");
            }
        } else {
            throw new CardException("Mana tidak cukup!");
        }
    }

    public void discardCardOnHand(Card card) {
        this.onHand.remove(card);
    }

    public void discardCharacterFieldCards(int field) {
        this.characterFieldCards[field] = null;
    }

    public void useManaForExp(int field, int mana) throws CardException, PlayerException {
        this.characterFieldCards[field].addExp(mana);
        this.useMana(mana);
    }

    public boolean hasEnoughMana(int mana) {
        return this.mana >= mana;
    }

    public boolean canDeploy(Card card) throws CardException {
        if (card instanceof Character) {
            return hasEnoughMana(card.getMana());

        } else if (card instanceof Spell) {
            return hasEnoughMana(card.getMana());

        } else {
            throw new CardException("Class is not recognized!");
        }
    }

    public void resetMana() {
        this.mana = Math.min(this.round, MAX_MANA);
    }

    public void attackOpponentPlayer(SummonedCharacter characterFieldCard, Player opponentPlayer) {
        characterFieldCard.attackPlayer(opponentPlayer);
        characterFieldCard.setBattleAvailability(0);
    }

    public void attackOpponentCard(SummonedCharacter characterFieldCard, SummonedCharacter opponentCharacterFieldCard, Player opponentPlayer) throws CardException {
        characterFieldCard.attackCharacter(opponentCharacterFieldCard);
        if (opponentCharacterFieldCard.getTotalHealth() <= 0) {
            //discard opponent card
            characterFieldCard.addExp(opponentCharacterFieldCard.getLevel());
            opponentPlayer.discardCharacterFieldCards(opponentCharacterFieldCard.getField());
        }
        if (characterFieldCard.getTotalHealth() <= 0){
            this.discardCharacterFieldCards(characterFieldCard.getField());
        }
        characterFieldCard.setBattleAvailability(0);
    }

    public static void main(String[] args) throws IOException, URISyntaxException, CardException, PlayerException {
        System.out.println("Hello");
        CardDatabase.initialize();
        Player player_1 = new Player("Rayhan", "deck_1.csv");
        System.out.println("Nama : "+player_1.getName());
        System.out.println("Mana : "+player_1.getMana());
//        player_1.useMana(1);
        System.out.println("Mana Setelah Use Mana : "+player_1.getMana());
        Character character_1 = CardDatabase.getCharacter(3);
        player_1.addToField(1, character_1);
        SummonedCharacter[] fieldCards = player_1.getCharacterFieldCards();
        System.out.println("Field Card 1 : "+fieldCards[1].getName());

        Player player_2 = new Player("Adiya", "deck_1.csv");
        System.out.println("Nama : "+player_2.getName());
        System.out.println("Mana : "+player_2.getMana());
        Character character_2 = CardDatabase.getCharacter(4);
        player_2.addToField(2, character_2);
        SummonedCharacter[] fieldCards2 = player_2.getCharacterFieldCards();
        System.out.println("Field Card 2 : "+fieldCards2[2].getName());

        System.out.println("Health character player 2: "+ fieldCards2[2].getTotalHealth());
        player_1.attackOpponentCard(fieldCards[1], fieldCards2[2], player_2);
        System.out.println("Exp character player 1: "+ fieldCards[1].getExp());
        System.out.println("Level character player 1 sebelum membunuh musuh: "+ fieldCards[1].getLevel());
        System.out.println("Health character player 2 setelah diserang: "+ fieldCards2[2].getTotalHealth());
        System.out.println("Level character player 2: "+ fieldCards2[2].getLevel());
        fieldCards[1].addExp(8);
//        player_1.attackOpponentCard(fieldCards[1], fieldCards2[2], player_2);
        System.out.println("Character player 2 mati");
        System.out.println("Exp character player 1 setelah membunuh musuh: "+ fieldCards[1].getExp());
        System.out.println("Level character player 1 setelah membunuh musuh: "+ fieldCards[1].getLevel());

        if (fieldCards2[2] == null) {
            System.out.println("fieldCard player 2 kosong");
        }
    }
}
