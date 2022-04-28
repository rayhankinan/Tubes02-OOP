package com.aetherwars.controller;

import com.aetherwars.model.board.Phase;
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
import javafx.geometry.Insets;
import javafx.scene.input.*;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.EventHandler;

import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.image.*;
import jdk.nashorn.internal.codegen.FieldObjectCreator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class BoardController {
    @FXML
    private Button nextPhase;

    @FXML
    private Text cardDetailName, cardDetailAtk, cardDetailHP, cardDetailLevel, cardDetailExp, cardDetailType, cardDetailDesc, deckCapacity, manaCapacity, round, playerOneName, playerTwoName;

    @FXML
    private ImageView cardDetailImage;

    @FXML
    private Rectangle phaseDrawBlock, phasePlanBlock, phaseAttackBlock, phaseEndBlock;

    @FXML
    private Circle discardCard;

    @FXML
    private Pane drawPane, handCard1, handCard2, handCard3, handCard4, handCard5, cardDetail, fieldA1, fieldB1, fieldC1, fieldD1, fieldE1, fieldA2, fieldB2, fieldC2, fieldD2, fieldE2;

    private CardController handCardControl1, handCardControl2, handCardControl3, handCardControl4, handCardControl5;
    private Board board;

    private Pane slotClicked = null;

    private Player playerOne, playerTwo;

    public static final DataFormat cardData = new DataFormat("Card Format");

    @FXML
    public void initialize() {
        // initial set up
        try {
            CardDatabase.initialize();
            this.board = new Board("yaya", "YOYO", "deck_1.csv", "deck_1.csv");
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(this.board.getPlayer2());
        this.playerOne = this.board.getPlayer1();
        this.playerTwo = this.board.getPlayer2();
        this.playerOneName.setText(this.playerOne.getName());
        this.playerTwoName.setText(this.playerTwo.getName());

        Pane[] slots = { handCard1, handCard2, handCard3, handCard4, handCard5, fieldA1, fieldB1, fieldC1, fieldD1, fieldE1, fieldA2, fieldB2, fieldC2, fieldD2, fieldE2 };
        // set up mouse hover & drag-n-drop event handlers
        for (int i = 0; i < slots.length; i++){
            this.setUpHover(slots[i]);
            this.setUpDrag(slots[i]);
            this.setUpClick(slots[i]);
        }
        this.setUpDiscardDrop(this.discardCard);

        // set up button handler
        EventHandler<ActionEvent> buttonEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                setPhase(board.getPhase().toString());
            }
        };
        this.nextPhase.setOnAction(buttonEvent);

        // TODO : BLOM BISA INI
        // this.displayDrawCard(this.board.getCurrentPlayer());

        // JANGAN LUPA set di bawah ini
        // this.deckCapacity.setText(brapa / brapa dari punya player <33);
        // this.manaCapacity.setText(brapa / brapa dari punya player <33);

        // testing aja, JANGAN LUPA apus yg di bawah ini
        Level level;
        try {
            level = new Level(403, "OYA? BODO", "BAPAK KAU", "Bottle O' Enchanting.png", 9, 3);
            System.out.println(level);
            this.setHandOrFieldCard(this.handCard1, level);
        }
        catch (Exception e) {
            System.out.println("masuk sini");
            System.out.println(e);
        }
    }

    /* Board setter */
    public void setBoard(Board board){
        this.board = board;
    }

    /* Sets next phase */
    public void setPhase(String currentPhase) {
        this.board.nextPhase();
        // JANGAN LUPA line di bawah ini nunggu get round dari player, karena round itu disimpennya di player (jdnya pake round bukan turn :V !!)
        // this.round.setText("Round " + Integer.toString(this.playerOne.getRound()));
        switch (this.board.getPhase()) {
            case DRAW:
                this.phaseEndBlock.setFill(Color.valueOf("#E7E7E7"));
                this.phaseDrawBlock.setFill(Color.ORANGE);
                try {
                    this.board.switchTurn();
                }
                catch (DeckException e) {
                    System.out.println(e);
                }
                // JANGAN LUPA: fungsi buat ganti HANDCARD
                return;
            case PLANNING:
                this.phaseDrawBlock.setFill(Color.valueOf("#E7E7E7"));
                this.phasePlanBlock.setFill(Color.ORANGE);
                break;
            case ATTACK:
                this.phasePlanBlock.setFill(Color.valueOf("#E7E7E7"));
                this.phaseAttackBlock.setFill(Color.ORANGE);
                break;
            case END:
                this.phaseAttackBlock.setFill(Color.valueOf("#E7E7E7"));
                this.phaseEndBlock.setFill(Color.ORANGE);
                // JANGAN LUPA: kalo sempet tambahin pop up kek 'your turn is up, klik next phase biar pemaen brikutny bs gerak'
                break;
        }
    }

    /* Display detailed card for Level*/
    public void displayCard(Level level) {
        Image newImg = new Image("/com/aetherwars/model/card/spell/level/image/" + level.getImagepath().toString());
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
        Image newImg = new Image("/com/aetherwars/model/card/spell/morph/image/" + morph.getImagepath().toString());
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
        Image newImg = new Image("/com/aetherwars/model/card/spell/potion/image/" + potion.getImagepath().toString());
        this.cardDetailName.setText(potion.getName());
        this.cardDetailImage.setImage(newImg);
        this.cardDetailAtk.setText("+" + Integer.toString(potion.getTempAttack()));
        this.cardDetailHP.setText("+" + Integer.toString(potion.getTempHealth()));
        this.cardDetailLevel.setText("-");
        this.cardDetailType.setText("Spell");
        this.cardDetailExp.setText("-");
        this.cardDetailDesc.setText(potion.getDescription());
    }

    /* Display detailed card
     * only summoned character can be put in the field*/
    public void displayCard(SummonedCharacter character){
        Image newImg = new Image("/com/aetherwars/model/card/character/image/" + character.getImagepath().toString());
        this.cardDetailName.setText(character.getName());
        this.cardDetailImage.setImage(newImg);
        this.cardDetailAtk.setText(Integer.toString(character.getTotalAttack()));
        this.cardDetailHP.setText(Integer.toString(character.getTotalHealth()));
        this.cardDetailLevel.setText(Integer.toString(character.getLevel()));
        this.cardDetailType.setText(Objects.toString(character.getType()));
        this.cardDetailExp.setText(Objects.toString(character.getExp()));
        this.cardDetailDesc.setText(character.getDescription());
    }

    /* Display detailed card for Swap*/
    public void displayCard(Swap swap) {
        Image newImg = new Image("/com/aetherwars/model/card/spell/swap/image/" + swap.getImagepath().toString());
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
            // drawController.drawCard(this.board.getCurrentPlayer());

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /* Set hand/field card pane */
    public void setHandOrFieldCard(Pane slot, Card card) throws Exception {
        if (slot.getId().equals("field1") || slot.getId().equals("field2")) {
            SummonedCharacter character = (SummonedCharacter) card;
            FieldCardController fieldCardControl;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/FieldCard.fxml"));
            Pane newLoadedPane = loader.load();
            slot.getChildren().add(newLoadedPane);
            fieldCardControl = loader.getController();
            fieldCardControl.setFieldCard(character);
            slot.setUserData(card);
        }
        else {
            CardController handCardControl;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/Card.fxml"));
            Pane newLoadedPane = loader.load();
            slot.getChildren().add(newLoadedPane);
            handCardControl = loader.getController();
            handCardControl.setCard(card);
            slot.setUserData(card);
        }
    }

    /* EVENT HANDLERS */
    /* Disable & Enabled Handlers */
    public boolean slotEnabled(Pane slot) {
        // make sure events only applicable to current player's buttons
        if (slot.getId().equals("field1") && board.getTurn() != 1) {
            return false;
        }
        if (slot.getId().equals("field2") && board.getTurn() != 2) {
            return false;
        }
        return true;
    }

    /* Hover event */
    public void setUpHover(Pane slot) {
        slot.setOnMouseEntered(mouseEvent -> {
            if (slotEnabled(slot)) {
                if (slot.getUserData() != null) {
                    this.displayCard((Card) slot.getUserData());
                    this.setCardDetailOpacity(1.0);
                }
            }
        });
        slot.setOnMouseExited(mouseEvent -> this.setCardDetailOpacity(0.0) );
    }

    /* Drag and Drop event */
    public void setUpDrag(Pane slot) {
        slot.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (slotEnabled(slot) && board.getPhase() == Phase.PLANNING) {
                    if (slot.getUserData() != null) {
                        Dragboard db = slot.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        content.put(cardData, "filled");
                        db.setContent(content);
                        event.consume();
                    }
                }
            }
        });
        slot.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (slotEnabled(slot) && board.getPhase() == Phase.PLANNING) {
                    if (slot.getUserData() != null) {
                        if (event.getTransferMode() == TransferMode.MOVE) {
                            slot.getChildren().clear();
                            slot.setUserData(null);
                            slot.setBackground(new Background(new BackgroundFill(Color.valueOf("E7E7E7"), CornerRadii.EMPTY, Insets.EMPTY)));
                        }
                        event.consume();
                    }
                }
            }
        });
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

    public void setUpClick(Pane slot) {
        slot.setOnMouseClicked(mouseEvent -> {
            // on click behavior for PLANNING phase
            if (this.board.getPhase() == Phase.PLANNING) {
                if (slot.getId().equals("field1")) {
                    if (board.getTurn() != 1){
                        return;
                    }
                }
                if (slot.getId().equals("field2")) {
                    if (board.getTurn() != 2){
                        return;
                    }
                }
                if (this.slotClicked != null) {
                    if (slot != this.slotClicked && !slot.getId().equals("field1") && !slot.getId().equals("field2")) {
                        return;
                    }
                    // set on click
                    if (this.slotClicked == slot){
                        slot.setBackground(new Background(new BackgroundFill(Color.valueOf("E7E7E7"), CornerRadii.EMPTY, Insets.EMPTY)));
                        this.slotClicked = null;
                    }
                    else if (slot.getUserData() == null) {
                        try {
                            this.setHandOrFieldCard(slot, (Card) this.slotClicked.getUserData());
                            this.slotClicked.setBackground(new Background(new BackgroundFill(Color.valueOf("E7E7E7"), CornerRadii.EMPTY, Insets.EMPTY)));
                            this.slotClicked.getChildren().clear();
                            this.slotClicked.setUserData(null);
                            this.slotClicked = null;
                        }
                        catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    else {
                        System.out.println("berhasil");
                        // TODO : taro efek kartu ke karakter di board disini
                    }
                }
                else {
                    if ((slot.getId().equals("field1")) || (slot.getId().equals("field2")) || slot.getUserData() == null) {
                        return;
                    }
                    this.slotClicked = slot;
                    slot.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }

            // on click behavior for ATTACK phase
            else if (this.board.getPhase() == Phase.ATTACK) {
                if (!(slot.getId().equals("field1") || slot.getId().equals("field2"))) {
                    return;
                }
                if (slot.getId().equals("field1")) {
                    if (this.slotClicked == null) {
                        if (board.getTurn() != 1){
                            return;
                        }
                    }
                    else {
                        if (this.slotClicked.getId().equals("field1") && this.slotClicked != slot){
                            return;
                        }
                    }
                }
                if (slot.getId().equals("field2")) {
                    if (this.slotClicked == null) {
                        if (board.getTurn() != 2){
                            return;
                        }
                    }
                    else {
                        if (this.slotClicked.getId().equals("field2") && this.slotClicked != slot){
                            return;
                        }
                    }
                }
                // set on click
                if (this.slotClicked == slot){
                    slot.setBackground(new Background(new BackgroundFill(Color.valueOf("E7E7E7"), CornerRadii.EMPTY, Insets.EMPTY)));
                    this.slotClicked = null;
                }
                else if (this.slotClicked != null) {
                    this.slotClicked.setBackground(new Background(new BackgroundFill(Color.valueOf("E7E7E7"), CornerRadii.EMPTY, Insets.EMPTY)));
                    // TODO : attack function taro disini
                    this.slotClicked = null;
                }
                else {
                    this.slotClicked = slot;
                    slot.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        });
    }
}
