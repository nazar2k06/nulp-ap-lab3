package Game.Elements;

import Main.*;
import java.io.FileWriter;
import java.util.ArrayList;

public class Maps {
	public static interface Map {
		Map makeObject();

		String getName();
		void startGame(ArrayList<Gamer> gamers, FileWriter writer);
		void startCommandGame(ArrayList<Command> commands, FileWriter writer);
	}

	private static class SimpleMap implements Map {
		private static final String name = "SimpleMap";

		public SimpleMap makeObject() {
			return new SimpleMap();
		}

		@Override
		public String toString() {
			String info = "";

			info += "--- " + getName() + " ---\n";
			info += "simple map to play\n";
			info += "--- " + getName() + " ---\n";

			return info;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public void startGame(ArrayList<Gamer> gamers, FileWriter writer) {
			Main.printlnWithFile("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ Start game ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~", writer);

			Main.printlnWithFile("\n~~~ Gamers ~~~", writer);
			for (int i = 0;i < gamers.size();i++) {
				Main.printlnWithFile("--- Gamer " + (i + 1) + " ---", writer);
				Main.printlnWithFile("Gamer - " + gamers.get(i), writer);
				Main.printlnWithFile("Droid - " + gamers.get(i).droid.getName(), writer);
				Main.printlnWithFile("--- Gamer " + (i + 1) + " ---\n", writer);
			}

			Main.delay(8000);

			while (gamers.size() > 1) {
				Gamer master = null;
				Gamer slave = null;

				master = Gamer.getRandomGamer(gamers, null);
				slave = Gamer.getRandomGamer(gamers, master);

				Main.printWithFile("             ", writer);
				Main.printWithFile(master.name + " (" + master.droid.getHealth() + ") - > ", writer);
				Main.printlnWithFile(slave.name + " (" + slave.droid.getHealth() + ")", writer);

				Main.printlnWithFile("--- " + master + " Attack ---", writer);
				master.droid.hit(slave.droid, writer);
				Main.printlnWithFile("--- " + master + " Attack ---\n", writer);

				Main.printlnWithFile("--- Сonclusion for " + slave + " ---", writer);
				Main.printlnWithFile("Health " + slave.droid.getHealth(), writer);

				if (slave.droid.getHealth() == 0) {
					Main.printlnWithFile("Lost", writer);

					gamers.remove(slave);
				}
				Main.printlnWithFile("--- Сonclusion for " + slave + " ---\n", writer);

				Main.delay(4000);
			}

			Main.printWithFile("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ", writer);
			Main.printWithFile(gamers.getFirst().toString(), writer);
			Main.printlnWithFile(" ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~", writer);
		}

		@Override
		public void startCommandGame(ArrayList<Command> commands, FileWriter writer) {
			Main.printlnWithFile("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ Start game ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~", writer);

			Main.printlnWithFile("\n~~~ Commans ~~~", writer);
			for (int i = 0;i < commands.size();i++) {
				Main.printlnWithFile("--- Command " + (i + 1) + " ---", writer);
				Main.printlnWithFile("Command - " + commands.get(i), writer);

				for (int j = 0;j < commands.get(i).gamers.size();j++) {
					Main.printlnWithFile("--- Gamer " + (j + 1) + " ---", writer);
					Main.printlnWithFile("Gamer - " + commands.get(i).gamers.get(j), writer);
					Main.printlnWithFile("Droid - " + commands.get(i).gamers.get(j).droid.getName(), writer);
					Main.printlnWithFile("--- Gamer " + (j + 1) + " ---\n", writer);
				}

				Main.printlnWithFile("--- Command " + (i + 1) + " ---\n", writer);
			}

			Main.delay(8000);

			while (commands.size() > 1) {
				Command command_master = null;
				Command command_slave = null;
				Gamer master = null;
				Gamer slave = null;

				command_master = Command.getRandomCommand(commands, null);
				command_slave = Command.getRandomCommand(commands, command_master);
				master = Gamer.getRandomGamer(command_master.gamers, null);
				slave = Gamer.getRandomGamer(command_slave.gamers, null);

				Main.printWithFile("             ", writer);
				Main.printWithFile(command_master.name + " - ", writer);
				Main.printWithFile(master.name + " (" + master.droid.getHealth() + ") - > ", writer);
				Main.printWithFile(command_slave.name + " - ", writer);
				Main.printlnWithFile(slave.name + " (" + slave.droid.getHealth() + ")", writer);

				Main.printlnWithFile("--- " + master + " Attack ---", writer);
				master.droid.hit(slave.droid, writer);
				Main.printlnWithFile("--- " + master + " Attack ---\n", writer);

				Main.printlnWithFile("--- Сonclusion for " + slave + " ---", writer);
				Main.printlnWithFile("Health " + slave.droid.getHealth(), writer);

				if (slave.droid.getHealth() == 0) {
					Main.printlnWithFile("Lost", writer);
				}
				Main.printlnWithFile("--- Сonclusion for " + slave + " ---\n", writer);

				for (Command command : commands) {
					command.gamers.removeIf(gamer -> gamer.droid.getHealth() == 0);
				}

				commands.removeIf(command -> command.gamers.isEmpty());
				Main.delay(4000);
			}

			Main.printWithFile("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ", writer);
			Main.printWithFile(commands.getFirst().toString(), writer);
			Main.printlnWithFile(" ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~", writer);
		}
	}


