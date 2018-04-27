package automail;

import java.util.ArrayList;

import exceptions.MailAlreadyDeliveredException;

public class ReportDelivery {
	private static ReportDelivery instance = null;
	private ArrayList<MailItem> MAIL_DELIVERED;
	private float total_score = 0;
	
	private ReportDelivery() {
	}
	
	public static ReportDelivery getReportDelivery() {
		if(instance == null) {
			instance = new ReportDelivery();
		}
		return instance;
	}
	
	public void attachMaildelivered(ArrayList<MailItem> mailDelivered) {
		MAIL_DELIVERED = mailDelivered;
	}
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
	
    private double calculateDeliveryScore(MailItem deliveryItem) {
    	// Penalty for longer delivery times
    	double priority_weight = 0;
        // Take (delivery time - arrivalTime)**penalty * (1+sqrt(priority_weight))
    	if(deliveryItem instanceof PriorityMailItem){
    		priority_weight = ((PriorityMailItem) deliveryItem).getPriorityLevel();
    	}
        return Math.pow(Clock.Time() - deliveryItem.getArrivalTime(),PropertiesLoader.getTimePenalty())*(1+Math.sqrt(priority_weight));
    }
    
    public void printResults(){
        System.out.println("T: "+Clock.Time()+" | Simulation complete!");
        System.out.println("Final Delivery time: "+Clock.Time());
        System.out.printf("Final Score: %.2f%n", total_score);
    }
}
