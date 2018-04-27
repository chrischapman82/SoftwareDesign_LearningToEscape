package automail;

import strategies.IMailPool;

public class RobotWeak extends Robot {

	public static final boolean IS_STRONG = false;
	public static final int MAX_TUBE_CAPACITY = 4;
	
	public RobotWeak(IMailDelivery delivery) {
		super(delivery, IS_STRONG, MAX_TUBE_CAPACITY);
	}
}
