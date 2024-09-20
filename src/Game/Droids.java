package Game;

import Main.*;

import java.io.FileWriter;
import java.util.ArrayList;

public class Droids {
	public static interface Droid {
		Droid clone();
		void hit(Droid other, FileWriter writer);

		void setHealth(int health);
		void setHolding(int holding);

		int getHealth();
		String getName();
		int getMaxHealth();
	}

	private static class SimpleDroid implements Droid {
		private int health;
		private int holding;

		private static final int default_damage = 15;
		private static final String name = "SimpleDroid";
		private static final int max_health = 130;

		public SimpleDroid() {
			health = max_health;
			holding = 0;
		}

		@Override
		public SimpleDroid clone() {
			return new SimpleDroid();
		}

		@Override
		public void hit(Droid other, FileWriter writer) {
			int damage;
			int other_health;

			if (holding > 0) {
				Main.printlnWithFile("Frozen", writer);

				holding--;
				return;
			}

			damage = getDefaultDamage();
			other_health = other.getHealth() - damage;

			Main.printlnWithFile("Damage " + (other.getHealth() - other_health), writer);
			other.setHealth(other_health);
		}

		@Override
		public String toString() {
			String info = "";

			info += "--- " + getName() + " ---\n";
			info += "health: " + getMaxHealth() + "\n";
			info += "damage: " + getDefaultDamage() + "\n";
			info += "--- " + getName() + " ---\n";

			return info;
		}

		@Override
		public void setHealth(int health) {
			this.health = Math.max(health, 0);
		}

		@Override
		public void setHolding(int holding) {
			this.holding = holding;
		}

		@Override
		public int getHealth() {
			return health;
		}

		private int getDefaultDamage() {
			return default_damage;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getMaxHealth() {
			return max_health;
		}
	}

	private static class SuperDroid implements Droid {
		private static final String name = "SuperDroid";
		private static final int max_health = 100;
		private static final int default_damage = 10;
		private static final int damage_ultra_koef = 2;

		private int health;
		private int holding;

		private boolean damage_ultra_flag;

		public SuperDroid() {
			health = max_health;
			holding = 0;

			damage_ultra_flag = false;
		}

		@Override
		public SuperDroid clone() {
			return new SuperDroid();
		}

		@Override
		public void hit(Droid other, FileWriter writer) {
			int damage;
			int other_health;

			if (holding > 0) {
				Main.printlnWithFile("Frozen", writer);

				holding--;
				return;
			}

			damage = (!damage_ultra_flag) ? getDefaultDamage() : getDefaultDamage() * damage_ultra_koef;
			other_health = other.getHealth() - damage;

			if (damage_ultra_flag) {
				Main.printlnWithFile("Ultra damage", writer);
			}
			Main.printlnWithFile("Damage " + (other.getHealth() - other_health), writer);

			damage_ultra_flag = !damage_ultra_flag;
			other.setHealth(other_health);
		}

		@Override
		public String toString() {
			String info = "";

			info += "--- " + getName() + " ---\n";
			info += "health: " + getMaxHealth() + "\n";
			info += "damage: " + getDefaultDamage() + "\n";
			info += "--- " + getName() + " ---\n";

			return info;
		}

		@Override
		public void setHealth(int health) {
			this.health = Math.max(health, 0);
		}

		@Override
		public void setHolding(int holding) {
			this.holding = holding;
		}

		@Override
		public int getHealth() {
			return health;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getMaxHealth() {
			return max_health;
		}

		private int getDefaultDamage() {
			return default_damage;
		}
	}

	private static class FreezeDroid implements Droid {
		private static final int default_damage = 10;
		private static final String name = "FreezeDroid";
		private static final int max_health = 140;
		private static final int max_holdings_count = 6;
		private static final int hold_chance = 3;

		private int health;
		private int holding;

		private int holdings_count;

		public FreezeDroid() {
			health = max_health;
			holding = 0;

			holdings_count = max_holdings_count;
		}

		@Override
		public FreezeDroid clone() {
			return new FreezeDroid();
		}

