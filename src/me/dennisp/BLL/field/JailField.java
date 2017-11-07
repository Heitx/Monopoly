package me.dennisp.BLL.field;

import me.dennisp.BLL.Driver;
import me.dennisp.DAL.MonopolyConstants;
import me.dennisp.BLL.Player;

/**
 *
 * @author erso
 */
public class JailField extends Field {

    public JailField(String name, int position) {
        super(name, position);
    }

    @Override
    public void consequense(Driver driver, Player victim) {
        super.consequense(driver, victim);
        victim.setPosition(MonopolyConstants.JAIL_POS);
    }

    @Override
    public String toString()
    {
        return "JailField {" + "name=" + getName() + ", position=" + getPosition() + '}';
    }

}
