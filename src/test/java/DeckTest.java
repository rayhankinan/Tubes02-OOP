import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.CardException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.IOException;
import java.net.URISyntaxException;

public class DeckTest {
    @Before
    public void initialize() throws CardException, IOException, URISyntaxException {
        CardDatabase.initialize();
    }

    @Test
    public void createDeck() {

    }

    @Test
    public void transactionDeck() {

    }

    @Test
    public void threeCardsDeck() {

    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(DeckTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure);
        }

        System.out.println(result.wasSuccessful());
    }
}
