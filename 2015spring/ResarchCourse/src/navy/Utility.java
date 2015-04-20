package navy;

import java.util.Random;

/**
 * Utility for research course
 * 
 * @author suhaijun
 *
 */
public class Utility {
	public static void main(String[] args) {
		double[] testVs = randomGaussian(10000);
		int k = 0;
		for (int i = 0; i < testVs.length; i++) {
			if (testVs[i] > 1 || testVs[i] < 0) {
				++k;
				continue;
			}
			System.out.println(testVs[i]);
		}
		System.out.println(k);
	}

	public static double[] randomUniform(int size) {
		double[] values = new double[size];
		Random rd = new Random();
		for (int i = 0; i < size; i++) {
			values[i] = rd.nextDouble();
		}
		return values;
	}
	
	/**
	 * generate random number between 0, 1 with normal distribution (mean=0.5,
	 * deviation=0.5)
	 * 
	 * @param size
	 * @return
	 */
	public static double[] randomGaussian(int size) {
		return randomGaussian(size, 0.5, 0.5);
	}

	/**
	 * generate random number with normal distribution
	 * 
	 * @param size
	 * @param mean
	 * @param deviation
	 * @return
	 */
	public static double[] randomGaussian(int size, double mean, double deviation) {
		double[] values = new double[size];
		Random rd = new Random();
		for (int i = 0; i < size; i++) {
			double v = rd.nextGaussian();
			if (v > 1.0 || v < -1.0) {
				--i;
				continue; // ignore
			}
			values[i] = mean + v * deviation;
		}
		return values;
	}

}
