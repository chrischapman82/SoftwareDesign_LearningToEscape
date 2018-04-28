package automail;

import strategies.IMailPool;

/**
 * A strong robot that is a variation of the standard robot that is able to lift
 * heavy items and can carry 4 items in a tube.
 */

public class RobotStrong extends Robot {

	public static final boolean IS_STRONG = true;
	public static final int MAX_TUBE_CAPACITY = 4;
	public static final String TAG = "strong";
	
	public RobotStrong() {
		super(IS_STRONG, MAX_TUBE_CAPACITY);

	}
}
