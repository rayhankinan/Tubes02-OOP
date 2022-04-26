package com.aetherwars.controller;

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
    public void setFieldCard(SummonedCharacter character) {
        Image newImg = new Image(Objects.requireNonNull(Character.class.getResource("/image" + character.getImagepath())).toString());
        String xp = Integer.toString(character.getExp());
        String level = Integer.toString(character.getLevel());
        // TODO: Tambahin exp per level (nunggu dari models)
        String desc = xp + "/___ [" + level + "]";

        cardImage.setImage(newImg);
        cardAtt.setText(Integer.toString(character.getAttack()));
        cardHp.setText(Integer.toString(character.getHealth()));
        cardLevel.setText(desc);
    }
}
