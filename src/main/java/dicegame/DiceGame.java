package dicegame;

import java.util.ArrayList;
import java.util.List;


class DiceGame {

    private boolean gameEnded;
    private boolean roundEnded;
    private boolean maxScore;
    private boolean commented;

    private Player currentPlayer;
    private Player leadingPlayer;
    private Player bestPlayer;
    private List<Player> players;

    private int playersNumber;
    private int diceNumber;
    private int winsNumber;

    private int playersPlayedCounter;
    private int roundCounter;

    /**
     * binds parameters to fields, initialises a list of players and sets round counter value to 1
     * @param playersNumber number of players in the game
     * @param diceNumber number of dice to throw
     * @param winsNumber number of wins to finish the game
     */
    DiceGame(int playersNumber, int diceNumber, int winsNumber) {
        this.playersNumber = playersNumber;
        this.diceNumber = diceNumber;
        this.winsNumber = winsNumber;
        players = new ArrayList<>();
        roundCounter = 1;
    }

    /**
     * returns the number of dice in this game
     * @return number of dice in this game
     */
    int getDiceNumber() {
        return diceNumber;
    }

    /**
     * returns the condition of a game
     * @return true if the game is ended, false otherwise
     */
    boolean isGameEnded() {
        return gameEnded;
    }

    /**
     * adds new player to list
     * @param player player
     */
    void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * it waits other players or/and commentator threads if player has already thrown dice in this round
     * or/and commentator hasn't commented his throw;
     * updates current player, sets the score, indicates that the player has already thrown dice
     * in this round, increments the counter of players that have thrown dice in this round;
     * updates the leader of a round and the field that indicates that this throw is waiting
     * for commentary
     * @param player player
     */
    synchronized void throwDice(Player player) {
        while ((!commented || player.isThrown()) && !gameEnded && currentPlayer != null) {
            try {
                wait();
            }
            catch (InterruptedException ex) {
                System.out.println("Something went wrong!");
            }
        }

        currentPlayer = player;
        currentPlayer.setScore();
        currentPlayer.setThrown(true);

        playersPlayedCounter++;

        if (playersPlayedCounter == playersNumber)
            roundEnded = true;

        if (leadingPlayer == null || currentPlayer.getScore() > leadingPlayer.getScore())
            leadingPlayer = currentPlayer;

        commented = false;
        notifyAll();
    }

    /**
     * it prints information about player's throw, max score in a round and a leading player;
     * updates commented field to indicate that this throw was commented
     */
    synchronized void comment() {
        if (!roundEnded) {
            while (commented) {
                try {
                    wait();
                }
                catch (InterruptedException ex) {
                    System.out.println("Something went wrong!");
                }
            }

            if (!maxScore) {
                System.out.println(currentPlayer.toString() + ": " + currentPlayer.getScore());

                if (leadingPlayer.getScore() == diceNumber * 6)
                    maxScore = true;

                if (playersPlayedCounter != playersNumber && !maxScore)
                    System.out.println("Max score in round " + roundCounter + ": " + leadingPlayer.getScore() +
                           ", " + leadingPlayer.toString());
            }

            commented = true;
            notifyAll();
        }
    }

    /**
     * executed in the end of each round;
     * increments the number of wins of leading player, updates the player with most number of wins;
     * prints results of a round;
     * updates fields
     */
    synchronized void endRound() {
        if (roundEnded) {
            leadingPlayer.setRoundsWon(leadingPlayer.getRoundsWon() + 1);
            if (bestPlayer == null || leadingPlayer.getRoundsWon() > bestPlayer.getRoundsWon())
                bestPlayer = leadingPlayer;

            System.out.println("\nRound " + roundCounter + ":\n" + "Winner: " + leadingPlayer.toString() +
                    ", Score: " + leadingPlayer.getScore() + ", Rounds won: " + leadingPlayer.getRoundsWon());

            if (bestPlayer.getRoundsWon() != winsNumber) {
                System.out.println("Player with max number of wins: " + bestPlayer.toString() +
                        ", Wins: " + bestPlayer.getRoundsWon());

                roundCounter++;
                playersPlayedCounter = 0;
                roundEnded = false;
                leadingPlayer = null;
                currentPlayer = null;
                commented = true;
                maxScore = false;

                for (Player p : players)
                    p.setThrown(false);

                System.out.println("\nRound " + roundCounter + " started!");
            }
            else gameEnded = true;

            System.out.println();
            notifyAll();
        }
    }

    /**
     * prints results of the game
     */
    synchronized void endGame() {
        System.out.println(bestPlayer.toString() + " wins in this game! Congratulations!!!");
        System.out.println("\nRating table: ");

        players.sort((p1, p2) -> p2.getRoundsWon() - p1.getRoundsWon());

        for (Player p : players)
            System.out.println(p.toString() + ", wins: " + p.getRoundsWon());
    }
}
