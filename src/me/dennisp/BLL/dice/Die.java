package me.dennisp.BLL.dice;

public class Die {
	private static String[] printPossibilities;

	static {
		printPossibilities = new String[] {
				"┃       ┃", // 0
				"┃   X   ┃", // 1
				"┃     X ┃", // 2
				"┃ X     ┃", // 3
				"┃ X   X ┃", // 4
				"┏━━━━━━━┓", // 5
				"┗━━━━━━━┛" // 6
		};
	}

	private int sides;
	private int rolled;

	public Die(int sides) {
		this.sides = sides;
	}

	public void roll() {
		rolled =  1 + (int) (Math.random() * sides);
	}

	public int getRolled() {
		return rolled;
	}

	public String[] getPrint() {
		String[] pp = printPossibilities;

		return new String[] {
				rolled == 2 ? pp[1] : rolled == 3 ? pp[3] : rolled == 4 || rolled == 5 || rolled == 6 ? pp[4] : pp[0],
				rolled == 1 || rolled == 3 || rolled == 5 ? pp[1] : rolled == 6 ? pp[4] : pp[0],
				rolled == 2 ? pp[1] : rolled == 3 ? pp[2] : rolled == 4 || rolled == 5 || rolled == 6 ? pp[4] : pp[0]
		};
	}

	public static String[] getPrintPossibilities() {
		return printPossibilities;
	}
}
