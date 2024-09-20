package Game;

import Main.*;

import java.util.ArrayList;

public class Command {
	public String name;
	public ArrayList<Gamer> gamers;

	public Command() {
		name = "Game.Command";
		gamers = null;
	}

	@Override
	public String toString() {
		return name;
	}

	// functions
	public static Command getCommand() {
		Command command = new Command();
		int gamers_count = 0;

		command.name = Main.enterStringValue("Write name:");
		command.gamers = new ArrayList<Gamer>();

		gamers_count = Main.enterIntValue("Gamers count: ", 1, 10);
		System.out.println();

		do {
			System.out.println("--- Set gamer No" + (command.gamers.size() + 1) + "---");
			command.gamers.add(Gamer.getGamer());
			System.out.println("--- Set gamer No" + (command.gamers.size()) + "---\n");

			gamers_count--;
		} while (gamers_count > 0);

		return command;
	}

	public static Command getRandomCommand(ArrayList<Command> commands, Command reserved) {
		Command command = null;

		if (reserved == null) {
			return commands.get(Main.random.nextInt(commands.size()) );
		}

		do {
			command = commands.get(Main.random.nextInt(commands.size()) );
		} while (command == reserved);

		return command;
	}
}
