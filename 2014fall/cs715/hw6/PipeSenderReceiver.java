

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * This classes will start 3 threads: doTask, receive, receiveObject
 * 
 * TA (id = 0) 
 * 	send primitive date to TB 
 * 	send objects to TC 
 * 	Receive objects from TB
 * 
 * TB (id = 1) 
 * 	Receive primitive form TA and reply object to TA
 * 
 * TA (id = 2) 
 * 	Receive objects form TA 
 * 	send objects data to TB
 * 
 * @author Haijun Su Date Nov 18, 2014
 *
 */
public class PipeSenderReceiver implements Runnable {

	private static final int DO_TASK = 0, RECEIVE = 1, RECEIVE_OBJECT = 2;
	public static final int TA = 0, TB = 1, TC = 2;
	private int whichOne = 0;
	/**
	 * Thread ID
	 */
	private int id;

	/**
	 * Thread name
	 */
	private String name;

	// to write Objects
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	// to write raw bytes (pipe 1)
	private InputStream is1;
	private OutputStream os1;

	// to write raw bytes (pipe 2)
	private InputStream is2;
	private OutputStream os2;

	public PipeSenderReceiver(int id, InputStream is1, OutputStream os1,
			InputStream is2, OutputStream os2, ObjectInputStream ois,
			ObjectOutputStream oos) {
		this.id = id;
		this.is1 = is1;
		this.os1 = os1;
		this.is2 = is2;
		this.os2 = os2;
		this.ois = ois;
		this.oos = oos;
		this.name = getNameByID(id);

	}

	@Override
	public void run() {
		int meDo = whichOne++;
		if (meDo == DO_TASK) {
			new Thread(this, name + "_receive").start();
			log("DO_TASK start...");
			doTask();
		} else if (meDo == RECEIVE) {
			new Thread(this, name + "_receiveObject").start();
			if (id == TB) {
				log("RECEIVE start...");
				receive();
			}
		} else if (meDo == RECEIVE_OBJECT) {
			log("RECEIVE_OBJECT start...");
			receiveObject();
		}
	}

	private void doTask() {
		try {
			Message msg = null;
			switch (id) {
			case TA: // TA
				// send primitive date to TB
				log("sends primitive data to TB: 50");
				os1.write(50);
				// send objects to TC
				// create the message
				msg = new Message(2014, id);
				log("sends object message to TC: " + msg);
				oos = new ObjectOutputStream(os2);
				oos.writeObject(msg);
				break;
			case TB: // TB
				// nothing to do
				break;
			case TC: // TC
				// send objects data to TB
				// create the message
				msg = new Message(2015, id);
				log("sends object message to TB: " + msg);
				oos = new ObjectOutputStream(os1);
				oos.writeObject(msg);
				break;
			default:
				log("Should not be here");

			}
		} catch (IOException e) {
			log("Error in doTask! Error: " + e.getMessage());
		}

	}

	private void receive() {
		try {
			int curr = 0;
			// reads message from TA
			curr = is1.read();
			log("reads from TA: " + curr);
			// reply message to TA
			// create the message
			Message message = new Message(2016, id);
			log("replies object message to TA: " + message);
			oos = new ObjectOutputStream(os1);
			oos.writeObject(message);
		} catch (IOException e) {
			log("Error in receive! Error: " + e.getMessage());
		}

	}

	private void receiveObject() {
		try {
			// receive object
			ois = new ObjectInputStream(is2);
			Message msg = (Message) ois.readObject();
			log("reads object from " + getNameByID(msg.id) + ": " + msg);

		} catch (IOException e) {
			log("Error in receive! Error: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			log("Error in receive! Error: " + e.getMessage());
		}
	}

	private void log(Object message) {
		System.out.println(name + " - " + message);
	}

	private String getNameByID(int id) {
		String name = "";
		switch (id) {
		case TA:
			name = "TA";
			break;
		case TB:
			name = "TB";
			break;
		case TC:
			name = "TC";
			break;
		default:
			name = "TT_" + id;
			log("Should not be here");
		}
		return name;
	}
}
