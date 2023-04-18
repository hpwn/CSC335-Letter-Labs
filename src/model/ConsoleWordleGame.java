package model;

import java.util.Scanner;

public class ConsoleWordleGame {
	public static void main(String[] args) {
		String dictionaryFilePath = "/dictionary.txt";
		WordleController wordleController = new WordleController(dictionaryFilePath);
		Scanner scanner = new Scanner(System.in);

		// ANSI escape codes for colors
		String ANSI_RESET = "\u001B[0m";
		String ANSI_YELLOW = "\u001B[33m";
		String ANSI_GREEN = "\u001B[32m";

		while (true) {
			System.out.println("Welcome to Wordle! Type 'quit' to exit.");
			wordleController.resetGame();

			while (!wordleController.isGameOver() && !wordleController.isGameWon()) {
				System.out.printf("You have %d attempts remaining. Enter your guess: ",
						wordleController.getAttemptsRemaining());
				String guess = scanner.nextLine();

				if (guess.equalsIgnoreCase("quit")) {
					System.out.println("Thanks for playing!");
					scanner.close();
					System.exit(0);
				}

				String feedback = wordleController.processGuess(guess);
				if (feedback.equals("INVALID")) {
					System.out.println("Invalid guess. Please enter a 5-letter word.");
				} else {
					StringBuilder coloredGuess = new StringBuilder();
					for (int i = 0; i < feedback.length(); i++) {
						char c = feedback.charAt(i);
						if (c == '■') {
							coloredGuess.append(ANSI_GREEN).append(guess.charAt(i)).append(ANSI_RESET);
						} else if (c == '□') {
							coloredGuess.append(ANSI_YELLOW).append(guess.charAt(i)).append(ANSI_RESET);
						} else {
							coloredGuess.append(guess.charAt(i));
						}
					}
					System.out.println("Guessed word: " + coloredGuess);
				}
			}

			if (wordleController.isGameWon()) {
				System.out.println("Congratulations! You guessed the word: " + wordleController.getSelectedWord());
			} else {
				System.out.println("Game over! The word was: " + wordleController.getSelectedWord());
			}

			System.out.println("Would you like to play again? Type 'yes' to continue or 'no' to quit.");
			String playAgain = scanner.nextLine();
			if (!playAgain.equalsIgnoreCase("yes")) {
				System.out.println("Thanks for playing!");
				scanner.close();
				System.exit(0);
			}
		}
	}
}
