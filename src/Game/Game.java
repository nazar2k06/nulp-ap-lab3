package Game;

import Main.*;

import java.io.FileWriter;
import java.io.IOException;

public class Game {
	public Game() {

	}

	public void menu() {
		while (true) {
			int menu_select = 0;

			System.out.println("\n~~~ Game.Droids game menu ~~~");
			System.out.println(" 0. Exit");
			System.out.println(" 1. Show Game.Droids");
			System.out.println(" 2. Show Game.Maps");
			System.out.println(" 3. Show Game.Modes");
			System.out.println(" 4. Read game from file");
			System.out.println(" 5. Start Game.Game");

			menu_select = Main.enterIntValue("Select an item:", 0, 5);

			switch (menu_select) {
				case 0:
					return;
				case 1:
					Droids.printDroidsInfo();
					break;
				case 2:
					Maps.printMapsInfo();
					break;
				case 3:
					Modes.printModesInfo();
					break;
				case 4:
					Main.printFile();
					break;
				case 5:
					startGame();
					break;
			}
		}
	}

	private void startGame() {
		System.out.println("\n~~~ Game.Game start ~~~");

		Maps.Map map = Maps.getMap();
		Modes.Mode mode = Modes.getMode();
		FileWriter writer = null;
		String file_name = Main.enterStringValue("Write file name (Enter to none): ");

		if (!file_name.isEmpty()) {
			try {
				writer = new FileWriter(file_name);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (mode != null) {
			mode.startGame(map, writer);
		}

		if (!file_name.isEmpty()) {
			try {
				writer.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
