package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import service.AuthService;
import service.AuthServiceImpl;
import utils.Configuration;
import utils.Logger;

/**
 * 
 * @author Haijun Su
 * Date Dec 4, 2014
 *
 */
public class AuthEngine {
	private static Logger logger = Logger.getLogger(AuthEngine.class);
	
	public static void main(String[] args) {
        try {
        	Configuration.init();
        	logger.debug("Starting server...");
            AuthService engine = new AuthServiceImpl();
            AuthService stub = (AuthService) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.createRegistry(24680);
            registry.rebind(Configuration.SERVICE_NAME, stub);
            logger.info("AuthEngine bound");
        } catch (Exception e) {
            logger.error("AuthEngine exception:");
            e.printStackTrace();
        }
		
		
	}
}
