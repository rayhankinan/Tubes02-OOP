import com.aetherwars.model.card.CardDatabase;
import com.aetherwars.model.card.CardException;
import com.aetherwars.model.card.character.Character;
import com.aetherwars.model.card.character.SummonedCharacter;
import com.aetherwars.model.card.spell.level.Level;
import com.aetherwars.model.card.spell.morph.Morph;
import com.aetherwars.model.card.spell.potion.Potion;
import com.aetherwars.model.card.spell.swap.Swap;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

public class CardTest {
    @BeforeClass
    public static void initialize() throws CardException, IOException, URISyntaxException {
        CardDatabase.initialize();
    }

    @Test
    public void characterTest() throws CardException {
        Character character_1 = CardDatabase.getCharacter(7);
        SummonedCharacter summonedCharacter_1 = new SummonedCharacter(character_1);

        Character character_2 = CardDatabase.getCharacter(7);
        SummonedCharacter summonedCharacter_2 = new SummonedCharacter(character_2);

        double initialTotalHealth = summonedCharacter_1.getTotalHealth();

        summonedCharacter_2.attackCharacter(summonedCharacter_1);

        assertEquals(initialTotalHealth, summonedCharacter_1.getTotalHealth() + summonedCharacter_2.getTotalAttack(), 0.0);
    }

    @Test
    public void swapTest() throws CardException {
        Character character = CardDatabase.getCharacter(1);
        SummonedCharacter summonedCharacter = new SummonedCharacter(character);

        double initialCurrentHealth = summonedCharacter.getCurrentHealth();
        double initialCurrentAttack = summonedCharacter.getCurrentAttack();

        Swap s = CardDatabase.getSwap(203);
        summonedCharacter.addActivable(s);

        Potion p = CardDatabase.getPotion(101);
        summonedCharacter.addActivable(p);

        summonedCharacter.decrementTemporaryDuration();
        summonedCharacter.decrementTemporaryDuration();

        assertEquals(initialCurrentAttack, summonedCharacter.getCurrentHealth(), 0.0);
        assertEquals(initialCurrentHealth, summonedCharacter.getCurrentAttack(), 0.0);
    }

    @Test
    public void potionTest() throws CardException {
        Character character = CardDatabase.getCharacter(1);
        SummonedCharacter summonedCharacter = new SummonedCharacter(character);

        double initialCurrentHealth = summonedCharacter.getCurrentHealth();
        double initialCurrentAttack = summonedCharacter.getCurrentAttack();

        Swap s = CardDatabase.getSwap(201);
        summonedCharacter.addActivable(s);

        Potion p = CardDatabase.getPotion(102);
        summonedCharacter.addActivable(p);

        summonedCharacter.decrementTemporaryDuration();
        summonedCharacter.decrementTemporaryDuration();

        assertEquals(initialCurrentHealth, summonedCharacter.getCurrentHealth(), 0.0);
        assertEquals(initialCurrentAttack, summonedCharacter.getCurrentAttack(), 0.0);
    }

    @Test
    public void morphTest() throws CardException {
        Character character = CardDatabase.getCharacter(1);
        SummonedCharacter summonedCharacter = new SummonedCharacter(character);

        Swap s = CardDatabase.getSwap(201);
        summonedCharacter.addActivable(s);

        Potion p = CardDatabase.getPotion(102);
        summonedCharacter.addActivable(p);

        Morph m = CardDatabase.getMorph(301);
        summonedCharacter.addActivable(m);

        assertEquals(0, summonedCharacter.getTemporary().size());
        assertEquals(2, summonedCharacter.getId());
    }

    @Test
    public void levelTest() throws CardException {
        Character character = CardDatabase.getCharacter(1);
        SummonedCharacter summonedCharacter = new SummonedCharacter(character);

        double initialCurrentHealth = summonedCharacter.getCurrentHealth();
        double initialCurrentAttack = summonedCharacter.getCurrentAttack();

        Level l = CardDatabase.getLevel(401);
        summonedCharacter.addActivable(l);

        assertEquals(initialCurrentHealth + summonedCharacter.getHealthup(), summonedCharacter.getCurrentHealth(), 0.0);
        assertEquals(initialCurrentAttack + summonedCharacter.getAttackup(), summonedCharacter.getCurrentAttack(), 0.0);
    }
}
