package automail;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public final class PropertiesLoader {
	/** The variables specified in the properties file. **/
	private static Double timePenalty;
	private static Integer mailToCreate;
	private static Integer seed;
	private static Integer lastDeliveryTime;
	private static Integer floorsInBuilding;
	private static ArrayList<String> robotTypes;
	
	public static void loadProperties() {
		// Properties allows usage of a properties file to set variiables
    	Properties automailProperties = new Properties();
		
    	//read from file
		try {
			FileReader inStream = null;
			inStream = new FileReader("automail.Properties");
			automailProperties.load(inStream);
			if (inStream != null) {
                inStream.close();
            }
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		//list of variables set in properties file. further vars will need to be added manually.
		timePenalty = Double.parseDouble(automailProperties.getProperty("Delivery_Penalty"));
		mailToCreate = Integer.parseInt(automailProperties.getProperty("Mail_to_Create"));
		seed = Integer.parseInt(automailProperties.getProperty("Seed"));
        lastDeliveryTime = Integer.parseInt(automailProperties.getProperty("Last_Delivery_Time"));
        floorsInBuilding = Integer.parseInt(automailProperties.getProperty("Number_of_Floors"));
        
        // adding in the robot types
        // Only takes in the form Robot_Type_i  as given
        String robotType;
        int i = 1;
        robotTypes = new ArrayList<>();
        while ((robotType = automailProperties.getProperty("Robot_Type_" + Integer.toString(i))) != null) {
        	robotTypes.add(robotType);
        	i++;
        }
	}

	/**
	 * @return A Double representing the scaling of penalty for slow delivery of items
	 */
	public static Double getTimePenalty() {
		return timePenalty;
	}

	/**
	 * @return Integer representing the initial specification for amount of mail to create
	 */
	public static Integer getMailToCreate() {
		return mailToCreate;
	}

	/**
	 * @return Integer representing the seed value for random generation in the simulation
	 */
	public static Integer getSeed() {
		return seed;
	}

	/**
	 * @return Integer representing the cutoff time for new mail to arrive
	 */
	public static Integer getLastDeliveryTime() {
		return lastDeliveryTime;
	}

	/**
	 * @return Integer amount of floors in the building for the simulation
	 */
	public static Integer getFloorsInBuilding() {
		return floorsInBuilding;
	}
	
	/**
	 * @return Arraylist of strings, each entry will specify the type of robot to be used in simulation
	 */
	public static ArrayList<String> getRobotTypes() {
		return robotTypes;
	}

}
