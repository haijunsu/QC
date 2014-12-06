package utils;

import java.util.ResourceBundle;

public class Configuration {

	private static ResourceBundle props = ResourceBundle
			.getBundle("resources.auth");
	
	public static final String SERVICE_NAME = "authService";

	public static void init() throws Exception {
		// set debug level
		String level = props.getString("debug.level");
		Logger.logLevel = Integer.parseInt(level);
	}

}
