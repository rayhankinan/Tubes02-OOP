import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.player.Player;
import com.aetherwars.model.player.PlayerException;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    @BeforeClass
    public static void initialize() throws IOException, URISyntaxException, CardException {
        CardDatabase.initialize();
    }

    @Test
    public void useManaTest() throws IOException, URISyntaxException, CardException, PlayerException {
        Player player = new Player("Rayhan", "deck_1.csv");
        player.useMana(1);
        assertEquals(0, player.getMana());
    }

    @Test
    public void takeDamageTest() throws IOException, URISyntaxException, CardException {
        Player player = new Player("Rayhan", "deck_1.csv");
        player.takeDamage(1);
        assertEquals(79, player.getHealth(), 0.0);
    }
}
