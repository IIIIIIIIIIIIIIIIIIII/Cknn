package util;

import org.dreambot.api.methods.map.Tile;

public class Vars {

	private static final Tile GE = new Tile(3165, 3477, 0);
	private static final Tile BOOTH = new Tile(3165, 3484, 0);
	private static final int CLERK = 2148;
	private final static Tile DWARF = new Tile(3015, 3453, 0);
	private final static Tile GLORY = new Tile(3087, 3496, 0);
	private final static int GLORY0 = 1704;
	private final static int RING0 = 2572;
	private final static int CANNON_SET = 12863;
	private final static int COINS = 995;

	public static int getCannonSet() {
		return CANNON_SET;
	}

	public static int getGloryId() {
		return GLORY0;
	}

	public static int getRingId() {
		return RING0;
	}

	public static Tile getGe() {
		return GE;
	}

	public static Tile getBooth() {
		return BOOTH;
	}

	public static int getClerk() {
		return CLERK;
	}

	public static Tile getDwarf() {
		return DWARF;
	}

	public static Tile getGlory() {
		return GLORY;
	}

	public static int getCoins() {
		return COINS;
	}
}
