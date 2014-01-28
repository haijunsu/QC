/*
 * 
 */
package ch5;

import java.util.Arrays;

/**
 * 
 * @author Haijun Su Created date: 2013-10-25
 */
public class FindExercise {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] a = new int[] { 10, 3, 5, 11, 4, 7, 6, 15, 4, 13, 5, 6 };
		FindExercise f = new FindExercise();
		System.out.println(Arrays.toString(a));
		System.out.println(Arrays.toString(f.find(a)));

	}

	public int[] find(int[] a) {
		int[] b = new int[a.length];
		b[0] = 0;
		for (int i = 0; i < a.length; i++) {
			int j = i - 1;
			boolean isFound = false;
			while (!isFound && j >= 0) {
				j = b[j];
				System.out.println("a[" + i + "] Compare with a[" + j + "]");
				if (a[j] >= a[i]) {
					b[i] = j; 
					isFound = true;
				}
				j--;
			}
			if (!isFound) b[i] = i;
		}
		return b;
	}

}
