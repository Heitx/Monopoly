package me.dennisp.BLL.field;

import me.dennisp.BLL.Player;
import me.dennisp.BLL.field.consequence.Consequently;

/**
 *
 * @author erso
 */
public class OwnableField extends Field {
    public static float SELL_PERCENT = 0.5F;

    private int price;
    private Player owner;

	public OwnableField(String name, int position, int price) {
		super(name, position);

		this.price = price;
		this.owner = null;
	}

    public OwnableField(String name, int position, int price, Consequently conseq) {
        super(name, position, null, conseq);

        this.price = price;
        this.owner = null;
    }

    public int getPrice() {
        return price;
    }

    public int getSellPrice() {
        return (int) (getPrice() * SELL_PERCENT);
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "OwnableField{" + "name=" + getName() + ", position=" + getPosition()
                + ", price=" + price + ", owner=" + owner + '}';
    }
}