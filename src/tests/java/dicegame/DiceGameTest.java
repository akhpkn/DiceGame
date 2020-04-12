package dicegame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceGameTest {
    private DiceGame diceGame;

    @Test
    void getDiceNumber() {
        diceGame = new DiceGame(4, 3, 3);
        assertEquals(3, diceGame.getDiceNumber());
        diceGame = new DiceGame(4, 2, 3);
        assertEquals(2, diceGame.getDiceNumber());
    }

    @Test
    void isGameEnded() {
        diceGame = new DiceGame(4, 3, 4);
        assertFalse(diceGame.isGameEnded());
    }
}