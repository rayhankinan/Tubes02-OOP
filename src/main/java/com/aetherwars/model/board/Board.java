package com.aetherwars.model.board;

import com.aetherwars.model.card.CardException;
import com.aetherwars.model.deck.DeckException;
import com.aetherwars.model.player.Player;

import java.io.IOException;
import java.net.URISyntaxException;

public class Board implements TakeTurns {
    private final Player player1;
    private final Player player2;
    private Phase phase;
    private int turn;

    public Board(String playerName1, String playerName2, String pathDeck1, String pathDeck2) throws IOException, URISyntaxException, CardException, DeckException {

        this.player1 = new Player(playerName1, pathDeck1);
        this.player2 = new Player(playerName2, pathDeck2);

        this.phase = Phase.DRAW;
        this.turn = 1;
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
    public void switchTurn() throws DeckException {
        if (this.turn == 1) {
            this.turn = 2;
        } else {
            this.turn = 1;
        }

        this.getCurrentPlayer().addRound();
        this.getCurrentPlayer().resetMana();
    }

    @Override
    public void nextPhase() {
        if (this.phase == Phase.DRAW) {
            this.phase = Phase.PLANNING;

        } else if (this.phase == Phase.PLANNING) {
            this.phase = Phase.ATTACK;

        } else if (this.phase == Phase.ATTACK){
            this.phase = Phase.END;

        } else {
            this.phase = Phase.DRAW;
        }
    }
}
