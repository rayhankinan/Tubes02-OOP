package com.aetherwars.model;

import com.aetherwars.model.card.Card;
import com.aetherwars.model.card.CardException;

import java.util.List;
import java.util.ArrayList;

public class Deck {
    private final List<Card> buffer;
    private final List<Card> temporaryChoice;
    public boolean deckEmpty;

    public Deck() {
        this.buffer = new ArrayList<>();
        this.temporaryChoice = new ArrayList<>();
        buildDeck();
        this.deckEmpty = false;
    }

    public void buildDeck() {
        /*TODO*/
    }

    public void addCard(Card c) {
        this.buffer.add(c);
    }

    public Card getCard() throws CardException {
        if (this.buffer.size() > 0) {
            return this.buffer.remove(0);
        } else {
            throw new CardException("Deck is empty!");
        }
    }

    public void printAll() {
        for (Card c : this.buffer) {
            System.out.println(c);
        }
    }

    public Card drawCard() throws Exception{
        //loop 3 times
        for (int i = 0; i < 3; i++) {
            if (this.buffer.size() > 0) {
                this.temporaryChoice.add(this.buffer.remove(0));
            }
            else{
                deckEmpty = true;
                throw new Exception("Deck is empty!");
            }
        }
        //bingung bagaimana cara mengambil choice card dari temporaryChoice
        //misalkan user memilih card tengah berarti card index ke 1
        int userChoice = 1;
        Card result = this.temporaryChoice.remove(userChoice);
        // kembalikan temporaryChoice ke buffer
        this.buffer.addAll(this.temporaryChoice);
        this.temporaryChoice.clear();
        return result;
    }

    public boolean isDeckEmpty(){
        return this.deckEmpty;
    }
}
