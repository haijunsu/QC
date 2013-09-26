package cs313.ch1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * There should be a procedure that reads in a line from the terminal into an
 * array of char. A second procedure has as its input the location in the array
 * that it should start reading from the char array, one by one and creates an
 * integer from the first digit encountered to until it finds a non-digit.
 * 
 * There should be another procedure that once two integers are in memory, adds
 * them and outputs both numbers and the result.
 * 
 * @author suhaijun
 * 
 */

public class Sep03 {

	/**
	 * main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Sep03 hw = new Sep03();
		hw.processInput(hw.readLine());
	}

	/**
	 * Read char[] from terminal.
	 * 
	 * @return
	 */
	public char[] readLine() {
		System.out.print("Please input some integer numbers: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine().toCharArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Print sum to console
	 * @param preNum
	 * @param input
	 * @param start
	 * @param end
	 * @return sum value
	 */
	private int processSum(int preNum, char[] input, int start, int end) {
		int num = 0;
		for (int j = start; j < end; j++) {
			num += Integer.parseInt(String.valueOf(input[j]))
					* (int) Math.pow(10.0, end - (j + 1));
		}
		if (preNum > -1) {
			System.out.println(preNum + " + " + num + " = " + (preNum + num));
			num = preNum + num;
		}
		
		return num;
	}

	/**
	 * Process input value.
	 * 
	 * @param input
	 */
	public void processInput(char[] input) {
		int start = 0;
		int preNum = -1;
		boolean isfound = false;
		for (int i = 0; i < input.length; i++) {
			if (Character.isDigit(input[i])) {
				if (!isfound) {
					isfound = true;
					start = i;
				}
			} else {
				if (isfound) {
					preNum = processSum(preNum, input, start, i);
					isfound = false;
				}
			}
		}
		// handle the last number.
		if (isfound) {
			processSum(preNum, input, start, input.length);
		}
	}
}
