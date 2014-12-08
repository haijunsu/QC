package server;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import service.AuthService;
import service.AuthServiceImpl;
import utils.Configuration;
import utils.Logger;

/**
 * Server side of RMI project.
 * 
 * Date Dec 4, 2014
 *
 * @author Haijun Su
 * @author Youchen Ren
 */
public class AuthEngine {
	/**
	 * logger adaptor
	 */
	private static Logger logger = Logger.getLogger(AuthEngine.class);

	/**
	 * application main function
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(Configuration.appInfo());
			Configuration.init(false);
			logger.debug("Starting server...");
			AuthService engine = new AuthServiceImpl();
			AuthService stub = (AuthService) UnicastRemoteObject.exportObject(
					engine, 0);
			Registry registry = LocateRegistry
					.createRegistry(Configuration.SERVER_PORT);
			registry.rebind(Configuration.SERVICE_NAME, stub);
			logger.info("AuthEngine bound");
			InetAddress ip = InetAddress.getLocalHost();
			String hostname = ip.getHostName();
			System.out
					.println("***************************************************************"
							+ System.lineSeparator()
							+ "If client is running on other computer, the server.name "
							+ System.lineSeparator()
							+ "value needs be changed as '"
							+ hostname
							+ "' "
							+ System.lineSeparator()
							+ "in file resources/auth.properties."
							+ System.lineSeparator()
							+ "***************************************************************");

		} catch (Exception e) {
			logger.error("AuthEngine exception: " + e.getMessage());
			e.printStackTrace();
		}

	}
}
