package server.impl;

import common.Logger;

/**
 * Contestants attend the written and winners attend the game.
 * 
 * @author Haijun Su Date Nov 13, 2014
 *
 */
public class Contestant extends Base implements Runnable {

	private static Logger logger = Logger.getLogger(Contestant.class);

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
	 * The question from game he is thinking and answering
	 */
	private int currentQuestion;

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

	public void joinGroup() {
		logger.info("Ask for join a group");
		announcer.joinGroup();
	}

	public void askForSeat() {
		logger.info("Enter a classroom and take a seat");
		announcer.askForSeat();
	}

	public void takingExam() {
		logger.info("taking exam...");
		logger.info("Thinking and answering...");
		try {
			thinkingAndAnswer(announcer.getExamTime());
		} catch (InterruptedException e) {
			logger.warn("Thinking was interrupted.");
			e.printStackTrace();
		}
	}

	public boolean submitExam() {
		logger.debug("Submitting answer.");
		announcer.submitAnswers(id);
		logger.debug("My score: " + examScore + ". Am I win? " + winExam);
		return winExam;
	}

	public void readyForGame() {
		logger.info("waiting for being introduced by announcer.");
		announcer.readyForGame(id);
		logger.info("Thank you. I am ready for the game!");
	}
	
	public void playGame() {
		logger.debug("waiting to start game...");
		while (!host.isRoundQuestionsFinished()) {
			// get current question
			currentQuestion = host.getRoundQuestion(currentQuestion);
			boolean isInterrupted = false;
			try {
				thinkingAndAnswer(host.getAnswerTimeout());
			} catch (InterruptedException e) {
				logger.warn("Thinking was interrupted.");
				isInterrupted = true;
			}
			if (isInterrupted) {
				logger.debug("I lost a question. Another guy has submitted the answer.");
				host.response();
			} else {
				logger.debug("submit answer.");
				host.submitAnswer(id);
			}
		}
		logger.debug("Round questions have been finished.");		
	}

	public void playFinalQuestion() {
		logger.debug("Get the final question");
		host.getFinalQuestion(id);
		if (gameScore >= 0) {
			try {
				logger.debug("Prepare wager.");
				this.wager = getRandomNumber(0, gameScore);
				// thinking
				thinkingAndAnswer(host.getAnswerTimeout());
			} catch (InterruptedException e) {
				logger.warn("Thinking was interrupted.");
			}
			logger.info("Submit the final question.");
			host.submitFinalAnswer(id);
			logger.info("All done. Bye.");
		} else {
			logger.info("I don't have any score. good-bye");
			host.quitFinalAnswer(id);
		}
	}
	/**
	 * Run contestant thread
	 */
	@Override
	public void run() {
		logger.debug("Start...");
		logger.info("Ask for join a group");
		// build group
		announcer.joinGroup();

		logger.info("Enter a classroom and take a seat");
		// having a seat
		announcer.askForSeat();

		logger.info("taking exam...");
		logger.info("Thinking and answering...");
		try {
			thinkingAndAnswer(announcer.getExamTime());
		} catch (InterruptedException e) {
			logger.warn("Thinking was interrupted.");
			e.printStackTrace();
		}
		logger.debug("Submitting answer.");
		announcer.submitAnswers(id);
		logger.debug("My score: " + examScore + ". Am I win? " + winExam);
		if (!winExam) {
			ContestantsThreadManager.remove(getName());
			logger.info("Exit. Age: " + age());
			return;
		}
		logger.info("waiting for being introduced by announcer.");
		announcer.readyForGame(id);
		logger.info("Thank you. I am ready for the game!");

		logger.debug("waiting to start game...");
		while (!host.isRoundQuestionsFinished()) {
			// get current question
			currentQuestion = host.getRoundQuestion(currentQuestion);
			boolean isInterrupted = false;
			try {
				thinkingAndAnswer(host.getAnswerTimeout());
			} catch (InterruptedException e) {
				logger.warn("Thinking was interrupted.");
				isInterrupted = true;
			}
			if (isInterrupted) {
				logger.debug("I lost a question. Another guy has submitted the answer.");
				host.response();
			} else {
				logger.debug("submit answer.");
				host.submitAnswer(id);
			}
		}
		logger.debug("Round questions have been finished.");

		logger.debug("Get the final question");
		host.getFinalQuestion(id);
		if (gameScore >= 0) {
			try {
				logger.debug("Prepare wager.");
				this.wager = getRandomNumber(0, gameScore);
				// thinking
				thinkingAndAnswer(host.getAnswerTimeout());
			} catch (InterruptedException e) {
				logger.warn("Thinking was interrupted.");
			}
			logger.info("Submit the final question.");
			host.submitFinalAnswer(id);
			logger.info("All done. Bye.");
		} else {
			logger.info("I don't have any score. good-bye");
			host.quitFinalAnswer(id);
		}
		ContestantsThreadManager.remove(getName());
		logger.info("Exit. Age: " + age());
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

	public int getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(int currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	@Override
	public String toString() {
		return "Contestant [id=" + id + ", examScore=" + examScore
				+ ", winExam=" + winExam + "]";
	}

}
