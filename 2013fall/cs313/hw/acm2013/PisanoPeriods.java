/*
 * 
 */
package acm2013;

import java.math.BigInteger;

/**
 * D. Pisano Periods
 * 
 * @author Haijun Su Created date: 2013-11-17
 */
public class PisanoPeriods extends BaseAcm {

	public enum FormulaType {
		TwoToN, FiveToN, FiveToNTwice, TenToN
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PisanoPeriods pp = new PisanoPeriods();
		int size = pp.inputInteger();
		pp.process(pp.inputIntegerDatasets(size, 2));

	}

	private void process(Integer[][] data) {
		System.out.println("========== Processing ==========");
		System.out.println("Input: \n" + data.length);
		int[][] results = new int[data.length][2];
		for (int i = 0; i < data.length; i++) {
			System.out.println(data[i][0] + " " + data[i][1]);
			int m = data[i][1];
			int n = 0;
			int km = 0;
			long fn_1 = 1; // f(n-2) = f(0) = 0
			long fn_2 = 0; // f(n-1) = f(1) = 1
			long fn = 0;
			while (true) {
				// calculate fn
				if (n == 0) {
					fn = 0;
				} else if (n == 1) {
					fn = 1;
				} else {
					fn = fn_1 + fn_2;
				}
				// guess km
				if (m > 2) {
					if (n % 2 == 0 && fn % m == 0) {
						km = n;
					}
				} else if (m == 2) {
					// f3 = 4, f6 = 8, f9 = 34 ...
					km = 3;
				} else {
					// ignore m < 2, it shouldn't reach this codes.
					break;
				}
				if (km > 0 && n > 0) {
					//if (verifyKm(km, m, n, fn_2, fn_1)) {
						//System.out.println("call break!");
						break;
					//}
				}
				km = 0;
				n++;
				fn_2 = fn_1;
				fn_1 = fn;
			}
			results[i][0] = data[i][0];
			results[i][1] = km;
		}
		// }
		System.out.println("========== Done ==========");
		System.out.println("Result output: ");
		for (int[] pp : results) {
			System.out.println(pp[0] + " " + pp[1]);
		}
	}

	private boolean verifyKm(int km, int m, int n, long fn_2, long fn_1) {
		System.out.println("::: km = " + km + ", m = " + m + ", n = " + n);
		// k(m) <= m^2 - 1
		if (km > m * m - 1)
			return false;
		long max = 0;
		long mTwoToN = (int) Math.pow(2, n);
		long kmTwoToN = 3 * (int) Math.pow(2, n - 1);
		max = Math.max(max, kmTwoToN);
		long mFiveToN = (int) Math.pow(5, n);
		long kmFiveToN = 4 * mFiveToN;
		max = Math.max(max, kmFiveToN);
		long mFiveToNtwice = 2 * mFiveToN;
		long kmFiveToNtwice = 6 * n;
		max = Math.max(max, kmFiveToNtwice);
		// because of m >= 2, n must greater than 2.
		long mTenToN = (int) Math.pow(10, n);
		long kmTenToN = 15 * (int) Math.pow(10, n - 1);
		max = Math.max(max, kmTenToN);
		long fn = 0;
		System.out.println("Max = " + max);
		for (long i = n; i <= max; i++) {
			fn = fn_1 + fn_2;
			System.out.println("f(" + i + ")=" + fn );
			if (i == kmTwoToN) {
				System.out.println("kmTwoToN");
				if (fn % mTwoToN != 0)
					return false;
			} else if (i == kmFiveToN) {
				System.out.println("mFiveToN=" + mFiveToN + ", fn=" + fn
						+ ", kmFiveToN=" + kmFiveToN);
				if (fn % mFiveToN != 0)
					return false;
			} else if (i == kmFiveToNtwice) {
				System.out.println("mFiveToNtwice=" + mFiveToNtwice + ", fn="
						+ fn + ", kmFiveToNtwice=" + kmFiveToNtwice);
				if (fn % mFiveToNtwice != 0)
					return false;
			} else if (i == kmTenToN) {
				System.out.println("kmTenToN");
				if (fn % mTenToN != 0)
					return false;
			}
			fn_2 = fn_1;
			fn_1 = fn;
		}
		return true;
	}

	/**
	 * Check if the double is integer.
	 * 
	 * @param d
	 * @return
	 */
	private boolean isInteger(double d) {
		System.out.println("logValue = " + Double.toString(d));
		return (d * 10000) % 10000 == 0;
	}

	/**
	 * Logarithm
	 * 
	 * @param base
	 * @param num
	 * @return
	 */
	private double logOfBase(int base, int num) {
		return Math.log((double) num) / Math.log((double) base);
	}

}
