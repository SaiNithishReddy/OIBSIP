// Task 2
//*** WELCOME TO NUMBER GUESSING GAME ***
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

class NumberGame {
    private final int lowerBound;
    private final int upperBound;
    private final int maxAttempts;
    private final int totalRounds;
    private int totalScore;
    private final Random random;
    private final Scanner scanner;

    public NumberGame(int lowerBound, int upperBound, int maxAttempts, int totalRounds) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.maxAttempts = maxAttempts;
        this.totalRounds = totalRounds;
        this.totalScore = 0;
        this.random = new Random();
        this.scanner = new Scanner(System.in);
    }

    private void initializeGame() {
        System.out.println("--------------------");
        System.out.println("*** WELCOME TO NUMBER GUESSING GAME ***");
        System.out.println("Guess the number between " + lowerBound + " to " + upperBound);
        System.out.println("NOTE: Guess the number within " + maxAttempts + " trials in each " + totalRounds + " rounds");
        System.out.println("--------------------");
    }

    private void startGame() {
        for (int round = 1; round <= totalRounds; round++) {
            playRound(round);
        }

        displayGameOver();
    }

    private void playRound(int round) {
        int targetNumber = generateRandomNumber();
        int attempts = 0;
        int score = 0;

        System.out.println("\nRound " + round + ":");
        System.out.println("You have " + maxAttempts + " attempts to guess the number.");

        while (attempts < maxAttempts) {
            try {
                System.out.print("Enter your guess (1-100): ");
                int userGuess = scanner.nextInt();

                if (userGuess < 1 || userGuess > 100) {
                    throw new IllegalArgumentException("Guess value must be between 1 and 100.");
                }

                attempts++; // Increment attempts only when input is successfully parsed

                if (userGuess == targetNumber) {
                    System.out.println("Congratulations! You guessed the correct number in " + attempts + " attempts.");
                    score = maxAttempts - attempts + 1;
                    totalScore += score;
                    break;
                } else if (userGuess < targetNumber) {
                    System.out.println("Guess value is too low. Try again.");
                } else {
                    System.out.println("Guess value is too high. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Clear the input buffer
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        if (attempts == maxAttempts) {
            System.out.println("Sorry, you've run out of attempts in round " + round + ". The correct number was: " + targetNumber);
        }

        System.out.println("Round " + round + " Score: " + score);
    }

    private int generateRandomNumber() {
        return random.nextInt(upperBound - lowerBound + 1) + lowerBound;
    }

    private void displayGameOver() {
        System.out.println("\nGame Over!");
        System.out.println("Total Score: " + totalScore);
        //scanner.close();
    }

    public static void main(String[] args) {
        NumberGame numberGame = new NumberGame(1, 100, 5, 3);
        numberGame.initializeGame();
        numberGame.startGame();
    }
}