package model;

import java.io.Serializable;

public class WordleAccount  implements Serializable {

	private String username;
	private String password;
	private int gamesPlayed;
	private int gamesWon;
	private int numberOfGuessesToWin;
	
	public WordleAccount(String username, String password) {
		this.username = username;
		this.password = password;
		this.gamesPlayed = 0;
		this.gamesWon = 0;
		//this.numberOfGuessesToWin = 0;
	}
	
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	public void playedAGame() {
		gamesPlayed ++;
	}
	
	public void wonGame() {
		gamesWon ++;
		System.out.println("adding to number of games won. Here is the total: " + getGamesWon());
	}
	
	public int getGamesPlayed() {
		return gamesPlayed;
	}
	
	public int getGamesWon() {
		//System.out.println("here are the number of games won");
		return gamesWon;
	}
	
	
	
	public boolean verifyCredentials(String username, String password) {
	    return username.equals(this.username) && password.equals(this.password);
	}
}
