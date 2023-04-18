package view_controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.WordleController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class WordleGUI extends Application {
	private static final int MAX_ATTEMPTS = 6;

	private WordleController wordleController;

	private AnchorPane root;
	private Label instructionsLabel;
	private TextField guessField;
	private Button submitButton;
	private Button newGameButton;
	private Label[][] feedbackLabels;
	private Label[][] guessLabels;
	private int currentRow;
	private BarChart<String, Number> guessDistributionBarChart;

	@Override
	public void start(Stage primaryStage) {
		wordleController = new WordleController("/dictionary.txt");
		layoutWindow();
		applyTheme("light");

		primaryStage.setTitle("Wordle Game");
		primaryStage.setScene(new Scene(root, 600, 400));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	// Initialize, set up event listeners and layout
	private void layoutWindow() {
		root = new AnchorPane();

		VBox vbox = new VBox(20);
		vbox.setPrefSize(600, 400);
		vbox.setAlignment(Pos.CENTER);

		instructionsLabel = new Label("Guess the 5-letter word. You have 6 attempts.");
		guessField = new TextField();
		submitButton = new Button("Submit");
		newGameButton = new Button("New Game");
		
		submitButton.setOnAction(event -> handleSubmit());
		newGameButton.setOnAction(event -> startNewGame());
		
		Button lightModeButton = new Button("Light Mode");
		Button darkModeButton = new Button("Dark Mode");

		lightModeButton.setOnAction(event -> applyTheme("light"));
		darkModeButton.setOnAction(event -> applyTheme("dark"));

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		
		
		//set up squares 
		feedbackLabels = new Label[MAX_ATTEMPTS][5];
		for (int row = 0; row < MAX_ATTEMPTS; row++) {
			for (int col = 0; col < 5; col++) {
				Label label = new Label("");
				label.setPrefSize(50, 50);
				label.setAlignment(Pos.CENTER);
				label.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray;");
				feedbackLabels[row][col] = label;
				gridPane.add(label, col, row);
			}
		}

		vbox.getChildren().addAll(instructionsLabel, gridPane, guessField, submitButton, newGameButton, lightModeButton, darkModeButton);
		root.getChildren().add(vbox);

	}

	//for stats at the end of a game
	private void showStatsPage() {
		Stage statsStage = new Stage();
		statsStage.initModality(Modality.APPLICATION_MODAL);
		statsStage.setTitle("Wordle Game Statistics");

		VBox vbox = new VBox(20);
		vbox.setPadding(new Insets(20));
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: #f2f2f2;");

		Label statsLabel = new Label("Game Statistics");
		statsLabel.setPadding(new Insets(0, 0, 10, 0));
		Label gamesPlayedLabel = new Label("Games played: " + wordleController.getGameStats().getGamesPlayed());
		gamesPlayedLabel.setPadding(new Insets(0, 10, 0, 10));
		Label gamesWonLabel = new Label("Games won: " + wordleController.getGameStats().getGamesWon());
		gamesWonLabel.setPadding(new Insets(0, 10, 0, 10));
		Label gamesLostLabel = new Label("Games lost: " + wordleController.getGameStats().getGamesLost());
		gamesLostLabel.setPadding(new Insets(0, 10, 0, 10));

		guessDistributionBarChart = createGuessDistributionGraph(wordleController.getGameStats().getAttemptsPerGame()); //for the bar graph

		vbox.getChildren().addAll(statsLabel, gamesPlayedLabel, gamesWonLabel, gamesLostLabel,
				guessDistributionBarChart);

		Scene statsScene = new Scene(vbox, 600, 400);
		statsStage.setScene(statsScene);
		statsStage.show();
	}

	//for the stats distribution graph
	private BarChart<String, Number> createGuessDistributionGraph(List<List<Integer>> attemptsPerGame) {
		/// create the x-axis and y-axis
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		// create a new bar chart with the x-axis and y-axis
		BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

		// set the label for the x-axis and y-axis
		xAxis.setLabel("Attempts");
		yAxis.setLabel("Correct Letters");

		// create a frequency map to count number of correct letters in each attempt
		Map<String, Integer> frequencyMap = new TreeMap<>();

		// iterate through each attempt in attemptsPerGame
		for (List<Integer> correctLettersList : attemptsPerGame) {
			for (int i = 0; i < correctLettersList.size(); i++) {
				String key = "Attempt " + (i + 1);
				frequencyMap.put(key, frequencyMap.getOrDefault(key, 0) + correctLettersList.get(i));
			}
		}

		// create a new series to add to the bar chart
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName("Guess Distribution");

		// iterate through the entries in the frequency map
		for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
			series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
		}

		// add the series to the bar chart
		barChart.getData().add(series);
		return barChart;
	}

	// Handles the submit button click, processing the user's guess and updating the game state
	private void handleSubmit() {
		if (currentRow >= MAX_ATTEMPTS) {
			startNewGame();
			return;
		}

		String feedback = wordleController.processGuess(guessField.getText());
		// take the user guess
		String currentGuess = guessField.getText();

		System.out.println("here is the word: " + guessField.getText()); //debugging
		if (feedback.equals("INVALID")) {
			instructionsLabel.setText("Please enter a 5-letter word.");
			guessField.clear();
			return;
		}

		displayFeedback(feedback, currentRow, currentGuess);

		//if game is over...
		if (wordleController.isGameOver()) {
			wordleController.updateGameStats();
			showStatsPage();
			String correctWord = wordleController.getSelectedWord();
			//print message win/lose
			if (wordleController.isGameWon()) {
				instructionsLabel
						.setText("Congratulations! The word was: " + correctWord + ".");
			} else {
				instructionsLabel
						.setText("No more attempts. The word was: " + correctWord + ".");
			}
			wordleController.startNewGame();
			currentRow = 0;
		} 
		else {
			instructionsLabel.setText("Attempts remaining: " + wordleController.getAttemptsRemaining());
			currentRow++;
		}
		guessField.clear();
	}

	// Starts a new game by resetting the game state, updating the user interface, and clearing feedback
	private void startNewGame() {
		wordleController.startNewGame();
		currentRow = 0;
		resetFeedbackLabels();
		instructionsLabel.setText("Guess the 5-letter word. You have 6 attempts.");
		guessField.clear();
	}

	// Resets the feedback labels to their initial state
	private void resetFeedbackLabels() {
		for (int row = 0; row < MAX_ATTEMPTS; row++) {
			for (int col = 0; col < 5; col++) {
				feedbackLabels[row][col].setText("");
				feedbackLabels[row][col]
						.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray;");
			}
		}
	}

	// Displays the feedback from the game controller for the user's guess in the feedback grid
	private void displayFeedback(String feedback, int row, String currentGuess) {
		String answer = wordleController.getSelectedWord();
		for (int col = 0; col < feedback.length(); col++) {
			char ch = currentGuess.charAt(col);
			System.out.println("current char: " + ch);
			System.out.println("selected word:" + wordleController.getSelectedWord());
			// char guessChar = currentGuess.charAt(col);
			Label label = feedbackLabels[row][col];
			// Label guessLabel = guessLabels[row][col];
			label.setText(String.valueOf(ch));

			// correct letter correct placement
			if (currentGuess.charAt(col) == answer.charAt(col)) {
				label.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: green;");
			}
			// correct letter, wrong placement
			else if (answer.contains(Character.toString(currentGuess.charAt(col)))) {
				label.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: yellow;");
			}
			// letter not in word
			else {
				label.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray;");
			}

		}
	}
	
	//Light mode/dark mode
	private void applyTheme(String theme) {
	    if (theme.equals("light")) {
	        root.setStyle("-fx-background-color: #ffffff;");
	        instructionsLabel.setStyle("-fx-text-fill: #000000;");
	    } else if (theme.equals("dark")) {
	        root.setStyle("-fx-background-color: #2b2b2b;");
	        instructionsLabel.setStyle("-fx-text-fill: #ffffff;");
	    }
	}

}