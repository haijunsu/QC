import java.util.Random;

/**
 * This class implement general methods that can be used by sub-classes.
 * 
 * @author Haijun Su Date Nov 16, 2014
 *
 */
public abstract class Base {
	
	protected int id;
		
	/**
	 * addition value for random seed.
	 */
	private static int randomSeed = 1000;

	/**
	 * Generate random number in range
	 *
	 * @param start
	 * @param end
	 * @return random number
	 */
	public int getRandomNumber(int start, int end) {
		if (start > end) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		Random randomGenerator = new Random(System.currentTimeMillis() + randomSeed);
		long range = (long) end - (long) start + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * randomGenerator.nextDouble());
		int randomNumber = (int) (fraction + start);
		randomSeed = randomNumber * 1000;
		return randomNumber;
	}

	/**
	 * Generate random number array in length with start value and end value
	 *
	 * @param start
	 * @param len
	 * @return
	 */
	public int[] getRandomNumberSequence(int start, int end, int len) {
		Random randomGenerator = new Random(System.currentTimeMillis() + randomSeed);
		int[] randomNumberSequence = new int[len];
		for (int i = 0; i < len; i++) {
			long fraction = (long) (len * randomGenerator.nextDouble());
			randomNumberSequence[i] = (int) (fraction % end + start);
		}
		randomSeed = randomNumberSequence[0] * 1000;
		return randomNumberSequence;

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
	 * Print out debug message
	 * @param message
	 */
	protected void debug(Object message) {
		GuessWhatWho.log(0, getName() + " - " + message);
	}
	/**
	 * Print out info message
	 * @param message
	 */
	protected void info(Object message) {
		GuessWhatWho.log(1, getName() + " - " + message);
	}

	/**
	 * Print out warn message
	 * @param message
	 */
	protected void warn(Object message) {
		GuessWhatWho.log(2, getName() + " - " + message);
	}

	/**
	 * Print out error message
	 * @param message
	 */
	protected void error(Object message) {
		GuessWhatWho.log(3, getName() + " - " + message);
	}

	protected void thinkingAndAnswer() {
		
	}
	/**
	 * The instance name, which will be used for output.
	 * 
	 * @return
	 */
	public abstract String getName();

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
