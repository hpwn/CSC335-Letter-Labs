package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordleController {
	private List<String> words; // List of words loaded from the dictionary file
	private String selectedWord;
	private int attempts;

	// loads words from the dictionary file and initializes the game
	public WordleController(String dictionaryFilePath) {
		loadWords(dictionaryFilePath);
		resetGame();
	}

	// Load words from the dictionary file into the words list
	private void loadWords(String fileName) {
		words = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName)))) {
			String word;
			while ((word = br.readLine()) != null) {
				words.add(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Resets the game state, selecting a new random word and resetting attempts and gameWon flag
	public void resetGame() {
	    Random random = new Random();
	    selectedWord = words.get(random.nextInt(words.size()));
	    attempts = 6;
	    gameWon = false;
	}

	// Processes the player's guess, providing feedback and updating the game state
	public String processGuess(String guess) {
	    guess = guess.toLowerCase().trim();
	    if (guess.length() != 5) {
	        return "INVALID";
	    }
	    
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
	
	// Checks if the game is over (no attempts remaining)
	public boolean isGameOver() {
		return attempts == 0;
	}

	private boolean gameWon = false;

	// Checks if the game has been won
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