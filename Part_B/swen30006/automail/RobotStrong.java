package automail;

import strategies.IMailPool;

public class RobotStrong extends Robot {

	public static final boolean IS_STRONG = true;
	
	public RobotStrong(IMailDelivery delivery, IMailPool mailPool) {
		super(delivery, mailPool, IS_STRONG);
	}
}
