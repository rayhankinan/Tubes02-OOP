package com.aetherwars.controller;
import com.aetherwars.model.card.Card;

import com.aetherwars.model.card.character.Character;

import com.aetherwars.model.card.spell.level.Level;
import com.aetherwars.model.card.spell.morph.Morph;
import com.aetherwars.model.card.spell.potion.Potion;
import com.aetherwars.model.card.spell.swap.Swap;
import javafx.fxml.*;
import javafx.scene.text.Text;
import javafx.scene.image.*;

import java.util.Objects;

// CONTROLLER FOR CARD
public class CardController {
    @FXML
    private ImageView cardImage;

    @FXML
    private Text cardName;

    @FXML
    private Text cardMana;

    @FXML
    private Text cardDesc;

    @FXML
    public void initialize(){}

    /*
    * Display character card information for character card*/
    public void setCard(Character character){
        Image newImg = new Image("/com/aetherwars/model/card/character/image/" + character.getImagepath().toString());
        String atk = Double.toString(character.getBaseAttack());
        String hp = Double.toString(character.getBaseHealth());
        String desc = "ATK " + atk + "/HP " + hp;

        cardImage.setImage(newImg);
        cardName.setText(character.getName());
        cardMana.setText(Integer.toString(character.getMana()));
        cardDesc.setText(desc);
    }

    /*
     * Display character card information for level spell card*/
    public void setCard(Level level){
        Image newImg = new Image("/com/aetherwars/model/card/spell/level/image/" + level.getImagepath().toString());
        String lvl = Integer.toString(level.getLevel());
        String desc = "LEVEL " + lvl;

        cardImage.setImage(newImg);
        cardName.setText(level.getName());
        cardMana.setText(Integer.toString(level.getMana()));
        cardDesc.setText(desc);
    }

    /*
     * Display character card information for potion spell card*/
    public void setCard(Potion potion){
        Image newImg = new Image("/com/aetherwars/model/card/spell/potion/image/" + potion.getImagepath().toString());
        String atk = Double.toString(potion.getTempAttack());
        String hp = Double.toString(potion.getTempHealth());
        String desc = "ATK + " + atk + "/HP +" + hp;

        cardImage.setImage(newImg);
        cardName.setText(potion.getName());
        cardMana.setText(Integer.toString(potion.getMana()));
        cardDesc.setText(desc);
    }

    /*
     * Display character card information for potion morph card*/
    public void setCard(Morph morph){
        Image newImg = new Image("/com/aetherwars/model/card/spell/morph/image/" + morph.getImagepath().toString());
        String desc = "MORPH";

        cardImage.setImage(newImg);
        cardName.setText(morph.getName());
        cardMana.setText(Integer.toString(morph.getMana()));
        cardDesc.setText(desc);
    }

    /*
     * Display character card information for potion swap card*/
    public void setCard(Swap swap){
        Image newImg = new Image("/com/aetherwars/model/card/spell/swap/image/" + swap.getImagepath().toString());
        String dur = Integer.toString(swap.getDuration());
        String desc = "SWAP " + dur;

        cardImage.setImage(newImg);
        cardName.setText(swap.getName());
        cardMana.setText(Integer.toString(swap.getMana()));
        cardDesc.setText(desc);
    }
    
    /*
    * Call the relevance method based on the instance of card */
    public void setCard(Card card) {
        if (card instanceof Character){
            this.setCard((Character) card);
        } else if (card instanceof Level){
            this.setCard((Level) card);
        } else if (card instanceof Potion){
            this.setCard((Potion) card);
        } else if (card instanceof Morph){
            this.setCard((Morph) card);
        } else if (card instanceof Swap) {
            this.setCard((Swap) card);
        }
    }
}

