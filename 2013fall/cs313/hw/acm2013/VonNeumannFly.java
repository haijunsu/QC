/*
 * 
 */
package acm2013;

/**
 * B. Von Neumann's Fly
 * 
 * <pre>
 * Please input the data sets size: 5
 * Please input the numbers of line: 1 250 10 15 20
 * Please input the numbers of line: 2 10.7 3.5 4.7 5.5
 * Please input the numbers of line: 3 523.7 15.3 20.7 33.3
 * Please input the numbers of line: 4 1000 30 30 50
 * Please input the numbers of line: 5 500 15 15 25
 * ======= Processing =======
 * Input: 
 * 5
 * 1 250 10 15 20
 * 2 10.7 3.5 4.7 5.5
 * 3 523.7 15.3 20.7 33.3
 * 4 1000 30 30 50
 * 5 500 15 15 25
 * ======= Done =======
 * Result output: 
 * 1 200
 * 2 7.18
 * 3 484.42
 * 4 833.33
 * 5 416.67
 * 
 * </pre>
 * 
 * @author Haijun Su Created date: 2013-11-17
 */
public class VonNeumannFly extends BaseAcm {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		VonNeumannFly vnf = new VonNeumannFly();
		int size = vnf.inputInteger();
		vnf.process(vnf.inputFloatDatasets(size, 5));
	}

	/**
	 * Logic of solution.
	 * 
	 * @param data
	 */
	private void process(Float[][] data) {
		System.out.println("======= Processing =======");
		System.out.println("Input: \n" + data.length);
		float[][] results = new float[data.length][2];
		for (int i = 0; i < data.length; i++) {
			System.out.println(formatNumber(data[i][0]) + " "
					+ formatNumber(data[i][1]) + " " + formatNumber(data[i][2])
					+ " " + formatNumber(data[i][3]) + " "
					+ formatNumber(data[i][4]));
			results[i][0] = data[i][0];
			results[i][1] = fixFloatValue(
					travel(data[i][1], data[i][2], data[i][3], data[i][4]), 2);
		}
		System.out.println("======= Done =======");
		System.out.println("Result output: ");
		for (int i = 0; i < results.length; i++) {
			System.out.println(formatNumber(results[i][0]) + " "
					+ formatNumber(results[i][1]));
		}
	}

	/**
	 * Using recursive to solve the problem
	 * 
	 * @param distance
	 *            distance between A and B
	 * @param speedA
	 *            A's speed
	 * @param speedB
	 *            B's speed
	 * @param speedF
	 *            Fly's speed
	 * @return
	 */
	private float travel(Float distance, Float speedA, Float speedB,
			Float speedF) {
		if (distance <= 0f) {
			return 0f;
		}
		// System.out.println(distance);
		float timeFly = distance / (speedB + speedF); // time of fly meet b
		Float newdistance = distance - timeFly * (speedA + speedB); // remind
		// revert A and B because the fly turns back.
		return timeFly * speedF
				+ travel(fixFloatValue(newdistance, 5), speedB, speedA, speedF);
	}
}
