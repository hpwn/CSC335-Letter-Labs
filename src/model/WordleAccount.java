package model;

import java.io.Serializable;

public class WordleAccount  implements Serializable {

	private String username;
	private String password;
	
	public WordleAccount(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	public boolean verifyCredentials(String username, String password) {
	    return username.equals(this.username) && password.equals(this.password);
	}
}
