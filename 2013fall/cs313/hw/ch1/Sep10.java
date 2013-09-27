package ch1;

/**
 * An array contains either digits or space. A procedure has as its input the
 * location in the array that it should start reading from the char array, one
 * by one and creates an integer from the first digit encountered to until it
 * finds a non-digit.
 * 
 * There should be another procedure that once two integers are in memory, adds
 * them and outputs both numbers and the result. It continues using the sum as
 * one of the integers.
 * 
 * This is a program revised the homework Sep03.
 * 
 * Outputs: 
 * Sum(23) = 23 
 * Sum(23 + 4379) = 4402 
 * Sum(4402 + 9459) = 13861
 * Sum(13861 + 320) = 14181 
 * Sum(14181 + 378) = 14559
 * 
 * @author suhaijun
 * @see Sep03
 */
public class Sep10 {

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Sep10 hw = new Sep10();
		char[] input = { '2', '3', ' ', '4', '3', '7', '9', ' ', '9', '4', '5',
				'9', ',', '3', '2', '0', '\'', '0', '3', '7', '8', ',' };
		hw.process(input);
	}

	/**
	 * Process char array and print sum of number values.
	 * 
	 * @param input
	 */
	private void process(char[] input) {
		int preNum = 0;
		int start = 0;
		boolean isFound = false;
		for (int i = 0; i < input.length; i++) {
			if (isDigit(input[i])) {
				if (!isFound) {
					isFound = true;
					start = i;
				}
			} else {
				if (isFound) {
					int num = buildNumber(input, start, i);
					printSum(preNum, num);
					preNum += num;
					isFound = false;
				}
			}
		}
		// handle the last number
		if (isFound) {
			printSum(preNum, buildNumber(input, start, input.length));
		}
	}

	/**
	 * Print sum value to screen
	 * 
	 * @param num1
	 * @param num2
	 */
	private void printSum(int num1, int num2) {
		if (num1 == 0) {
			System.out.println("Sum(" + num2 + ") = " + num2);
		} else {
			System.out.println("Sum(" + num1 + " + " + num2 + ") = "
					+ (num1 + num2));
		}

	}

	/**
	 * Find the number values in a char in a rang and build them to a integer.
	 * 
	 * @param input
	 * @param start
	 * @param end
	 * @return
	 */
	private int buildNumber(char[] input, int start, int end) {
		int num = 0;
		for (int i = start; i < end; i++) {
			num = num * 10 + (input[i] - '0');
		}
		return num;
	}

	/**
	 * Check if the char is between '0' and '9'
	 * 
	 * @param c0
	 * @return
	 */
	private boolean isDigit(char c0) {
		int num = c0 - '0';
		return num >= 0 && num < 10;
	}
}
