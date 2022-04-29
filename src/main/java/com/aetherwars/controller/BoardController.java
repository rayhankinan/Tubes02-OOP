package com.aetherwars.controller;

import com.aetherwars.model.board.Phase;
import com.aetherwars.model.card.*;
import com.aetherwars.model.card.spell.Spell;
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
import javafx.scene.control.ProgressBar;
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

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

public class BoardController {
    @FXML
    private Button nextPhase;

    @FXML
    private Text cardDetailName, cardDetailAtk, cardDetailHP, cardDetailLevel, cardDetailExp, cardDetailType, cardDetailDesc, deckCapacity, manaCapacity, round, playerOneName, playerTwoName;

    @FXML
    private ProgressBar progressBar1, progressBar2;
    @FXML
    private ImageView cardDetailImage;

    @FXML
    private Rectangle phaseDrawBlock, phasePlanBlock, phaseAttackBlock, phaseEndBlock;

    @FXML
    private Circle discardCard;

    @FXML
    private Pane drawPane, handCard1, handCard2, handCard3, handCard4, handCard5, cardDetail, fieldA1, fieldB1, fieldC1, fieldD1, fieldE1, fieldA2, fieldB2, fieldC2, fieldD2, fieldE2, playerOneImage, playerTwoImage;

    private Pane[] handSlots;
    private Pane[] field1Slots;
    private Pane[] field2Slots;

    private CardController handCardController;

    private FieldCardController fieldCardController;

    private DrawController drawController;

    private Board board;

    private Pane slotClicked = null;

    private Player playerOne, playerTwo;

    public static final DataFormat cardData = new DataFormat("Card Format");

