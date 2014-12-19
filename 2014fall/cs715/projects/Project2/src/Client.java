import client.Proj2Client;

import common.Logger;

/**
 * Start Proj2Client
 * 
 * @author Haijun Su
 * Date Dec 19, 2014
 *
 */
public class Client {

	/**
	 * Logger instance
	 */
	private static Logger logger = Logger.getLogger(Client.class);

	/**
	 * Server port
	 */
	private static int port = 8803;

	/**
	 * Server name/IP address
	 */
	private static String address = "localhost";

	public static void main(String args[]) {
		handleArgs(args);
		Proj2Client client = new Proj2Client(address, port);
		System.out.println(client.ClientInfo());
		client.start();
	}

	/**
	 * Handle args and update default values
	 * 
	 * @param args
	 */
	public static void handleArgs(String[] args) {
		if (args.length % 2 != 0) {
			// usage
			System.out
					.println("Usage: \n\tjava Client [-host <hostname/address>] [-port <num>] [-log <num>]");

			System.out
					.println("\t\t-host\tServer name or IP address. Default value is '"
							+ address + "'");
			System.out.println("\t\t-port\tServer port. Default value is "
					+ port);
			System.out.println();
			System.out.println("\t\t-log\tlog level. Default level (warn). "
					+ "Debug levels: 0-Debug, 1-Info, 2-Warn, 3-Error");
			System.exit(1);
		}

		for (int i = 0; i < args.length;) { // log level
			if ("-log".equalsIgnoreCase(args[i])) {
				Logger.logLevel = Integer.valueOf(args[i + 1]);
				logger.debug("logLevel = " + Logger.logLevel);
			} else if ("-port".equalsIgnoreCase(args[i])) { // server port
				port = Integer.valueOf(args[i + 1]);
				logger.debug("port = " + port);
			} else if ("-host".equals(args[i])) { // server name/IP
				address = args[i + 1];
				logger.debug("host = " + address);
			}
			i += 2;
		}
	}

}
