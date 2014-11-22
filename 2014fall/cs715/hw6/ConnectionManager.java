

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Requirement: 
 * 1. TA will send primitive data to TB and objects to TC 
 * 2. TB will reply back with an object to TA 
 * 3. TC will send objects data to TB
 * 
 * @author Haijun Su Date Nov 18, 2014
 *
 */
public class ConnectionManager {

	/**
	 * pipe for primitive data (TA->TB)
	 */
	static private PipedInputStream pis1;
	static private PipedOutputStream pos1;

	/**
	 * pipe for object data (TA->TC)
	 */
	static private PipedInputStream pis2;
	static private PipedOutputStream pos2;

	/**
	 * pipe for object data (TC->TB)
	 */
	static private PipedInputStream pis3;
	static private PipedOutputStream pos3;

	/**
	 * pipe for object data (TB->TA)
	 */
	static private PipedInputStream pis4;
	static private PipedOutputStream pos4;

	/**
	 * write object
	 */
	static private ObjectOutputStream oos;
	static private ObjectInputStream ois;

	public static void main(String argv[]) {
		try {

			// set up a pipe
			System.out.println("Pipe setup");
			pos1 = new PipedOutputStream();
			pis1 = new PipedInputStream(pos1);

			pos2 = new PipedOutputStream();
			pis2 = new PipedInputStream(pos2);

			pos3 = new PipedOutputStream();
			pis3 = new PipedInputStream(pos3);

			pos4 = new PipedOutputStream();
			pis4 = new PipedInputStream(pos4);

			System.out.println("Object creation");
			PipeSenderReceiver pa = new PipeSenderReceiver(
					PipeSenderReceiver.TA, null, pos1, pis4, pos2, ois, oos);
			PipeSenderReceiver pb = new PipeSenderReceiver(
					PipeSenderReceiver.TB, pis1, pos4, pis3, null, ois, oos);
			PipeSenderReceiver pc = new PipeSenderReceiver(
					PipeSenderReceiver.TC, null, pos3, pis2, null, ois, oos);
			Thread ta = new Thread(pa, "TA_doTask");
			Thread tb = new Thread(pb, "TB_doTask");
			Thread tc = new Thread(pc, "TC_doTask");
			System.out.println("Thread execution");
			ta.start();
			tb.start();
			tc.start();
		} // end TRY
		catch (Exception exc) {
			System.out.println(exc);
		} // end CATCH
	}
} // end CLASS ConnectionManager
