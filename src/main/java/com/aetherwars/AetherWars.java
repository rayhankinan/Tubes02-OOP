package com.aetherwars;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.aetherwars.model.card.*;
import com.aetherwars.model.card.Character;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.aetherwars.util.CSVReader;

public class AetherWars extends Application {
    private static final String CHARACTER_CSV_FILE_PATH = "card/data/character.csv";
    private static final String SPELL_POTION_FILE_PATH = "card/data/spell_ptn.csv";
    private static final String SPELL_LEVEL_FILE_PATH = "card/data/spell_lvl.csv";
    private static final String SPELL_MORPH_FILE_PATH = "card/data/spell_morph.csv";
    private static final String SPELL_SWAP_FILE_PATH = "card/data/spell_swap.csv";

    public void loadCards() throws IOException, URISyntaxException {
        File characterCSVFile = new File(getClass().getResource(CHARACTER_CSV_FILE_PATH).toURI());
        CSVReader characterReader = new CSVReader(characterCSVFile, "\t");
        characterReader.setSkipHeader(true);
        List<String[]> characterRows = characterReader.read();
        for (String[] row : characterRows) {
            Character c = new Character(Integer.parseInt(row[0]), row[1], Type.valueOf(row[2]), row[3], row[4], Integer.parseInt(row[5]), Integer.parseInt(row[6]), Integer.parseInt(row[7]), Integer.parseInt(row[8]), Integer.parseInt(row[9]));
            System.out.println(c);
        }
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
            text.setText("Minecraft: Aether Wars!");
        } catch (Exception e) {
            text.setText("Failed to load cards: " + e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
