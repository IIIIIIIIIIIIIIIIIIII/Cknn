package util;

import org.dreambot.api.methods.map.Tile;

/**
 * Created by T on 11/06/2017.
 */

public class Dis {

	public static boolean eQ(Tile current, Tile eq, Tile destination) {
		return current.distance(destination) > eq.distance(destination);
	}

	public static boolean eQ(Tile current, Tile eq, Tile destination, int ex) {
		return current.distance(destination) > eq.distance(destination) + ex;
	}
}
