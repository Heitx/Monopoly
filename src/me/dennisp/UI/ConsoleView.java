package me.dennisp.UI;

import me.dennisp.BLL.Player;
import me.dennisp.UI.parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class ConsoleView {
	private Parser parser;
	private final String defSignature;

	public ConsoleView() {
		parser = new Parser();
		defSignature = "[  %02d  ]";
	}

	public Parser getParser() {
		return parser;
	}

	public void print(String s) {
		System.out.print(s);
	}

	public void print(String ... s) {
		for(int i = 0; i < s.length; i++) {
			print(s[i] + " ");
		}
	}

	public void println(String s) {
		System.out.println(s);
	}

	public void println(String ... s) {
		for(int i = 0; i < s.length; i++) {
			println(s[i]);
		}
	}

	public void println(String prefix, String ... s) {
		for(int i = 0; i < s.length; i++) {
			println(prefix + s[i]);
		}
	}

	public void printlnf(String s, Object ... objects) {
		System.out.println(String.format(s, objects));
	}

	public void printBoard(int fields, Player ... players) {
		// prints a board where the top and bottom row each are 1/3 of the field (total 2/3).
		// the sides (left and right) combined are 1/3.

		int horizontal = (int) Math.ceil(fields / 3.0);
		int vertical = (fields) / 6;

		String signature = "[  %02d  ]";

		int loops = vertical + 1;

		for(int i = 0; i <= loops; i++) {
			if(i == 0) {
				// prints the first row

				for(int j = 0; j < horizontal; j++) {
					printSignature(j + 1, players);
				}
			} else if(i == loops) {
				// prints the last row
				println("");

				for(int j = 0; j < horizontal; j++) {
					printSignature(fields - (i + j - 1), players);
				}
			} else {
				// prints the left- and right column with spaces in between.
				println("");

				printSignature(fields - (i - 1), players); // left column
				System.out.printf("%" +((signature.length() - 2) * (horizontal - 2))+ "s", ""); // spaces
				printSignature(horizontal + i, players); // right column
			}
		}

		println("");
	}

	private void printSignature(int position, Player[] players) {
		if(players != null && players.length > 0) {
			int sameLocation = 0;
			List<Character> playerChars = new ArrayList<>(players.length);

			for(Player player : players) {
				if(position == player.getPosition()) {
					playerChars.add(player.getName().charAt(0));
					sameLocation++;
				}
			}

			String signature = defSignature;

			if(sameLocation > 4) {
				signature = "[4 %02d +]";
			} else if(sameLocation > 0) {
				while(playerChars.size() < 4) {
					playerChars.add(' ');
				}

				Character[] startLetter = playerChars.toArray(new Character[4]);

				StringBuilder builder = new StringBuilder();
				builder.append("[]");
				builder.insert(1, "" + startLetter[0] + startLetter[1] + startLetter[2] + startLetter[3]);
				builder.insert(3, "%02d");

				signature = builder.toString();
			}

			System.out.printf(signature, position);
		} else {
			System.out.printf(defSignature, position);
		}
	}
}
