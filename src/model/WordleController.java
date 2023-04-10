package model;

import java.util.Random;

public class WordleController {
	private Dictionary dictionary;
	private WordleGame wordleGame;

	public WordleController(String dictionaryFilePath) {
		dictionary = new Dictionary(dictionaryFilePath);
		startNewGame();
	}

	public void startNewGame() {
		String selectedWord = dictionary.getRandomWord();
		wordleGame = new WordleGame(selectedWord);
	}

	public String processGuess(String guess) {
		return wordleGame.processGuess(guess);
	}

	public boolean isGameOver() {
		return wordleGame.isGameOver();
	}

	public boolean isGameWon() {
		return wordleGame.isGameWon();
	}

	public int getAttemptsRemaining() {
		return wordleGame.getAttemptsRemaining();
	}

	public String getSelectedWord() {
		return wordleGame.getSelectedWord();
	}
}
