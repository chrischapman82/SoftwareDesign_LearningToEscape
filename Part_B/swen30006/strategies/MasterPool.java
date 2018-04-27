package strategies;

import java.util.ArrayList;
import java.util.ListIterator;

import automail.Building;
import automail.MailItem;
import automail.PriorityMailItem;
import automail.Robot;
import automail.RobotBig;
import automail.RobotStrong;
import automail.RobotWeak;
import strategies.*;

public class MasterPool implements IMasterPool {

	// we have determined there to be 2 robots and 2 sub-pools in this implementation
	private static final int NUM_ROBOTS = 2;
	private static final int NUM_POOLS = 2;
	private static final int MAX_WEIGHT = 2000;
	private int divider;
	private ArrayList<Robot> robots;
	
	private IMailPool upperPool;
	private IMailPool lowerPool;
	
	public MasterPool(ArrayList<Robot> robots) {
		
		divider = Building.FLOORS / 2;
		
		upperPool = new UpperPool();
		lowerPool = new LowerPool();
		
		this.robots = robots;
		
		// confirm correct number and type of robots
		assert(robots.size() == NUM_ROBOTS);
		if(robots.get(0) instanceof RobotWeak && robots.get(1) instanceof RobotWeak) {
			System.out.println("Invalid configuration");
		}
		
		// assign robots to pools based on types of robots
		if(robots.get(0) instanceof RobotWeak) {
			robots.get(0).setMailPool(upperPool);
			robots.get(1).setMailPool(lowerPool);
		}
		else if(robots.get(1) instanceof RobotWeak) {
			robots.get(1).setMailPool(upperPool);
			robots.get(0).setMailPool(lowerPool);
		}
		else if(robots.get(0) instanceof RobotBig && robots.get(1) instanceof RobotStrong) {
			robots.get(0).setMailPool(upperPool);
			robots.get(1).setMailPool(lowerPool);
		}
		else if(robots.get(1) instanceof RobotBig && robots.get(0) instanceof RobotStrong) {
			robots.get(1).setMailPool(upperPool);
			robots.get(0).setMailPool(lowerPool);
		}
		
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
