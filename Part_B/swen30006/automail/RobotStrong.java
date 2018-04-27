package automail;

import strategies.IMailPool;

public class RobotStrong extends Robot {

	public static final boolean IS_STRONG = true;
	public static final int MAX_TUBE_CAPACITY = 4;
	
	public RobotStrong(IMailPool mailPool) {
		super(mailPool, IS_STRONG, MAX_TUBE_CAPACITY);
	}
}
