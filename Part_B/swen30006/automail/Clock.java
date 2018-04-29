package automail;

public class Clock {
	
	/** Represents the current time **/
    private static int Time = 0;
    
    /** The threshold for the latest time for mail to arrive **/
    public static final int LAST_DELIVERY_TIME = PropertiesLoader.getLastDeliveryTime();
    
    /**
     * 
     * @return int representing the current time of the Clock
     */
    public static int Time() {
    	return Time;
    }
    
    /**
     * increment the time of the Clock by one
     */
    public static void Tick() {
    	Time++;
    }
}
