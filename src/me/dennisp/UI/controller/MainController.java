package me.dennisp.UI.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import me.dennisp.BLL.Domain;
import me.dennisp.BLL.Driver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import me.dennisp.BLL.Player;
import me.dennisp.BLL.field.FieldTemplate;
import me.dennisp.BLL.field.OtherField;
import me.dennisp.BLL.field.OwnableField;
import me.dennisp.Main;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
	private HashMap<Player, PlayerLayoutController> playerController;
	private Domain domain;

	@FXML private VBox vboxPlayer;
	@FXML private Pane gridBoard;
	@FXML private Button btnThrow;
	@FXML private Button btnBuy;
	@FXML private Button btnSell;
	@FXML private Button btnEndTurn;

	public MainController() {
		this.domain = new Driver();
		this.playerController = new HashMap<>();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializePlayers();
		initializeBoard();
	}

	@FXML
	void ButtonThrow_Click(ActionEvent event) {
		initializeBoard();
	}

	@FXML
	void ButtonBuy_Click(ActionEvent event) {

	}

	@FXML
	void ButtonSell_Click(ActionEvent event) {

	}

	@FXML
	void ButtonEndTurn_Click(ActionEvent event) {

	}

	private void initializeBoard() {
		assignGridPositionToFields();
		addShapeToFields();
		drawBoard();
	}

	private void drawBoard() {
		FieldTemplate[] fields = domain.getFields();

		gridBoard.getChildren().clear();

		for(FieldTemplate field : fields) {
			Rectangle shape = (Rectangle) field.getShape();

			if(field instanceof OwnableField) {
				shape.setFill(Color.GREEN);
			} else if(field instanceof OtherField) {
				shape.setFill(Color.DARKSEAGREEN);
			} else {
				shape.setFill(Color.DIMGREY);
			}

			Label label = new Label(field.getPosition() + System.lineSeparator() + field.getName());
			label.setLayoutX(shape.getX());
			label.setLayoutY(shape.getY());
			label.setPrefWidth(shape.getWidth());
			label.setPrefHeight(shape.getHeight());
			label.setPadding(new Insets(2));
			label.setTextAlignment(TextAlignment.CENTER);
			label.setContentDisplay(ContentDisplay.CENTER);
			label.setAlignment(Pos.CENTER);

			gridBoard.getChildren().add(shape);
			gridBoard.getChildren().add(label);
		}
	}

	private void addShapeToFields() {
		FieldTemplate[] fields = domain.getFields();

		// formula:
		// O = C * 2 + R * 2 + 4

		int columns = (int) Math.ceil(fields.length / 4.0);
		int rows = (int) Math.floor(fields.length / 4.0 + 2);

		final double width = (int) (gridBoard.getWidth() / columns);
		final double height = (int) (gridBoard.getHeight() / rows);

		for(FieldTemplate field : fields) {
			Point2D point = field.getGridPosition();

			Rectangle shape = new Rectangle(point.getX() * width, point.getY() * height, width, height);
			shape.xProperty().bind();

			field.setShape(shape);
		}
	}

	private void assignGridPositionToFields() {
		FieldTemplate[] fields = domain.getFields();

		int columns = (int) Math.ceil(fields.length / 4.0);
		int rows = (int) Math.floor(fields.length / 4.0 + 2);

		// assigning grid position to the fields

		int idSC = 0; // start counter
		int idEC = fields.length - 1; // end counter

		for(int i = 0; i < columns; i++, idSC++) {
			fields[idSC].setGridPosition(new Point2D(i, 0));
		}

		for(int i = 1; i < rows - 1; i++, idSC++, idEC--) {
			fields[idSC].setGridPosition(new Point2D(columns - 1, i));
			fields[idEC].setGridPosition(new Point2D(0, i));
		}

		for(int i = 0; i < columns; i++, idEC--) {
			fields[idEC].setGridPosition(new Point2D(i, rows - 1));
		}
	}

	private void initializePlayers() {
		int amount = getAmountOfPlayers();

		for(int i = 0; i < amount; i++) {
			domain.addPlayer(getPlayerName(i + 1));
		}

		addPlayers();
	}

	private int getAmountOfPlayers() {
		int amount = 0;
		Dialog dialogAmount = new TextInputDialog("2");
		dialogAmount.setTitle("Monopoly - Player Creation");
		dialogAmount.setHeaderText("How many players are going to play (min. 2)?");
		dialogAmount.setContentText("Players:");

		do {
			Optional o = dialogAmount.showAndWait();

			try {
				amount = o.isPresent() ? Integer.parseInt((String) o.get()) : 0;
			} catch(NumberFormatException ex) {
				// runs when the input format is not a number
			}
		} while(amount < 2);

		return amount;
	}

	private String getPlayerName(int playerIndex) {
		Dialog dialogAmount = new TextInputDialog();
		dialogAmount.setTitle("Monopoly - Player Creation");
		dialogAmount.setHeaderText(String.format("Please enter the name of player #%d", playerIndex));
		dialogAmount.setContentText(String.format("Player #%d:", playerIndex));

		Optional o = dialogAmount.showAndWait();

		return o.isPresent() ? (String) o.get() : "Nameless";
	}

	private void addPlayers() {
		try {
			for(Player player : domain.getPlayers()) {
				FXMLLoader loader = new FXMLLoader(Main.getViewPath("person_layout"));
				Pane pane = loader.load();
				PlayerLayoutController controller = loader.getController();

				controller.setLabelName(player.getName());
				controller.setLabelMoney(player.getMoney() + "");
				controller.setLabelWorth(player.getWorth() + "");
				controller.setPlayer(player);

				playerController.put(player, controller);
				vboxPlayer.getChildren().add(pane);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}