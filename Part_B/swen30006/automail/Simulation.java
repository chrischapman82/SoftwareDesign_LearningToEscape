package automail;


import exceptions.MailAlreadyDeliveredException;
import strategies.Automail;

import java.util.ArrayList;


/**
 * This class simulates the behaviour of AutoMail
 */
public class Simulation {
	
    private static ArrayList<MailItem> MAIL_DELIVERED = new ArrayList<MailItem>();
    private static double total_score = 0;

    public static void main(String[] args) { 

    	PropertiesLoader.loadProperties();

        Automail automail = new Automail(new ReportDelivery());
        MailGenerator generator = new MailGenerator();
        
        /** Initiate all the mail */
        ArrayList<MailItem> mail;
        
        while(MAIL_DELIVERED.size() != generator.MAIL_AMOUNT) {
        	//get all mail for the current time
            mail = generator.getMail();
            //check if there is any mail to "arrive" at the current time;
            if(mail != null) {
            	//do have new mail, add the mail to the pool
            	automail.addIncomingMail(mail);
            }
            //continue simulation
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
