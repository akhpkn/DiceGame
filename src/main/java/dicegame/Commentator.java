package dicegame;

public class Commentator implements Runnable {

    private DiceGame diceGame;

    /**
     * binds parameter to the field
     * @param diceGame dice game
     */
    Commentator(DiceGame diceGame) {
        this.diceGame = diceGame;
    }

    /**
     * method to execute in thread
     */
    @Override
    public void run() {
        while (!diceGame.isGameEnded()) {
            diceGame.comment();
            diceGame.endRound();
        }
        diceGame.endGame();
    }
}
