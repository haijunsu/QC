/*
 * 
 */

/**
 * 
 * @author Haijun Su Created date: 2013-11-14
 */
public class DiscreteMath {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(modularExponentiation(11, new int[] { 1, 0, 1, 0, 0,
				0, 0, 1, 0, 0 }, 645));
		System.out.println(gcd(123552, 92928));
		System.out.println(gcd(252, 198));
	}

	static int modularExponentiation(int a, int[] exponentiations, int divisor) {
		int x = 1;
		int power = a % divisor;
		System.out.println("power=" + power);
		for (int i = exponentiations.length - 1; i >= 0; i--) {
			if (exponentiations[i] == 1)
				x = (x * power) % divisor;
			System.out.println("a[" + i +"]=" + exponentiations[i] + ", x=" + x + ", power=" + power);
			power = (power * power) % divisor;
		}
		return x;
	}
	static int gcd(int a, int b) {
		int x = a;
		int y = b;
		while (y!=0) {
			int r = x % y;
			x = y;
			y = r;
		}
		return x;
	}
}
