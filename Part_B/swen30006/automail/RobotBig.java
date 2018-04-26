package automail;

import strategies.IMailPool;

public class RobotBig extends Robot {

	public static final boolean IS_STRONG = false;
	
	public RobotBig(IMailDelivery delivery, IMailPool mailPool) {
		super(delivery, mailPool, IS_STRONG);
	}
}
