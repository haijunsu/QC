import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Task 1: written exam Goal: Only the best 4 contestants will be able to
 * complete 1. make group (each group has its own lock) 2. enter a classroom
 * (notified by announcer) 3. have a counter to counter contestants. 4. the last
 * one notifies all of them to start the exam. 5. using random value to take the
 * exam (<=exam_time) 6. after done, wait for the result (use a different object
 * for each contestant, rwcv.java) 7. Announcer generates random numbers array
 * score[num_contestants] and assign score to each constestant 8. Announcer
 * notify contestant one by on in FCFS order (Submit exam order) 9. Winners
 * continue and others end.
 * 
 * Task 2: game 1. Announcer print an opening message 2. Announcer creates the
 * Host thread and Host waits for signal 3. Announcer introduces 4 contestants
 * and each contestant print a message and wait for Host 4. Announcer signals
 * the Host and exits. 5. Host will ask numQuestions with numRounds (while loop)
 * 6. Steps for each question: a. Host print a friendly message b. Contestant
 * thinking (sleep for random time). 1st wakes up answers the question. c. Host
 * generate random number to decide whether the answer correct.(rightPercent) d.
 * Host print message and update contestant's score.(increase or decrease score)
 * 7. After run all rounds, enter Final Guess What or Who? 8. Host singal all
 * contestants in order and wait for the contestants' notifies 9. Contestant
 * choose a random 0~his score to wager (50% chance). if Constestant's score <
 * 0, says good-bye and exits 10. Host update the scores print winner. 11. Say
 * good-bye and exit
 * 
 * @author Haijun Su Date Nov 13, 2014
 *
 */
public class GuessWhatWho {

	private static final long START_TIME = System.currentTimeMillis();

	/**
	 * default info level. Debug levels: 0-Debug, 1-Info, 2-Warn, 3-Error
	 */
	private static int defaultDebugLevel = 1;

	/**
	 * Date format
	 */
	private static SimpleDateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * The default values for the parameters are:
	 */
	private static int numRounds = 2;
	private static int numQuestions = 5;
	private static int questionValues = 200;
	private static float rightPercent = 0.65f;

	/**
	 * Groups of room capacity
	 */
	private static int room_capacity = 4;
	private static int num_contestants = 15;

	private static Game game = null;

	public static Game getGame() {
		return game;
	}

	/**
	 * The age of the application. It also can be used as thread's age.
	 * 
	 * @return age
	 */
	public static final long age() {
		return System.currentTimeMillis() - START_TIME;
	}

	public static void log(int level, String message) {
		if (level >= defaultDebugLevel) {
			System.out.println(df.format(new Date()) + " - "
					+ getDebugLevel(level) + ": " + message);
		}
	}

	private static String getDebugLevel(int level) {
		String rtnValue = "Error";
		switch (level) {
		case 0:
			rtnValue = "DEBUG";
			break;
		case 1:
			rtnValue = "INFO ";
			break;
		case 2:
			rtnValue = "WARN ";
			break;
		case 3:
			rtnValue = "ERROR";
			break;

		default:
			break;
		}
		return rtnValue;
	}

	public static void main(String[] args) {
		//TODO parser command line
		// construct announcer and contestants.
		Announcer annc = new Announcer(room_capacity, num_contestants);
		Contestant[] contestants = new Contestant[num_contestants];
		for (int i = 0; i < contestants.length; i++) {
			contestants[i] = new Contestant(i, annc);
		}
		annc.setContestants(contestants);
		// construct game object
		game = new Game(numRounds, numQuestions, questionValues,
				rightPercent);
		// start threads
		Thread anncT = new Thread(annc, annc.getName());
		anncT.start();
		for (int i = 0; i < contestants.length; i++) {
			Thread ctt = new Thread(contestants[i], contestants[i].getName());
			ContestantsThreadManager.add(ctt);
			ctt.start();
		}

	}

}
