package util;

/**
 * Created by T on 11/06/2016.
 */

public enum Glory {

	GLORY1(1706, 1), GLORY2(1708, 2), GLORY3(1710, 3), GLORY4(1712, 4), GLORY5(11976, 5), GLORY6(11978, 6);

	Glory(int id, int charges) {
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
