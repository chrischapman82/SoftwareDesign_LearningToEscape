package automail;

import strategies.IMailPool;


/**
 * A weak robot that is a variation of the standard robot that is unable to lift
 * heavy items and can carry 4 items in a tube.
 */
public class RobotWeak extends Robot {

	public static final boolean IS_STRONG = false;
	public static final int MAX_TUBE_CAPACITY = 4;
	public static final int MAX_WEIGHT = 2000;
	public static final String TAG = "weak";
	
	public RobotWeak() {
		super(IS_STRONG, MAX_TUBE_CAPACITY);

	}
}
