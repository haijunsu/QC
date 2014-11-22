public class Contestant extends Base implements Runnable {

	/**
	 * The id of Contestant
	 */
	private int examScore;

	private int gameScore;

	private boolean winExam;

	private boolean hasFinal;

	private Announcer announcer;

	private Host host;

	public Contestant(int id, Announcer announcer) {
		this.id = id;
		this.announcer = announcer;
	}

	@Override
	public void run() {
		debug("Start...");
		info("Ask for join a group");
		// build group
		announcer.joinGroup();

		info("enter a classroom");

		info("Ask for a seat");
		// having a seat
		announcer.askForSeat();

		info("Thinking and answering...");
		long thinkingTime = getRandomNumber(0, announcer.getExamTime());
		debug("thinking time: " + thinkingTime);
		try {
			Thread.sleep(thinkingTime);
		} catch (InterruptedException e) {
			warn("Thinking was interrupted.");
			e.printStackTrace();
		}
		debug("Submits answer.");
		announcer.submitAnswers(id);
		debug("My score: " + examScore + ". Am I win? " + winExam);
		if (!winExam) {
			info("I am lost and exit.");
			ContestantsThreadManager.remove(getName());
			info("Age: " + age());
			return;
		}
		debug("waiting for being introduced by announcer.");
		announcer.readyForGame(id);
		info("Thank you. I am ready for the game!");

		debug("waiting to start game...");
		while (host.getRoundQuestion() >= 0) {
			thinkingTime = getRandomNumber(0, host.getAnswerTimeout());
			boolean isInterrupted = false;
			debug("thinking time: " + thinkingTime);
			try {
				Thread.sleep(thinkingTime);
			} catch (InterruptedException e) {
				warn("Thinking was interrupted.");
				isInterrupted = true;
			}
			if (isInterrupted) {
				host.response();
			} else {
				debug("submit answer.");
				host.submitAnswer(id);
			}
		}
		if (gameScore >= 0) {
			thinkingTime = getRandomNumber(0, host.getAnswerTimeout());
			boolean isInterrupted = false;
			debug("thinking time: " + thinkingTime);
			try {
				Thread.sleep(thinkingTime);
			} catch (InterruptedException e) {
				warn("Thinking was interrupted.");
				isInterrupted = true;
			}
			if (isInterrupted) {
				host.quitFinalAnswer();
			} else {
				host.submitFinalAnswer();
			}
		} else {
			host.quitFinalAnswer();
		}
		ContestantsThreadManager.remove(getName());
		info("Age: " + age());
		info("exit");
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

	public void setHost(Host host) {
		this.host = host;
	}

	public int getGameScore() {
		return gameScore;
	}

	public int getWager() {
		if (gameScore > 0) {
			return getRandomNumber(0, gameScore);
		}
		return 0;
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