	private static class PoisonMap implements Map {
		private static final String name = "PoisonMap";
		private static final int max_damage = 40;
		private static final int damage_chance = 3;

		public PoisonMap makeObject() {
			return new PoisonMap();
		}

		@Override
		public String toString() {
			String info = "";

			info += "--- " + getName() + " ---\n";
			info += "map that sometimes damages players\n";
			info += "--- " + getName() + " ---\n";

			return info;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public void startGame(ArrayList<Gamer> gamers, FileWriter writer) {
			boolean damage_flag;
			int damage;

			Main.printlnWithFile("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ Start game ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~", writer);

			Main.printlnWithFile("\n~~~ Gamers ~~~", writer);
			for (int i = 0;i < gamers.size();i++) {
				Main.printlnWithFile("--- Gamer " + (i + 1) + " ---", writer);
				Main.printlnWithFile("Gamer - " + gamers.get(i), writer);
				Main.printlnWithFile("Droid - " + gamers.get(i).droid.getName(), writer);
				Main.printlnWithFile("--- Gamer " + (i + 1) + " ---\n", writer);
			}

			Main.delay(8000);

			while (gamers.size() > 1) {
				final Gamer master ;
				final Gamer slave ;

				master = Gamer.getRandomGamer(gamers, null);
				slave = Gamer.getRandomGamer(gamers, master);
				damage_flag = Main.random.nextInt(damage_chance) == 1;
				damage = Main.random.nextInt(max_damage);

				Main.printWithFile("             ", writer);
				Main.printWithFile(master.name + " (" + master.droid.getHealth() + ") - > ", writer);
				Main.printlnWithFile(slave.name + " (" + slave.droid.getHealth() + ")", writer);

				Main.printlnWithFile("--- " + master + " Attack ---", writer);
				master.droid.hit(slave.droid, writer);
				Main.printlnWithFile("--- " + master + " Attack ---\n", writer);

				if (damage_flag) {
					Main.printlnWithFile("--- Map damage ---", writer);
					Main.printlnWithFile("Damage " + damage, writer);

					for (Gamer gamer : gamers) {
						gamer.droid.setHealth(gamer.droid.getHealth() - damage);

						Main.printlnWithFile("Gamer " + gamer.name, writer);
						Main.printlnWithFile("Health " + gamer.droid.getHealth(), writer);

						if (gamer.droid.getHealth() == 0) {
							Main.printlnWithFile("Lost", writer);
						}

						Main.printlnWithFile("", writer);
					}

					Main.printlnWithFile("--- Map damage ---\n", writer);
				}

				Main.printlnWithFile("--- Сonclusion for " + slave + " ---", writer);
				Main.printlnWithFile("Health " + slave.droid.getHealth(), writer);

				if (slave.droid.getHealth() == 0) {
					Main.printlnWithFile("Lost", writer);
				}
				Main.printlnWithFile("--- Сonclusion for " + slave + " ---\n", writer);

				gamers.removeIf(gamer -> (gamer.droid.getHealth() == 0 && gamer != master));
				Main.delay(4000);
			}

			Main.printWithFile("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ", writer);
			Main.printWithFile(gamers.getFirst().toString(), writer);
			Main.printlnWithFile(" ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~", writer);
		}

		public void startCommandGame(ArrayList<Command> commands, FileWriter writer) {
			boolean damage_flag;
			int damage;

			Main.printlnWithFile("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ Start game ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~", writer);

			Main.printlnWithFile("\n~~~ Commans ~~~", writer);
			for (int i = 0;i < commands.size();i++) {
				Main.printlnWithFile("--- Command " + (i + 1) + " ---", writer);
				Main.printlnWithFile("Command - " + commands.get(i), writer);

				for (int j = 0;j < commands.get(i).gamers.size();j++) {
					Main.printlnWithFile("--- Gamer " + (j + 1) + " ---", writer);
					Main.printlnWithFile("Gamer - " + commands.get(i).gamers.get(j), writer);
					Main.printlnWithFile("Droid - " + commands.get(i).gamers.get(j).droid.getName(), writer);
					Main.printlnWithFile("--- Gamer " + (j + 1) + " ---\n", writer);
				}

				Main.printlnWithFile("--- Command " + (i + 1) + " ---\n", writer);
			}

			Main.delay(8000);

			while (commands.size() > 1) {
				final Command command_master;
				final Command command_slave;
				Gamer master = null;
				Gamer slave = null;

				command_master = Command.getRandomCommand(commands, null);
				command_slave = Command.getRandomCommand(commands, command_master);
				master = Gamer.getRandomGamer(command_master.gamers, null);
				slave = Gamer.getRandomGamer(command_slave.gamers, null);
				damage_flag = Main.random.nextInt(damage_chance) == 1;
				damage = Main.random.nextInt(max_damage);

				Main.printWithFile("             ", writer);
				Main.printWithFile(command_master.name + " - ", writer);
				Main.printWithFile(master.name + " (" + master.droid.getHealth() + ") - > ", writer);
				Main.printWithFile(command_slave.name + " - ", writer);
				Main.printlnWithFile(slave.name + " (" + slave.droid.getHealth() + ")", writer);

				Main.printlnWithFile("--- " + master + " Attack ---", writer);
				master.droid.hit(slave.droid, writer);
				Main.printlnWithFile("--- " + master + " Attack ---\n", writer);

				if (damage_flag) {
					Main.printlnWithFile("--- Map damage ---", writer);
					Main.printlnWithFile("Damage " + damage, writer);

					for (Command command : commands) {
						for (int j = 0; j < command.gamers.size(); j++) {
							command.gamers.get(j).droid.setHealth(command.gamers.get(j).droid.getHealth() - damage);

							Main.printlnWithFile("Gamer " + command.gamers.get(j).name, writer);
							Main.printlnWithFile("Health " + command.gamers.get(j).droid.getHealth(), writer);

							if (command.gamers.get(j).droid.getHealth() == 0) {
								Main.printlnWithFile("Lost", writer);
							}

							Main.printlnWithFile("", writer);
						}
					}

					Main.printlnWithFile("--- Map damage ---\n", writer);
				}

				Main.printlnWithFile("--- Сonclusion for " + slave + " ---", writer);
				Main.printlnWithFile("Health " + slave.droid.getHealth(), writer);

				if (slave.droid.getHealth() == 0) {
					Main.printlnWithFile("Lost", writer);
				}
				Main.printlnWithFile("--- Сonclusion for " + slave + " ---\n", writer);

				for (Command command : commands) {
					command.gamers.removeIf(gamer -> gamer.droid.getHealth() == 0);
				}

				commands.removeIf(command -> (command.gamers.isEmpty() && command != command_master));
				Main.delay(4000);
			}

			Main.printWithFile("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ", writer);
			Main.printWithFile(commands.getFirst().toString(), writer);
			Main.printlnWithFile(" ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~", writer);
		}
	}

