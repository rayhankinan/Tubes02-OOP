import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.board.Board;

import com.aetherwars.model.deck.DeckException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    @BeforeClass
    public static void initialize() throws IOException, URISyntaxException, CardException {
        CardDatabase.initialize();
    }

    @Test
    public void switchTurn() throws IOException, URISyntaxException, CardException, DeckException {
        Board board = new Board("yaya", "YOYO", "deck_1.csv", "deck_1.csv");
        board.switchTurn();
        assertEquals(2, board.getTurn());
    }
}
