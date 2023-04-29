package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WordleAccount implements Serializable {

	private String username;
	private String password;
	private int gamesPlayed;
	private int gamesWon;
	private int numberOfGuessesToWin;

	private int gamesWonInOneGuess;
	private int gamesWonInTwoGuess;
	private int gamesWonInThreeGuess;
	private int gamesWonInFourGuess;
	private int gamesWonInFiveGuess;
	private int gamesWonOnLastGuess;

	public WordleAccount(String username, String password) {
		this.username = username;
		this.password = password;
		this.gamesPlayed = 0;
		this.gamesWon = 0;
		this.gamesWonInOneGuess = 0;
		this.gamesWonInTwoGuess = 0;
		this.gamesWonInThreeGuess = 0;
		this.gamesWonInFourGuess = 0;
		this.gamesWonInFiveGuess = 0;
		this.gamesWonOnLastGuess = 0;

	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void playedAGame() {
		gamesPlayed++;
	}

	public void wonGame() {
		gamesWon++;
		System.out.println("adding to number of games won. Here is the total: " + getGamesWon());
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public int getGamesWon() {
		// System.out.println("here are the number of games won");
		return gamesWon;
	}

	public void guessesUsedInGame(int attempts) {
		if (attempts == 1) {
			gamesWonInOneGuess++;
		}
		if (attempts == 2) {
			gamesWonInTwoGuess++;
		}
		if (attempts == 3) {
			gamesWonInThreeGuess++;
		}
		if (attempts == 4) {
			gamesWonInFourGuess++;
		}
		if (attempts == 5) {
			gamesWonInFiveGuess++;
		}
		if (attempts == 6) {
			gamesWonOnLastGuess++;
		}
	}

	public int gamesWonInOne() {
		return gamesWonInOneGuess;
	}

	public int gamesWonInTwo() {
		return gamesWonInTwoGuess;
	}

	public int gamesWonInThree() {
		return gamesWonInThreeGuess;
	}

	public int gamesWonInFour() {
		return gamesWonInFourGuess;
	}

	public int gamesWonInFive() {
		return gamesWonInOneGuess;
	}

	public int gamesWonInSix() {
		return gamesWonOnLastGuess;
	}

	public boolean verifyCredentials(String username, String password) {
		return username.equals(this.username) && password.equals(this.password);
	}
}
