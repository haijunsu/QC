package utils;

import java.util.ResourceBundle;

/**
 * Application configuration. Load information from resources/auth.properties
 * 
 * Date Dec 7, 2014
 * 
 * @author Haijun Su
 * @author Youchen Ren
 *
 */
public class Configuration {

	/**
	 * logger adapter
	 */
	private static Logger logger = Logger.getLogger(Configuration.class);

	/**
	 * Bundle resource to resources/auth.properties
	 */
	private static ResourceBundle props = ResourceBundle
			.getBundle("resources.auth");

	/**
	 * Authentication service name
	 */
	public static final String SERVICE_NAME = "authService";

	/**
	 * Remote server name
	 */
	public static String SERVER_NAME = "localhost";

	/**
	 * Server port
	 */
	public static int SERVER_PORT = 24680;

	/**
	 * Initial configuration. If isClient is true, it also initial SERVER_NAME
	 * value
	 * 
	 * @param isClient
	 */
	public static void init(boolean isClient) {
		// set debug level
		String level = props.getString("debug.level");
		if (level != null) {
			try {
				Logger.logLevel = Integer.parseInt(level);
			} catch (NumberFormatException e) {
				logger.error("Can't set the debug level. error: debug.level is not a number. debug.level="
						+ level);
			}
		}
		logger.info("DEBUG_LEVEL=" + Logger.logLevel);

		// set server name
		if (isClient) {
			String host = props.getString("server.name");
			if (host != null) {
				SERVER_NAME = host;
			}
			logger.info("SERVER_NAME=" + SERVER_NAME);
		}

		// set server port
		String port = props.getString("server.port");
		if (port != null) {
			try {
				SERVER_PORT = Integer.parseInt(port);
			} catch (NumberFormatException e) {
				logger.error("Can't set the server port. error: server.port is not a number. server.port="
						+ port);
			}
		}
		logger.info("SERVER_PORT=" + SERVER_PORT);
	}

	/**
	 * Application information
	 * 
	 * @return
	 */
	public static String appInfo() {
		return "********************************************************"
				+ System.lineSeparator()
				+ "* This is RMI porject of course CS715 in Fall 2014.    *"
				+ System.lineSeparator()
				+ "* Students are Haijun Su and Youchen Ren.              *"
				+ System.lineSeparator()
				+ "********************************************************";
	}
}
