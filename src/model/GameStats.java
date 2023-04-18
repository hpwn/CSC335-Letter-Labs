package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStats {
	private int gamesPlayed;
	private int gamesWon;
	private int gamesLost;
	private int currentStreak;
	private int maxStreak;
	private List<List<Integer>> attemptsPerGame;

	public GameStats() {
		gamesPlayed = 0;
		gamesWon = 0;
		gamesLost = 0;
		currentStreak = 0;
		maxStreak = 0;
		attemptsPerGame = new ArrayList<>();
	}

	public void addGameStats(boolean won, int attempts, List<Integer> correctLettersList) {
		gamesPlayed++;
		if (won) {
			gamesWon++;
			currentStreak++;
			if (currentStreak > maxStreak) {
				maxStreak = currentStreak;
			}
		} else {
			gamesLost++;
			currentStreak = 0;
		}

		attemptsPerGame.add(correctLettersList);
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public int getGamesWon() {
		return gamesWon;
	}

	public int getGamesLost() {
		return gamesPlayed - gamesWon;
	}

	public List<List<Integer>> getAttemptsPerGame() {
		return attemptsPerGame;
	}

	public double getWinPercentage() {
		return (double) gamesWon / (double) gamesPlayed * 100;
	}

	public int getCurrentStreak() {
		return currentStreak;
	}

	public int getMaxStreak() {
		return maxStreak;
	}
}
