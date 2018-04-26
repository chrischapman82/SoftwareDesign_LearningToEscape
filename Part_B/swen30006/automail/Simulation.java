package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
import exceptions.MailAlreadyDeliveredException;
import strategies.Automail;

import java.util.ArrayList;


/**
 * This class simulates the behaviour of AutoMail
 */
public class Simulation {
	
    private static ArrayList<MailItem> MAIL_DELIVERED;
    private static double total_score = 0;

    public static void main(String[] args) { //throws IOException {
    	System.out.println("hello");
    	PropertiesLoader.loadProperties();

        Automail automail = new Automail(new ReportDelivery());
        MailGenerator generator = new MailGenerator();
        
        /** Initiate all the mail */
        generator.generateAllMail();
        ArrayList<MailItem> mail;
        MAIL_DELIVERED = new ArrayList<MailItem>();
        while(MAIL_DELIVERED.size() != generator.MAIL_TO_CREATE) {
        	//System.out.println("-- Step: "+Clock.Time());
            mail = generator.getMail();
            if(mail != null) {
	            for(MailItem m: mail) {
	            	automail.mailPool.addToPool(m);
	            	if (m instanceof PriorityMailItem) {
	            		PriorityMailItem pmail = (PriorityMailItem) m;
	            		automail.robots.get(0).behaviour.priorityArrival(pmail.getPriorityLevel(), m.weight);
	                	automail.robots.get(1).behaviour.priorityArrival(pmail.getPriorityLevel(), m.weight);
	            	}
	            }
            }
            automail.step();

            Clock.Tick();
        }
        printResults();
    }
    
    static class ReportDelivery implements IMailDelivery {
    	/** Confirm the delivery and calculate the total score */
    	public void deliver(MailItem deliveryItem){
    		if(!MAIL_DELIVERED.contains(deliveryItem)){
                System.out.printf("T: %3d > Delivered     [%s]%n", Clock.Time(), deliveryItem.toString());
    			MAIL_DELIVERED.add(deliveryItem);
    			// Calculate delivery score
    			total_score += calculateDeliveryScore(deliveryItem);
    		}
    		else{
    			try {
    				throw new MailAlreadyDeliveredException();
    			} catch (MailAlreadyDeliveredException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }
    
    private static double calculateDeliveryScore(MailItem deliveryItem) {
    	// Penalty for longer delivery times
    	double priority_weight = 0;
        // Take (delivery time - arrivalTime)**penalty * (1+sqrt(priority_weight))
    	if(deliveryItem instanceof PriorityMailItem){
    		priority_weight = ((PriorityMailItem) deliveryItem).getPriorityLevel();
    	}
        return Math.pow(Clock.Time() - deliveryItem.getArrivalTime(),PropertiesLoader.getTimePenalty())*(1+Math.sqrt(priority_weight));
    }

    public static void printResults(){
        System.out.println("T: "+Clock.Time()+" | Simulation complete!");
        System.out.println("Final Delivery time: "+Clock.Time());
        System.out.printf("Final Score: %.2f%n", total_score);
    }
}
