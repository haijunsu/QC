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
package server.impl;

import common.Logger;

public class GuessWhatWho {

	private static Logger logger = Logger.getLogger(GuessWhatWho.class);

	/**
	 * Application starting time
	 */
	private static final long START_TIME = System.currentTimeMillis();

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
	 * Announcer
	 */
	private static Announcer announcer = null;

	/**
	 * Host
	 */
	private static Host host = null;

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

	public static void main(String[] args) {
		handleArgs(args);
		//construct announcer and contestants.
		Announcer annc = new Announcer(room_capacity, num_contestants);
		Contestant[] contestants = new Contestant[num_contestants];
		for (int i = 0; i < contestants.length; i++) {
			contestants[i] = new Contestant(i, annc);
		}
		annc.setContestants(contestants);
		// construct game object
		game = new Game(numRounds, numQuestions, questionValues, rightPercent);
		// start announcer
		Thread anncT = new Thread(annc, annc.getName());
		anncT.start();
		//start contestants
		for (int i = 0; i < contestants.length; i++) {
			Thread ctt = new Thread(contestants[i], contestants[i].getName());
			// Register to ContestsThreadManager
			ContestantsThreadManager.add(ctt);
			ctt.start();
		}

	}

	public static int getNumRounds() {
		return numRounds;
	}

	public static void setNumRounds(int numRounds) {
		GuessWhatWho.numRounds = numRounds;
	}

	public static int getNumQuestions() {
		return numQuestions;
	}

	public static void setNumQuestions(int numQuestions) {
		GuessWhatWho.numQuestions = numQuestions;
	}

	public static int getQuestionValues() {
		return questionValues;
	}

	public static void setQuestionValues(int questionValues) {
		GuessWhatWho.questionValues = questionValues;
	}

	public static float getRightPercent() {
		return rightPercent;
	}

	public static void setRightPercent(float rightPercent) {
		GuessWhatWho.rightPercent = rightPercent;
	}

	public static int getRoom_capacity() {
		return room_capacity;
	}

	public static void setRoom_capacity(int room_capacity) {
		GuessWhatWho.room_capacity = room_capacity;
	}

	public static int getNum_contestants() {
		return num_contestants;
	}

	public static void setNum_contestants(int num_contestants) {
		GuessWhatWho.num_contestants = num_contestants;
	}

	public static void setGame(Game game) {
		GuessWhatWho.game = game;
	}

	public static Announcer getAnnouncer() {
		return announcer;
	}

	public static void setAnnouncer(Announcer announcer) {
		GuessWhatWho.announcer = announcer;
	}

	public static Host getHost() {
		return host;
	}

	public static void setHost(Host host) {
		GuessWhatWho.host = host;
	}

	public static void init() {
		logger.debug("Initial objects");
		GuessWhatWho.game = new Game(numRounds, numQuestions, questionValues,
				rightPercent);
		GuessWhatWho.announcer = new Announcer(room_capacity, num_contestants);
		GuessWhatWho.host = new Host();
	}

	/**
	 * Handle args and update default values
	 * 
	 * @param args
	 */
	public static void handleArgs(String[] args) {
		if (args.length % 2 != 0) {
			// usage
			System.out
					.println("Usage: \n\tjava server.impl.GuessWhatWho [-rqvpgc <num>] [-log <num>]");

			System.out.println("\t\t-r\tNumber of rounds in the game");
			System.out.println("\t\t-q\tNumber of questions per round");
			System.out.println("\t\t-v\tQuestion value");
			System.out.println("\t\t-p\tRight percent (0-100)");
			System.out.println("\t\t-g\tRoom capacity (group size)");
			System.out.println("\t\t-c\tNumber of Contestants");
			System.out.println();
			System.out.println("\t\t-log\tlog level");
			System.exit(1);

		}

		for (int i = 0; i < args.length;) { // log level
			if ("-log".equalsIgnoreCase(args[i])) {
				Logger.logLevel = Integer.valueOf(args[i + 1]);
				logger.debug("logLevel = " + Logger.logLevel);
			} else if ("-r".equals(args[i])) { // number of round
				int numRounds = Integer.valueOf(args[i + 1]);
				GuessWhatWho.setNumRounds(numRounds);
				logger.debug("numRounds = " + numRounds);
			} else if ("-q".equals(args[i])) { // number of questions/round
				int numQuestions = Integer.valueOf(args[i + 1]);
				GuessWhatWho.setNumQuestions(numQuestions);
				logger.debug("numQuestions = " + numQuestions);
			} else if ("-v".equals(args[i])) { // question value
				int questionValues = Integer.valueOf(args[i + 1]);
				GuessWhatWho.setQuestionValues(questionValues);
				logger.debug("questionValues = " + questionValues);
			} else if ("-p".equals(args[i])) { // right percent
				float rightPercent = Float.valueOf(args[i + 1]) / 100;
				GuessWhatWho.setRightPercent(rightPercent);
				logger.debug("rightPercent = " + rightPercent);
			} else if ("-g".equals(args[i])) { // room capacity (group size)
				int room_capacity = Integer.valueOf(args[i + 1]);
				GuessWhatWho.setRoom_capacity(room_capacity);
				logger.debug("room_capacity = " + room_capacity);
			} else if ("-c".equals(args[i])) { // number of contestants
				int num_contestants = Integer.valueOf(args[i + 1]);
				GuessWhatWho.setNum_contestants(num_contestants);
				logger.debug("num_contestants = " + num_contestants);
			}
			i += 2;
		}
	}

}
