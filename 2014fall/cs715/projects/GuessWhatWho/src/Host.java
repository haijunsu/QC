import java.util.ArrayList;
import java.util.List;

/**
 * Hosts hosts the game.
 * 
 * @author Haijun Su Date Nov 16, 2014
 *
 */
public class Host extends Base implements Runnable {

	/**
	 * Contestants who are playing the game
	 */
	private Contestant[] contestants;

	/**
	 * Game object
	 */
	private Game game;

	/**
	 * Queue that Contestants are waiting for GuessWhatWho question.
	 */
	private List<Contestant> waitGuessWhatWhoContestants = new ArrayList<Contestant>();
	/**
	 * answer timeout.
	 */
	private int answerTimeout = 20;

	/**
	 * Round for the game questions.
	 */
	private int currentRound;

	/**
	 * Question position in current round
	 */
	private int currentQuestion;

	/**
	 * Condition value. Waiting for announcer to start the game
	 */
	private boolean canStartGame = false;

	/**
	 * Condition Vaule. Contestant needs to wait the question before Host
	 * announces it.
	 */
	private Object waitForQuestion = new Object();

	/**
	 * Condition Vaule. If Host has already announces the question, Contestant
	 * doesn't have to wait.
	 */
	private boolean questionIsReady;

	/**
	 * Condition Vaule. Host needs to wait for all contestants sumbit their
	 * answers.
	 */
	private Object waitForAnswer = new Object();

	/**
	 * Condition Vaule. Identity which contestant is the first one that sumbits
	 * the answer for current question.
	 */
	private int submitId = -1;

	/**
	 * Condition value. If contestant lost a chance to answer question, he/she
	 * has to response the host. Also if contestant sumbits an answer, it is a
	 * response. If all contestants have responed the question, the Host can
	 * grade it.
	 */
	private int responseCounter;

	/**
	 * Construct Host.
	 */
	public Host() {
		// get the game object
		this.game = GuessWhatWho.getGame();
	}

	/**
	 * Find contestant by id
	 * 
	 * @param id
	 * @return
	 */
	private Contestant getContestantById(int id) {
		// since there is only 4 elements, linear search is ok
		for (int i = 0; i < contestants.length; i++) {
			if (contestants[i].getId() == id) {
				return contestants[i];
			}
		}
		return null;
	}

	/**
	 * contestants who will play the game
	 * 
	 * @param contestants
	 */
	public void setContestants(Contestant[] contestants) {
		this.contestants = contestants;
	}

	/**
	 * Whether all round questions have been asked.
	 * 
	 * @return
	 */
	public boolean isRoundQuestionsFinished() {
		return currentQuestion == this.game.getNumQuestions()
				* this.game.getNumRounds();
	}

	/**
	 * Wait for announcer signal to start the game.
	 */
	public synchronized void waitGameStartSignal() {
		info("wait for start signal...");
		if (!canStartGame) {
			while (true) {
				try {
					wait();
					break;
				} catch (InterruptedException e) {
					warn("Waiting for start signal is interrupted");
					e.printStackTrace();
					if (canStartGame)
						break;
				}
			}
		}
	}

	/**
	 * Announcer notifies him to start the game
	 */
	public synchronized void startGame() {
		canStartGame = true;
		notify();
	}

	/**
	 * Contestant ask for current question and current question must great than
	 * the question he has answered.
	 * 
	 * @param question
	 * @return
	 */
	public int getRoundQuestion(int question) {
		synchronized (waitForQuestion) {
			if (!questionIsReady && currentQuestion <= question) {
				while (true) {
					try {
						// current question is not ready
						waitForQuestion.wait();
						break;
					} catch (InterruptedException e) {
						warn("waiting for question is interrupted.");
						if (questionIsReady && currentQuestion > question) {
							break;
						}
					}
				}
			}
		}
		return currentQuestion;
	}

	/**
	 * Contestant submits the answer
	 * 
	 * @param id
	 */
	public synchronized void submitAnswer(int id) {
		++responseCounter;
		if (submitId < 0) { // only the 1st contestant can get score
			submitId = id;
			// wake up other contestants
			ContestantsThreadManager.interruptOthers(getContestantById(id)
					.getName());
		}
		if (responseCounter == contestants.length) {
			questionIsReady = false;
			synchronized (waitForAnswer) {
				waitForAnswer.notify();
			}
		}
	}

	/**
	 * Contestant has been interrupt and just response that he/she has lost the
	 * question.
	 */
	public synchronized void response() {
		// For interrupted contestant
		++responseCounter;
		if (responseCounter == contestants.length) {
			questionIsReady = false;
			synchronized (waitForAnswer) {
				waitForAnswer.notify();
			}
		}
	}

