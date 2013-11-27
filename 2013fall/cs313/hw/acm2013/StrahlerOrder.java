/*
 * 
 */
package acm2013;

import java.util.ArrayList;
import java.util.List;

/**
 * C. Strahler Order
 * 
 * <pre>
 * Please input the data sets size: 2
 * Please give the set properties in one line.
 * Please input the numbers of line: 1 7 8
 * ========= Data set #1, 7 is the mouth of the river, 8 edges  ==========
 * Please input the numbers of line: 1 3
 * Please input the numbers of line: 2 3
 * Please input the numbers of line: 6 4
 * Please input the numbers of line: 3 4
 * Please input the numbers of line: 3 5
 * Please input the numbers of line: 6 7
 * Please input the numbers of line: 5 7
 * Please input the numbers of line: 4 7
 * ========== input finish for set #1 ==========
 * Please give the set properties in one line.
 * Please input the numbers of line: 2 3 4
 * ========= Data set #2, 3 is the mouth of the river, 4 edges  ==========
 * Please input the numbers of line: 1 3
 * Please input the numbers of line: 2 1
 * Please input the numbers of line: 4 1
 * Please input the numbers of line: 4 3
 * ========== input finish for set #2 ==========
 * =========== Processing data set #1 =========
 * 1 7 8
 * 1 3
 * 2 3
 * 6 4
 * 3 4
 * 3 5
 * 6 7
 * 5 7
 * 4 7
 * =========== Processing data set #2 =========
 * 2 3 4
 * 1 3
 * 2 1
 * 4 1
 * 4 3
 * ========= Done =========
 * Result output: 
 * 1 3
 * 2 2
 * 
 * </pre>
 * 
 * @author Haijun Su Created date: 2013-11-16
 */
public class StrahlerOrder extends BaseAcm {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StrahlerOrder so = new StrahlerOrder();
		// get data size
		int size = so.inputInteger();
		// store each set settings
		List<Integer[]> data = new ArrayList<Integer[]>();
		for (int i = 0; i < size; i++) {
			System.out.println("Please give the set properties in one line.");
			Integer[] numbers = so.inputIntegersFromOneLine(3);
			data.add(numbers);
			System.out.println("========= Data set #" + numbers[0] + ", "
					+ numbers[1] + " is the mouth of the river, " + numbers[2]
					+ " edges " + " ==========");
			for (int j = 0; j < numbers[2]; j++) {
				Integer[] edge = so.inputIntegersFromOneLine(2);
				data.add(edge);
			}
			System.out.println("========== input finish for set #" + numbers[0]
					+ " ==========");
		}
		so.process(data);

	}

	/**
	 * Logic of the solution
	 * 
	 * @param data
	 */
	private void process(List<Integer[]> data) {
		List<Integer[]> results = new ArrayList<Integer[]>();
		int order = 0;
		int setNumber = 0;
		int mouthNode = 0;
		int edges = 0;
		int index = 0;
		for (Integer[] row : data) {
			if (row.length == 3) { // set properties
				// print row data
				System.out.println("=========== Processing data set #" + row[0]
						+ " =========");
				System.out.println(row[0] + " " + row[1] + " " + row[2]);
				// get set number
				setNumber = row[0];
				// get mouth node
				mouthNode = row[1];
				// get edges
				edges = row[2];
				// init order value
				order = 0;
				// init index
				index = 0;
			} else {
				System.out.println(row[0] + " " + row[1]);
				if (row[1] == mouthNode) { // find an order
					order++;
				}
				if (index == edges) {
					Integer[] riverOrder = new Integer[] { setNumber, order };
					results.add(riverOrder);
				}
			}
			index++;
		}
		// print results
		System.out.println("========= Done =========");
		System.out.println("Result output: ");
		for (Integer[] riverOrder : results) {
			System.out.println(riverOrder[0] + " " + riverOrder[1]);
		}
	}

}
