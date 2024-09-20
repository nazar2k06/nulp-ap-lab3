package Main;

import Game.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Random;

public class Main {
	public static Scanner in;
	public static Random random;

	public static void main(String[] args) {
		Game game = new Game();
		in = new Scanner(System.in);
		random = new Random();

		game.menu();
	}

	public static int enterIntValue(String hint, int min, int max) {
		while (true) {
			System.out.print(hint);

			if (in.hasNextInt()) {
				int value = in.nextInt();
				in.nextLine();

				if (value >= min && value <= max) {
					return value;
				}
			}
			else {
				in.nextLine();
			}
		}
	}

	public static String enterStringValue(String hint) {
		System.out.print(hint);
		return in.nextLine();
	}

	public static void delay(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void printFile() {
		String file_name = Main.enterStringValue("Write file name (Enter to none): ");

		if (!file_name.isEmpty()) {
			try {
				System.out.println(Files.readString(Paths.get(file_name)) );
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void printWithFile(String string, FileWriter writer) {
		System.out.print(string);

		if (writer != null) {
			try {
				writer.write(string);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void printlnWithFile(String string, FileWriter writer) {
		System.out.println(string);

		if (writer != null) {
			try {
				writer.write(string + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}