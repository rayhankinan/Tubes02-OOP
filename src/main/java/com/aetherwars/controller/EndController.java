package com.aetherwars.controller;

import com.aetherwars.model.player.Player;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;

public class EndController {
    @FXML
    private Text winnerName;

    @FXML
    private Pane main;

    public void showWinner(Player player) {
        Node parent = this.main.getParent();
        parent.toFront();
        this.winnerName.setText(player.getName());
    }
}