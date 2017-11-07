package me.dennisp.BLL.field;

import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;
import me.dennisp.BLL.Driver;
import me.dennisp.BLL.Player;
import me.dennisp.BLL.field.consequence.Consequently;

public class Field implements FieldTemplate {
	private String name;
	private int position;
	private Point2D gridPosition;
	private Shape shape;
	private Consequently consequent;

	public Field(String name, int position) {
		this(name, position, null, null);
	}

	public Field(String name, int position, Point2D gridPosition) {
		this(name, position, gridPosition, null);
	}

	public Field(String name, int position, Point2D gridPosition, Consequently consequent) {
		this.name = name;
		this.position = position;
		this.gridPosition = gridPosition;
		this.consequent = consequent;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public Point2D getGridPosition() {
		return gridPosition;
	}

	@Override
	public void setGridPosition(Point2D gridPosition) {
		this.gridPosition = gridPosition;
	}

	@Override
	public Shape getShape() {
		return shape;
	}

	@Override
	public void setShape(Shape shape) {
		this.shape = shape;
	}

	@Override
	public void consequense(Driver driver, Player victim) {
		if(consequent != null) { consequent.consequense(driver, victim); }
	}
}
