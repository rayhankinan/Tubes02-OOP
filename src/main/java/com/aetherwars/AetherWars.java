package com.aetherwars;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AetherWars extends Application {
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
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
