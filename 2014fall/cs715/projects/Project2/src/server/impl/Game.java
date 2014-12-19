package server.impl;
/**
 * Define game properties
 * 
 * @author Haijun Su Date Nov 16, 2014
 *
 */
public class Game {
	private int numRounds = 2;
	private int numQuestions = 5;
	private int questionValues = 200;
	private float rightPercent = 0.65f;

	public Game(int numRounds, int numQuestions, int questionValues,
			float rightPercent) {
		this.numRounds = numRounds;
		this.numQuestions = numQuestions;
		this.questionValues = questionValues;
		this.rightPercent = rightPercent;
	}

	public int getNumRounds() {
		return numRounds;
	}

	public void setNumRounds(int numRounds) {
		this.numRounds = numRounds;
	}

	public int getNumQuestions() {
		return numQuestions;
	}

	public void setNumQuestions(int numQuestions) {
		this.numQuestions = numQuestions;
	}

	public int getQuestionValues() {
		return questionValues;
	}

	public void setQuestionValues(int questionValues) {
		this.questionValues = questionValues;
	}

	public float getRightPercent() {
		return rightPercent;
	}

	public void setRightPercent(float rightPercent) {
		this.rightPercent = rightPercent;
	}

	@Override
	public String toString() {
		return "Game [numRounds=" + numRounds + ", numQuestions="
				+ numQuestions + ", questionValues=" + questionValues
				+ ", rightPercent=" + rightPercent + "]";
	}
	
	
}
