package strategies;

import java.util.ArrayList;

import automail.IMailDelivery;
import automail.PropertiesLoader;
import automail.Robot;
import automail.RobotBig;
import automail.RobotStrong;
import automail.RobotWeak;
import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;

public class Automail {
	
    public ArrayList<Robot> robots;
    public IMailPool mailPool;
    
    
    // TODO I am unsure about whether this is the right place to put these...
    public static final String BOT_WEAK = "weak";
    public static final String BOT_STRONG = "strong";
    public static final String BOT_BIG = "big";
    
    public Automail(IMailDelivery delivery) {
    	// Swap between simple provided strategies and your strategies here
    	    	
    	// Initialise the robots arraylist
    	robots = new ArrayList<>();
    	
    	/** Initialize the MailPool */
    	mailPool = new WeakStrongMailPool();
    	String robot1Type = PropertiesLoader.getRobot1Type();
    	String robot2Type = PropertiesLoader.getRobot2Type();
    	if(robot1Type == BOT_WEAK && robot2Type == BOT_WEAK) {
    		System.err.println("Two weak robots is too weak");
    		System.exit(0);
    	}
    	//initialise robots based on specifications in properties file
    	
    	robots.add(createRobot(delivery, mailPool, robot1Type));
    	robots.add(createRobot(delivery, mailPool, robot2Type));
    }
    
    
    /* 
     * Creates and returns a robot based off of:
     * robotName	- The type of robot to be created
     * delivery		- The delivery used
     * mailPool		- The shared mailPool for all robots
     */
    public Robot createRobot(IMailDelivery delivery, IMailPool mailPool, String robotName) {
    	switch (robotName) {
    	case (BOT_WEAK):
    		return new RobotWeak(delivery, mailPool);
    	case (BOT_STRONG):
    		return new RobotStrong(delivery, mailPool);
    	case (BOT_BIG):
    		return new RobotBig(delivery, mailPool);
    	default:
    		return null;
    	}
    }
    
    public void priorityArrival(int priorityLevel, int weight) {
    	for (Robot r: robots) {
    		r.behaviour.priorityArrival(priorityLevel, weight);
    	}
    }
    
    public void step() {
    	for(Robot r: robots) {
    		try {
				r.step();
			} catch (ExcessiveDeliveryException|ItemTooHeavyException e) {
				e.printStackTrace();
				System.out.println("Simulation unable to complete.");
				System.exit(0);
			}
	    }
	}
}
