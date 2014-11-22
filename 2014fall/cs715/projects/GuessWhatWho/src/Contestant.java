public class Contestant extends Base implements Runnable {

	/**
	 * Score of the written exam
	 */
	private int examScore;

	/**
	 * Score of the game
	 */
	private int gameScore;

	/**
	 * Flag whether wins the written exam
	 */
	private boolean winExam;

	/**
	 * Flag whether wins the game
	 */
	private boolean hasFinal;

	/**
	 * Wager for the final question.
	 */
	private int wager;

	/**
	 * Announcer
	 */
	private Announcer announcer;

	/**
	 * Host
	 */
	private Host host;

	/**
	 * Construct with id and Announcer
	 * 
	 * @param id
	 * @param announcer
	 */
	public Contestant(int id, Announcer announcer) {
		this.id = id;
		this.announcer = announcer;
	}

	/**
	 * Run contestant thread
	 */
	@Override
	public void run() {
		debug("Start...");
		info("Ask for join a group");
		// build group
		announcer.joinGroup();

		info("Enter a classroom and ask for a seat");
		// having a seat
		announcer.askForSeat();

		info("taking exam...");
		info("Thinking and answering...");
		try {
			thinkingAndAnswer(announcer.getExamTime());
		} catch (InterruptedException e) {
			warn("Thinking was interrupted.");
			e.printStackTrace();
		}
		debug("Submitting answer.");
		announcer.submitAnswers(id);
		debug("My score: " + examScore + ". Am I win? " + winExam);
		if (!winExam) {
			ContestantsThreadManager.remove(getName());
			info("Exit. Age: " + age());
			return;
		}
		info("waiting for being introduced by announcer.");
		announcer.readyForGame(id);
		info("Thank you. I am ready for the game!");

		debug("waiting to start game...");
		while (host.getRoundQuestion() >= 0) {
			boolean isInterrupted = false;
			try {
				thinkingAndAnswer(host.getAnswerTimeout());
				;
			} catch (InterruptedException e) {
				warn("Thinking was interrupted.");
				isInterrupted = true;
			}
			if (isInterrupted) {
				debug("I lost a question. Another guy has submitted the answer.");
				host.response();
			} else {
				debug("submit answer.");
				host.submitAnswer(id);
			}
		}
		debug("Round questions have been finished.");

		debug("Get the final question");
		host.getFinalQuestion(id);
		if (gameScore >= 0) {
			try {
				debug("Prepare wager.");
				this.wager = getRandomNumber(0, gameScore);
				// thinking
				thinkingAndAnswer(host.getAnswerTimeout());
			} catch (InterruptedException e) {
				warn("Thinking was interrupted.");
			}
			info("Submit the final question.");
			host.submitFinalAnswer(id);
			info("All done. Bye.");
		} else {
			info("I don't have any score. good-bye");
			host.quitFinalAnswer(id);
		}
		ContestantsThreadManager.remove(getName());
		info("Exit. Age: " + age());
	}

	public void adjustGameScore(int score) {
		this.gameScore += score;
	}

	@Override
	public String getName() {
		return "Contestant " + id;
	}

	public void setExamScore(int examScore) {
		this.examScore = examScore;
	}

	public int getExamScore() {
		return this.examScore;
	}

	public void setWinExam(boolean winExam) {
		this.winExam = winExam;
	}

	public boolean isWinExam() {
		return this.winExam;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public int getGameScore() {
		return gameScore;
	}

	public int getWager() {
		return this.wager;
	}

	public boolean isHasFinal() {
		return hasFinal;
	}

	public void setHasFinal(boolean hasFinal) {
		this.hasFinal = hasFinal;
	}

	@Override
	public String toString() {
		return "Contestant [id=" + id + ", examScore=" + examScore
				+ ", winExam=" + winExam + "]";
	}

}
