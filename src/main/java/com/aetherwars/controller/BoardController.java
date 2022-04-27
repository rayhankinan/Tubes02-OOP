package com.aetherwars.controller;

import com.aetherwars.model.card.*;
import com.aetherwars.model.player.*;
import com.aetherwars.model.card.character.Character;
import com.aetherwars.model.card.character.SummonedCharacter;
import com.aetherwars.model.card.spell.level.Level;
import com.aetherwars.model.card.spell.morph.Morph;
import com.aetherwars.model.card.spell.potion.Potion;
import com.aetherwars.model.card.spell.swap.Swap;
import com.aetherwars.model.deck.*;
import com.aetherwars.model.board.Board;
import javafx.scene.input.*;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.EventHandler;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.image.*;

import java.io.IOException;
import java.util.Objects;

public class BoardController {
    @FXML
    private Button nextPhase;

    @FXML
    private Text cardDetailName, cardDetailAtk, cardDetailHP, cardDetailLevel, cardDetailExp, cardDetailType, cardDetailDesc, deckCapacity, manaCapacity, turn;

    @FXML
    private ImageView cardDetailImage;

    @FXML
    private Rectangle phaseDrawBlock, phasePlanBlock, phaseAttackBlock, phaseEndBlock;

    @FXML
    private Circle discardCard;

    @FXML
    private Pane drawPane, handCard1, handCard2, handCard3, handCard4, handCard5, cardDetail, fieldA1, fieldB1, fieldC1, fieldD1, fieldE1;

    private String currentPhase = "";

    private Player currentPlayer;

    private int currentTurn = 1;

    private Board board;

    public static final DataFormat cardData = new DataFormat("Card Format");


