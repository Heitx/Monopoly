package me.dennisp.BLL;

import me.dennisp.BLL.dice.Dice;
import me.dennisp.BLL.dice.Die;
import me.dennisp.BLL.field.*;
import me.dennisp.BLL.field.consequence.PayConsequence;
import me.dennisp.DAL.MonopolyConstants;
import me.dennisp.UI.ConsoleView;
import me.dennisp.UI.command.Command;

import java.util.LinkedList;
import java.util.List;

public class Driver implements Domain {
	private ConsoleView view;

	private boolean gameFinished;
	private boolean turnEndable;
	private List<Player> playerList;
	private List<Player> lostPlayerList;
	private Player currentPlayer;
	private Dice dice;
	private FieldTemplate[] fields;

	public Driver() {
		view = new ConsoleView();

		gameFinished = false;
		turnEndable = false;
		playerList = new LinkedList<>();
		lostPlayerList = new LinkedList<>();
		dice = new Dice(0, 1);

		fields = new FieldTemplate[MonopolyConstants.FIELD_NAMES.length];
		fillFields();
		//startGame();
	}

	public ConsoleView getView() {
		return view;
	}

	@Override
	public void addPlayer(String player) {
		playerList.add(new Player(player, fields));
	}

	@Override
	public Player[] getPlayers() {
		return playerList.toArray(new Player[playerList.size()]);
	}

	@Override
	public FieldTemplate[] getFields() {
		return fields;
	}

	@Override
	public void movePlayer() {

	}

	@Override
	public boolean buyField() {
		return false;
	}

	@Override
	public boolean sellField() {
		return false;
	}

	@Override
	public boolean endTurn() {
		return false;
	}

	private void startGame() {
		initalizeGame();
		view.println(startMessage());
		view.getParser().reset();
		runGameLoop();
	}

	private void fillFields() {
		for(int i = 0; i < fields.length; i++) {
			switch(i + 1) {
				case 1: // other fields
				case 3:
				case 5:
				case 8:
				case 11:
				case 18:
				case 21:
				case 23:
				case 34:
				case 37:
				case 39:
					fields[i] = new OtherField(MonopolyConstants.FIELD_NAMES[i], i + 1);
					// do something..
					break;
				case 31: // Go to jail:
					fields[i] = new JailField(MonopolyConstants.FIELD_NAMES[i], i + 1);
					break;
				case 6: // Shipping companies:
				case 16:
				case 26:
				case 36:
					fields[i] = new OtherField(MonopolyConstants.FIELD_NAMES[i], i + 1);
					break;
				case 13: // Breweries: Dice is an argument, as the payable amount depends on the total number of dots
				case 29:
					fields[i] = new OtherField(MonopolyConstants.FIELD_NAMES[i], i + 1);
					break;
				default: // Streets:
					fields[i] = new OwnableField(MonopolyConstants.FIELD_NAMES[i], i + 1, i * 10, new PayConsequence());
			}
		}
	}

	private void initalizeGame() {
		view.println("How many players?");

		int playersIngame;

		do {
			playersIngame = view.getParser().getPlayerAmount();

			if(playersIngame < 2) {
				view.println("Error: at least two players!");
			}
		} while(playersIngame < 2);

		for(int i = 0; i < playersIngame; i++) {
			view.println("Enter name of player #" + (i + 1) + ":");
			playerList.add(new Player(view.getParser().getPlayerName(), fields));
		}

		currentPlayer = playerList.get(0);
	}

	// TODO: Seperate functions to the text and winning condition...
	private void runGameLoop() {
		while(!gameFinished) {
			view.print(currentPlayer.getName() + " > ");
			proceedCommand(view.getParser().getCommand());
			view.println("- - - - - - - - - - - - - - - - - - - - - -");
		}
	}

//	int totalValue = 0;
//
//					for(int i = 0; i < victim.getBoughtFields().size(); i++) {
//		totalValue += victim.getBoughtFields().get(i).getPrice() / 2;
//	}
//
//					if(totalValue >= price) {
//
//	} else {
//		// TODO: Add the victim player to a lost player list.
//	}

	private void proceedCommand(Command command) {
		switch(command.getCommandWord()) {
			case UNKNOWN:
				view.println(">> Unknown command!");
				break;
			case HELP:
				view.println(">> Commands: " + view.getParser().getValidCommands());
				break;
			case THROW:
				throwAndMove(command);
				break;
			case BALANCE:
				balance(command);
				break;
			case BUY:
				buy(command);
				break;
			case SELL:
				sell(command);
				break;
			case BOARD:
				board(command);
				break;
			case ENDTURN:
				endTurn(command);
				break;
		}
	}

