package automail;

import java.util.*;

import strategies.IMailPool;

/**
 * This class generates the mail.
 * Constants in this class are based on observations of typical mail arrivals
 */
public class MailGenerator {

    public final int MAIL_TO_CREATE;

    private int mailCreated;

    private final Random random;
    
    private HashMap<Integer,ArrayList<MailItem>> allMail;

    /**
     * Constructor for mail generation
     * @param mailToCreate roughly how many mail items to create
     * @param mailPool where mail items go on arrival
     * @param seed random seed for generating mail
     */

    public MailGenerator(){
    	// This seed is used to make the behaviour deterministic */
        if((PropertiesLoader.getSeed()) != null){
        	this.random = new Random((long) PropertiesLoader.getSeed());
        }
        else{
        	this.random = new Random();	
        }
        // Vary arriving mail by +/-20%
        int mailToCreate = PropertiesLoader.getMailToCreate();
        MAIL_TO_CREATE = mailToCreate*4/5 + random.nextInt(mailToCreate*2/5);
        // System.out.println("Num Mail Items: "+MAIL_TO_CREATE);
        
        mailCreated = 0;
        allMail = new HashMap<Integer,ArrayList<MailItem>>();
    }

    /**
     * @return a new mail item that needs to be delivered
     */
    private MailItem generateMail(){
        int dest_floor = generateDestinationFloor();
        int priority_level = generatePriorityLevel();
        int arrival_time = generateArrivalTime();
        int weight = generateWeight();
        // Check if arrival time has a priority mail
        if(	(random.nextInt(6) > 0) ||  // Skew towards non priority mail
        	(allMail.containsKey(arrival_time) &&
        	allMail.get(arrival_time).stream().anyMatch(e -> PriorityMailItem.class.isInstance(e))))
        {
        	return new MailItem(dest_floor,arrival_time,weight);      	
        } else {
        	return new PriorityMailItem(dest_floor,arrival_time,weight,priority_level);
        }   
    }


    /**
     * This class initializes all mail and sets their corresponding values,
     */
    public void generateAllMail(){
        while(!(mailCreated == MAIL_TO_CREATE)){
            MailItem newMail =  generateMail();
            int timeToDeliver = newMail.getArrivalTime();
            /** Check if key exists for this time **/
            if(allMail.containsKey(timeToDeliver)){
                /** Add to existing array */
                allMail.get(timeToDeliver).add(newMail);
            }
            else{
                /** If the key doesn't exist then set a new key along with the array of MailItems to add during
                 * that time step.
                 */
                ArrayList<MailItem> newMailList = new ArrayList<MailItem>();
                newMailList.add(newMail);
                allMail.put(timeToDeliver,newMailList);
            }
            /** Mark the mail as created */
            mailCreated++;

        }

    }
    
    /**
     * While there are steps left, create a new mail item to deliver
     * @return Priority
     */
    public ArrayList<MailItem> getMail(){
    	// Check if there are any mail to create
        if(this.allMail.containsKey(Clock.Time())){
        	return allMail.get(Clock.Time());
        }else {
        	//ArrayList<MailItem> emptyArray = new ArrayList<MailItem>();
        	return null;
        }
    }
    

    /**
     * @return a destination floor between the ranges of GROUND_FLOOR to FLOOR
     */
    private int generateDestinationFloor(){
        return Building.LOWEST_FLOOR + random.nextInt(Building.FLOORS);
    }

    /**
     * @return a random priority level selected from 10 and 100
     */
    private int generatePriorityLevel(){
        return random.nextInt(4) > 0 ? 10 : 100;
    }
    
    /**
     * @return a random weight
     */
    private int generateWeight(){
    	final double mean = 200.0; // grams for normal item
    	final double stddev = 700.0; // grams
    	double base = random.nextGaussian();
    	if (base < 0) base = -base;
    	int weight = (int) (mean + base * stddev);
        return weight > 5000 ? 5000 : weight;
    }
    
    /**
     * @return a random arrival time before the last delivery time
     */
    private int generateArrivalTime(){
        return 1 + random.nextInt(Clock.LAST_DELIVERY_TIME);
    }

    /**
     * Returns a random element from an array
     * @param array of objects
     */
    private Object getRandom(Object[] array){
        return array[random.nextInt(array.length)];
    }
}
