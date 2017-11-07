package me.dennisp.UI.command;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommandWords {
	private Map<String, CommandWord> validCommands;

	public CommandWords() {
		validCommands = new LinkedHashMap<>();

		CommandWord[] values = CommandWord.values();

		for(CommandWord value : values) {
			if(value != CommandWord.UNKNOWN) {
				validCommands.put(value.getCommand(), value);
			}
		}
	}

	public Map<String, CommandWord> getValidCommands() {
		return validCommands;
	}

	public CommandWord getCommandWord(String command) {
		return isCommand(command) ? validCommands.get(command) : CommandWord.UNKNOWN;
	}

	public boolean isCommand(String command) {
		return validCommands.containsKey(command.toLowerCase());
	}
}
