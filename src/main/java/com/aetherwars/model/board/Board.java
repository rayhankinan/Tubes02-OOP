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

    public Board(String playerName1, String playerName2) throws IOException, URISyntaxException, CardException, DeckException {
        /* TODO: Input deck-name from user */
        String deckName1 = "deck_1.csv";
        String deckName2 = "deck_1.csv";

        this.player1 = new Player(playerName1, deckName1);
        this.player2 = new Player(playerName2, deckName2);

        /* TODO: Input index from user */
        player1.drawCard(1);
        player2.drawCard(1);

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
    public void switchTurn() throws DeckException {
        if (this.turn == 1) {
            this.turn = 2;
        } else {
            this.turn = 1;
        }

        this.getCurrentPlayer().addRound();
        this.getCurrentPlayer().resetMana();

        /* TODO: Input index from user */
        this.getCurrentPlayer().drawCard(1);
    }

    @Override
    public void nextPhase() {
        if (this.phase == Phase.DRAW) {
            this.phase = Phase.PLANNING;

        } else if (this.phase == Phase.PLANNING) {
            this.phase = Phase.ATTACK;

        } else {
            /* TODO: Implement next player DRAW PHASE */
            this.phase = Phase.DRAW;
        }
    }
}
