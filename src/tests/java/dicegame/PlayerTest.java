package dicegame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;
    private DiceGame diceGame;

    @Test
    void getScore() {
        diceGame = new DiceGame(3, 4, 3);
        player = new Player(diceGame);
        player.setScore();
        assertTrue(player.getScore() >= 3 && player.getScore() <= 24);
    }

    @Test
    void getRoundsWon() {
        diceGame = new DiceGame(3, 4, 3);
        player = new Player(diceGame);
        player.setRoundsWon(4);
        assertEquals(4, player.getRoundsWon());
    }

    @Test
    void isThrown() {
        diceGame = new DiceGame(3, 4, 3);
        player = new Player(diceGame);
        player.setThrown(true);
        assertTrue(player.isThrown());
    }

    @Test
    void setThrown() {
        diceGame = new DiceGame(3, 4, 3);
        player = new Player(diceGame);
        player.setThrown(false);
        assertFalse(player.isThrown());
    }

    @Test
    void setRoundsWon() {
        diceGame = new DiceGame(3, 4, 3);
        player = new Player(diceGame);
        player.setRoundsWon(4);
        assertEquals(4, player.getRoundsWon());
    }

    @Test
    void setScore() {
        diceGame = new DiceGame(3, 4, 3);
        player = new Player(diceGame);
        player.setScore();
        assertTrue(player.getScore() >= 3 && player.getScore() <= 24);
    }

    @Test
    void testToString() {
        diceGame = new DiceGame(3, 4 ,3);
        player = new Player(diceGame);
        assertEquals("Player 1", player.toString());
    }
}