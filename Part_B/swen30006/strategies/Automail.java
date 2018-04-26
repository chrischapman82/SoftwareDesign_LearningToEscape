package strategies;

import java.util.ArrayList;

import automail.IMailDelivery;
import automail.Robot;
import automail.RobotBig;
import automail.RobotStrong;
import automail.RobotWeak;

public class Automail {
	
    public ArrayList<Robot> robots;
    public IMailPool mailPool;
    
    
    // TODO I am unsure about whether this is the right place to put these...
    public static final String BOT_WEAK = "weak robot";
    public static final String BOT_STRONG = "strong robot";
    public static final String BOT_BIG = "big robot";
    
    public Automail(IMailDelivery delivery) {
    	// Swap between simple provided strategies and your strategies here
    	    	
    	// Initialise the robots arraylist
    	robots = new ArrayList<>();
    	
    	/** Initialize the MailPool */
    	mailPool = new WeakStrongMailPool();
    	
    	// REMOVED THE BEHAVIOUR HERE
    	    	
    	// for adding types of robots
    	robots.add(createRobot(delivery, mailPool, BOT_WEAK));
    	robots.add(createRobot(delivery, mailPool, BOT_STRONG));
    }
    
    
    /* TODO added chris 24 apr
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
    
    
}
