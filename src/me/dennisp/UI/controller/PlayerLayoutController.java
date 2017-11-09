package me.dennisp.UI.controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import me.dennisp.BLL.Player;
import me.dennisp.BLL.field.OwnableField;

public class PlayerLayoutController {
	final int ROW_HEIGHT = 24;

	@FXML private Label labelName;
	@FXML private Label labelMoney;
	@FXML private Label labelWorth;
	@FXML private ListView<OwnableField> listviewBuilding;

	public void setLabelName(String labelName) {
		this.labelName.setText(labelName);
	}

	public void setLabelMoney(String labelMoney) {
		this.labelMoney.setText(labelMoney);
	}

	public void setLabelWorth(String labelWorth) {
		this.labelWorth.setText(labelWorth);
	}

	public ListView<OwnableField> getListView() {
		return listviewBuilding;
	}

	public void setPlayer(Player player) {
		ObservableList<OwnableField> fields = FXCollections.observableArrayList(player.getBoughtFields());

		listviewBuilding.setPrefHeight(fields.size() * ROW_HEIGHT);
		listviewBuilding.setItems(fields);
		listviewBuilding.setCellFactory(param -> {
			ListCell<OwnableField> cell = new ListCell<OwnableField>() {
				@Override
				protected void updateItem(OwnableField item, boolean empty) {
					super.updateItem(item, empty);

					if(item != null) {
						setText(item.getName());
					}
				}
			};

			return cell;
		});

		fields.addListener((ListChangeListener<OwnableField>) c -> listviewBuilding.setPrefHeight(fields.size() * ROW_HEIGHT));
	}
}
