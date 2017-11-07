package me.dennisp.UI.parser;

import me.dennisp.UI.command.Command;
import me.dennisp.UI.command.CommandWord;
import me.dennisp.UI.command.CommandWords;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Parser {
	private Scanner scanner;
	private CommandWords cmdWords;

	public Parser() {
		this.scanner = new Scanner(System.in);
		this.cmdWords = new CommandWords();
	}

	public int getPlayerAmount() {
		int i = 0;

		if(scanner.hasNextInt()) {
			i = scanner.nextInt();
		} else {
			scanner.nextLine();
		}

		return i;
	}

	public String getPlayerName() {
		return scanner.next();
	}

	public Command getCommand() {
		String input = scanner.nextLine();
		CommandWord command;
		List<String> arguments = new ArrayList<>();

		Scanner tokenizer = new Scanner(input);

		if(tokenizer.hasNext()) {
			command = cmdWords.getCommandWord(tokenizer.next());

			while(tokenizer.hasNext()) arguments.add(tokenizer.next());
		} else {
			command = CommandWord.UNKNOWN;
		}

		return new Command(command, arguments.toArray(new String[arguments.size()]));
	}

	public void reset() {
		scanner.nextLine();
	}

	public String getValidCommands() {
		StringBuilder builder = new StringBuilder();

		Map<String, CommandWord> map = cmdWords.getValidCommands();

		for(String s : map.keySet()) {
			builder.append(s + " ");
		}

		return builder.toString();
	}
}
