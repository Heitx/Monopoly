package me.dennisp.BLL.field;

import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;
import me.dennisp.BLL.field.consequence.Consequently;

public interface FieldTemplate extends Consequently {
	String getName();
	int getPosition();
	Point2D getGridPosition();
	void setGridPosition(Point2D position);
	Shape getShape();
	void setShape(Shape shape);
}