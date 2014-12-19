package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import server.impl.Announcer;
import server.impl.Contestant;
import server.impl.ContestantsThreadManager;
import server.impl.GuessWhatWho;
import server.impl.Host;
import common.Logger;
import common.Message;
import common.Message.Command;
import common.Message.Role;
/**
 * 
 * 
 * @author Haijun Su
 * Date Dec 19, 2014
 *
 */
public class ServiceThread implements Runnable {

	/**
	 * Logger instance
	 */
	private Logger logger = Logger.getLogger(ServiceThread.class);

	/**
	 * socket
	 */
	private Socket socket = null;

	/**
	 * Thread of service thread
	 */
	private Thread thread = null;

	/**
	 * This thread services a Contestant
	 */
	private Contestant contestant = null;

	/**
	 * This thread services the Announcer
	 */
	private Announcer announcer = null;

	/**
	 * This thread services the Host
	 */
	private Host host = null;

	/**
	 * Thread name
	 */
	private String name;

	/**
	 * Construct thread with socket
	 */
	public ServiceThread(Socket socket) {
		this.socket = socket;
		this.thread = new Thread(this);
	}

	/**
	 * Start the thread
	 */
	public void start() {
		this.thread.start();
	}

	@Override
	public void run() {
		logger.debug("starting...");
		try {
			// input/output stream
			ObjectOutputStream dos = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream dis = new ObjectInputStream(
					socket.getInputStream());
			// ask client to identify itself
			Message msg = new Message("Service", Role.Service,
					"What's your name?");
			logger.debug("Send identity request.");
			dos.writeObject(msg);
			logger.debug("waiting for identity...");
			msg = (Message) dis.readObject();
			// change thread name
			this.name = "SERVICE::" + msg.getSender();
			this.thread.setName(this.name);
			// change logger adapter name
			logger = Logger.getLogger(this.name);
			// construct object to service
			switch (msg.getRole()) {
			case Contestant:
				contestant = new Contestant(msg.getIntValue(),
						GuessWhatWho.getAnnouncer());
				GuessWhatWho.getAnnouncer().addContestant(contestant);
				break;
			case Announcer:
				announcer = GuessWhatWho.getAnnouncer();
				break;
			case Host:
				host = GuessWhatWho.getHost();
				break;

			default:
				break;
			}
			if (Role.Contestant == msg.getRole()) {
				// add contestant to ContestantsThreadManger
				ContestantsThreadManager.add(this.thread);
			}
			// read to service
			dos.writeObject(new Message(name, Role.Service, "Welcome! I am "
					+ name + ". How can I help you?"));
			do {
				// handle requests
				Message rcvMsg = (Message) dis.readObject();
				logger.debug("received: " + rcvMsg);
				if (Command.EXIT == rcvMsg.getCommad()) {
					socket.close();
					break;
				}
				dos.writeObject(handleMessage(rcvMsg));
			} while (true);
		} catch (IOException e) {
			logger.error("Error: " + e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			logger.error("Error: " + e.getMessage());
			e.printStackTrace();
		}
		if (contestant != null) {
			// remove contestant from thread manager
			ContestantsThreadManager.remove(this.name);
		}
		logger.debug("exit");
	}

	/**
	 * Handle all supported messages
	 * @param msg
	 * @return
	 */
	private Message handleMessage(Message msg) {
		switch (msg.getRole()) {
		case Announcer:
			msg = handleAnnouncerMsg(msg);
			break;

		case Contestant:
			msg = handleContestantMsg(msg);
			break;

		case Host:
			msg = handleHostMsg(msg);
			break;

		case Other:
			msg = handleOtherMsg(msg);
			break;

		default:
			break;
		}
		return msg;
	}

	/**
	 * Handle other type message, such as configuration
	 * @param msg
	 * @return
	 */
	private Message handleOtherMsg(Message msg) {
		logger.debug("handleOtherMsg: " + msg);
		Message rtnMsg = new Message(this.name, Role.Service, Command.next);
		switch (msg.getCommad()) {
		case configuration:
			rtnMsg.setIntValue(GuessWhatWho.getNum_contestants());
			rtnMsg.setCommand(Command.EXIT);
			break;

		default:
			break;
		}
		return rtnMsg;
	}

	/**
	 * Handle all messages from HostClient.
	 * @param msg
	 * @return
	 */
	private Message handleHostMsg(Message msg) {
		logger.debug("handleHostMsg: " + msg);
		Message rtnMsg = new Message(this.name, Role.Service);
		rtnMsg.setCommand(Command.next);
		switch (msg.getCommad()) {
		case waitGameStartSignal:
			host.waitGameStartSignal();
			break;

		case runRoundQuestions:
			host.runRoundQuestions();
			break;

		case runFianlQuestion:
			rtnMsg.setTxtMsg(host.runFianlQuestion());
			rtnMsg.setCommand(Command.EXIT);
			break;

		default:
			break;
		}
		return rtnMsg;
	}

	/**
	 * Handle all messages from ContestentClient
	 * @param msg
	 * @return
	 */
	private Message handleContestantMsg(Message msg) {
		logger.debug("handleContestantMsg: " + msg);
		Message rtnMsg = new Message(this.name, Role.Service, Command.next);
		switch (msg.getCommad()) {
		case joinGrp:
			contestant.joinGroup();
			break;
		case askForSeat:
			contestant.askForSeat();
			break;
		case takingExam:
			contestant.takingExam();
			break;
		case submitExam:
			if (!contestant.submitExam()) {
				rtnMsg.setCommand(Command.EXIT);
			}
			break;
		case readyForGame:
			contestant.readyForGame();
			break;

		case playGame:
			contestant.playGame();
			break;
		case playFinalQuestion:
			contestant.playFinalQuestion();
			rtnMsg.setCommand(Command.EXIT);
			break;
		default:
			break;
		}
		return rtnMsg;
	}

	/**
	 * Handle all messages from AnnouncerClient
	 * @param msg
	 * @return
	 */
	private Message handleAnnouncerMsg(Message msg) {
		logger.debug("handleAnnouncerMsg: " + msg);
		Message rtnMsg = new Message(this.name, Role.Service, Command.next);
		switch (msg.getCommad()) {
		case givePermissionEnterClassroom:
			announcer.givePermissionEnterClassroom();
			break;
		case startExam:
			announcer.startExam();
			break;
		case gradeExam:
			announcer.gradeExam();
			break;

		case prepareHost:
			announcer.prepareHost();
			break;

		case introduceWinners:
			announcer.introduceWinners();
			break;

		case startGame:
			announcer.startGame();
			rtnMsg.setCommand(Command.EXIT);
			break;

		default:
			break;
		}
		return rtnMsg;
	}

	/**
	 * Thread name
	 * @return
	 */
	public String getName() {
		return name;
	}

}
