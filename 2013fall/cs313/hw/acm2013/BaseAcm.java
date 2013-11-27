/*
 * 
 */
package acm2013;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This is base class for Greater New York Programming Contest projects
 * 
 * @author Haijun Su Created date: 2013-11-16
 */
public class BaseAcm {
	public enum InputType {
		Integer, Float
	}

	/**
	 * Input Integer Data sets
	 * 
	 * @param size
	 *            Data set size
	 * @param lengthOfRow
	 *            numbers each line
	 * @return
	 */
	protected Integer[][] inputIntegerDatasets(int size, int lengthOfRow) {
		return (Integer[][]) input(size, lengthOfRow, InputType.Integer);
	}

	/**
	 * Input Float Data sets
	 * 
	 * @param size
	 *            Data set size
	 * @param lengthOfRow
	 *            numbers each line
	 * @return
	 */
	protected Float[][] inputFloatDatasets(int size, int lengthOfRow) {
		return (Float[][]) input(size, lengthOfRow, InputType.Float);
	}

	/**
	 * Input Integers from one line
	 * 
	 * @param lengthOfRow
	 *            numbers each line
	 * @return
	 */
	protected Integer[] inputIntegersFromOneLine(int lengthOfRow) {
		return (Integer[]) inputNumbersFromOneLine(lengthOfRow,
				InputType.Integer);
	}

	/**
	 * Input Floats from one line
	 * 
	 * @param lengthOfRow
	 *            numbers each line
	 * @return
	 */
	protected Integer[] inputFloatsFromOneLine(int lengthOfRow) {
		return (Integer[]) inputNumbersFromOneLine(lengthOfRow,
				InputType.Integer);
	}

	/**
	 * Input a integer number.
	 * 
	 * @return
	 */
	protected int inputInteger() {
		int size = 0;
		// ask for tree height
		System.out.print("Please input the data sets size: ");
		while (size < 1) {
			Scanner scan = new Scanner(System.in);
			size = scan.nextInt();
			if (size < 1) {
				System.out
						.println("The value is too small. Please try again: ");
			}
		}
		return size;
	}

	/**
	 * Input numbers from one line
	 * 
	 * @param lengthOfRow
	 *            how many numbers in one line
	 * @param type
	 *            number type
	 * @return
	 */
	private Object[] inputNumbersFromOneLine(int lengthOfRow, InputType type) {
		Object[] numbers = null;
		switch (type) {
		case Integer:
			numbers = new Integer[lengthOfRow];
			break;

		case Float:
			numbers = new Float[lengthOfRow];
			break;

		default:
			System.out.println("Unsupport type!");
			return null;
		}
		System.out.print("Please input the numbers of line: ");
		Scanner scan = new Scanner(System.in);
		String numberLine = scan.nextLine();
		// need handle the input errors if I have more time.
		StringTokenizer st = new StringTokenizer(numberLine, " ");
		int elementIndex = 0;
		while (st.hasMoreTokens() && elementIndex < lengthOfRow) {
			switch (type) {
			case Integer:
				numbers[elementIndex++] = Integer.parseInt(st.nextToken()
						.trim());
				break;

			case Float:
				numbers[elementIndex++] = Float.parseFloat(st.nextToken()
						.trim());
				break;

			default:
				break;
			}
		}
		return numbers;
	}

	/**
	 * Input data set
	 * 
	 * @param size
	 *            data set size
	 * @param lengthOfRow
	 *            numbers each line
	 * @param type
	 *            number type
	 * @return
	 */
	protected Object[][] input(int size, int lengthOfRow, InputType type) {
		if (size < 1)
			return null;
		Object[][] data = null;
		switch (type) {
		case Integer:
			data = new Integer[size][];
			break;

		case Float:
			data = new Float[size][];
			break;

		default:
			System.out.println("Unsupport type!");
			return null;
		}
		// input lines of number
		for (int i = 0; i < data.length; i++) {
			data[i] = inputNumbersFromOneLine(lengthOfRow, type);
		}
		return data;
	}
	
	/**
	 * Format output of float number. Remove the ".0" at end of the number.
	 * 
	 * @param num
	 * @return
	 */
	public String formatNumber(float num) {
		if (num % 1 == 0) {
			return Integer.toString((int) num);
		}
		return Float.toString(num);
	}
	
	/**
	 * Fix float value
	 * @param f
	 * @return
	 */
	public Float fixFloatValue(float f, int scale) {
		BigDecimal bigD = new BigDecimal(Float.toString(f));
		BigDecimal one = new BigDecimal("1");
		return bigD.divide(one, scale, BigDecimal.ROUND_HALF_UP)
				.floatValue();
	}
	/**
	 * Fix double value
	 * @param f
	 * @return
	 */
	public Double fixDoubleValue(double f, int scale) {
		BigDecimal bigD = new BigDecimal(Double.toString(f));
		BigDecimal one = new BigDecimal("1");
		return bigD.divide(one, scale, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}

}
