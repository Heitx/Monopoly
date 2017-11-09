package me.dennisp.BLL;

import me.dennisp.BLL.field.FieldTemplate;

public interface Domain {
	void initialization();
	void addPlayer(String player);
	Player getCurrentPlayer();
	Player[] getPlayers();
	FieldTemplate[] getFields();
	void movePlayer();
	boolean buyField();
	boolean sellField(int index);
	boolean endTurn();
}
