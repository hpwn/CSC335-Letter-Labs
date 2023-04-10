package tests;

import model.Dictionary;
import model.WordleController;
import model.WordleGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class WordleGameTest {
	private WordleController wordleController;
	private WordleGame wordleGame;
	private Dictionary dictionary;

	@BeforeEach
	public void setUp() {
		wordleController = new WordleController("/dictionary.txt");
		dictionary = new Dictionary("/dictionary.txt");
		String selectedWord = dictionary.getRandomWord();
		wordleGame = new WordleGame(selectedWord);
	}

	@Test
	public void testProcessGuess() {
		String guess = wordleGame.getSelectedWord();
		String feedback = wordleGame.processGuess(guess);
		assertEquals("■■■■■", feedback);
	}

	@Test
	public void testControllerProcessGuess() {
		String selectedWord = wordleController.getSelectedWord();
		String feedback = wordleController.processGuess(selectedWord);
		assertEquals("■■■■■", feedback);
	}

	@Test
	void testInvalidGuess() {
		WordleGame game = new WordleGame("apple");
		String feedback = game.processGuess("applee");
		assertEquals("INVALID", feedback);

		feedback = game.processGuess("ap1le");
		assertEquals("INVALID", feedback);

		feedback = game.processGuess("ap le");
		assertEquals("INVALID", feedback);

		feedback = game.processGuess("");
		assertEquals("INVALID", feedback);

		feedback = game.processGuess(null);
		assertEquals("INVALID", feedback);
	}

	@Test
	public void testIsGameOver() {
		for (int i = 0; i < 6; i++) {
			wordleGame.processGuess("wrong");
		}
		assertTrue(wordleGame.isGameOver());
	}

	@Test
	public void testControllerIsGameOver() {
		for (int i = 0; i < 6; i++) {
			wordleController.processGuess("wrong");
		}
		assertTrue(wordleController.isGameOver());
	}

	@Test
	public void testIsGameWon() {
		String guess = wordleGame.getSelectedWord();
		wordleGame.processGuess(guess);
		assertTrue(wordleGame.isGameWon());
	}

	@Test
	public void testControllerIsGameWon() {
		String selectedWord = wordleController.getSelectedWord();
		wordleController.processGuess(selectedWord);
		assertTrue(wordleController.isGameWon());
	}

	@Test
	public void testAttemptsRemaining() {
		wordleGame.processGuess("wrong");
		assertEquals(5, wordleGame.getAttemptsRemaining());
	}

	@Test
	public void testControllerAttemptsRemaining() {
		wordleController.processGuess("wrong");
		assertEquals(5, wordleController.getAttemptsRemaining());
	}

	@Test
	public void testSelectedWord() {
		String selectedWord = wordleGame.getSelectedWord();
		assertNotNull(selectedWord);
		assertEquals(5, selectedWord.length());
	}

	@Test
	public void testControllerSelectedWord() {
		String selectedWord = wordleController.getSelectedWord();
		assertNotNull(selectedWord);
		assertEquals(5, selectedWord.length());
	}

	@Test
	public void testControllerStartNewGame() {
		wordleController.startNewGame();
		assertEquals(6, wordleController.getAttemptsRemaining());
	}

	@Test
	public void testDictionaryRandomWord() {
		String randomWord = dictionary.getRandomWord();
		assertNotNull(randomWord);
		assertEquals(5, randomWord.length());
	}

	@Test
	public void testDictionaryConstructor() {
		Dictionary dictionary = new Dictionary("/dictionary.txt");
		assertNotNull(dictionary);
	}

	@Test
	public void testWordleGameConstructor() {
		WordleGame wordleGame = new WordleGame("apple");
		assertNotNull(wordleGame);
	}

	@Test
	public void testWordleControllerConstructor() {
		WordleController wordleController = new WordleController("/dictionary.txt");
		assertNotNull(wordleController);
	}

	@Test
	public void testDictionaryLoadWordsException() {
		assertThrows(RuntimeException.class, () -> {
			new Dictionary("/nonexistent_dictionary_file.txt");
		});
	}

	@Test
	public void testGetWordCount() {
		Dictionary dictionary = new Dictionary("/dictionary.txt");
		int wordCount = dictionary.getWordCount();
		assertTrue(wordCount > 0);
	}

	@Test
	public void testGetWords() {
		Dictionary dictionary = new Dictionary("/dictionary.txt");
		List<String> words = dictionary.getWords();
		assertNotNull(words);
		assertFalse(words.isEmpty());
	}

	@Test
	public void testContainsWord() {
		Dictionary dictionary = new Dictionary("/dictionary.txt");
		String word = dictionary.getRandomWord();
		assertTrue(dictionary.containsWord(word));
	}

	@Test
	public void testSize() {
		Dictionary dictionary = new Dictionary("/dictionary.txt");
		int size = dictionary.getWords().size();
		assertTrue(size > 0);
	}

	@Test
	public void testGetWordByIndex() {
		Dictionary dictionary = new Dictionary("/dictionary.txt");
		String word = dictionary.getWordByIndex(0);
		assertNotNull(word);
		assertTrue(word.length() >= 3 && word.length() <= 5);

		word = dictionary.getWordByIndex(-1);
		assertNull(word);

		word = dictionary.getWordByIndex(dictionary.getWords().size());
		assertNull(word);
	}

}