    @FXML
    public void initialize() {
        // initial set up
        try {
            CardDatabase.initialize();
            this.board = new Board("yaya", "YOYO", "deck_2.csv", "deck_2.csv");
        }
        catch (Exception e) {
            System.out.println(e);
        }
        this.playerOne = this.board.getPlayer1();
        this.playerTwo = this.board.getPlayer2();
        this.playerOneName.setText(this.playerOne.getName());
        this.playerTwoName.setText(this.playerTwo.getName());

        Player curPlayer = this.board.getCurrentPlayer();
        this.deckCapacity.setText("Deck: " + curPlayer.getDeck().getSize() + "/" + curPlayer.getDeck().getMaxCapacity());
        this.manaCapacity.setText("Mana: " + curPlayer.getMana() + "/10");

        this.handSlots = new Pane[]{handCard1, handCard2, handCard3, handCard4, handCard5};
        this.field1Slots = new Pane[]{fieldA1, fieldB1, fieldC1, fieldD1, fieldE1};
        this.field2Slots = new Pane[]{fieldA2, fieldB2, fieldC2, fieldD2, fieldE2};

        // set up mouse hover & drag-n-drop event handlers
        this.setUpClick(this.playerOneImage);
        this.setUpClick(this.playerTwoImage);
        this.setEventHandlers(this.handSlots);
        this.setEventHandlers(this.field1Slots);
        this.setEventHandlers(this.field2Slots);
        this.setUpDiscardDrop(this.discardCard);

        // set up button handler
        EventHandler<ActionEvent> buttonEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                setPhase();
            }
        };

        this.nextPhase.setOnAction(buttonEvent);

        this.displayDrawCard();
        try {
         this.reloadHand(curPlayer);
        }
        catch (Exception e){
         System.out.println(e);
        }
    }

    /* Board setter */
    public void setBoard(Board board){
        this.board = board;
    }

    /* Load Hand */
    public void reloadHand(Player player) throws Exception {
        for (int i = 0; i < this.handSlots.length; i++) {
            this.handSlots[i].getChildren().clear();
        }
        for (int i = 0; i < player.getOnHand().size(); i++ ) {
            this.setHandCard(this.handSlots[i], player.getOnHand().get(i));
        }
    }

    public void reloadField() throws Exception {
        SummonedCharacter[] field1 = playerOne.getCharacterFieldCards();
        SummonedCharacter[] field2 = playerTwo.getCharacterFieldCards();
        for (int i = 0; i < field1.length; i++) {
            this.field1Slots[i].getChildren().clear();
            this.field2Slots[i].getChildren().clear();
            if (field1[i] != null) {
                this.setFieldCard(this.field1Slots[i], field1[i]);
            }
            if (field2[i] != null) {
                this.setFieldCard(this.field2Slots[i], field2[i]);
            }
        }
    }

    /* Sets next phase */
    public void setPhase() {
        this.board.nextPhase();
        Player curPlayer = this.board.getCurrentPlayer();
        this.updateCharacterFieldCardAttackAvailability(curPlayer.getCharacterFieldCards());
        this.deckCapacity.setText("Deck: " + curPlayer.getDeck().getSize() + "/" + curPlayer.getDeck().getMaxCapacity());
        this.manaCapacity.setText("Mana: " + curPlayer.getMana() + "/10");
        this.round.setText("Round " + curPlayer.getRound());
        switch (this.board.getPhase()) {
            case DRAW:
                this.phaseEndBlock.setFill(Color.valueOf("#E7E7E7"));
                this.phaseDrawBlock.setFill(Color.ORANGE);
                try {
                    this.reloadHand(curPlayer);
                    this.displayDrawCard();
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case PLANNING:
                try {
                    this.reloadHand(curPlayer);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                this.phaseDrawBlock.setFill(Color.valueOf("#E7E7E7"));
                this.phasePlanBlock.setFill(Color.ORANGE);
                break;
            case ATTACK:
                this.phasePlanBlock.setFill(Color.valueOf("#E7E7E7"));
                this.phaseAttackBlock.setFill(Color.ORANGE);
                break;
            case END:
                SummonedCharacter[] field1 = playerOne.getCharacterFieldCards();
                SummonedCharacter[] field2 = playerOne.getCharacterFieldCards();
                for (int i = 0; i < 5; i++) {
                    if (field1[i] != null) {
                        field1[i].decrementTemporaryDuration();
                    }
                    if (field2[i] != null) {
                        field2[i].decrementTemporaryDuration();
                    }
                }
                try {
                    reloadField();
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                this.phaseAttackBlock.setFill(Color.valueOf("#E7E7E7"));
                this.phaseEndBlock.setFill(Color.ORANGE);
                if (checkEndGame() != null) {
                    this.displayEndGame(checkEndGame());
                }
                try {
                    this.board.switchTurn();
                }
                catch (Exception e) {
                    System.out.println(e);
                }
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

    /*
    * Display detail character from hand */
    public void displayCard(Character character){
        Image newImg = new Image("/com/aetherwars/model/card/character/image/" + character.getImagepath().toString());
        this.cardDetailName.setText(character.getName());
        this.cardDetailImage.setImage(newImg);
        this.cardDetailAtk.setText(Integer.toString(character.getBaseAttack()));
        this.cardDetailHP.setText(Integer.toString(character.getBaseHealth()));
        this.cardDetailLevel.setText("1");
        this.cardDetailType.setText(Objects.toString(character.getType()));
        this.cardDetailExp.setText("0");
        this.cardDetailDesc.setText(character.getDescription());
    }

    /* Display detailed card
     * only summoned character can be put in the field*/
    public void displayCard(SummonedCharacter character){
        Image newImg = new Image("/com/aetherwars/model/card/character/image/" + character.getImagepath().toString());
        this.cardDetailName.setText(character.getName());
        this.cardDetailImage.setImage(newImg);
//        String atkText = "+" + Integer.toString(character.getCurrentAttack());
//        String hpText = "+" + Integer.toString(character.getCurrentHealth());
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
        if (card instanceof SummonedCharacter){
            this.displayCard((SummonedCharacter) card);
        } else if (card instanceof Character) {
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

    public void displayEndGame(Player player){
        try {
            FXMLLoader drawLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/End.fxml"));
            Pane newLoadedPane = drawLoader.load();
            drawPane.getChildren().add(newLoadedPane);
            EndController endController = drawLoader.getController();
            endController.showWinner(player);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    /* Display draw card */
    public void displayDrawCard(){
        try {
            FXMLLoader drawLoader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/Draw.fxml"));
            Pane newLoadedPane = drawLoader.load();
            drawPane.getChildren().add(newLoadedPane);
            drawController = drawLoader.getController();
            drawController.drawCard(this.board.getCurrentPlayer());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /* Set hand/field card pane */
    public void setHandCard(Pane slot, Card card) throws Exception {
        if (!slot.getId().equals("field1") && !slot.getId().equals("field2")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/Card.fxml"));
            Pane newLoadedPane = loader.load();
            slot.getChildren().add(newLoadedPane);
            handCardController = loader.getController();
            handCardController.setCard(card);
        }
    }

    public void setFieldCard(Pane slot, SummonedCharacter card) throws Exception {
        if (slot.getId().equals("field1") || slot.getId().equals("field2")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aetherwars/view/FieldCard.fxml"));
            Pane newLoadedPane = loader.load();
            slot.getChildren().add(newLoadedPane);
            fieldCardController = loader.getController();
            fieldCardController.setFieldCard(card);
        }
    }

    /* EVENT HANDLERS */
    public void setEventHandlers (Pane[] slots) {
        for (int i = 0; i < slots.length; i++){
            this.setUpHover(slots[i]);
            this.setUpDrag(slots[i]);
            this.setUpClick(slots[i]);
            slots[i].setUserData(i);
        }
    }

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

    public int getSlotNum(Pane slot) {
        return ((int) slot.getUserData());
    }

    public boolean slotEmpty(Pane slot) {
        int field = this.getSlotNum(slot);
        if (slot.getId().equals("field1") || slot.getId().equals("field2")) {
            if (this.board.getCurrentPlayer().getField(field) != null) {
                return true;
            }
        } else {
            if (this.board.getCurrentPlayer().getHand(field) != null) {
                return true;
            }
        }
        return false;
    }

    public Card getCard(Pane slot) {
        int field = this.getSlotNum(slot);
        if (slot.getId().equals("field1")) {
            if (this.playerOne.getField(field) != null) {
                return this.playerOne.getField(field);
            }
        } else if (slot.getId().equals("field2")) {
            if (this.playerTwo.getField(field) != null) {
                return this.playerTwo.getField(field);
            }
        } else {
            if (this.board.getCurrentPlayer().getHand(field) != null) {
                return this.board.getCurrentPlayer().getHand(field);
            }
        }
        return null;
    }

    public void throwCard (Pane slot) {
        int field = this.getSlotNum(slot);
        if (slot.getId().equals("field1") || slot.getId().equals("field2")) {
            this.board.getCurrentPlayer().discardCharacterFieldCards(field);
        } else {
            Card card = this.board.getCurrentPlayer().getHand(field);
            this.board.getCurrentPlayer().discardCardOnHand(card);
        }
    }

    public Player checkEndGame(){
        Player player1 = this.board.getCurrentPlayer();
        Player player2 = this.board.getOppositePlayer();
        if (player1.getHealth() <= 0 || player1.getDeck().getSize() <= 0 ){
            return player2;
        } else if (player2.getDeck().getSize() <= 0|| player2.getHealth() <= 0 ){
            return player1;
        } else {
            return null;
        }
    }

    /* Hover event */
    public void setUpHover(Pane slot) {
        slot.setOnMouseEntered(mouseEvent -> {
            if (this.getCard(slot) != null) {
                this.displayCard(this.getCard(slot));
                this.setCardDetailOpacity(1.0);
            }
        });
        slot.setOnMouseExited(mouseEvent -> this.setCardDetailOpacity(0.0) );
    }

    /* Drag and Drop event */
    public void setUpDrag(Pane slot) {
        slot.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (slotEnabled(slot) && board.getPhase() == Phase.PLANNING) {
                    if (getCard(slot) != null) {
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
                    if (getCard(slot) != null) {
                        if (event.getTransferMode() == TransferMode.MOVE) {
                            throwCard(slot);
                            slot.getChildren().clear();
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
                if (slot.getId().equals("playerImage")) {
                    return;
                }

                if (slot.getId().equals("field1")) {
                    if (board.getTurn() != 1) {
                        if (! (this.getCard(this.slotClicked) instanceof Spell)){
                            return;
                        }
                    }
                }
                if (slot.getId().equals("field2")) {
                    if (board.getTurn() != 2) {
                        if (! (this.getCard(this.slotClicked) instanceof Spell)){
                            return;
                        }
                    }
                }

                if (this.slotClicked != null) {
                    if (slot != this.slotClicked && !slot.getId().equals("field1") && !slot.getId().equals("field2")) {
                        return;
                    }
                    // set on click
                    if (this.slotClicked == slot) {
                        slot.setBackground(new Background(new BackgroundFill(Color.valueOf("E7E7E7"), CornerRadii.EMPTY, Insets.EMPTY)));
                        this.slotClicked = null;
                    } else if (this.getCard(slot) == null && !(this.getCard(this.slotClicked) instanceof Spell)) {
                        try {
                            this.board.getCurrentPlayer().addToField(this.getSlotNum(slot), this.getCard(this.slotClicked));
                            this.slotClicked.setBackground(new Background(new BackgroundFill(Color.valueOf("E7E7E7"), CornerRadii.EMPTY, Insets.EMPTY)));
                            this.slotClicked.getChildren().clear();
                            this.slotClicked = null;
                            this.setFieldCard(slot, (SummonedCharacter) this.getCard(slot));
                            reloadHand(this.board.getCurrentPlayer());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else {
                        try {
                            Player curPlayer = this.board.getCurrentPlayer();
                            Player oppPlayer = this.board.getOppositePlayer();
                            if (this.getCard(slot) != null) {
                                if (getCard(this.slotClicked) instanceof Spell) {
                                    // PLAYER 1 NARO SPELL KE FIELD 1 ato PLAYER 2 NARO SPELL KE FIELD 2
                                    if ((this.board.getTurn() == 1 && slot.getId().equals("field1")) || (this.board.getTurn() == 2 && slot.getId().equals("field2"))){
                                        curPlayer.addToField(getSlotNum(slot), getCard(this.slotClicked));
                                        // CUR PLAYER NARO SPELL KE OPP PLAYER
                                    } else {
                                        oppPlayer.addToField(getSlotNum(slot), getCard(this.slotClicked));
                                    }
                                    reloadField();
                                    reloadHand(this.board.getCurrentPlayer());
                                    this.slotClicked.setBackground(new Background(new BackgroundFill(Color.valueOf("E7E7E7"), CornerRadii.EMPTY, Insets.EMPTY)));
                                    this.slotClicked.getChildren().clear();
                                    this.slotClicked = null;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                } else {
                    if ((slot.getId().equals("field1")) || (slot.getId().equals("field2")) || this.getCard(slot) == null) {
                        return;
                    }
                    this.slotClicked = slot;
                    slot.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }

            // on click behavior for ATTACK phase
            else if (this.board.getPhase() == Phase.ATTACK) {
                if (this.slotClicked == null) {
                    if (((SummonedCharacter) getCard(slot)).getBattleAvailability() == 0) {
                        return;
                    }
                }
                if (slot.getId().equals("playerImage")) {
                    if (this.slotClicked == null || !this.board.getOppositePlayer().isFieldEmpty()) {
                        return;
                    } else {
                        // TODO : implement health player + ATTACK PLAYER
                        this.slotClicked.setBackground(new Background(new BackgroundFill(Color.valueOf("E7E7E7"), CornerRadii.EMPTY, Insets.EMPTY)));
                        // CEK KARTU YANG DIPEGANG SEKARANG
                        SummonedCharacter card = (SummonedCharacter) getCard(this.slotClicked);
                        Player curPlayer = this.board.getCurrentPlayer();
                        curPlayer.attackOpponentPlayer(card, this.board.getOppositePlayer());
//                        card.attackPlayer(this.board.getOppositePlayer());
                        int curHealth = this.board.getOppositePlayer().getHealth();
                        int maxHealth = this.board.getOppositePlayer().getMaxHp();
                        double healthBar = (double) curHealth / (double) maxHealth;
                        System.out.println(maxHealth);
                        System.out.println(healthBar);
                        if (this.board.getOppositePlayer() == playerTwo) {
                            progressBar2.setProgress(healthBar);
                        } else {
                            progressBar1.setProgress(healthBar);
                        }
                        this.slotClicked = null;
                    }
                }
                if (!(slot.getId().equals("field1") || slot.getId().equals("field2"))) {
                    return;
                }
                if (slot.getId().equals("field1")) {
                    if (this.slotClicked == null) {
                        if (board.getTurn() != 1) {
                            return;
                        }
                    } else {
                        if (this.slotClicked.getId().equals("field1") && this.slotClicked != slot) {
                            return;
                        }
                    }
                }
                if (slot.getId().equals("field2")) {
                    if (this.slotClicked == null) {
                        if (board.getTurn() != 2) {
                            return;
                        }
                    } else {
                        if (this.slotClicked.getId().equals("field2") && this.slotClicked != slot) {
                            return;
                        }
                    }
                }
                // set on click
                if (this.slotClicked == slot) {
                    slot.setBackground(new Background(new BackgroundFill(Color.valueOf("E7E7E7"), CornerRadii.EMPTY, Insets.EMPTY)));
                    this.slotClicked = null;
                } else if (this.slotClicked != null) {
                    // cek kartu musuh ada gak
                    Player curPlayer = this.board.getCurrentPlayer();
                    Player oppPlayer = this.board.getOppositePlayer();
                    if (oppPlayer.getField(getSlotNum(slot)) != null) {
                        this.slotClicked.setBackground(new Background(new BackgroundFill(Color.valueOf("E7E7E7"), CornerRadii.EMPTY, Insets.EMPTY)));
                        try {
                            curPlayer.attackOpponentCard((SummonedCharacter) getCard(this.slotClicked), (SummonedCharacter) oppPlayer.getField(getSlotNum(slot)), this.board.getOppositePlayer());
                        } catch (CardException e) {
                            e.printStackTrace();
                        }
                        this.slotClicked = null;
                        try {
                            this.reloadField();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                } else {
                    this.slotClicked = slot;
                    slot.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }

        });
    }

    public void updateCharacterFieldCardAttackAvailability(SummonedCharacter[] fieldCards){
        for (SummonedCharacter characterFieldCard : fieldCards){
            if (characterFieldCard != null) {
                characterFieldCard.setBattleAvailability(1);
            }
        }
    }

}