package me.dennisp.BLL.dice;

public class Dice {
	private Die[] dice;

	public Dice(int sides1, int sides2) {
		dice = new Die[] { new Die(sides1), new Die(sides2) };
	}

	public Die getDice(Target number) {
		return dice[number.ordinal()];
	}

	public void toss() {
		for(int i = 0; i < dice.length; i++) {
			dice[i].roll();
		}
	}

	public int getSumOfLastThrow() {
		return dice[0].getRolled() + dice[1].getRolled();
	}

	public boolean wasLastDiceIdentical() {
		return dice[0].getRolled() == dice[1].getRolled();
	}

	public String[] getPrint(Target number) {
		return dice[number.ordinal()].getPrint();
	}

	public enum Target {
		FIRST, SECOND
	}
}
