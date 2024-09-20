package Game;

import Main.*;

import java.util.ArrayList;

class Gamer {
	public String name;
	public Droids.Droid droid;

	public Gamer() {
		name = "gamer";
		droid = null;
	}

	 @Override
	 public String toString() {
		 return name;
	 }

	// functions
	public static Gamer getGamer() {
		Gamer gamer = new Gamer();

		gamer.name = Main.enterStringValue("Write name:");
		gamer.droid = Droids.getDroid();

		return gamer;
	}

	public static Gamer getRandomGamer(ArrayList<Gamer> gamers, Gamer reserved) {
		Gamer gamer = null;

		if (reserved == null) {
			return gamers.get(Main.random.nextInt(gamers.size()) );
		}

		do {
			gamer = gamers.get(Main.random.nextInt(gamers.size()) );
		} while (gamer == reserved);

		return gamer;
	}
 }
