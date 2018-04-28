package strategies;

import java.util.ArrayList;

import automail.Building;
import automail.MailItem;
import automail.PriorityMailItem;
import automail.Robot;
import automail.RobotBig;
import automail.RobotStrong;
import automail.RobotWeak;

public class MasterPool implements IMasterPool {

	// we have determined there to be 2 robots and 2 sub-pools in this implementation
	private static final int NUM_ROBOTS = 2;		// The number of robots in the system	
	
	private int divider;				// The floor that divides the building in half
	private ArrayList<Robot> robots;	// The robots that are being used
	private IMailPool upperPool;		// The pool for the upper half of the building
	private IMailPool lowerPool;		// The pool for the lower half of the building
	
	public MasterPool(ArrayList<Robot> robots) {
		
		divider = Building.FLOORS / 2;	// Sets divider to the floor in the middle (floored)
		upperPool = new SubPool();		// The mail pool for the upper half of the building
		lowerPool = new SubPool();		// The mail pool for the lower half of the building
		
		this.robots = robots;			// Stores the robots for later use
		
		// Confirms correct number and type of robots
		assert(robots.size() == NUM_ROBOTS);
		
		if(robots.get(0) instanceof RobotWeak && robots.get(1) instanceof RobotWeak) {
			System.out.println("Invalid configuration");
			System.exit(0);
		}
		
		// Assign the current robots to their respective pools
		assignRobotsToPools(robots.get(0), robots.get(1));
	}
	
	/* Assigns robots to different pools based on the given ruleset. Works for 2 robots.
	 * 
	 * 1. A weak robot will always be set to the upper pool
	 * 2. If there is a strong robot and a big robot.
	 * 		Big -> upper, Strong -> lower
	 * 3. For Strong-Strong & Big-Big, assign one to each
	 */
	public void assignRobotsToPools(Robot robot1, Robot robot2) {
		
		if(robot1 instanceof RobotWeak) {
			robot1.setMailPool(upperPool);
			robot2.setMailPool(lowerPool);
		}
		else if(robot2 instanceof RobotWeak) {
			robot2.setMailPool(upperPool);
			robot1.setMailPool(lowerPool);
		}
		else if(robot1 instanceof RobotBig && robot2 instanceof RobotStrong) {
			robot1.setMailPool(upperPool);
			robot2.setMailPool(lowerPool);
		}
		else if(robot2 instanceof RobotBig && robots.get(0) instanceof RobotStrong) {
			robot2.setMailPool(upperPool);
			robot1.setMailPool(lowerPool);
		}
		else {
			robot1.setMailPool(upperPool);
			robot2.setMailPool(lowerPool);
		}
	}
	
	
	/* 
	 * Distribute mail according to the given rule set:
	 * 	1. If mail is a priority item -> add to lower
	 * 	2. If there is a weak robot, give all heavy mail to upper
	 * 	3. Otherwise, give all mail to their given half o the building
	 * 
	 * @param mailItem - The mail to be distributed to the pools stored within MasterPool
	 */
	public void distributeMail(MailItem mailItem) {
		
		if (mailItem instanceof PriorityMailItem) {
			lowerPool.addToPool(mailItem);
			return;
		} 
		if (robots.get(0) instanceof RobotWeak || robots.get(1) instanceof RobotWeak) {
			if (mailItem.getWeight() > RobotWeak.MAX_WEIGHT ||  mailItem.getDestFloor() <= divider) {
				lowerPool.addToPool(mailItem);
			}
			else {
				upperPool.addToPool(mailItem);
			}
			return;
		} 
		
		if (mailItem.getDestFloor() <= divider) {
			lowerPool.addToPool(mailItem);
		}
		else {
			upperPool.addToPool(mailItem);
		}
	}
}
