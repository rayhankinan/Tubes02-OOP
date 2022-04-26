package com.aetherwars.controller;

import com.aetherwars.model.card.*;
import com.aetherwars.model.player.Player;
import com.aetherwars.model.deck.Deck;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class DrawController {
    @FXML
    private Pane drawCard1, drawCard2, drawCard3;

    @FXML
    public void initialize() {

    }

    public void drawCard(Player player) {
        try {
            Deck deck = player.getDeck();
            Card card1 = deck.getCard();
            Card card2 = deck.getCard();
            Card card3 = deck.getCard();
            insertCard(drawCard1, card1);
            insertCard(drawCard2, card2);
            insertCard(drawCard3, card3);
        }
        catch (CardException e){
            System.out.println(e);
        }
    }

    public void insertCard(Pane slot, Card card) {
        try {
            FXMLLoader cardLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/Card.fxml"));
            slot = cardLoader.load();
            CardController cardController = cardLoader.getController();
            cardController.setCard(card);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
