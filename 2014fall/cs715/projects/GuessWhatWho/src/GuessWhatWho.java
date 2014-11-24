import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Task 1: take a preliminary written exam 
 * Goal: Only the best 4 contestants will be able to complete 
 *    1. make a group (each group has its own lock) and wait for entering classroom
 *    2. enter a classroom (notified by announcer) 
 *    3. have a counter to counter contestants who have a seat
 *    4. the last one notifies all of them to start the exam. 
 *    5. using random value to take the exam (<=exam_time) 
 *    6. after done, wait for the result (use a different object for each contestant, rwcv.java) 
 *    7. Announcer generates random numbers array score[num_contestants] and assign score to 
 *       each constestant (same order of array)
 *    8. Announcer notify contestant one by on in FCFS order (Submitting answer order) 
 *    9. Winners continue and others end.
 * 
 * Task 2: game 
 * 	  1. Announcer print an opening message 
 * 	  2. Announcer creates the Host thread and Host waits for signal 
 *    3. Announcer introduces 4 contestants and each contestant print a message and wait for Host 
 *    4. Announcer signals the Host, then exits. 
 *    5. Host will ask numQuestions with numRounds (while loop)
 *    6. Steps for each question: 
 *        a. Host print a friendly message 
 *        b. Contestant thinking (sleep for random time). 1st wakes up answers the question. 
 *        c. Host generate random number to decide whether the answer correct.(rightPercent) 
 *        d. Host print message and update contestant's score.(increase or decrease score)
 *    7. After run all rounds, enter Final Guess What or Who? 
 *    8. Host singal all contestants in order and wait for the contestants' notifies 
 *    9. Contestant choose a random 0~his score to wager. if Constestant's score < 0, says good-bye 
 *       and exits 
 *    10. Host grades the last question for all contestants. (each contestant get 50% to get score).
 *    10. Host update the scores print winner. 
 *    11. Say good-bye and exit
 * 
 * @author Haijun Su Date Nov 13, 2014
 *
 */
public class GuessWhatWho {

	/**
	 * Application starting time
	 */
	private static final long START_TIME = System.currentTimeMillis();

	/**
	 * default info level. Debug levels: 0-Debug, 1-Info, 2-Warn, 3-Error
	 */
	private static int logLevel = 1;

	/**
	 * Date format
	 */
	private static SimpleDateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * Length of the game
	 */
	private static int numRounds = 2;

	/**
	 * Number of questions in each round
	 */
	private static int numQuestions = 5;

	/**
	 * Question value
	 */
	private static int questionValues = 200;

	/**
	 * Percent of right answer
	 */
	private static float rightPercent = 0.65f;

	/**
	 * Groups of room capacity
	 */
	private static int room_capacity = 4;
	/**
	 * Number of contestants
	 */
	private static int num_contestants = 13;

	/**
	 * Game properties
	 */
	private static Game game = null;

	/**
	 * Game object
	 * 
	 * @return
	 */
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

	/**
	 * Utility to print message on console
	 * 
	 * @param level
	 * @param message
	 */
	public static void log(int level, String message) {
		if (level >= logLevel) {
			System.out.println(df.format(new Date()) + " - "
					+ getDebugLevel(level) + ": " + message);
		}
	}

	/**
	 * Convert log lever from integer to string
	 * 
	 * @param level
	 * @return
	 */
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

	/**
	 * Handle args and update default values
	 * 
	 * @param args
	 */
	public static void handleArgs(String[] args) {
		if (args.length % 2 != 0) {
			// usage
			System.out.println("Usage: \n\tjava GuessWhatWho [-rqvpgc <num>] [-log <num]");
			System.out.println("\t\t-log\tlog level");
			System.out.println("\t\t-r\tNumber of rounds in the game");
			System.out.println("\t\t-q\tNumber of questions per round");
			System.out.println("\t\t-v\tQuestion value");
			System.out.println("\t\t-p\tRight percent (0-100)");
			System.out.println("\t\t-g\tRoom capacity (group size)");
			System.out.println("\t\t-c\tNumber of Contestants");
			System.exit(1);
			
		}
		for (int i = 0; i < args.length;) { // log level
			if("-log".equals(args[i])) {
				logLevel = Integer.valueOf(args[i+1]);
				log(0, "logLevel = " + logLevel);
			} else if ("-r".equals(args[i])) { // number of round
				numRounds = Integer.valueOf(args[i+1]);
				log(0, "numRounds = " + numRounds);
			} else if ("-q".equals(args[i])) { // number of questions/round
				numQuestions = Integer.valueOf(args[i+1]);
				log(0, "numQuestions = " + numQuestions);
			} else if ("-v".equals(args[i])) { // question value
				questionValues = Integer.valueOf(args[i+1]);
				log(0, "questionValues = " + questionValues);
			} else if ("-p".equals(args[i])) { // right percent
				rightPercent = Float.valueOf(args[i+1])/100;
				log(0, "rightPercent = " + rightPercent);
			} else if ("-g".equals(args[i])) { // room capacity (group size)
				room_capacity = Integer.valueOf(args[i+1]);
				log(0, "room_capacity = " + room_capacity);
			} else if ("-c".equals(args[i])) { // number of contestants
				num_contestants = Integer.valueOf(args[i+1]);
				log(0, "num_contestants = " + num_contestants);
			}
			i += 2;
		}
	}

	public static void main(String[] args) {
		// handle arguments
		handleArgs(args);
		// construct announcer and contestants.
		Announcer annc = new Announcer(room_capacity, num_contestants);
		Contestant[] contestants = new Contestant[num_contestants];
		for (int i = 0; i < contestants.length; i++) {
			contestants[i] = new Contestant(i, annc);
		}
		annc.setContestants(contestants);
		// construct game object
		game = new Game(numRounds, numQuestions, questionValues, rightPercent);
		log(0, game.toString());
		// start announcer
		Thread anncT = new Thread(annc, annc.getName());
		anncT.start();
		// start contestants
		for (int i = 0; i < contestants.length; i++) {
			Thread ctt = new Thread(contestants[i], contestants[i].getName());
			// Register to ContestsThreadManager
			ContestantsThreadManager.add(ctt);
			ctt.start();
		}

	}

}
