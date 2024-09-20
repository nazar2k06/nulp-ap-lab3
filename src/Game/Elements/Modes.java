package Game.Elements;

import Main.*;
import java.io.FileWriter;
import java.util.ArrayList;

public class Modes {
	public static interface Mode {
		Modes.Mode clone();

		void startGame(Maps.Map map, FileWriter writer);
		String getName();
	}

	private static class SimpleMode implements Mode {
		private static final String name = "SimpleMode";

		public SimpleMode clone() {
			return new SimpleMode();
		}

		public String toString() {
			String info = "";

			info += "--- " + getName() + " ---\n";
			info += "simple 1v1 battle mode\n";
			info += "--- " + getName() + " ---\n";

			return info;
		}

		@Override
		public void startGame(Maps.Map map, FileWriter writer) {
			ArrayList<Gamer> gamers = new ArrayList<Gamer>();

			for (int i = 0;i < 2;i++) {
				System.out.println("\n--- Set gamer No" + (i + 1) + "---");
				gamers.add(Gamer.getGamer());
			}

			map.startGame(gamers, writer);
		}

		@Override
		public String getName() {
			return name;
		}

	}

	private static class FightMode implements Mode {
		private static final String name = "FightMode";

		public FightMode clone() {
			return new FightMode();
		}

		public String toString() {
			String info = "";

			info += "--- " + getName() + " ---\n";
			info += "multi-droid battle mode\n";
			info += "--- " + getName() + " ---\n";

			return info;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public void startGame(Maps.Map map, FileWriter writer) {
			ArrayList<Gamer> gamers = new ArrayList<Gamer>();
			int gamers_count;

			gamers_count = Main.enterIntValue("Gamers count: ", 0, 10);
			System.out.println();

			do {
				System.out.println("--- Set gamer No" + (gamers.size() + 1) + "---");
				gamers.add(Gamer.getGamer());
				System.out.println("--- Set gamer No" + (gamers.size()) + "---\n");

				gamers_count--;
			} while (gamers.size() < 2 || gamers_count > 0);

			map.startGame(gamers, writer);
		}
	}

	private static class CommandMode implements Mode {
		private static final String name = "CommandMode";

		public CommandMode clone() {
			return new CommandMode();
		}

		public String toString() {
			String info = "";

			info += "--- " + getName() + " ---\n";
			info += "command battle mode\n";
			info += "--- " + getName() + " ---\n";

			return info;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public void startGame(Maps.Map map, FileWriter writer) {
			ArrayList<Command> commands = new ArrayList<Command>();
			int commands_count;

			commands_count = Main.enterIntValue("Commands count: ", 1, 10);
			System.out.println();

			do {
				System.out.println("--- Set command No" + (commands.size() + 1) + "---");
				commands.add(Command.getCommand());
				System.out.println("--- Set command No" + (commands.size()) + "---\n");

				commands_count--;
			} while (commands_count > 0);

			map.startCommandGame(commands, writer);
		}
	}

	// functions
	public static Mode getMode() {
		int mode_index;

		System.out.println("\n--- Modes ---");
		printModesIndexes();
		System.out.println("--- Modes ---");

		mode_index = Main.enterIntValue("Choose Mode:", 0, getModesCout() - 1);

		return getModeByIndex(mode_index);
	}

	public static Mode getModeByIndex(int index) {
		ArrayList<Mode> modes = getModesList();

		return modes.get(index).clone();
	}

	public static int getModesCout() {
		ArrayList<Mode> modes = getModesList();

		return modes.size();
	}

	private static ArrayList<Mode> getModesList() {
		ArrayList<Mode> modes = new ArrayList<>();

		modes.add(new SimpleMode());
		modes.add(new FightMode());
		modes.add(new CommandMode());

		return modes;
	}

	public static void printModesInfo() {
		ArrayList<Mode> modes = getModesList();
		System.out.println("\n~~~ Modes ~~~");

		for (int i = 0;i < modes.size();i++) {
			System.out.println(modes.get(i));
		}
	}

	private static void printModesIndexes() {
		ArrayList<Mode> modes = getModesList();

		for (int i = 0;i < modes.size();i++) {
			System.out.print(i);
			System.out.println(" - " + modes.get(i).getName());
		}
	}
}
