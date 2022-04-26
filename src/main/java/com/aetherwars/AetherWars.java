package com.aetherwars;

import java.io.IOException;
import java.net.URISyntaxException;

import com.aetherwars.model.deck.Deck;
import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.CardException;
import com.aetherwars.controller.BoardController;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AetherWars extends Application {

    public void loadCards() throws IOException, URISyntaxException, CardException {
        CardDatabase.initialize();
    }

    public void loadDecks(String deckName) throws IOException, URISyntaxException, CardException {
        Deck deck = new Deck(deckName);

        deck.printAll();
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader boardLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/Board.fxml"));
            Parent root = boardLoader.load();
            Scene scene = new Scene(root, 1200, 800);

            stage.setTitle("Minecraft: Aether Wars");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
