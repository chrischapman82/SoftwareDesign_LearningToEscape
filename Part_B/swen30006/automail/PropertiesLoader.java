package automail;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public final class PropertiesLoader {
	private static Double timePenalty;
	private static Integer mailToCreate;
	private static Integer seed;
	private static Integer lastDeliveryTime;
	private static Integer floorsInBuilding;
	private static String robot1Type;
	private static String robot2Type;
	
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
        robot1Type = automailProperties.getProperty("Robot_Type_1");
        robot2Type = automailProperties.getProperty("Robot_Type_2");
	}

	public static Double getTimePenalty() {
		return timePenalty;
	}

	public static Integer getMailToCreate() {
		return mailToCreate;
	}

	public static Integer getSeed() {
		return seed;
	}

	public static Integer getLastDeliveryTime() {
		return lastDeliveryTime;
	}

	public static Integer getFloorsInBuilding() {
		return floorsInBuilding;
	}
	public static String getRobot1Type() {
		return robot1Type;
	}

	public static String getRobot2Type() {
		return robot2Type;
	}

}
