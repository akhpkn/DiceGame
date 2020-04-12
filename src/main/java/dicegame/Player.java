package dicegame;

import java.util.Random;

class Player implements Runnable {

    private static int countOfPlayers;

    private static Random random = new Random();

    private int playerId;
    private int score;
    private int roundsWon;

    private boolean thrown;

    private DiceGame diceGame;

    /**
     * binds parameter to the field, increments the count of players and sets the id, adds the player to a game
     * @param diceGame dice game
     */
    Player(DiceGame diceGame) {
        this.diceGame = diceGame;
        playerId = ++countOfPlayers;
        diceGame.addPlayer(this);
    }

    /**
     * returns the current score of a player
     * @return the score of a player
     */
    int getScore() {
        return score;
    }

    /**
     * returns a number of rounds that player won
     * @return how much rounds the player won
     */
    int getRoundsWon() {
        return roundsWon;
    }

    /**
     * returns if player has already thrown dice in current round
     * @return true if player has thrown dice in current round, false otherwise
     */
    boolean isThrown() {
        return thrown;
    }

    /**
     * binds parameter to the field
     * @param thrown true or false
     */
    void setThrown(boolean thrown) {
        this.thrown = thrown;
    }

    /**
     * binds parameter to the field
     * @param roundsWon how much rounds player has won
     */
    void setRoundsWon(int roundsWon) {
        this.roundsWon = roundsWon;
    }

    /**
     * sets the amount of points player got after throwing dice;
     * it is a random value from 1*n to 6*n, where n is a number of dice
     */
    void setScore() {
        score = random.nextInt(5 * diceGame.getDiceNumber() + 1) + diceGame.getDiceNumber();
    }

    /**
     * method to execute in a thread
     * player throws dice while the game is not ended
     */
    @Override
    public void run() {
        while (!diceGame.isGameEnded())
            diceGame.throwDice(this);
    }

    /**
     * returns info about the player, containing player's id
     * @return info about the player
     */
    @Override
    public String toString() {
        return "Player " + playerId;
    }
}
