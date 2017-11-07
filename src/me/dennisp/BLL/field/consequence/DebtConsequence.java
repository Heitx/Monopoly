package me.dennisp.BLL.field.consequence;

import me.dennisp.BLL.Driver;
import me.dennisp.BLL.Player;

public class DebtConsequence implements Consequently {
	Player receiver;
	int debt;

	public DebtConsequence(Player receiver, int debt) {
		this.receiver = receiver;
		this.debt = debt;
	}

	@Override
	public void consequense(Driver driver, Player victim) {
		if(victim.getWorth() < debt) {
			// lost
		} else {
			if(victim.pay(receiver, debt)) {
				driver.getView().printlnf("%s paid the debt to %s.", victim.getName(), receiver.getName());
				victim.setConsequent(null);
			} else {
				driver.getView().println("You have to sell your properties to continue playing!");
			}
		}
	}
}
