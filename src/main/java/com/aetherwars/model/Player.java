package com.aetherwars.model;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.deck.Deck;
import com.aetherwars.model.card.character.SummonedCharacter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private static final int MAX_HP = 80;
    private static final int MAX_MANA = 10;
    private static final int MIN_HP = 0;
    private static final int MIN_MANA = 0;
    private static final int MAX_CARD_IN_HAND = 5;

    private final String name;
    private int health;
    private int mana;
    /*private List<Card> onHand;*/
    private Deck deck;

    /*private List<SummonedCharacter> character_zone;*/

    public Player(String playerName, String deckFilename) throws IOException, URISyntaxException, CardException {
        this.name = playerName;
        this.health = MAX_HP;
        this.mana = 1;
        /*this.onHand = new ArrayList<Card>(MAX_CARD_IN_HAND);*/
        this.deck = new Deck(deckFilename);

        /*this.character_zone = new ArrayList<SummonedCharacter>();*/

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

    /*public List<Card> getOnHand() {return this.onHand;}*/

    public Deck getDeck() {return this.deck;}

    public void useMana(int mana) throws Exception {
        if (this.mana - mana < MIN_MANA) {
            throw new Exception("Mana is not sufficient!");
        } else {
            this.mana -= mana;
        }
    }

    public void heal(int health) {
        if (this.health + health > MAX_HP) {
            this.health = MAX_HP;
        } else {
            this.health += health;
        }
    }

    public void takeDamage(int damage) {
        if (this.health - damage < MIN_HP) {
            this.health = 0;
        } else {
            this.health -= damage;
        }
    }

    public void drawCard() {
/*        try {
            this.onHand.add(this.deck.drawCard());
        } catch (Exception e) {
            //deck kosong
            e.printStackTrace();
        }
        if (this.onHand.size() > MAX_CARD_IN_HAND) {
            Random r = new Random();
            int idx = r.nextInt(this.onHand.size());
            this.onHand.remove(idx);
        }*/
    }

    public void takeTurn() {
        /* TODO */
    }
}
