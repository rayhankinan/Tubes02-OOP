import com.aetherwars.model.board.Phase;
import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.board.Board;
import com.aetherwars.model.player.Player;
import com.aetherwars.model.player.PlayerException;

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

public class PlayerTest {
    Player player;

    @BeforeClass
    public static void initialize() throws IOException, URISyntaxException, CardException {
        CardDatabase.initialize();
    }

    @Test
    public void useManaTest() throws IOException, URISyntaxException, CardException, PlayerException {
        Player player = new Player("Rayhan", "deck_1.csv");
        player.useMana(1);
        assertEquals(999, player.getMana());
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
