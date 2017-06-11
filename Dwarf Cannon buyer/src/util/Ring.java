package util;

/**
 * Created by T on 11/06/2016.
 */

public enum Ring {

	RING1(11988, 1), RING2(11986, 2), RING3(11984, 3), RING4(11982, 4), RING5(11980, 5);

	Ring(int id, int charges) {
		this.id = id;
		this.charges = charges;
	}

	private int id;
	private int charges = 0;

	public int getId() {
		return id;
	}

	public int getCharges() {
		return charges;
	}
}