	/**
	 * Ask game question and grade it after all contestants has answered it.
	 */
	public void askQuestion() {
		info("Asking question: What is the meaning of number "
				+ currentQuestion + " in Round " + currentRound + "?");
		synchronized (waitForQuestion) {
			submitId = -1;
			questionIsReady = true;
			waitForQuestion.notifyAll();
		}
		debug("wait for answer of " + currentQuestion + " in round "
				+ currentRound);
		synchronized (waitForAnswer) {
			if (submitId < 0) {
				while (true) {
					try {
						waitForAnswer.wait();
						break;
					} catch (InterruptedException e) {
						warn("Waiting for answer is interrupted.");
						e.printStackTrace();
						if (submitId > -1) {// answer is ready
							break;
						}
					}
				}
			}
		}
		// check answer
		if (submitId > -1) {
			boolean correct = getRandomNumber(0, 100) <= (game
					.getRightPercent() * 100);
			Contestant ctt = getContestantById(submitId);
			debug("submit id: " + submitId);
			if (correct) {
				info(ctt.getName() + " is correct. Add score "
						+ game.getQuestionValues());
				ctt.adjustGameScore(game.getQuestionValues());
			} else {
				info(ctt.getName() + " is wrong. Substract score "
						+ game.getQuestionValues());
				ctt.adjustGameScore(0 - game.getQuestionValues());
			}
		} else {
			info("Nobody answers this question: " + currentQuestion);
		}

		// reset status
		synchronized (this) {
			responseCounter = 0;
			questionIsReady = false;
		}

	}

	/**
	 * Contestants ask for the final question
	 * 
	 * @param id
	 */
	public void getFinalQuestion(int id) {
		Contestant ctt = getContestantById(id);
		synchronized (this) {
			waitGuessWhatWhoContestants.add(ctt);
			info(ctt.getName() + " is asking for the final question.");
			if (waitGuessWhatWhoContestants.size() == contestants.length) {
				responseCounter = 0; // reset counter
				notify();
			}
		}
		synchronized (ctt) {
			while (true) {
				try {
					ctt.wait();
					break;
				} catch (InterruptedException e) {
					warn("waiting for final is interrupted.");
					if (ctt.isHasFinal()) {
						break;
					}
				}
			}
		}
	}

	/**
	 * Constestant submits the final answer.
	 * 
	 * @param id
	 */
	public synchronized void submitFinalAnswer(int id) {
		Contestant ctt = getContestantById(id);
		if (ctt.getGameScore() >= 0) {
			// grade question
			int weger = ctt.getWager();
			boolean correct = getRandomNumber(0, 100) <= 50; // 50%
																// percent
			if (correct) {
				info("Congs, " + ctt.getName()
						+ ". You have the right answer and earn " + weger
						+ " credits.");
				ctt.adjustGameScore(weger);
			} else {
				info("Sorry, " + ctt.getName()
						+ ". Your answer is wrong and you lost " + weger
						+ " credits.");
				ctt.adjustGameScore(0 - weger);
			}
		}
		++responseCounter;
		if (responseCounter == contestants.length) {
			notify();
		}
	}

	/**
	 * Contestant has no wager for the question and quit.
	 * 
	 * @param id
	 */
	public synchronized void quitFinalAnswer(int id) {
		info("I am sorry, " + getContestantById(id).getName() + ". Good-bye.");
		++responseCounter;
		if (responseCounter == contestants.length) {
			notify();
		}
	}

	/**
	 * announce the final question
	 */
	public synchronized void distributeFinalQuestion() {
		debug("handle final question");
		info("wait for all contestants ready for final question.");
		if (waitGuessWhatWhoContestants.size() < contestants.length) {
			while (true) {
				try {
					wait();
					break;
				} catch (InterruptedException e) {
					warn("Waiting contestants is interrupted.");
					e.printStackTrace();
					if (waitGuessWhatWhoContestants.size() == contestants.length) {
						break;
					}
				}
			}
		}
		info("Final question is blablabla......");
		debug("notify all contestants in order.");
		for (int i = 0; i < waitGuessWhatWhoContestants.size(); i++) {
			Contestant ctt = waitGuessWhatWhoContestants.get(i);
			info("Wake up " + ctt.getName() + " to answer final question.");
			synchronized (ctt) {
				ctt.setHasFinal(true);
				ctt.notify();
			}
		}

	}

	@Override
	public void run() {
		debug("Start...");
		waitGameStartSignal();
		info("Game start...");
		// round questions
		currentRound = 0;
		while (++currentRound <= game.getNumRounds()) {
			debug("Start round-" + currentRound);
			while (++currentQuestion <= currentRound * game.getNumQuestions()) {
				debug("Question: " + currentQuestion);
				askQuestion();
			}
			info("Round " + currentRound + " has done.");
		}

		distributeFinalQuestion();

		synchronized (this) {

			if (responseCounter < contestants.length) {
				// wait for answers
				while (true) {
					try {
						wait();
						break;
					} catch (InterruptedException e) {
						warn("Waiting contestant's answer is interrupted.");
						if (responseCounter == contestants.length) {
							break;
						}
					}
				}
			}
		}

		// find winner
		Contestant winner = contestants[0];// assume the first one is winner
		info(winner.getName() + " game score is " + winner.getGameScore());
		for (int i = 1; i < contestants.length; i++) {
			info(contestants[i].getName() + " game score is "
					+ contestants[i].getGameScore());
			// if they have same score, the previous one win because he may has
			// higher exam score.
			if (winner.getGameScore() < contestants[i].getGameScore()) {
				winner = contestants[i];
			}
		}

		// announce game winner
		info("The game winner is " + winner.getName()
				+ ". The highest game score is " + winner.getGameScore());
		info("Game over!");
		debug("exit.");
	}

	public int getAnswerTimeout() {
		return this.answerTimeout;
	}

	@Override
	public String getName() {
		return "Host";
	}

}
