package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.Logger;
import common.Message;
import common.Message.Command;
import common.Message.Role;

/**
 * AnnouncerClient is used to call Announcer methods on server side.
 * 
 * @author Haijun Su
 * Date Dec 19, 2014
 *
 */
public class AnnouncerClient implements Runnable {
	/**
	 * Logger instance
	 */
	private static Logger logger = Logger.getLogger(AnnouncerClient.class);

	/**
	 * socket
	 */
	private Socket socket = null;

	/**
	 * Construct with socket
	 * @param socket
	 */
	public AnnouncerClient(Socket socket) {
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
			Message sendMsg = new Message("AnnouncerClient", Role.Announcer);
			logger.debug("Send identity: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);

			// givePermissionEnterClassroom
			sendMsg = new Message("AnnouncerClient", Role.Announcer,
					Command.givePermissionEnterClassroom);
			logger.debug("Send: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);

			// startExam
			sendMsg = new Message("AnnouncerClient", Role.Announcer,
					Command.startExam);
			logger.debug("Send: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);

			// gradeExam
			sendMsg = new Message("AnnouncerClient", Role.Announcer,
					Command.gradeExam);
			logger.debug("Send: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);

			// prepareHost
			sendMsg = new Message("AnnouncerClient", Role.Announcer,
					Command.prepareHost);
			logger.debug("Send: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);

			// introduceWinners
			sendMsg = new Message("AnnouncerClient", Role.Announcer,
					Command.introduceWinners);
			logger.debug("Send: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);

			// startGame
			sendMsg = new Message("AnnouncerClient", Role.Announcer,
					Command.startGame);
			logger.debug("Send: " + sendMsg);
			dos.writeObject(sendMsg);
			recMsg = (Message) dis.readObject();
			logger.debug("The server responds: " + recMsg);

			// exit
			sendMsg = new Message("AnnouncerClient", Role.Announcer,
					Command.EXIT);
			dos.writeObject(sendMsg);
			socket.close();
			logger.info("I have done my job. Exit.");

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

}
