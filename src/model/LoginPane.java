package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view_controller.WordleGUI;

public class LoginPane {

	private Label label = new Label("Login or Create Wordle Account");
	private Label usernameLabel = new Label("Username:");
	private Label passwordLabel = new Label("Password:");
	private TextField usernameField = new TextField();
	private PasswordField passwordField = new PasswordField();
	private Button loginButton = new Button("Login");
	private Button createAccountButton = new Button("Create Account");
	private Button logoutButton = new Button("Log Out");
	private LocalDate today = LocalDate.now();
	public Boolean loggedIn = false;

	public WordleAccount accountLoggedIn;

	private Pane loginPane;

	// potentially add the wordle game here in con

	public LoginPane() {

		// create the login pane
		loginPane = createLoginPane();
	}

	public boolean loogedOn() {
		return loggedIn;
	}

	public Pane getLoginPane() {
		return loginPane;
	}

	public Button getLoginButton() {
		return loginButton;
	}

	public Button getCreateAccountButton() {
		return createAccountButton;
	}

	public Button logoutButton() {
		return logoutButton;
	}

	public String getUsername() {
		return usernameField.getText();
	}

	public String getPassword() {
		return passwordField.getText();
	}

	public int getScore() {
		return accountLoggedIn.getGamesWon();
	}

	public void wonGame() {
		if (accountLoggedIn != null) {
			accountLoggedIn.wonGame();
			List<WordleAccount> accounts = getAllAccounts();
			for (int i = 0; i < accounts.size(); i++) {
				WordleAccount account = accounts.get(i);
				if (account.getUsername().equals(accountLoggedIn.getUsername())) {
					accounts.set(i, accountLoggedIn);
					break;
				}
			}
			saveAllAccounts(accounts);
			System.out.println(
					"number of wins for " + accountLoggedIn.getUsername() + " is " + accountLoggedIn.getGamesWon());
		}
	}

	public void playedGame() {
		if (accountLoggedIn != null) {
			accountLoggedIn.playedAGame();
			;
			List<WordleAccount> accounts = getAllAccounts();
			for (int i = 0; i < accounts.size(); i++) {
				WordleAccount account = accounts.get(i);
				if (account.getUsername().equals(accountLoggedIn.getUsername())) {
					accounts.set(i, accountLoggedIn);
					break;
				}
			}
			saveAllAccounts(accounts);
			System.out.println(
					"number of plays for " + accountLoggedIn.getUsername() + " is " + accountLoggedIn.getGamesPlayed());
		}
	}

	public void guessesUsed(int guesses) {
		if (accountLoggedIn != null) {
			accountLoggedIn.guessesUsedInGame(guesses);
			System.out.println("guesses used: " + guesses);

			List<WordleAccount> accounts = getAllAccounts();
			for (int i = 0; i < accounts.size(); i++) {
				WordleAccount account = accounts.get(i);
				if (account.getUsername().equals(accountLoggedIn.getUsername())) {
					accounts.set(i, accountLoggedIn);
					break;
				}
			}
			saveAllAccounts(accounts);
			System.out.println(
					"number of plays for " + accountLoggedIn.getUsername() + " is " + accountLoggedIn.getGamesPlayed());
		}
	}

	// create the login pane
	private Pane createLoginPane() {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(10);
		pane.setVgap(10);
		pane.setPadding(new Insets(25, 25, 25, 25));

		pane.add(label, 0, 0, 2, 1);
		pane.add(usernameLabel, 0, 1);
		pane.add(usernameField, 1, 1);
		pane.add(passwordLabel, 0, 2);
		pane.add(passwordField, 1, 2);

		HBox buttonBox = new HBox(10);
		buttonBox.setAlignment(Pos.CENTER_RIGHT);
		buttonBox.getChildren().addAll(loginButton, createAccountButton);
		pane.add(buttonBox, 1, 4);

		loginButton.setOnAction((ActionEvent e) -> {
			String username = getUsername();
			String password = getPassword();
			System.out.println("Username: " + username);
			System.out.println("Password: " + password);
			List<WordleAccount> accounts = getAllAccounts();

			boolean matchFound = false;
			for (WordleAccount account : accounts) {
				// System.out.println("current account username" + account.getUsername());
				// System.out.println("current account password" + account.getPassword());
				if (account.verifyCredentials(username, password)) {
					// System.out.println("Correct username and password");
					matchFound = true;
					loggedIn = true;
					accountLoggedIn = account;
					label.setText("Welcome, " + accountLoggedIn.getUsername() + " you have won "
							+ accountLoggedIn.getGamesWon() + " games and played " + accountLoggedIn.getGamesPlayed()
							+ " games. " + "how many times won in two tries: " + accountLoggedIn.gamesWonInTwo());
					pane.getChildren().removeAll(usernameLabel, usernameField, passwordLabel, passwordField, buttonBox);

					pane.add(logoutButton, 1, 4);

					break;
				}
			}

			if (!matchFound) {
				System.out.println("Incorrect username or password");
			}
		});

		logoutButton.setOnAction((ActionEvent e) -> {
			loggedIn = false;
			label.setText("Login or Create Wordle Account");
			usernameField.clear();
			passwordField.clear();
			pane.add(usernameLabel, 0, 1);
			pane.add(usernameField, 1, 1);
			pane.add(passwordLabel, 0, 2);
			pane.add(passwordField, 1, 2);
			pane.getChildren().removeAll(logoutButton);
			pane.add(buttonBox, 1, 4);
		});

		createAccountButton.setOnAction((ActionEvent e) -> {
			String username = getUsername();
			String password = getPassword();
			WordleAccount user = new WordleAccount(username, password);
			saveUser(user);
		});

		return pane;
	}

	private void saveUser(WordleAccount user) {
		try {
			System.out.println(user);
			System.out.println("saving " + user.getUsername() + " and the points of " + user.getGamesWon());
			List<WordleAccount> accounts = getAllAccounts();
			accounts.add(user);
			FileOutputStream fileOut = new FileOutputStream("accounts.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(accounts);
			out.close();
			fileOut.close();
			System.out.println("attempting to save game stats");
		} catch (Exception e) {
			System.err.println("Error saving user: " + e.getMessage());
		}
	}

	public List<WordleAccount> getAllAccounts() {
		List<WordleAccount> accounts = new ArrayList<>();
		try {
			FileInputStream fileIn = new FileInputStream("accounts.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Object obj = in.readObject();
			if (obj instanceof List) {
				accounts = (List<WordleAccount>) obj;
			}
			in.close();
			fileIn.close();
			System.out.println("deserialized from file");
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error reading accounts from file");
			e.printStackTrace();
		}
		return accounts;
	}

	private void saveAllAccounts(List<WordleAccount> accounts) {
		try {
			FileOutputStream fileOut = new FileOutputStream("accounts.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(accounts);
			out.close();
			fileOut.close();
			System.out.println("Saved all accounts.");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

}
