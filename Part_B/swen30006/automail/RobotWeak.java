package automail;

import strategies.IMailPool;

public class RobotWeak extends Robot {

	public static final boolean IS_STRONG = false;
	
	public RobotWeak(IMailDelivery delivery, IMailPool mailPool) {
		super(delivery, mailPool, IS_STRONG);
	}
}
