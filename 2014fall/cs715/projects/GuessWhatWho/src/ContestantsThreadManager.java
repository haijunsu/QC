import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author Haijun Su Date Nov 16, 2014
 *
 */
public class ContestantsThreadManager {

	private static Map<String, Thread> cttMaps = Collections
			.synchronizedMap(new HashMap<String, Thread>());

	private static void debug(String message) {
		GuessWhatWho.log(0, "ContestantsThreadManager - " + message);
	}
	
	/**
	 * add a constestant thread
	 * @param thread
	 */
	public static void add(Thread thread) {
		debug(thread.getName() + " is added.");
		cttMaps.put(thread.getName(), thread);
	}
	
	/**
	 * remove a constestant thread
	 * @param name
	 */
	public static void remove(String name) {
		debug(name + " is removed.");
		cttMaps.remove(name);
	}
	
	/**
	 * Interrupt all contestant threads except the excludeName
	 * @param excludeName
	 */
	public static void interruptOthers(String excludeName) {
		Iterator<Thread> threads = cttMaps.values().iterator();
		while (threads.hasNext()) {
			Thread thread = (Thread) threads.next();
			if (!thread.getName().equals(excludeName)) {
				debug(thread.getName() + " is interrupted.");
				thread.interrupt();
			}
		}
	}
}
