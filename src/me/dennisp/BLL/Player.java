package me.dennisp.BLL;

import javafx.geometry.Point2D;
import me.dennisp.BLL.dice.Dice;
import me.dennisp.BLL.field.FieldTemplate;
import me.dennisp.BLL.field.OwnableField;
import me.dennisp.BLL.field.consequence.Consequently;
import me.dennisp.DAL.MonopolyConstants;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private String name;
	private FieldTemplate currentField;
	private FieldTemplate[] fields;
	private List<OwnableField> boughtFields;
	private int money;
	private int boardLaps;

	private Consequently conseq;

	public Player(String name) {
		this(name, null, null);
	}

	public Player(String name, FieldTemplate[] fields) {
		this(name, fields[0], fields);
	}

	public Player(String name, FieldTemplate currentField, FieldTemplate[] fields) {
		this.name = name;
		this.currentField = currentField;
		this.fields = fields;
		this.boughtFields = new ArrayList<>(1);
		this.money = MonopolyConstants.START_MONEY;
		this.boardLaps = 0;

		this.conseq = null;
	}

	public String getName() {
		return name;
	}

	public FieldTemplate getCurrentField() {
		return currentField;
	}

	public int getPosition() {
		return currentField.getPosition();
	}

	public void setPosition(int pos) {
		currentField = fields[pos];
	}

	public boolean buyField(OwnableField field) {
		if(hasMoney(field.getPrice())) {
			boughtFields.add(field);
			field.setOwner(this);
			decreaseMoney(field.getPrice());
			return true;
		}

		return false;
	}

	public boolean sellField(OwnableField field) {
		if(boughtFields.contains(field)) {
			boughtFields.remove(field);
			field.setOwner(null);
			money += field.getPrice() * OwnableField.SELL_PERCENT;
			return true;
		}

		return false;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = Math.max(money, 0);
	}

	public boolean hasMoney(int amount) {
		return money >= amount;
	}

	public void increaseMoney(int amount) { setMoney(money + amount); }

	public void decreaseMoney(int amount) { setMoney(money - amount); }

	public List<OwnableField> getBoughtFields() {
		return boughtFields;
	}

	public String[] getBoughtFieldNames() {
		List<OwnableField> fields = getBoughtFields();
		String[] names = new String[fields.size()];

		for(int i = 0; i < names.length; i++) {
			names[i] = fields.get(i).getName();
		}

		return names;
	}

	public int getBoardLaps() {
		return boardLaps;
	}

	public Consequently getConsequent() {
		return conseq;
	}

	public void setConsequent(Consequently conseq) {
		this.conseq = conseq;
	}

	public int getWorth() {
		int sum = money;

		for(OwnableField field : boughtFields) {
			sum += (int) (field.getPrice() * OwnableField.SELL_PERCENT);
		}

		return sum;
	}

	public boolean pay(Player player, int amount) {
		if(hasMoney(amount)) {
			this.decreaseMoney(amount);
			player.increaseMoney(amount);

			return true;
		}

		return false;
	}

	public void move(Dice die) {
		// TODO: newPosition is not correct.
		int newPosition = (1 + die.getSumOfLastThrow()) - 1;

		if(newPosition >= fields.length) {
			money += MonopolyConstants.PASSING_START;
			boardLaps++;
		}

		currentField = fields[newPosition % fields.length];
	}

	@Override
	public String toString() {
		return String.format("%s is on field %s.", name, currentField.getName());
	}
}
