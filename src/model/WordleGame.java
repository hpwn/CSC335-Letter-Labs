package model;

public class WordleGame {
	private String selectedWord;
	private int attempts;
	private boolean gameWon;

	public WordleGame(String selectedWord) {
		this.selectedWord = selectedWord;
		attempts = 6;
		gameWon = false;
	}

	public String processGuess(String guess) {
		if (!isValidGuess(guess)) {
			return "INVALID";
		}

		guess = guess.toLowerCase().trim();

		StringBuilder feedback = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			if (guess.charAt(i) == selectedWord.charAt(i)) {
				feedback.append('■');
			} else if (selectedWord.contains(guess.substring(i, i + 1))) {
				feedback.append('□');
			} else {
				feedback.append('·');
			}
		}

		if (guess.equals(selectedWord)) {
			attempts = 0;
			gameWon = true;
		} else {
			attempts--;
		}

		return feedback.toString();
	}

	private boolean isAlpha(String s) {
		return s.chars().allMatch(Character::isLetter);
	}

	private boolean isValidGuess(String guess) {
		if (guess == null) {
			return false;
		}
		return guess.length() == 5 && isAlpha(guess);
	}

	public boolean isGameOver() {
		return attempts == 0;
	}

	public boolean isGameWon() {
		return gameWon;
	}

	public int getAttemptsRemaining() {
		return attempts;
	}

	public String getSelectedWord() {
		return selectedWord;
	}
}
