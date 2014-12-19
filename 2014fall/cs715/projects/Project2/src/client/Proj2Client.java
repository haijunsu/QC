package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.Logger;
import common.Message;
import common.Message.Command;
import common.Message.Role;

/**
 * Get configuration from server and initial client objects.
 * 
 * @author Haijun Su
 * Date Dec 19, 2014
 *
 */
public class Proj2Client {

	/**
	 * Logger instance
	 */
	private static Logger logger = Logger.getLogger(Proj2Client.class);

	/**
	 * Server port
	 */
	private int port;
	
	/**
	 * Server name/IP address
	 */
	private String address;
	
	/**
	 * Socket connection between server and client
	 */
	private Socket connection;

	/**
	 * Construct with server address and port
	 * 
	 * @param address
	 * @param port
	 */
	public Proj2Client(String address, int port) {
		this.address = address;
		this.port = port;
	}

	/**
	 * Start client
	 */
	public void start() {
		try {
			connection = new Socket(address, port);
			logger.debug("Connect established");
			ObjectOutputStream dos = new ObjectOutputStream(
					connection.getOutputStream());
			ObjectInputStream dis = new ObjectInputStream(
					connection.getInputStream());
			// client identity
			Message msg = (Message) dis.readObject();
			logger.debug("The server responds: " + msg);
			Message cfgMsg = new Message("Proj2Client", Role.Other,
					"Proj2Client");
			logger.debug("Send identity: " + cfgMsg);
			dos.writeObject(cfgMsg);
			msg = (Message) dis.readObject();
			logger.debug("The server responds: " + msg);
			// query configuration
			cfgMsg = new Message("Proj2Client", Role.Other,
					Command.configuration);
			logger.debug("Query configuration: " + cfgMsg);
			dos.writeObject(cfgMsg);
			msg = (Message) dis.readObject();
			logger.debug("The server responds: " + msg);
			// # of contestants that server supports
			int numContestants = msg.getIntValue();
			
			logger.debug("Send exit connection command: " + cfgMsg);
			cfgMsg = new Message("Proj2Client", Role.Other, Command.EXIT);
			dos.writeObject(cfgMsg);
			// close connection
			connection.close();
			// init announcer
			Socket anncSocket = new Socket(address, port);
			Thread anncT = new Thread(new AnnouncerClient(anncSocket),
					"AnnouncerClient");
			anncT.start();
			// init contestants
			for (int i = 0; i < numContestants; i++) {
				Socket contestantSocket = new Socket(address, port);
				Thread contestantT = new Thread(new ContestantClient(
						contestantSocket, i), "numContestants-" + i);
				contestantT.start();
			}
			// init host
			Socket hostSocket = new Socket(address, port);
			Thread hostT = new Thread(new HostClient(hostSocket),
					"AnnouncerClient");
			hostT.start();

		} catch (Exception exc) {
			logger.error(exc.getMessage());
			exc.printStackTrace();
		}
	}

	/**
	 * Application information
	 * 
	 * @return
	 */
	public String ClientInfo() {

		return "********************************************************"
				+ System.lineSeparator()
				+ "* This is porject 2 of course CS715 in Fall 2014.      *"
				+ System.lineSeparator()
				+ "* Students is Haijun Su                                *"
				+ System.lineSeparator()
				+ "********************************************************";

	}
}
