package me.dennisp.UI.command;

public enum CommandWord {
	THROW("throw"), BALANCE("balance"), BUY("buy"), SELL("sell"), BOARD("board"), ENDTURN("endturn"), HELP("help"), UNKNOWN("unknown");

	private String command;

	CommandWord(String command) {
		this.command = command;
	}

	public String getCommand() {
		return command;
	}
}
