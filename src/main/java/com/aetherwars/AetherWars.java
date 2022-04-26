package com.aetherwars;

import java.io.IOException;
import java.net.URISyntaxException;

import com.aetherwars.model.deck.Deck;
import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.CardException;

import javafx.application.Application;
import javafx.scene.Group;
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
        Text text = new Text();
        text.setText("Loading...");
        text.setX(50);
        text.setY(50);

        Group root = new Group();
        root.getChildren().add(text);

        Scene scene = new Scene(root, 1280, 720);

        stage.setTitle("Minecraft: Aether Wars");
        stage.setScene(scene);
        stage.show();

        try {
            this.loadCards();
            this.loadDecks("deck_1.csv");
            text.setText("Minecraft: Aether Wars!");
        } catch (Exception e) {
            text.setText("Failed to load cards: " + e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
