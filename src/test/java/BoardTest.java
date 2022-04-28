import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.board.Board;

import com.aetherwars.model.deck.DeckException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    @Before
    public void setUp() throws CardException, IOException, URISyntaxException, DeckException {
        CardDatabase.initialize();
        Board board = new Board("yaya", "YOYO", "deck_1.csv", "deck_1.csv");
    }

    @Test
    public void testBoard() throws CardException, IOException, URISyntaxException {
        JUnitCore junit = new JUnitCore();
        Result result = junit.run(BoardTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }

    @Test
    public void switchTurn() throws IOException, URISyntaxException, CardException, DeckException {
        CardDatabase.initialize();
        Board board = new Board("yaya", "YOYO", "deck_1.csv", "deck_1.csv");
        board.switchTurn();
        System.out.println(board.getTurn());
        assertEquals(1, board.getTurn());
    }

    //    create main
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(BoardTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure);
        }

        System.out.println(result.wasSuccessful());
    }


}
