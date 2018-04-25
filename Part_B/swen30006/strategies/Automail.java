package strategies;

import automail.IMailDelivery;
import automail.PropertiesLoader;
import automail.Robot;

public class Automail {
	      
    public Robot robot1, robot2;
    public IMailPool mailPool;
    
    public Automail(IMailDelivery delivery) {
    	// Swap between simple provided strategies and your strategies here
    	    	
    	/** Initialize the MailPool */
    	
    	//// Swap the next line for the one below
    	mailPool = new WeakStrongMailPool();
    	
    	/** Initialize the RobotAction */
    	boolean weak = false;  // Can't handle more than 2000 grams
    	boolean strong = true; // Can handle any weight that arrives at the building
    	

        
    	
    	//// Swap the next two lines for the two below those
    	IRobotBehaviour robotBehaviourW = new MyRobotBehaviour(weak);
    	IRobotBehaviour robotBehaviourS = new MyRobotBehaviour(strong);
    	//initialise robots based on specifications in properties file
    	if (PropertiesLoader.getRobot1Type().equals("weak")) {
    		robot1 = new Robot(robotBehaviourW, delivery, mailPool, weak);
    	} else if (PropertiesLoader.getRobot1Type().equals("strong")) {
    		robot1 = new Robot(robotBehaviourS, delivery, mailPool, strong);
    	}
    	if (PropertiesLoader.getRobot2Type().equals("weak")) {
    		robot2 = new Robot(robotBehaviourW, delivery, mailPool, weak);
    	} else if (PropertiesLoader.getRobot2Type().equals("strong")) {
    		robot2 = new Robot(robotBehaviourS, delivery, mailPool, strong);
    	}
    }
}
