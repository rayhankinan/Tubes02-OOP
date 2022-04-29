import com.aetherwars.model.board.Phase;
import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.board.Board;

import com.aetherwars.model.deck.DeckException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    Board board;

    @BeforeClass
    public static void initialize() throws CardException, IOException, URISyntaxException, DeckException {
        // Initialize the board
        CardDatabase.initialize();

    }

    @Test
    public void switchTurnTest() throws IOException, URISyntaxException, CardException, DeckException {
        Board board = new Board("yaya", "YOYO", "deck_1.csv", "deck_1.csv");
        board.switchTurn();
        assertEquals(2, board.getTurn());
    }

    @Test
    public void nextPhaseTest() throws IOException, URISyntaxException, CardException, DeckException {
        Board board = new Board("yaya", "YOYO", "deck_1.csv", "deck_1.csv");
        long status = 0;
        board.nextPhase();
        if (board.getPhase() == Phase.PLANNING) {
            status = 1;
        }
        assertEquals(1, status);
    }

    //    create main
//    public static void main(String[] args) {
//        Result result = JUnitCore.runClasses(BoardTest.class);
//
//        for (Failure failure : result.getFailures()) {
//            System.out.println(failure);
//        }
//
//        System.out.println(result.wasSuccessful());
//    }

}
