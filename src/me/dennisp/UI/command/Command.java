package me.dennisp.UI.command;

public class Command {
	private CommandWord command;
	private String[] arguments;

	public Command(CommandWord command, String[] arguments) {
		this.command = command;
		this.arguments = arguments;
	}

	public CommandWord getCommandWord() {
		return command;
	}

	public String[] getArguments() {
		return arguments;
	}

	public boolean hasArguments() {
		return arguments.length > 0;
	}
}
