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
    @BeforeClass
    public static void initialize() throws CardException, IOException, URISyntaxException {
        CardDatabase.initialize();
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
        Board board = new Board("yaya", "YOYO", "deck_1.csv", "deck_1.csv");
        board.switchTurn();
        System.out.println(board.getTurn());
        assertEquals(1, board.getTurn());
    }
}
