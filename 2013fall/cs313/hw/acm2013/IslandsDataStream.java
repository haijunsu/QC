/*
 * 
 */
package acm2013;

import java.util.Stack;

/**
 * A. Islands in the Data Stream
 * 
 * <pre>
 * Please input the dataset size: 4
 * Please input the numbers of line: 1 0 0 1 1 2 2 1 1 0 1 2 2 1 1 0 
 * Please input the numbers of line: 2 0 1 2 3 4 3 2 1 2 3 4 3 2 1 0
 * Please input the numbers of line: 3 0 1 0 1 0 1 0 1 0 1 0 1 0 1 0 
 * Please input the numbers of line: 4 0 1 2 3 4 5 6 7 6 5 4 3 2 1 0
 * Dataset size: 4
 * Raw data: 
 * 1  0 0 1 1 2 2 1 1 0 1 2 2 1 1 0 
 * 2  0 1 2 3 4 3 2 1 2 3 4 3 2 1 0 
 * 3  0 1 0 1 0 1 0 1 0 1 0 1 0 1 0 
 * 4  0 1 2 3 4 5 6 7 6 5 4 3 2 1 0 
 * Result output: 
 * 1  4  
 * 2  7  
 * 3  7  
 * 4  7
 * 
 * </pre>
 * 
 * @author Haijun Su Created date: 2013-11-16
 */
public class IslandsDataStream extends BaseAcm {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IslandsDataStream ids = new IslandsDataStream();
		int size = ids.inputInteger();
		Integer[][] data = ids.inputIntegerDatasets(size, 16);
		ids.process(data);
	}

	/**
	 * Logic of the solution
	 * 
	 * @param data
	 */
	private void process(Integer[][] data) {
		Stack<Integer> stack = new Stack<Integer>();
		int[][] result = new int[data.length][2];
		System.out.println("Dataset size: " + data.length);
		System.out.println("Raw data: ");
		for (int i = 0; i < data.length; i++) {
			System.out.print(data[i][0] + "  " + data[i][1] + " ");
			// put the 1st number in stack
			stack.push(data[i][1]);
			result[i][0] = data[i][0];
			result[i][1] = 0;
			for (int j = 2; j < data[i].length; j++) {
				System.out.print(data[i][j] + " ");
				int newValue = data[i][j];
				int lastValue = stack.pop();
				// if current element less than previous element, result add
				// one.
				if (lastValue > newValue)
					result[i][1] += 1;
				// remove all greater values from stack
				while (lastValue > newValue) {
					lastValue = stack.pop();
				}
				stack.push(lastValue);
				stack.push(newValue);
			}
			// clean stack for next loop
			stack.clear();
			// end line
			System.out.println();
		}
		System.out.println("Result output: ");
		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i][0] + "  " + result[i][1] + "  ");
		}
	}

}
