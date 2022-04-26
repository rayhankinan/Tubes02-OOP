package com.aetherwars.controller;

import com.aetherwars.model.card.*;
import com.aetherwars.model.card.character.Character;
import com.aetherwars.model.player.*;
import com.aetherwars.model.card.character.*;
import com.aetherwars.model.card.spell.level.Level;
import com.aetherwars.model.card.spell.morph.Morph;
import com.aetherwars.model.card.spell.potion.Potion;
import com.aetherwars.model.card.spell.swap.Swap;
import com.aetherwars.model.deck.*;
import com.aetherwars.model.board.Board;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.EventHandler;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.image.*;

import java.io.IOException;
import java.util.Objects;

public class BoardController {
    @FXML
    private Button card;

    @FXML
    private Text cardDetailName, cardDetailAtk, cardDetailHP, cardDetailLevel, cardDetailExp, cardDetailType, cardDetailDesc;

    @FXML
    private ImageView cardDetailImage;

    @FXML
    private Rectangle phaseDrawBlock, phasePlanBlock, phaseAttackBlock, phaseEndBlock, cardDetailBox1, cardDetailBox2;

    @FXML
    private Text deckCapacity, manaCapacity;

    @FXML
    private Pane drawPane, handCard1, handCard2, handCard3, handCard4, handCard5, cardDetailBox3;

    @FXML
    private Text turn;

    private String currentPhase = "";

    private Player currentPlayer;

    private int currentTurn;

    private Board board;


    @FXML
    public void initialize() {
        this.setCardDetailOpacity(0.0);
        this.phaseDrawBlock.setFill(Color.ORANGE);
        this.currentPhase = "DRAW";

        // this.currentPlayer = isi dari board !! <33;
        // this.deckCapacity.setText(brapa / brapa dari punya player <33);
        // this.manaCapacity.setText(brapa / brapa dari punya player <33);
        this.turn.setText(Integer.toString(this.currentTurn));


        // set up mouse hover event listener
        this.setUpHover(this.handCard1, "Card 1");
        this.setUpHover(this.handCard2, "Card 2");
        this.setUpHover(this.handCard3, "Card 3");
        this.setUpHover(this.handCard4, "Card 4");
        this.setUpHover(this.handCard5, "Card 5");
    }


    public void setUpHover(Pane handCard, String name) {
        handCard.setOnMouseEntered(mouseEvent -> {
            this.cardDetailName.setText(name);
            this.setCardDetailOpacity(1.0);
        });
        handCard.setOnMouseExited(mouseEvent -> this.setCardDetailOpacity(0.0) );
    }
    public void setCardDetailOpacity(double opacity) {
        this.cardDetailBox1.setOpacity(opacity);
        this.cardDetailBox2.setOpacity(opacity);
        this.cardDetailBox3.setOpacity(opacity);
        this.cardDetailDesc.setOpacity(opacity);
        this.cardDetailExp.setOpacity(opacity);
        this.cardDetailType.setOpacity(opacity);
        this.cardDetailLevel.setOpacity(opacity);
        this.cardDetailHP.setOpacity(opacity);
        this.cardDetailAtk.setOpacity(opacity);
        this.cardDetailImage.setOpacity(opacity);
        this.cardDetailName.setOpacity(opacity);
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public void nextPhase() {
        // if end --> CURRENT TURN +1 janlup !!! trs set this.turn jd this.currentTurn
    }

    /* Display detailed card
    * only summoned character can be put in the field*/
    public void displayCard(SummonedCharacter character){
        Image newImg = new Image(Objects.requireNonNull(Character.class.getResource("/image" + character.getImagepath())).toString());
        this.cardDetailName.setText(character.getName());
        this.cardDetailImage.setImage(newImg);
        this.cardDetailAtk.setText(Integer.toString(character.getAttack()));
        this.cardDetailHP.setText(Integer.toString(character.getHealth()));
        this.cardDetailLevel.setText(Integer.toString(character.getLevel()));
        this.cardDetailType.setText(Objects.toString(character.getType()));
        this.cardDetailExp.setText(Objects.toString(character.getExp()));
        this.cardDetailDesc.setText(character.getDescription());
    }

    /* Display detailed card for Level*/
    public void displayCard(Level level) {
        Image newImg = new Image(Objects.requireNonNull(Character.class.getResource("/image" + level.getImagepath())).toString());
        this.cardDetailName.setText(level.getName());
        this.cardDetailImage.setImage(newImg);
        this.cardDetailAtk.setText("-");
        this.cardDetailHP.setText("-");
        this.cardDetailLevel.setText(Integer.toString(level.getLevel()));
        this.cardDetailType.setText("Spell");
        this.cardDetailExp.setText("-");
        this.cardDetailDesc.setText(level.getDescription());
    }

    /* Display detailed card for Morph*/
    public void displayCard(Morph morph) {
        Image newImg = new Image(Objects.requireNonNull(Character.class.getResource("/image" + morph.getImagepath())).toString());
        this.cardDetailName.setText(morph.getName());
        this.cardDetailImage.setImage(newImg);
        this.cardDetailAtk.setText("-");
        this.cardDetailHP.setText("-");
        this.cardDetailLevel.setText("-");
        this.cardDetailType.setText("Morph Spell");
        this.cardDetailExp.setText("-");
        this.cardDetailDesc.setText(morph.getDescription());
    }

    /* Display detailed card for Potion*/
    public void displayCard(Potion potion) {
        Image newImg = new Image(Objects.requireNonNull(Character.class.getResource("/image" + potion.getImagepath())).toString());
        this.cardDetailName.setText(potion.getName());
        this.cardDetailImage.setImage(newImg);
        this.cardDetailAtk.setText("+" + Integer.toString(potion.getAttack()));
        this.cardDetailHP.setText("+" + Integer.toString(potion.getHealth()));
        this.cardDetailLevel.setText("-");
        this.cardDetailType.setText("Spell");
        this.cardDetailExp.setText("-");
        this.cardDetailDesc.setText(potion.getDescription());
    }

    /* Display detailed card for Swap*/
    public void displayCard(Swap swap) {
        Image newImg = new Image(Objects.requireNonNull(Character.class.getResource("/image" + swap.getImagepath())).toString());
        this.cardDetailName.setText(swap.getName());
        this.cardDetailImage.setImage(newImg);
        this.cardDetailAtk.setText("-");
        this.cardDetailHP.setText("-");
        this.cardDetailLevel.setText("-");
        this.cardDetailType.setText("Swap Spell");
        this.cardDetailExp.setText("-");
        this.cardDetailDesc.setText(swap.getDescription());
    }

    /*
     * Call the relevance method based on the instance of card */
    public void displayCard(Card card) {
        if (card instanceof Character){
            this.displayCard((Character) card);
        } else if (card instanceof Level){
            this.displayCard((Level) card);
        } else if (card instanceof Potion){
            this.displayCard((Potion) card);
        } else if (card instanceof Morph){
            this.displayCard((Morph) card);
        } else if (card instanceof Swap) {
            this.displayCard((Swap) card);
        }
    }

    /* Display draw card */
    public void displayDrawCard(){
        try {
            FXMLLoader drawLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/Draw.fxml"));
            this.drawPane = drawLoader.load();
            DrawController drawController = drawLoader.getController();
            // TODO : Draw card from player
            // drawController.drawCard(this.player siapa yaaaa :3);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /* Display field card */
    public void displayFieldCard(String field, SummonedCharacter character){

    }

    /* Display current player hand card */
    public void displayHandCard(Card card) {

    }

    /* hover event */
    public void hoverEvent(Card card, Pane pane){

    }



}
