package me.dennisp.UI.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
	private HashMap<Point2D, StackPane> cells;
	private Domain domain;

	@FXML private VBox vboxPlayer;
	@FXML private GridPane gridBoard;
	@FXML private Button btnThrow;
	@FXML private Button btnBuy;
	@FXML private Button btnSell;
	@FXML private Button btnEndTurn;
	@FXML private Label labelPlayerName;

	public MainController() {
		this.domain = new Driver();
		this.playerController = new HashMap<>();
		this.cells = new HashMap<>();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializePlayers();
		initializeBoard();

		domain.initialization();
		labelPlayerName.setText(domain.getCurrentPlayer().getName());
	}

	@FXML
	void ButtonThrow_Click(ActionEvent event) {
		domain.movePlayer();
	}

	@FXML
	void ButtonBuy_Click(ActionEvent event) {
		domain.buyField();
	}

	@FXML
	void ButtonSell_Click(ActionEvent event) {
		ListView<OwnableField> fields = playerController.get(domain.getCurrentPlayer()).getListView();

		if(fields.getSelectionModel().selectedIndexProperty().get() >= 0) {
			System.out.println("Works?");
		}

		//domain.sellField(playerController.get());
	}

	@FXML
	void ButtonEndTurn_Click(ActionEvent event) {
		domain.endTurn();
		labelPlayerName.setText(domain.getCurrentPlayer().getName());
	}

	private void initializeBoard() {
		addColmnsAndRowsToGrid();
		assignGridPositionToFields();
		addShapeToFields();
		drawBoard();
	}

	private void drawBoard() {
		Point2D point;

		for(FieldTemplate field : domain.getFields()) {
			point = field.getGridPosition();

			if(point != null) {
				StackPane pane = new StackPane();

				Rectangle rectangle = new Rectangle();

				rectangle.widthProperty().bind(gridBoard.getColumnConstraints().get((int) field.getGridPosition().getX()).percentWidthProperty());
				rectangle.heightProperty().bind(gridBoard.getRowConstraints().get((int) field.getGridPosition().getY()).percentHeightProperty());

				if(field instanceof OwnableField) {
					rectangle.setFill(Color.GREEN);
				} else if(field instanceof OtherField) {
					rectangle.setFill(Color.DARKSEAGREEN);
				} else {
					rectangle.setFill(Color.DIMGREY);
				}

				Label label = new Label(field.getPosition() + "\n" + field.getName());
				label.setPadding(new Insets(1));
				label.setFont(Font.font(12));
				label.setTextAlignment(TextAlignment.CENTER);
				label.setContentDisplay(ContentDisplay.CENTER);

				pane.getChildren().add(rectangle);
				pane.getChildren().add(label);

				cells.put(point, pane);
				gridBoard.add(pane, (int) point.getX(), (int) point.getY());
			}
		}
	}

	private void addShapeToFields() {
		FieldTemplate[] fields = domain.getFields();

		ObservableList cols = gridBoard.getColumnConstraints();
		ObservableList rows = gridBoard.getRowConstraints();

		Point2D point;
		ColumnConstraints col;
		RowConstraints row;

		for(FieldTemplate field : fields) {
			point = field.getGridPosition();
			col = (ColumnConstraints) cols.get((int) point.getX());
			row = (RowConstraints) rows.get((int) point.getY());

			Rectangle shape = new Rectangle();

			field.setShape(shape);
		}
	}

	private void assignGridPositionToFields() {
		FieldTemplate[] fields = domain.getFields();

		int columns = gridBoard.getColumnConstraints().size();
		int rows = gridBoard.getRowConstraints().size();

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

	private void addColmnsAndRowsToGrid() {
		FieldTemplate[] fields = domain.getFields();

		// formula:
		// O = C * 2 + R * 2 + 4

		int columns = (int) Math.ceil(fields.length / 4.0);
		int rows = (int) Math.floor(fields.length / 4.0 + 2);

		// adds the columns and rows

		for(int i = 0; i < columns; i++) {
			ColumnConstraints col = new ColumnConstraints();
			col.percentWidthProperty().bind(gridBoard.widthProperty().divide(columns));
			col.setHalignment(HPos.CENTER);

			gridBoard.getColumnConstraints().add(col);
		}

		for(int i = 0; i < rows; i++) {
			RowConstraints row = new RowConstraints();
			row.percentHeightProperty().bind(gridBoard.heightProperty().divide(rows));
			row.setValignment(VPos.CENTER);

			gridBoard.getRowConstraints().add(row);
		}
	}

	private void initializePlayers() {
		getPlayerNames();
		addPlayers();
	}

	private void getPlayerNames() {
		Dialog dialog = new TextInputDialog("Dennis, Bjarke");
		dialog.setTitle("Monpoly | Player Creation");
		dialog.setHeaderText("Seperate each player name by a comma.");
		dialog.setContentText("Names:");

		String[] names;

		do {
			Optional o = dialog.showAndWait();

			names = o.isPresent() ? ((String) o.get()).split(",") : null;
		} while(names == null || names.length < 2);

		for(String name : names) {
			domain.addPlayer(name);
		}
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