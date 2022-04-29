import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.character.Character;
import com.aetherwars.model.card.character.SummonedCharacter;
import com.aetherwars.model.player.Player;
import com.aetherwars.model.player.PlayerException;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
    public void takeDamageTest() throws IOException, URISyntaxException, CardException, PlayerException {
        Player player = new Player("Rayhan", "deck_1.csv");
        player.takeDamage(1);
        assertEquals(79, player.getHealth(), 0.0);
    }

    @Test
    public void addToFieldTest() throws IOException, URISyntaxException, CardException, PlayerException {
        Player player_1 = new Player("Rayhan", "deck_1.csv");
        Character character_1 = CardDatabase.getCharacter(3);
        player_1.addToField(0, character_1);
        long status = 0;
        if(player_1.getField(0) != null) {
            status = 1;
        }

        assertEquals(1, status);
    }

    @Test
    public void attackOpponentPlayerTest() throws IOException, URISyntaxException, CardException, PlayerException {
        Player player_1 = new Player("Rayhan", "deck_1.csv");
        Player player_2 = new Player("Munna", "deck_1.csv");
        Character character_1 = CardDatabase.getCharacter(3);
        player_1.addToField(0, character_1);
        player_1.attackOpponentPlayer((SummonedCharacter) player_1.getField(0), player_2);
        assertEquals(77, player_2.getHealth(), 0.0);
    }

    @Test
    public void attackOpponentCardTest() throws IOException, URISyntaxException, CardException {
        Player player_1 = new Player("Rayhan", "deck_1.csv");
        Player player_2 = new Player("Munna", "deck_1.csv");
        Character character_1 = CardDatabase.getCharacter(3);
        player_1.addToField(0, character_1);
        player_2.addToField(0,character_1);
        player_1.attackOpponentCard((SummonedCharacter) player_1.getField(0), (SummonedCharacter) player_2.getField(0), player_2);
        assertNull(player_2.getField(0));
    }
}
