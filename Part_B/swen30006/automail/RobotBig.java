package automail;

import strategies.IMailPool;


/**
 * RobotBig is is a variation of the standard robot that is able to lift
 * heavy items and can carry 6 items in a tube.
 */
public class RobotBig extends Robot {

	public static final boolean IS_STRONG = false;
	public static final int MAX_TUBE_CAPACITY = 6;
	public static final String TAG = "big";
	
	public RobotBig() {
		super(IS_STRONG, MAX_TUBE_CAPACITY);

	}
}
