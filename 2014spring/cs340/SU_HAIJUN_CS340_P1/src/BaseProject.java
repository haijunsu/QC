import java.util.Random;

public abstract class BaseProject {

	public void msg(String message) {
			ProjectMainClass.msg(getName(), message);
	}

	public abstract String getName();

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

	public long age() {
		return System.currentTimeMillis() - ProjectMainClass.startTime;
	}
}
