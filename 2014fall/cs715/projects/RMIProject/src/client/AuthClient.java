package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.AuthService;
import utils.Configuration;
import utils.Logger;
import entity.User;

public class AuthClient {

	private static Logger logger = Logger.getLogger(AuthClient.class);
	
	private static User profile = null;
	
	public static void main(String args[]) {
		try {
			Configuration.init();
			logger.debug("lookup registry...");
			Registry registry = LocateRegistry.getRegistry("localhost", 24680);
			AuthService service = (AuthService) registry.lookup(Configuration.SERVICE_NAME);
			profile = service.login("admin", "admin");
			logger.debug("authentication result: " + profile);
			if (profile == null) {
				logger.info("Login fail.");
				System.out.println("Login fail.");
			}
		} catch (Exception e) {
			logger.error("AuthService exception:");
			e.printStackTrace();
		}
	}
}
