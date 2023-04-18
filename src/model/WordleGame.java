package model;

import java.util.ArrayList;
import java.util.List;

public class WordleGame {
	private String selectedWord;
	private int attempts;
	private boolean gameWon;
	private List<Integer> correctLettersList;

	public WordleGame(String selectedWord) {
		this.selectedWord = selectedWord;
		attempts = 6;
		gameWon = false;
		correctLettersList = new ArrayList<>();
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

		int correctLetters = getCorrectLetters(guess);
        correctLettersList.add(correctLetters);
        
		return feedback.toString();
	}
	
	private int getCorrectLetters(String guess) {
        int count = 0;
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == selectedWord.charAt(i)) {
                count++;
            }
        }
        return count;
    }

	public List<Integer> getCorrectLettersList() {
        return correctLettersList;
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