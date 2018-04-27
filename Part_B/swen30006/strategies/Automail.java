package strategies;

import java.util.ArrayList;

import automail.IMailDelivery;
import automail.PropertiesLoader;
import automail.ReportDelivery;
import automail.Robot;
import automail.RobotBig;
import automail.RobotStrong;
import automail.RobotWeak;
import automail.MailItem;
import automail.PriorityMailItem;
import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;

public class Automail {
	
    public ArrayList<Robot> robots;
    public IMailPool mailPool;
    
    // I am unsure about whether this is the right place to put these...
    public static final String BOT_WEAK = "weak";
    public static final String BOT_STRONG = "strong";
    public static final String BOT_BIG = "big";
    
    public Automail() {
    	    	
    	// Initialise the robots arraylist
    	robots = new ArrayList<>();
    	
    	/** Initialize the MailPool */
    	mailPool = new WeakStrongMailPool();
    	/*
    	String robot1Type = PropertiesLoader.getRobot1Type();
    	String robot2Type = PropertiesLoader.getRobot2Type();
    	
    	if(robot1Type == BOT_WEAK && robot2Type == BOT_WEAK) {
    		System.err.println("Two weak robots is too weak");
    		System.exit(0);
    	}*/
    	
    	//initialise robots based on specifications in properties file   	
    	for (String robotType : PropertiesLoader.getRobotTypes()) {
    		robots.add(createRobot(mailPool, robotType));
    	}
    }
    
    /* 
     * Creates and returns a robot based off of:
     * robotName	- The type of robot to be created
     * delivery		- The delivery used
     * mailPool		- The shared mailPool for all robots
     */
    public Robot createRobot(IMailPool mailPool, String robotName) {
    	switch (robotName) {
    	case (BOT_WEAK):
    		return new RobotWeak(mailPool);
    	case (BOT_STRONG):
    		return new RobotStrong(mailPool);
    	case (BOT_BIG):
    		return new RobotBig(mailPool);
    	default:
    		return null;
    	}
    }
    
    // adds an array list of mail to mail pool and notifies all robots if any priority items
    public void addIncomingMail(ArrayList<MailItem> mail) {
    	// add the mail to the pool
        for(MailItem m: mail) {
        	mailPool.addToPool(m);
        	//check for priority mail to send priority arrival alert to robots
        	if (m instanceof PriorityMailItem) {
        		PriorityMailItem pmail = (PriorityMailItem) m;
            	priorityArrival(pmail.getPriorityLevel(), m.getWeight());
        	}
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
