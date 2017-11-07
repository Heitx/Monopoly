package me.dennisp.BLL.field;

import javafx.geometry.Point2D;
import me.dennisp.BLL.field.consequence.Consequently;

public class OtherField extends Field {

	public OtherField(String name, int position) {
		super(name, position, null);
	}

	public OtherField(String name, int position, Point2D gridPosition) {
		super(name, position, gridPosition);
	}

	public OtherField(String name, int position, Point2D gridPosition, Consequently consequent) {
		super(name, position, gridPosition, consequent);
	}
}