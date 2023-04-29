package model;

import java.util.Random;

public class WordleController {
	private Dictionary dictionary;
	private WordleGame wordleGame;
	private GameStats gameStats; // addeded new

	public WordleController(String dictionaryFilePath) {
		dictionary = new Dictionary(dictionaryFilePath);
		startNewGame();
		GameStats gameStatss = new GameStats(); // Added new
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

	public void resetGame() {
		startNewGame();
	}
	
	public int guessesUsed() {
		return wordleGame.getAttemptsRemaining() + 1;
	}

	// added for game stats
	public void updateGameStats() {
		gameStats.addGameStats(wordleGame.isGameWon(), 6 - wordleGame.getAttemptsRemaining(),
				wordleGame.getCorrectLettersList());
	}

	// Added this getter method for gameStats
	public GameStats getGameStats() {
		return gameStats;
	}
}