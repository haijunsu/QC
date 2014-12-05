package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Logger utility
 * @author Haijun Su
 * Date Dec 4, 2014
 *
 */
public class Logger {

	/**
	 * Date format
	 */
	private static SimpleDateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * default info level (warn). Debug levels: 0-Debug, 1-Info, 2-Warn, 3-Error
	 */
	public static int logLevel = 2;

	/**
	 * Which class send out the log
	 */
	private String instanceName;

	private Logger() {
		// not allowed to initial outside
	}

	/**
	 * Get logger for the class
	 * 
	 * @param clazz
	 * @return
	 */
	public static Logger getLogger(Class<?> clazz) {
		Logger logger = new Logger();
		logger.instanceName = clazz.getName();
		return logger;
	}

	/**
	 * Utility to print message on console
	 * 
	 * @param level
	 * @param message
	 */
	public void log(int level, Object message) {
		if (level >= logLevel) {
			System.out.println(df.format(new Date()) + " "
					+ getDebugLevel(level) + " " + instanceName + ": "
					+ message);
		}
	}

	/**
	 * Convert log lever from integer to string
	 * 
	 * @param level
	 * @return
	 */
	private static String getDebugLevel(int level) {
		String rtnValue = "Error";
		switch (level) {
		case 0:
			rtnValue = "DEBUG";
			break;
		case 1:
			rtnValue = "INFO ";
			break;
		case 2:
			rtnValue = "WARN ";
			break;
		case 3:
			rtnValue = "ERROR";
			break;

		default:
			break;
		}
		return rtnValue;
	}

	/**
	 * Print out debug message
	 * 
	 * @param message
	 */
	public void debug(Object message) {
		log(0, message);
	}

	/**
	 * Print out info message
	 * 
	 * @param message
	 */
	public void info(Object message) {
		log(1, message);
	}

	/**
	 * Print out warn message
	 * 
	 * @param message
	 */
	public void warn(Object message) {
		log(2, message);
	}

	/**
	 * Print out error message
	 * 
	 * @param message
	 */
	public void error(Object message) {
		log(3, message);
	}

}
