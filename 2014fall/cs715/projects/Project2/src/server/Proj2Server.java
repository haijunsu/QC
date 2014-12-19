package server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import server.impl.GuessWhatWho;
import common.Logger;

/**
 * Main server thread to listen and accept clients
 * 
 * @author Haijun Su 
 * Date Dec 18, 2014
 *
 */
public class Proj2Server {
	/**
	 * Logger instance
	 */
	private static Logger logger = Logger.getLogger(Proj2Server.class);
	
	/**
	 * ServerSocket
	 */
	private ServerSocket server;
	
	/**
	 * Server port
	 */
	private int port;

	/**
	 * Construct with port
	 * @param port
	 */
	public Proj2Server(int port) {
		this.port = port;
	}

	/**
	 * Start server.
	 * Initial server environment and listening
	 */
	public void start() {
		try {
			// init environment
			GuessWhatWho.init();
			server = new ServerSocket(port);
			while (true) {
				Socket connection = server.accept();
				// using thread to keep client and server conversion.
				ServiceThread service = new ServiceThread(connection);
				service.start();
			}
		} catch (Exception exc) {
			logger.error(exc.getMessage());
			exc.printStackTrace();
		}
	}

	/**
	 * Application information
	 * @return
	 */
	public String serverInfo() {
		InetAddress myAddress;
		try {
			myAddress = InetAddress.getLocalHost();

			return "********************************************************"
					+ System.lineSeparator()
					+ "* This is porject 2 of course CS715 in Fall 2014.      *"
					+ System.lineSeparator()
					+ "* Student is Haijun Su                                *"
					+ System.lineSeparator()
					+ "********************************************************"
					+ System.lineSeparator() + "Access method from client:"
					+ System.lineSeparator() + "\tjava Client -host "
					+ myAddress.getHostAddress() + " -port " + port
					+ System.lineSeparator();

		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}

	}
}
