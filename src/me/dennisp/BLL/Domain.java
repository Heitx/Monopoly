package me.dennisp.BLL;

import me.dennisp.BLL.field.FieldTemplate;

public interface Domain {
	void addPlayer(String player);
	Player[] getPlayers();
	FieldTemplate[] getFields();
	void movePlayer();
	boolean buyField();
	boolean sellField();
	boolean endTurn();
}
