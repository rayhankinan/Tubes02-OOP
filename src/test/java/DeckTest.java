import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.CardException;

import com.aetherwars.model.deck.Deck;
import com.aetherwars.model.deck.DeckException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

public class DeckTest {
    @BeforeClass
    public static void initialize() throws CardException, IOException, URISyntaxException {
        CardDatabase.initialize();
    }

    @Test
    public void createDeck() throws IOException, URISyntaxException, CardException {
        Deck deck = new Deck("deck_1.csv");
        int N = 0;
        boolean isEmpty = false;

        while (!isEmpty) {
            try {
                deck.getCard();
                N++;

            } catch (DeckException e) {
                isEmpty = true;
            }
        }

        assertEquals(53, N);
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(DeckTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure);
        }

        System.out.println(result.wasSuccessful());
    }
}