    @FXML
    public void initialize() {
        this.setCardDetailOpacity(0.0);
        this.phaseDrawBlock.setFill(Color.ORANGE);
        this.currentPhase = "DRAW";
        this.turn.setText("Turn " + Integer.toString(this.currentTurn));
        Pane[] startSlots = { handCard1, handCard2, handCard3, handCard4, handCard5, fieldA1, fieldB1, fieldC1, fieldD1, fieldE1 };

        // JANGAN LUPA: 5 line di bawah diapus, ini cuma buat testing
        this.fieldA1.setOpacity(0.50);
        this.fieldB1.setOpacity(0.50);
        this.fieldC1.setOpacity(0.50);
        this.fieldD1.setOpacity(0.50);
        this.fieldE1.setOpacity(0.50);

        // set up mouse hover & drag-n-drop event handlers
        for (int i = 0; i < startSlots.length; i++){
            this.setUpHover(startSlots[i]);
            this.setUpDragAndDrop(startSlots[i]);
        }
        this.setUpDiscardDrop(this.discardCard);

        // set up button handler
        EventHandler<ActionEvent> buttonEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                setPhase(currentPhase);
            }
        };
        this.nextPhase.setOnAction(buttonEvent);


        // JANGAN LUPA: set di bawah ini
        // this.currentPlayer = isi dari board !! <33;
        // this.deckCapacity.setText(brapa / brapa dari punya player <33);
        // this.manaCapacity.setText(brapa / brapa dari punya player <33);
    }

    /* Hover event */
    public void setUpHover(Pane handCard) {
        handCard.setOnMouseEntered(mouseEvent -> {
            if (handCard.getOpacity() == 1.00) {
                this.cardDetailName.setText(handCard.getId());
                this.setCardDetailOpacity(1.0);
            }
        });
        handCard.setOnMouseExited(mouseEvent -> this.setCardDetailOpacity(0.0) );
    }

    /* Drag and Drop event */
    public void setUpDragAndDrop(Pane slot) {
        // CHECK IF SLOT IS EMPTY OR NOT + summoned character

        // case when not empty: set for drag
        if (slot.getOpacity() == 1.00) { // JANGAN LUPA: ini ganti pake cek if slot is not empty (ada card yg bisa dipindah ke slot lain ato ngga)
            slot.setOnDragDetected(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    Dragboard db = slot.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.put(cardData, "MAMAMA"); // JANGAN LUPA: nanti disini yg "MAMAMA" itu diisi pake Card yg mo ditransfer
                    db.setContent(content);
                    event.consume();
                }
            });
            slot.setOnDragDone(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    if (event.getTransferMode() == TransferMode.MOVE) {
                        // JANGAN LUPA: nanti di line ini kosongin Card yg udah ditransfer
                        slot.setOnDragDetected(null);
                        slot.setOnDragDone(null);
                        slot.setOpacity(0.50); // JANGAN LUPA: line ini nanti diapus, skrg cm buat testing aja
                        setUpDragAndDrop(slot);
                    }
                    event.consume();
                }
            });
        }
        // case when empty: set for drop
        else {
            slot.setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    if (event.getGestureSource() != slot && event.getDragboard().hasContent(cardData)) {
                        Object content = event.getDragboard().getContent(cardData);
                        // if (!(content instanceof SummonedCharacter)) return; // JANGAN LUPA: ini cek lagi critanya biar yg bs di drop ke field itu cm summoned character aaokaok :V <3
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                }
            });
            slot.setOnDragDropped(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    boolean success = false;
                    if (event.getDragboard().hasContent(cardData)) {
                        success = true;
                    }
                    event.setDropCompleted(success);
                    // Card content = (Card) event.getDragboard().getContent(cardData); // JANGAN LUPA: ini critanya ngambil card dari contentnya tp tunggu contentnya bener dulu hehe
                    // JANGAN LUPA: di line ini, set slot pake Card yg ditransfer (pake content yg di line atas ini persis)
                    slot.setOnDragOver(null);
                    slot.setOnDragDropped(null);
                    slot.setOpacity(1.00); // JANGAN LUPA: line ini nanti diapus, skrg cm buat testing aja
                    setUpDragAndDrop(slot);
                    event.consume();
                }
            });
        }
    }

    /* Drop event for discarding Cards only */
    public void setUpDiscardDrop(Circle discardCard) {
        discardCard.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != discardCard && event.getDragboard().hasContent(cardData)) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            }
        });
        discardCard.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                boolean success = false;
                if (event.getDragboard().hasContent(cardData)) {
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }

    /* Sets card detail visibility */
    public void setCardDetailOpacity(double opacity) {
        this.cardDetail.setOpacity(opacity);
    }

    /* Board setter */
    public void setBoard(Board board){
        this.board = board;
    }

    /* Sets nxt phase */
    public void setPhase(String currentPhase) {
        if (currentPhase == "END") {
            this.phaseEndBlock.setFill(Color.valueOf("#E7E7E7"));
            this.phaseDrawBlock.setFill(Color.ORANGE);
            this.currentPhase = "DRAW";
            this.currentTurn += 1; // if end --> CURRENT TURN +1 JANGAN LUPA !!! trs set this.turn jd this.currentTurn
        }
        else if (currentPhase == "ATTACK") {
            this.phaseAttackBlock.setFill(Color.valueOf("#E7E7E7"));
            this.phaseEndBlock.setFill(Color.ORANGE);
            this.currentPhase = "END";
        }
        else if (currentPhase == "PLAN") {
            this.phasePlanBlock.setFill(Color.valueOf("#E7E7E7"));
            this.phaseAttackBlock.setFill(Color.ORANGE);
            this.currentPhase = "ATTACK";
        }
        else {
            this.phaseDrawBlock.setFill(Color.valueOf("#E7E7E7"));
            this.phasePlanBlock.setFill(Color.ORANGE);
            this.currentPhase = "PLAN";
        }
        // keknya disini taro kek semacam fungsi yang ngeupdate the whole board state gitu(?) kyk turnnya ato smcmnya
    }

    /* Display detailed card
    * only summoned character can be put in the field*/
    public void displayCard(SummonedCharacter character){
        Image newImg = new Image(Objects.requireNonNull(Character.class.getResource("/image" + character.getImagepath())).toString());
        this.cardDetailName.setText(character.getName());
        this.cardDetailImage.setImage(newImg);
        this.cardDetailAtk.setText(Integer.toString(character.getTotalAttack()));
        this.cardDetailHP.setText(Integer.toString(character.getTotalHealth()));
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
        this.cardDetailAtk.setText("+" + Integer.toString(potion.getTempAttack()));
        this.cardDetailHP.setText("+" + Integer.toString(potion.getTempHealth()));
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



}
