package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Dictionary {
	private List<String> words;
	private Random random;

	public Dictionary(String dictionaryFilePath) {
		loadWords(dictionaryFilePath);
		random = new Random();
	}

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

	public String getRandomWord() {
		return words.get(random.nextInt(words.size()));
	}

	public int getWordCount() {
		return words.size();
	}

	public List<String> getWords() {
		return Collections.unmodifiableList(words);
	}

	public boolean containsWord(String word) {
		return words.contains(word);
	}

	public String getWordByIndex(int index) {
		Dictionary dictionary = new Dictionary("/dictionary.txt");
		if (index >= 0 && index < dictionary.getWords().size()) {
			return dictionary.getWords().get(index);
		} else {
			return null;
		}
	}

}
