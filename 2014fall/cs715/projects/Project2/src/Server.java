import common.Logger;
import server.Proj2Server;
import server.impl.GuessWhatWho;

/**
 * Start Proj2Server
 * 
 * @author Haijun Su
 * Date Dec 19, 2014
 *
 */
public class Server {
	
	/**
	 * Logger instance
	 */
	private static Logger logger = Logger.getLogger(Server.class);
	
	/**
	 * Server port
	 */
	private static int port = 8803;

	public static void main(String[] args) {
		handleArgs(args);
		Proj2Server server = new Proj2Server(port);
		System.out.println(server.serverInfo());
		server.start();
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
					.println("Usage: \n\tjava Server [-port <num>] [-rqvpgc <num>] [-log <num>]");

			System.out.println("\t\t-port\tServer port. Default is " + port);
			System.out.println();
			System.out.println("\t\t-r\tNumber of rounds in the game");
			System.out.println("\t\t-q\tNumber of questions per round");
			System.out.println("\t\t-v\tQuestion value");
			System.out.println("\t\t-p\tRight percent (0-100)");
			System.out.println("\t\t-g\tRoom capacity (group size)");
			System.out.println("\t\t-c\tNumber of Contestants");
			System.out.println();
			System.out.println("\t\t-log\tlog level. Default level (warn). "
					+ "Debug levels: 0-Debug, 1-Info, 2-Warn, 3-Error");
			System.exit(1);

		}

		for (int i = 0; i < args.length;) { // log level
			if ("-log".equalsIgnoreCase(args[i])) {
				Logger.logLevel = Integer.valueOf(args[i + 1]);
				logger.debug("logLevel = " + Logger.logLevel);
			} else if ("-port".equalsIgnoreCase(args[i])) { // server port
				port = Integer.valueOf(args[i + 1]);
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
