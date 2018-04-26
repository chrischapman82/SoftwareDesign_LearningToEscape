package automail;

import strategies.IMailPool;

public class RobotBig extends Robot {

	public static final boolean IS_STRONG = false;
	public static final int MAXIMUM_TUBE_CAPACITY = 6;
	
	public RobotBig(IMailDelivery delivery, IMailPool mailPool) {
		super(delivery, mailPool, MAXIMUM_TUBE_CAPACITY, IS_STRONG);
	}
}
