package automail;

import strategies.IMailPool;

public class RobotBig extends Robot {

	public static final boolean IS_STRONG = false;
	public static final int MAX_TUBE_CAPACITY = 6;
	
	public RobotBig() {
		super(IS_STRONG, MAX_TUBE_CAPACITY);

	}
}
