package dicegame;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static int n, k, m;

    /**
     * reads number of players, number of dice and number of wins to finish a game
     * if input values are not correct, it throws exceptions
     */
    private static void input() {
        Scanner scanner = new Scanner(System.in);

        n = scanner.nextInt();
        if (n < 2 || n > 6)
            throw new IllegalArgumentException("The number of players should be from 2 to 6");

        k = scanner.nextInt();
        if (k < 1 || k > 5)
            throw new IllegalArgumentException("The number of dice should be from 1 to 5");

        m = scanner.nextInt();
        if (m < 1 || m > 100)
            throw new IllegalArgumentException("The number of wins should be from 1 to 100");
    }

    /**
     * creates a new game with input parameters,
     * creates commentator and players threads and starts them
     * @param n number of players
     * @param k number of dice
     * @param m number of wins to finish a game
     */
    private static void play(int n, int k, int m) {
        DiceGame diceGame = new DiceGame(n, k, m);
        Thread[] players = new Thread[n];

        System.out.println("Round 1 started!\n");

        for (int i = 0; i < n; i++) {
            players[i] = new Thread(new Player(diceGame));
            players[i].start();
        }

        Thread commentator = new Thread(new Commentator(diceGame));
        commentator.start();

    }

    /**
     * requests input values and starts a game
     * @param args args
     */
    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("Enter number of players(2 - 6), number of dices(1 - 5), number of wins to finish a game(1 - 100): ");
                input();
                play(n, k, m);
                break;
            }
            catch (InputMismatchException ex) {
                System.out.println("The numbers should be integers!");
            }
            catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
