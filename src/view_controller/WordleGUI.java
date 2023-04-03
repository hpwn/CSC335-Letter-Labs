package view_controller;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.WordleController;
//import model.WordleTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
    private int currentRow;
   


    @Override
    public void start(Stage primaryStage) {
        wordleController = new WordleController("/dictionary.txt");
        layoutWindow();
      //  WordleTimer wordleTimer = new WordleTimer(60); // Set the initial time to 60 seconds.


        primaryStage.setTitle("Wordle Game");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

 // Initializes the user interface components, setting up event listeners and layout
    private void layoutWindow() {
        root = new AnchorPane();
        
       //Label timerLabel = new Label();
       //timerLabel.textProperty().bind(Bindings.format("Time remaining: %d seconds", wordleTimer.timeRemainingProperty()));

        VBox vbox = new VBox(20);
        vbox.setPrefSize(600, 400);
        vbox.setAlignment(Pos.CENTER);
       // vbox.getChildren().add(1, timerLabel); // Add the timerLabel right below the instructionsLabel.

        instructionsLabel = new Label("Guess the 5-letter word. You have 6 attempts.");
        guessField = new TextField();
        submitButton = new Button("Submit");
        newGameButton = new Button("New Game");

        submitButton.setOnAction(event -> handleSubmit());
        newGameButton.setOnAction(event -> startNewGame());

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

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


        vbox.getChildren().addAll(instructionsLabel, gridPane, guessField, submitButton, newGameButton);
        root.getChildren().add(vbox);
        
    }

 // Handles the submit button click, processing the user's guess and updating the game state
    private void handleSubmit() {
    	// wordleTimer.start();
        if (currentRow >= MAX_ATTEMPTS) {
            startNewGame();
            return;
        }

        String feedback = wordleController.processGuess(guessField.getText());
        if (feedback.equals("INVALID")) {
            instructionsLabel.setText("Please enter a 5-letter word.");
            guessField.clear();
            return;
        }

        displayFeedback(feedback, currentRow);
     

        if (wordleController.isGameOver()) {
            String correctWord = wordleController.getSelectedWord();
            if (wordleController.isGameWon()) {
                instructionsLabel.setText("Congratulations! The word was: " + correctWord + ". Press 'Submit' to play again.");
            } else {
                instructionsLabel.setText("No more attempts. The word was: " + correctWord + ". Press 'Submit' to play again.");
            }
            wordleController.resetGame();
            currentRow = 0;
        } else {
            instructionsLabel.setText("Attempts remaining: " + wordleController.getAttemptsRemaining());
            currentRow++;
        }
        guessField.clear();
    }


 // Starts a new game by resetting the game state, updating the user interface, and clearing feedback
    private void startNewGame() {
    	//wordleTimer.reset();
        wordleController.resetGame();
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
                feedbackLabels[row][col].setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray;");
            }
        }
    }

 // Displays the feedback from the game controller for the user's guess in the feedback grid
    private void displayFeedback(String feedback, int row) {
        for (int col = 0; col < feedback.length(); col++) {
            char ch = feedback.charAt(col);
            Label label = feedbackLabels[row][col];
            label.setText(String.valueOf(ch));
            switch (ch) {
                case '■':
                    label.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: green;");
                    break;
                case '□':
                    label.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: yellow;");
                    break;
                case '·':
                    label.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightgray;");
                    break;
            }
        }
    }
}