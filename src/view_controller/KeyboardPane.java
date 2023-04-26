package view_controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class KeyboardPane extends BorderPane{

	private static final int SIZE = 26;
	private String[] keys = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
	                       "A", "S", "D", "F", "G", "H", "J", "K", "L",
	                       "Z", "X", "C", "V", "B", "N", "M"};
	private Button[] keyboard;
	private BorderPane pane;
	private String typedKey;
	
	public KeyboardPane() {
		pane = new BorderPane();
		pane.setPrefSize(600, 400);
		pane.setPadding(new Insets(10, 10, 10, 10));
		
		keyboard = new Button[SIZE];
		createKeyboard();
		
		HBox topRow = new HBox();
		HBox midRow = new HBox();
		HBox botRow = new HBox();
		placeKeys(topRow, 0, 10);
		placeKeys(midRow, 10, 19);
		placeKeys(botRow, 19, SIZE);
		
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(5.0);
		vbox.getChildren().addAll(topRow, midRow, botRow);
		vbox.setPrefHeight(500);
		
		pane.setCenter(vbox);
	}
	
	public BorderPane getKeyboardPane() {
		return pane;
	}
	
	private void createKeyboard() {
		for (int i = 0; i < SIZE; i++) {
			keyboard[i] = new Button(keys[i]);
			keyboard[i].setPrefSize(60, 10);
		}
		
	}


	private void placeKeys(HBox container, int startIndex, int endIndex) {
		container.setAlignment(Pos.CENTER);
		container.setPrefHeight(200);
		container.setSpacing(5.0);
		for (int i = startIndex; i < endIndex; i++) {
			container.getChildren().add(keyboard[i]);
		}
	}
	
	public void setKeyPressAction(EventHandler<ActionEvent> createAccountAction) {
		for (int i = 0; i < SIZE; i++) {
			keyboard[i].setOnAction(createAccountAction);
		}
	}
	//clear colors, create keyboard, Button click event handler?
}