	private static class HealMap implements Map {
		private static final String name = "HealMap";
		private static final int heal_chance = 4;

		public HealMap makeObject() {
			return new HealMap();
		}

		@Override
		public String toString() {
			String info = "";

			info += "--- " + getName() + " ---\n";
			info += "map that sometimes heals players\n";
			info += "--- " + getName() + " ---\n";

			return info;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public void startGame(ArrayList<Gamer> gamers, FileWriter writer) {
			boolean heal_flag;

			Main.printlnWithFile("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ Start game ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~", writer);

			Main.printlnWithFile("\n~~~ Gamers ~~~", writer);
			for (int i = 0;i < gamers.size();i++) {
				Main.printlnWithFile("--- Gamer " + (i + 1) + " ---", writer);
				Main.printlnWithFile("Gamer - " + gamers.get(i), writer);
				Main.printlnWithFile("Droid - " + gamers.get(i).droid.getName(), writer);
				Main.printlnWithFile("--- Gamer " + (i + 1) + " ---\n", writer);
			}

			Main.delay(8000);

			while (gamers.size() > 1) {
				Gamer master = null;
				Gamer slave = null;

				master = Gamer.getRandomGamer(gamers, null);
				slave = Gamer.getRandomGamer(gamers, master);
				heal_flag = Main.random.nextInt(heal_chance) == 1;

				Main.printWithFile("             ", writer);
				Main.printWithFile(master.name + " (" + master.droid.getHealth() + ") - > ", writer);
				Main.printlnWithFile(slave.name + " (" + slave.droid.getHealth() + ")", writer);

				Main.printlnWithFile("--- " + master + " Attack ---", writer);
				master.droid.hit(slave.droid, writer);
				Main.printlnWithFile("--- " + master + " Attack ---\n", writer);

				if (heal_flag) {
					Gamer gamer = Gamer.getRandomGamer(gamers, null);

					Main.printlnWithFile("--- Map Heal ---", writer);
					gamer.droid.setHealth(gamer.droid.getMaxHealth());

					Main.printlnWithFile("Gamer " + gamer.name, writer);
					Main.printlnWithFile("Health " + gamer.droid.getHealth(), writer);
					Main.printlnWithFile("--- Map Heal ---\n", writer);
				}

				Main.printlnWithFile("--- Сonclusion for " + slave + " ---", writer);
				Main.printlnWithFile("Health " + slave.droid.getHealth(), writer);

				if (slave.droid.getHealth() == 0) {
					Main.printlnWithFile("Lost", writer);
				}
				Main.printlnWithFile("--- Сonclusion for " + slave + " ---\n", writer);

				gamers.removeIf(gamer -> gamer.droid.getHealth() == 0);
				Main.delay(4000);
			}

			Main.printWithFile("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ", writer);
			Main.printWithFile(gamers.getFirst().toString(), writer);
			Main.printlnWithFile(" ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~", writer);
		}

		public void startCommandGame(ArrayList<Command> commands, FileWriter writer) {
			boolean heal_flag;

			Main.printlnWithFile("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ Start game ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~", writer);

			Main.printlnWithFile("\n~~~ Commans ~~~", writer);
			for (int i = 0;i < commands.size();i++) {
				Main.printlnWithFile("--- Command " + (i + 1) + " ---", writer);
				Main.printlnWithFile("Command - " + commands.get(i), writer);

				for (int j = 0;j < commands.get(i).gamers.size();j++) {
					Main.printlnWithFile("--- Gamer " + (j + 1) + " ---", writer);
					Main.printlnWithFile("Gamer - " + commands.get(i).gamers.get(j), writer);
					Main.printlnWithFile("Droid - " + commands.get(i).gamers.get(j).droid.getName(), writer);
					Main.printlnWithFile("--- Gamer " + (j + 1) + " ---\n", writer);
				}

				Main.printlnWithFile("--- Command " + (i + 1) + " ---\n", writer);
			}

			Main.delay(8000);

			while (commands.size() > 1) {
				Command command_master = null;
				Command command_slave = null;
				Gamer master = null;
				Gamer slave = null;

				command_master = Command.getRandomCommand(commands, null);
				command_slave = Command.getRandomCommand(commands, command_master);
				master = Gamer.getRandomGamer(command_master.gamers, null);
				slave = Gamer.getRandomGamer(command_slave.gamers, null);
				heal_flag = Main.random.nextInt(heal_chance) == 1;

				Main.printWithFile("             ", writer);
				Main.printWithFile(command_master.name + " - ", writer);
				Main.printWithFile(master.name + " (" + master.droid.getHealth() + ") - > ", writer);
				Main.printWithFile(command_slave.name + " - ", writer);
				Main.printlnWithFile(slave.name + " (" + slave.droid.getHealth() + ")", writer);

				Main.printlnWithFile("--- " + master + " Attack ---", writer);
				master.droid.hit(slave.droid, writer);
				Main.printlnWithFile("--- " + master + " Attack ---\n", writer);

				if (heal_flag) {
					Command command = Command.getRandomCommand(commands, null);
					Gamer gamer = Gamer.getRandomGamer(command.gamers, null);

					Main.printlnWithFile("--- Map Heal ---", writer);
					gamer.droid.setHealth(gamer.droid.getMaxHealth());

					Main.printlnWithFile("Gamer " + gamer.name, writer);
					Main.printlnWithFile("Health " + gamer.droid.getHealth(), writer);
					Main.printlnWithFile("--- Map Heal ---\n", writer);
				}

				Main.printlnWithFile("--- Сonclusion for " + slave + " ---", writer);
				Main.printlnWithFile("Health " + slave.droid.getHealth(), writer);

				if (slave.droid.getHealth() == 0) {
					Main.printlnWithFile("Lost", writer);
				}
				Main.printlnWithFile("--- Сonclusion for " + slave + " ---\n", writer);

				for (Command command : commands) {
					command.gamers.removeIf(gamer -> gamer.droid.getHealth() == 0);
				}

				commands.removeIf(command -> command.gamers.isEmpty());
				Main.delay(4000);
			}

			Main.printWithFile("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ", writer);
			Main.printWithFile(commands.getFirst().toString(), writer);
			Main.printlnWithFile(" ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~", writer);
		}
	}

	// functions
	public static Map getMap() {
		int map_index;

		System.out.println("\n--- Maps ---");
		printMapsIndexes();
		System.out.println("--- Maps ---");

		map_index = Main.enterIntValue("Choose map:", 0, getMapsCount() - 1);

		return getMapByIndex(map_index);
	}

	public static Map getMapByIndex(int index) {
		ArrayList<Map> maps = getMapsList();

		return maps.get(index).makeObject();
	}

	private static int getMapsCount() {
		ArrayList<Map> maps = getMapsList();

		return maps.size();
	}

	private static ArrayList<Map> getMapsList() {
		ArrayList<Map> maps = new ArrayList<>();

		maps.add(new SimpleMap());
		maps.add(new PoisonMap());
		maps.add(new HealMap());

		return maps;
	}

	public static void printMapsInfo() {
		ArrayList<Map> maps = getMapsList();
		System.out.println("\n~~~ Maps ~~~");

		for (int i = 0;i < maps.size();i++) {
			System.out.println(maps.get(i));
		}
	}

	private static void printMapsIndexes() {
		ArrayList<Map> maps = getMapsList();

		for (int i = 0;i < maps.size();i++) {
			System.out.print(i);
			System.out.println(" - " + maps.get(i).getName());
		}
	}
}
