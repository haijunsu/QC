
/**
 * This is the main class of project. In the main method, it starts threads.
 * 
 * @author Haijun Su
 *
 */
public class ProjectMainClass {

	/**
	 * Program startTime
	 */
	public static final long startTime = System.currentTimeMillis();

	public static final int INITIAL_NUMBER_OF_PRINCESSES = 8;
	
	public static final int GROUP_SIZE = 3;

	public static void main(String[] args) {
		Wizard wizard = new Wizard();
		Thread tWizard = new Thread(wizard);
		tWizard.start();
		Competitor[] competitors = new Competitor[INITIAL_NUMBER_OF_PRINCESSES];
		SharedData.princes = new Thread[INITIAL_NUMBER_OF_PRINCESSES];
		// There is an exception when priority is 0
		//int[] priorities = wizard.getRandomNumberSequence(1, 4, INITIAL_NUMBER_OF_PRINCESSES);
		// init princes
		for (int i = 0; i < SharedData.princes.length; i++) {

			competitors[i] = new Competitor(i);
			SharedData.princes[i] = new Thread(competitors[i]);
			SharedData.princes[i].start();
			//SharedData.princes[i].setPriority(priorities[i]);
		}
	}

	public static void msg(String name, String message) {

		System.out.print(name + " ["
				+ (System.currentTimeMillis() - ProjectMainClass.startTime) + "] "
				+ ": ");
		System.out.println(message);

	}

}
