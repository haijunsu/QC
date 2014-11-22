import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Haijun Su Date Nov 16, 2014
 *
 */
public class Host extends Base implements Runnable {

	/**
	 * Contestants
	 */
	private Contestant[] contestants;

	private Game game;
	/**
	 * Queue that Contestants are waiting for GuessWhatWho question.
	 */
	private List<Contestant> waitGuessWhatWhoContestants = new ArrayList<Contestant>();
	/**
	 * answer timeout. default is 20s.
	 */
	private int answerTimeout = 20;

	private int currentRound;

	private int currentQuestion;

	/**
	 * Condition Vaule
	 */
	private Object waitForQuestion = new Object();

	/**
	 * Condition Vaule
	 */
	private boolean questionIsReady;

	/**
	 * Condition Vaule
	 */
	private Object waitForAnswer = new Object();

	/**
	 * Condition Vaule
	 */
	private int submitId = -1;

	/**
	 * Condition value
	 */
	private int responseCount;

	/**
	 * Condition value
	 */
	private boolean isQuitFinal;

	/**
	 * Condition value
	 */
	private boolean isDoneFinal;

	public Host() {
		this.game = GuessWhatWho.getGame();
	}

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
	 * Condition value.
	 */
	private boolean canStartGame = false;

	public void setContestants(Contestant[] contestants) {
		this.contestants = contestants;
	}

	public synchronized void startGame() {
		canStartGame = true;
		notify();
	}

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

	public int getRoundQuestion() {
		synchronized (waitForQuestion) {
			if (!questionIsReady && currentQuestion >= 0) {
				while (true) {
					try {
						waitForQuestion.wait();
						break;
					} catch (InterruptedException e) {
						warn("waiting for question is interrupted.");
						if (questionIsReady) {
							break;
						}
					}
				}
			}
		}
		return currentQuestion;
	}

	public synchronized void submitAnswer(int id) {
		++responseCount;
		if (submitId < 0) { // only the 1st contestant can get score
			submitId = id;
			// wake up other contestants
			ContestantsThreadManager.interruptOthers(getContestantById(id)
					.getName());
		}
		if (responseCount == contestants.length) {
			questionIsReady = false;
			synchronized (waitForAnswer) {
				waitForAnswer.notify();
			}
		}
	}

	// For interrupted contestant
	public synchronized void response() {
		++responseCount;
		if (responseCount == contestants.length) {
			questionIsReady = false;
			synchronized (waitForAnswer) {
				waitForAnswer.notify();
			}
		}
	}

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
						if (submitId >= 0) {// answer is ready
							break;
						}
					}
				}
			}
		}
		// check answer
		boolean correct = getRandomNumber(0, 100) <= (game.getRightPercent() * 100);
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

		// reset status
		synchronized (this) {
			responseCount = 0;
		}

	}

	public void getFinalQuestion(int id) {
		Contestant ctt = getContestantById(id);
		waitGuessWhatWhoContestants.add(ctt);
		synchronized (this) {
			if (waitGuessWhatWhoContestants.size() == contestants.length) {
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
					e.printStackTrace();
					if (ctt.isHasFinal()) {
						break;
					}
				}
			}
		}
	}

	public synchronized void submitFinalAnswer() {
		isDoneFinal = true;
		notify();
	}

	public synchronized void quitFinalAnswer() {
		isQuitFinal = true;
		notify();
	}

	public synchronized void handleFinalQuestion() {
		debug("handle final question");
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
		for (int i = 0; i < waitGuessWhatWhoContestants.size(); i++) {
			Contestant ctt = waitGuessWhatWhoContestants.get(i);
			ctt.setHasFinal(true);
			ctt.notify();
			// wait for answer
			while (true) {
				try {
					wait();
					break;
				} catch (InterruptedException e) {
					warn("Waiting contestant's answer is interrupted.");
					e.printStackTrace();
					if (isQuitFinal || isDoneFinal) {
						break;
					}
				}
			}
			if (isDoneFinal) {
				// grade question
				int weger = ctt.getWager();
				boolean correct = getRandomNumber(0, 100) <= 50; // 50% percent
				if (correct) {
					ctt.adjustGameScore(weger);
				} else {
					ctt.adjustGameScore(0 - weger);
				}
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
			currentQuestion = 0;
			while (++currentQuestion <= game.getNumQuestions()) {
				debug("Question: " + currentQuestion);
				askQuestion();
			}
			info("Round " + currentRound + " has done.");
		}
		synchronized (waitForQuestion) {
			currentQuestion = -1;
			waitForQuestion.notifyAll();
		}

		handleFinalQuestion();

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