		@Override
		public void hit(Droid other, FileWriter writer) {
			int damage;
			boolean hold_flag;
			int other_health;

			if (holding > 0) {
				Main.printlnWithFile("Frozen", writer);

				holding--;
				return;
			}

			damage = getDefaultDamage();
			hold_flag = (Main.random.nextInt(hold_chance) == 1) && (holdings_count > 0);
			other_health = other.getHealth() - damage;

			if (hold_flag) {
				Main.printlnWithFile("Froze attack", writer);
				holdings_count--;
			}
			Main.printlnWithFile("Damage " + (other.getHealth() - other_health), writer);

			other.setHealth(other_health);
			other.setHolding((hold_flag) ? 1 : 0);
		}

		@Override
		public String toString() {
			String info = "";

			info += "--- " + getName() + " ---\n";
			info += "health: " + getMaxHealth() + "\n";
			info += "damage: " + getDefaultDamage() + "\n";
			info += "--- " + getName() + " ---\n";

			return info;
		}

		@Override
		public void setHealth(int health) {
			this.health = Math.max(health, 0);
		}

		@Override
		public void setHolding(int holding) {
			this.holding = holding;
		}

		@Override
		public int getHealth() {
			return health;
		}

		private int getDefaultDamage() {
			return default_damage;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getMaxHealth() {
			return max_health;
		}
	}

	private static class FireDroid implements Droid {
		private static final int default_damage = 15;
		private static final String name = "FireDroid";
		private static final int max_health = 130;
		private static final int fire_chance = 3;

		private int health;
		private int holding;

		public FireDroid() {
			health = max_health;
			holding = 0;
		}

		@Override
		public FireDroid clone() {
			return new FireDroid();
		}

		@Override
		public void hit(Droid other, FileWriter writer) {
			boolean fire_flag;
			int damage;
			int other_health;

			if (holding > 0) {
				Main.printlnWithFile("Frozen", writer);

				holding--;
				return;
			}

			fire_flag = Main.random.nextInt(fire_chance) == 1;
			damage = getDefaultDamage();
			other_health = (!fire_flag) ? other.getHealth() - damage : other.getHealth() / 2;

			if (fire_flag) {
				Main.printlnWithFile("Fiiiree!!!!", writer);
			}
			Main.printlnWithFile("Damage " + (other.getHealth() - other_health), writer);

			other.setHealth(other_health);
		}

		@Override
		public String toString() {
			String info = "";

			info += "--- " + getName() + " ---\n";
			info += "health: " + getMaxHealth() + "\n";
			info += "damage: " + getDefaultDamage() + "\n";
			info += "--- " + getName() + " ---\n";

			return info;
		}

		@Override
		public void setHealth(int health) {
			this.health = Math.max(health, 0);
		}

		@Override
		public void setHolding(int holding) {
			this.holding = holding;
		}

		@Override
		public int getHealth() {
			return health;
		}

		private int getDefaultDamage() {
			return default_damage;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getMaxHealth() {
			return max_health;
		}
	}

	// functions
	public static Droid getDroid() {
		int droid_index;

		System.out.println("\n--- Game.Droids ---");
		printDroidsIndexes();
		System.out.println("--- Game.Droids ---");

		droid_index = Main.enterIntValue("Choose Droid:", 0, getDroidsCount() - 1);

		return getDroidByIndex(droid_index);
	}

	public static Droid getDroidByIndex(int index) {
		ArrayList<Droid> droids = getDroidsList();

		return droids.get(index).clone();
	}

	public static int getDroidsCount() {
		ArrayList<Droid> droids = getDroidsList();

		return droids.size();
	}

	private static ArrayList<Droid> getDroidsList() {
		ArrayList<Droid> droids = new ArrayList<>();

		droids.add(new SimpleDroid());
		droids.add(new SuperDroid());
		droids.add(new FreezeDroid());
		droids.add(new FireDroid());

		return droids;
	}

	public static void printDroidsInfo() {
		ArrayList<Droid> droids = getDroidsList();
		System.out.println("\n~~~ Game.Droids ~~~");

		for (int i = 0;i < droids.size();i++) {
			System.out.println(droids.get(i));
		}
	}

	private static void printDroidsIndexes() {
		ArrayList<Droid> droids = getDroidsList();

		for (int i = 0;i < droids.size();i++) {
			System.out.print(i);
			System.out.println(" - " + droids.get(i).getName());
		}
	}
}

