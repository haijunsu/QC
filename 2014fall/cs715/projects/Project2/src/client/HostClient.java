package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.Logger;
import common.Message;
import common.Message.Command;
import common.Message.Role;

/**
 * HostClient is used to call Host methods on server side.
 * 
 * @author Haijun Su
 * Date Dec 19, 2014
 *
 */
public class HostClient implements Runnable {
	/**
	 * Logger instance
	 */
	private static Logger logger = Logger.getLogger(HostClient.class);
	
	/**
	 * socket
	 */
	private Socket socket = null;

	/**
	 * Construct with socket
	 * @param socket
	 */
	public HostClient(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			ObjectOutputStream dos = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream dis = new ObjectInputStream(
					socket.getInputStream());
			
			// client identity
			Message recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);
			Message sendMsg = new Message("HostClient",
					Role.Host);
			logger.debug("Send identity: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);
			
			//waitGameStartSignal
			sendMsg = new Message("HostClient",
					Role.Host, Command.waitGameStartSignal);
			logger.debug("Send: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);
			
			//runRoundQuestions
			sendMsg = new Message("HostClient",
					Role.Host, Command.runRoundQuestions);
			logger.debug("Send: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);

			//runFianlQuestion
			sendMsg = new Message("HostClient",
					Role.Host, Command.runFianlQuestion);
			logger.debug("Send: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);
			System.out.println("=================== Result ==================");
			System.out.println(recMsg.getTxtMsg());

			//exit
			sendMsg = new Message("HostClient",
					Role.Host, Command.EXIT);
			dos.writeObject(sendMsg);
			socket.close();

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

}
