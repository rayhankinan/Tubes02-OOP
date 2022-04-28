package com.aetherwars.controller;

import com.aetherwars.model.board.Phase;
import com.aetherwars.model.card.*;
import com.aetherwars.model.deck.DeckException;
import com.aetherwars.model.player.Player;
import com.aetherwars.model.deck.Deck;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawController {
    @FXML
    private Pane main, drawCard1, drawCard2, drawCard3;

    @FXML
    public void initialize() {}

    /* Sets card detail visibility */
    public void setPaneOpacity(double opacity) {
        this.main.setOpacity(opacity);
    }

    public void drawCard(Player player) {
        try {
            this.setPaneOpacity(1.00);
            Node parent = this.main.getParent();
            parent.toFront();
            Deck deck = player.getDeck();
            Card card1 = deck.getCard();
            Card card2 = deck.getCard();
            Card card3 = deck.getCard();
            this.setUpClick(drawCard1, card1, card2, card3, player);
            this.setUpClick(drawCard2, card2, card1, card3, player);
            this.setUpClick(drawCard3, card3, card2, card1, player);
            insertCard(drawCard1, card1);
            insertCard(drawCard2, card2);
            insertCard(drawCard3, card3);
        }
        catch (DeckException e){
            System.out.println(e);
        }
    }

    public void insertCard(Pane slot, Card card) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/Card.fxml"));
            Pane newLoadedPane = loader.load();
            slot.getChildren().add(newLoadedPane);
            CardController cardController = loader.getController();
            cardController.setCard(card);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public void setUpClick(Pane slot, Card card, Card remain1, Card remain2, Player player) {
        slot.setOnMouseClicked(mouseEvent -> {
            List<Card> remain = new ArrayList<>();
            remain.add(remain1);
            remain.add(remain2);
            // on click behavior
            try {
                player.drawCard(card, remain);
                setPaneOpacity(0.0);
            }
            catch (DeckException e) {
                System.out.println(e);
            }
            Node parent = this.main.getParent();
            parent.toBack();
        });
    }
}
