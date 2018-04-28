package strategies;

import java.util.ArrayList;

import automail.*;
import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;

/**
 * @author Project Group 13
 * Christopher Chapman, Raymond Sun, Liam Whittle
 *  
 */
public class Automail {
	
    public ArrayList<Robot> robots;	// List of all robots
    public MasterPool masterPool;	// TODO
    
    public Automail() {
    	robots = new ArrayList<>();				// Init list of robots

    	// Create robots based on specifications in properties file

    	for (String robotType : PropertiesLoader.getRobotTypes()) {
    		robots.add(createRobot(robotType));
    	}
    	
    	// Init master pool
    	masterPool = new MasterPool(robots);
    }    
    
    /* 
     * Creates and returns a robot based off of:
     * robotName	- The type of robot to be created
     * delivery		- The delivery strategy used
     * mailPool		- The shared mailPool strategy used for all robots
     * 
     * @param robotName - The string tag describing a robot type. Eg. "weak"
     * @return robot	- Returns the newly created robot
     * Returns null if robotName does not correspond to a robot type
     */
    public Robot createRobot(String robotName) {
    	switch (robotName) {
    	case (RobotWeak.TAG):
    		return new RobotWeak();
    	case (RobotStrong.TAG):
    		return new RobotStrong();
    	case (RobotBig.TAG):
    		return new RobotBig();
    	default:
    		return null;
    	}
    }
    
    /** Takes a list of mail, adding it to mail pool and notifying 
     * robots about priority items
     * 
     * @param mail A list of the incoming mail
     */
    public void addIncomingMail(ArrayList<MailItem> mail) {
    	// add the mail to the pool
        for(MailItem m: mail) {
        	masterPool.distributeMail(m);
        	//check for priority mail to send priority arrival alert to robots
        	if (m instanceof PriorityMailItem) {
        		PriorityMailItem pmail = (PriorityMailItem) m;
            	priorityArrival(pmail.getPriorityLevel(), m.getWeight());
        	}
        }
    }
    
    
    /**
     * Notifies robots of an arriving mailItem, sending information about
     * the priority level and weight.
     * 
     * @param priorityLevel	- The priority level of an arriving mailItem
     * @param weight - The weight of an arriving mailItem
     */
    public void priorityArrival(int priorityLevel, int weight) {
    	for (Robot r: robots) {
    		r.behaviour.priorityArrival(priorityLevel, weight);
    	}
    }
    
    // TODO double check this
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