	private void throwAndMove(Command command) {
		if(!turnEndable) {
			turnEndable = true;

			dice.toss();
			printDice();

			FieldTemplate oldField = currentPlayer.getCurrentField();
			currentPlayer.move(dice);
			FieldTemplate newField = currentPlayer.getCurrentField();
			view.printlnf("%s moved from %s to %s.", currentPlayer.getName(), oldField.getName(), newField.getName());

//			if(dice.wasLastDiceIdentical()) {
//				view.println(String.format("%s threw the dice and got %d in identical numbers of %d.", currentPlayer.getName(), dice.getSumOfLastThrow(), dice.getSumOfLastThrow() / 2));
//				view.println("In addition, you have got an extra turn!");
//				turnEndable = false;
//			} else {
//				view.println(String.format("%s threw the dice and got %d.", currentPlayer.getName(), dice.getSumOfLastThrow()));
//			}

			currentPlayer.getCurrentField().consequense(this, currentPlayer);
		} else {
			view.println("You have already thrown the dice!");
		}
	}

	private void printDice() {
		String[] pp = Die.getPrintPossibilities();

		String[] firstPrint = dice.getPrint(Dice.Target.FIRST);
		String[] secondPrint = dice.getPrint(Dice.Target.SECOND);

		String s;
		int random[] = new int[2];

		view.print(pp[pp.length - 2] +"  "+ pp[pp.length - 2]);
		view.println("");

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 5; j++) {
				if(j == 4) {
					s = firstPrint[i] +"  "+secondPrint[i];
				} else {
					random[0] = (int) (Math.random() * (pp.length - 2));
					random[1] = (int) (Math.random() * (pp.length - 2));
					s = pp[random[0]] +"  "+ pp[random[1]];
				}

				view.print("\r" + s);

				try {
					Thread.sleep(300 - j * 50);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}

			view.println("");
		}

		view.print(pp[pp.length - 1] +"  "+ pp[pp.length - 1] + "\n");

//		view.println(String.format("This allows to move from %s to %s.", currentField.getName(), currentPlayer.getCurrentField().getName()));

//		if(currentPlayer.getBoardLaps() == 2) {
//			driver.setGameFinished(true);
//			view.println(currentPlayer.getName() + " won the game!");
//		}
	}

	private void balance(Command command) {
		view.printlnf("%s: ", currentPlayer.getName().toUpperCase());
		view.printlnf("\tMoney Balance: %d", currentPlayer.getMoney());
		view.printlnf("\tMoney Worth: %d", currentPlayer.getWorth());

		if(currentPlayer.getBoughtFields().size() > 0) {
			view.println("\tOwnable fields:");
			view.println("\t\t- ", currentPlayer.getBoughtFieldNames());
		}
	}

	private void buy(Command command) {
		FieldTemplate currentField = currentPlayer.getCurrentField();

		if(currentField instanceof OwnableField) {
			OwnableField ownableField = (OwnableField) currentField;

			if(ownableField.getOwner() != null) {
				if(ownableField.getOwner() == currentPlayer) {
					view.printlnf("You are the owner of '%s'.", ownableField.getName());
				} else {
					view.printlnf("The owner of '%s' is %s.", ownableField.getName(), ownableField.getOwner().getName());
				}
			} else {
				if(currentPlayer.buyField(ownableField)) {
					view.printlnf("You bought '%s' for %d!", ownableField.getName(), ownableField.getPrice());
				} else {
					view.println("You do not have enough money!");
				}
			}
		}  else {
			view.printlnf("%s is not a buyable field!", currentField.getName());
		}
	}

	private void sell(Command command) {
		String[] arguments = command.getArguments();
		List<OwnableField> fields = currentPlayer.getBoughtFields();

		switch(arguments.length) {
			case 0:
				if(fields.size() == 0) {
					view.println("You do not own a field, so you cannot sell.");
				} else {
					for(int i = 0; i < fields.size(); i++) {
						view.printlnf("[%d] %s", i + 1, fields.get(i).getName());
					}
				}
				break;
			case 1:
				int index = -1;

				try {
					index = Integer.parseInt(arguments[0]) - 1;
				} catch(NumberFormatException e) {
					view.println("That is not a valid index!");
				}

				if(index >= 0 && index < fields.size()) {
					OwnableField ownableField = fields.get(index);

					if(currentPlayer.sellField(ownableField)) {
						view.printlnf("You sold '%s' for %d.", ownableField.getName(), ownableField.getSellPrice());
					} else {
						view.println("You cannot sell a field you have not bought!");
					}
				} else {
					view.println("The index does not exist!");
				}

				break;
			default:
				view.println("Too many arguments!");
		}
	}

	private void board(Command command) {
		view.printBoard(MonopolyConstants.FIELD_NAMES.length, playerList.toArray(new Player[playerList.size()]));
	}

	private void endTurn(Command command) {
		if(turnEndable) {
			if(currentPlayer.getConsequent() == null) {
				int index = playerList.indexOf(currentPlayer);
				currentPlayer = playerList.get((index + 1) % playerList.size());
			}

			turnEndable = false;
		} else {
			view.println("The turn is not endable yet. Throw the dices!");
		}
	}

	private String[] startMessage() {
		return new String[] {
				"-------------------------------------------",
				"| Welcome to \uD83C\uDD5C \uD83C\uDD5E \uD83C\uDD5D \uD83C\uDD5E \uD83C\uDD5F \uD83C\uDD5E \uD83C\uDD5B \uD83C\uDD68",
				"| Type 'help' for commands. Have fun!",
				"-------------------------------------------"
		};
	}
}
