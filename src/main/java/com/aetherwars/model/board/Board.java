package com.aetherwars.model.board;

import com.aetherwars.model.card.CardException;
import com.aetherwars.model.deck.Deck;
import com.aetherwars.model.player.Player;

import java.io.IOException;
import java.net.URISyntaxException;

public class Board implements InterfaceBoard {
    private final Player player1;
    private final Player player2;
    private Phase phase;
    private int turn;

    public Board(String namaPlayer1, String namaPlayer2) throws IOException, URISyntaxException, CardException {
        String namaDeck1 = "deck1";
        String namaDeck2 = "deck2";
        this.player1 = new Player(namaPlayer1, namaDeck1);
        this.player2 = new Player(namaPlayer2, namaDeck2);
        player1.drawCard();
        player2.drawCard();
        this.phase = Phase.DRAW;
    }

    public Player getPlayer1(){

        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public Player getCurrentPlayer() {
        if (this.turn == 1) {
            return this.player1;
        } else {
            return this.player2;
        }
    }

    public Player getOppositePlayer() {
        if (this.turn == 1) {
            return this.player2;
        } else {
            return this.player1;
        }
    }

    public int getTurn() {
        return this.turn;
    }

    public Phase getPhase() {
        return this.phase;
    }

    @Override
    public void switchTurn() {
        if (this.turn == 1) {
            this.turn = 2;
        } else {
            this.turn = 1;
        }
        this.phase = Phase.DRAW;
        getCurrentPlayer().addRound();
        getCurrentPlayer().resetMana();
        getCurrentPlayer().drawCard();
    }

    @Override
    public void nextPhase() {
        if (this.phase == Phase.DRAW) {
            this.phase = Phase.PLANNING;
        } else if (this.phase == Phase.PLANNING) {
            this.phase = Phase.ATTACK;
        }
    }
}
