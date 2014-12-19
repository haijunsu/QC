package server.impl;

import java.util.Random;

import common.Utils;

/**
 * This class implement general methods that can be used by sub-classes.
 * 
 * @author Haijun Su Date Nov 16, 2014
 *
 */
public abstract class Base {

	/**
	 * Id for sub-object. Inited by sub-class.
	 */
	protected int id;

	/**
	 * Generate random number in range
	 *
	 * @param start
	 * @param end
	 * @return random number
	 */
	public int getRandomNumber(int start, int end) {
		return Utils.getRandomNumber(start, end);
	}

	/**
	 * Generate random number array in length with start value and end value
	 *
	 * @param start
	 * @param len
	 * @return
	 */
	public int[] getRandomNumberSequence(int start, int end, int len) {
		return Utils.getRandomNumberSequence(start, end, len);
	}

	/**
	 * process age
	 * 
	 * @return
	 */
	protected long age() {
		return GuessWhatWho.age();
	}

	/**
	 * Thinking time for each question
	 * 
	 * @throws InterruptedException
	 */
	protected void thinkingAndAnswer(int maxTime) throws InterruptedException {
		if (maxTime < 10)
			maxTime = 11;// at list 1 for generate random number
		long nap = getRandomNumber(10, maxTime);
		Thread.sleep((long) nap);
	}

	/**
	 * The instance name, which will be used for output.
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * get object id
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Base other = (Base) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
