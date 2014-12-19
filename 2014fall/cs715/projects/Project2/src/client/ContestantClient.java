package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.Logger;
import common.Message;
import common.Message.Command;
import common.Message.Role;

/**
 * ContestantClient is used to call Contestant methods on server side.
 * 
 * @author Haijun Su
 * Date Dec 19, 2014
 *
 */
public class ContestantClient implements Runnable {

	/**
	 * Logger instance
	 */
	private Logger logger = Logger.getLogger(ContestantClient.class);
	
	/**
	 * Contestant id
	 */
	private int id;
	
	/**
	 * socket
	 */
	private Socket socket = null;

	/**
	 * Construct with socket and contestant id
	 * @param socket
	 * @param id
	 */
	public ContestantClient(Socket socket, int id) {
		this.socket = socket;
		this.id = id;
		logger = Logger.getLogger("ContestantClient-" + id);
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
			Message sendMsg = new Message("ContestantClient-" + id,
					Role.Contestant);
			sendMsg.setIntValue(id);
			logger.debug("Send identity: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);

			// joinGrp
			sendMsg = new Message("ContestantClient-" + id,
					Role.Contestant, Command.joinGrp);
			logger.debug("Send: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);

			// askForSeat
			sendMsg = new Message("ContestantClient-" + id,
					Role.Contestant, Command.askForSeat);
			logger.debug("Send: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);

			// takingExam
			sendMsg = new Message("ContestantClient-" + id,
					Role.Contestant, Command.takingExam);
			logger.debug("Send: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);

			// submitExam
			sendMsg = new Message("ContestantClient-" + id,
					Role.Contestant, Command.submitExam);
			logger.debug("Send: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);
			if (recMsg.getCommad() == Command.EXIT) {
				sendMsg = new Message("ContestantClient-" + id,
						Role.Contestant, Command.EXIT);
				logger.debug("Send: " + sendMsg);
				dos.writeObject(sendMsg);
				socket.close();
				logger.info("I am out and exit.");
				return;
			}

			// readyForGame
			sendMsg = new Message("ContestantClient-" + id,
					Role.Contestant, Command.readyForGame);
			logger.debug("Send: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);

			// playGame
			sendMsg = new Message("ContestantClient-" + id,
					Role.Contestant, Command.playGame);
			logger.debug("Send: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);

			// playFinalQuestion
			sendMsg = new Message("ContestantClient-" + id,
					Role.Contestant, Command.playFinalQuestion);
			logger.debug("Send: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);
			
			// exit
			sendMsg = new Message("ContestantClient-" + id,
					Role.Contestant, Command.EXIT);
			logger.debug("Send message: " + sendMsg);
			dos.writeObject(sendMsg);
			socket.close();
			logger.info("I have done all question. Exit.");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

}
