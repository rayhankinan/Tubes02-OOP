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
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
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
        Character character_1 = CardDatabase.getCharacter(3);
        SummonedCharacter summonedCharacter_1 = new SummonedCharacter(character_1);

        Character character_2 = CardDatabase.getCharacter(4);
        SummonedCharacter summonedCharacter_2 = new SummonedCharacter(character_2);

        Potion p = CardDatabase.getPotion(101);
        summonedCharacter_1.addActivable(p);

        int initialTotalHealth = summonedCharacter_1.getTotalHealth();

        summonedCharacter_2.attackCharacter(summonedCharacter_1);

        assertEquals(initialTotalHealth, summonedCharacter_1.getTotalHealth() + summonedCharacter_2.getTotalAttack());
    }

    @Test
    public void swapTest() throws CardException {
        Character character = CardDatabase.getCharacter(1);
        SummonedCharacter summonedCharacter = new SummonedCharacter(character);

        int initialCurrentHealth = summonedCharacter.getCurrentHealth();
        int initialCurrentAttack = summonedCharacter.getCurrentAttack();

        Swap s = CardDatabase.getSwap(203);
        summonedCharacter.addActivable(s);

        Potion p = CardDatabase.getPotion(101);
        summonedCharacter.addActivable(p);

        summonedCharacter.decrementTemporaryDuration();
        summonedCharacter.decrementTemporaryDuration();

        assertEquals(initialCurrentAttack, summonedCharacter.getCurrentHealth());
        assertEquals(initialCurrentHealth, summonedCharacter.getCurrentAttack());
    }

    @Test
    public void potionTest() throws CardException {
        Character character = CardDatabase.getCharacter(1);
        SummonedCharacter summonedCharacter = new SummonedCharacter(character);

        int initialCurrentHealth = summonedCharacter.getCurrentHealth();
        int initialCurrentAttack = summonedCharacter.getCurrentAttack();

        Swap s = CardDatabase.getSwap(201);
        summonedCharacter.addActivable(s);

        Potion p = CardDatabase.getPotion(102);
        summonedCharacter.addActivable(p);

        summonedCharacter.decrementTemporaryDuration();
        summonedCharacter.decrementTemporaryDuration();

        assertEquals(initialCurrentHealth, summonedCharacter.getCurrentHealth());
        assertEquals(initialCurrentAttack, summonedCharacter.getCurrentAttack());
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

        int initialCurrentHealth = summonedCharacter.getCurrentHealth();
        int initialCurrentAttack = summonedCharacter.getCurrentAttack();

        Level l = CardDatabase.getLevel(401);
        summonedCharacter.addActivable(l);

        assertEquals(initialCurrentHealth + summonedCharacter.getHealthup(), summonedCharacter.getCurrentHealth());
        assertEquals(initialCurrentAttack + summonedCharacter.getAttackup(), summonedCharacter.getCurrentAttack());
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(CardTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure);
        }

        System.out.println(result.wasSuccessful());
    }
}
