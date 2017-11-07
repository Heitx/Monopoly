package me.dennisp.BLL.field.consequence;

import me.dennisp.BLL.Driver;
import me.dennisp.BLL.Player;
import me.dennisp.BLL.field.FieldTemplate;
import me.dennisp.BLL.field.OwnableField;

public class PayConsequence implements Consequently {
	@Override
	public void consequense(Driver driver, Player victim) {
		FieldTemplate currentField = victim.getCurrentField();

		if(currentField instanceof OwnableField) {
			OwnableField ownableField = (OwnableField) currentField;

			Player owner = ownableField.getOwner();
			int sellPrice = ownableField.getSellPrice();

			if(owner == null) {
				driver.getView().println(String.format("This field is buyable for %d.", ownableField.getPrice()));
			} else {
				if(owner == victim) {
					driver.getView().println(String.format("%s owns this field.", victim.getName()));
				} else {
					if(!victim.pay(owner, sellPrice)) {
						victim.setConsequent(new DebtConsequence(owner, sellPrice));
						driver.getView().println(String.format("%s don't have enough money!", victim.getName()));
						driver.getView().println("A new debt consequent has been added.");
					} else {
						driver.getView().println(String.format("You have paid %d to %s.", sellPrice, owner.getName()));
					}
				}
			}
		}
	}
}
