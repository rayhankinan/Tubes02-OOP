package com.aetherwars.model.player;

import com.aetherwars.model.card.Card;
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
    private static final int MAX_HP = 80;
    private static final int MAX_MANA = 10;
    private static final int MIN_HP = 0;
    private static final int MIN_MANA = 0;
    private static final int MAX_CARD_IN_HAND = 5;
    private static final int MAX_CARD_IN_BOARD = 5;

    private final List<SummonedCharacter> characterFieldCards;
    private final List<Card> onHand;
    private final Deck deck;
    private final String name;

    private int health;
    private int mana;
    private int round;

    public Player(String playerName, String deckFilename) throws IOException, URISyntaxException, CardException {
        this.name = playerName;
        this.health = MAX_HP;
        this.mana = 1;
        this.onHand = new ArrayList<>(MAX_CARD_IN_HAND);
        this.deck = new Deck(deckFilename);
        this.characterFieldCards = new ArrayList<>(MAX_CARD_IN_BOARD);
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
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

    public void addRound(){
        this.round++;
    }

    public void takeDamage(int damage) {
        if (this.health - damage < MIN_HP) {
            this.health = 0;
        } else {
            this.health -= damage;
        }
    }

    public void drawCard(int id) throws DeckException {
        this.onHand.add(this.deck.drawCard(id));

        /* jika kartu di tangan sudah lebih dari 5 maka buang 1 kartu acak */

        if (this.onHand.size() > MAX_CARD_IN_HAND) {
            Random r = new Random();
            int idx = r.nextInt(this.onHand.size());
            this.onHand.remove(idx);
        }
    }

    public void addToField(int field, Card card) throws CardException {
        if (card instanceof Character) {
            SummonedCharacter fieldCard = new SummonedCharacter((Character) card);
            characterFieldCards.set(field, fieldCard);
            discardCardOnHand(card);

        } else if (card instanceof Spell) {
            characterFieldCards.get(field).addActivable((Applicable) card);
            discardCardOnHand(card);

        } else {
            throw new CardException("Class is not recognized!");
        }
    }

    public void discardCardOnHand(Card card) {
        this.onHand.remove(card);
    }

    public void discardCharacterFieldCards(int field) {
        this.characterFieldCards.remove(field);
    }

    public void useManaForExp(int field, int mana) throws CardException, PlayerException {
        /* CATATAN KINAN: INI UNTUK APA YA?  */

        this.characterFieldCards.get(field).addExp(mana);
        this.useMana(mana);
    }

    public boolean hasEnoughMana(int mana) {
        return this.mana >= mana;
    }

    public boolean canDeploy(int field, Card card) throws CardException {
        if (card instanceof Character) {
            return hasEnoughMana(card.getMana()) && characterFieldCards.get(field) == null;

        } else if (card instanceof Spell) {
            return hasEnoughMana(card.getMana()) && characterFieldCards.get(field) != null;

        } else {
            throw new CardException("Class is not recognized!");
        }
    }

    public void resetMana() {
        this.mana = Math.min(this.round, MAX_MANA);
    }

    public void attackOpponentPlayer(SummonedCharacter characterFieldCard, Player opponentPlayer) {
        characterFieldCard.attackPlayer(opponentPlayer);
    }

    public void attackOpponentCard(SummonedCharacter characterFieldCard, SummonedCharacter opponentCharacterFieldCard, Player opponentPlayer) {
        characterFieldCard.attackCharacter(opponentCharacterFieldCard);
        /*if (opponentCharacterFieldCard.getHealth() <= 0) {
            opponentPlayer.discardCharacterFieldCards(opponentCharacterFieldCard.getField());
        }*/
    }
}
