package com.aetherwars.model.deck;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.CardException;
import com.aetherwars.util.CSVReader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Deck {
    private final List<Card> buffer;

    public Deck(String deckFilename) throws IOException, URISyntaxException, CardException {
        this.buffer = new ArrayList<>();

        String deckPath = String.format("data/%s", deckFilename);
        File deckCSVFile = new File(Objects.requireNonNull(Deck.class.getResource(deckPath)).toURI());
        CSVReader deckReader = new CSVReader(deckCSVFile, "\t");
        deckReader.setSkipHeader(true);
        List<String[]> deckRows = deckReader.read();
        for (String[] row : deckRows) {
            int id = Integer.parseInt(row[0]);
            this.addCard(CardDatabase.getCard(id));
        }
    }

    public void addCard(Card c) {
        this.buffer.add(c);
    }

    public void returnCardsToDeck(List<Card> remainCards) {
        this.buffer.addAll(remainCards);
    }

    public Card getCard() throws DeckException {
        if (this.buffer.size() > 0) {
            return this.buffer.remove(0);
        } else {
            throw new DeckException("Deck is empty!");
        }
    }

    public void printAll() {
        for (Card c : this.buffer) {
            System.out.println(c);
        }
    }

    public List<Card> getThreeCards() throws DeckException {
        List<Card> threeCards = new ArrayList<>();

        if (this.buffer.size() >= 3) {
            for (int i = 0; i < 3; i++) {
                threeCards.add(this.buffer.remove(0));
            }

        } else if (this.buffer.size() == 0) {
            throw new DeckException("Deck is empty!");

        } else {
            for (int i = 0; i < this.buffer.size(); i++) {
                threeCards.add(this.buffer.remove(0));
            }
        }

        return threeCards;
    }
}
