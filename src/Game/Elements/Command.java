package Game.Elements;

import Main.*;
import java.util.ArrayList;

public class Command {
	public String name;
	public ArrayList<Gamer> gamers;

	public Command() {
		name = "Command";
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

		for (int i = 0; i < gamers_count;i++) {
			System.out.println("--- Set gamer No" + (i + 1) + "---");
			command.gamers.add(Gamer.getGamer());
			System.out.println("--- Set gamer No" + (i + 1) + "---\n");
		}

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
