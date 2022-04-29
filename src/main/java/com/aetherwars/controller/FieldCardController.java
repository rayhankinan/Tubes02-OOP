package com.aetherwars.controller;

import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.character.Character;
import com.aetherwars.model.card.character.SummonedCharacter;
import javafx.fxml.*;
import javafx.scene.text.Text;
import javafx.scene.image.*;

import java.util.Objects;

// CONTROLLER FOR FieldCard
public class FieldCardController {
    @FXML
    private ImageView cardImage;

    @FXML
    private Text cardAtt;

    @FXML
    private Text cardHp;

    @FXML
    private Text cardLevel;

    @FXML
    public void initialize(){}

    /*
    * Display Field Card*/
    public void setFieldCard(SummonedCharacter character) throws CardException {
        Image newImg = new Image("/com/aetherwars/model/card/character/image/" + character.getImagepath());
        String xp = Integer.toString(character.getExp());
        String nextXp = Integer.toString(character.getExpForNextLevel());
        String level = Integer.toString(character.getLevel());
        /* TODO: Tambahin exp per level (nunggu dari models) */
        String desc = xp + "/" + nextXp + " [" + level + "]";

        cardImage.setImage(newImg);
        cardAtt.setText(Double.toString(character.getTotalAttack()));
        cardHp.setText(Double.toString(character.getTotalHealth()));
        cardLevel.setText(desc);
    }
}
