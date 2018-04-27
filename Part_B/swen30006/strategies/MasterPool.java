package strategies;

import java.util.ArrayList;
import java.util.ListIterator;

import automail.Building;
import automail.MailItem;
import automail.PriorityMailItem;
import automail.Robot;
import strategies.IMailPool;

public class MasterPool implements IMasterPool {

	// we have determined there to be 2 robots and 2 sub-pools in this implementation
	private static final int NUM_ROBOTS = 2;
	private static final int NUM_POOLS = 2;
	private static final int MAX_WEIGHT = 2000;
	private int divider;
	
	private IMailPool upperPool;
	private IMailPool lowerPool;
	
	public MasterPool(ArrayList<Robot> robots) {
		
		divider = Building.FLOORS / 2;
		
		assert(robots.size() == NUM_ROBOTS);
		
		// construct upper pool and give it to robot 1
		upperPool = new UpperPool();
		robots.get(0).setMailPool(upperPool);
		
		// construct lower pool and give it to robot 2
		lowerPool = new LowerPool();
		robots.get(1).setMailPool(lowerPool);
	}
	
	public void distributeMail(MailItem mailItem) {
		
		// if it's a priority or heavy or upper floor
		if (mailItem instanceof PriorityMailItem || mailItem.getWeight() > MAX_WEIGHT || mailItem.getDestFloor() <= divider) {
			lowerPool.addToPool(mailItem); // Just add it on the end of the lower (strong robot) list
		}
		else{
			upperPool.addToPool(mailItem); // Just add it on the end of the upper (weak robot) list
		}
	}
	
}
