package automail;

import strategies.IMailPool;

public class RobotStrong extends Robot {

	public static final boolean IS_STRONG = true;
	public static final int MAXIMUM_CAPACITY = 4;
	
	public RobotStrong(IMailDelivery delivery, IMailPool mailPool) {
		super(delivery, mailPool, MAXIMUM_CAPACITY, IS_STRONG);
	}
}
