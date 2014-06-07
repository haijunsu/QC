import java.util.HashMap;
import java.util.Map;

/**
 *
 */

/**
 * @author Haijun Su
 *
 */
public class SharedData {

	/**
	 * prince threads
	 */
	public static Thread[] princes = null;

	/**
	 * All tasks for each prince
	 */
	public static Map<Integer, FcfsQueue<CompetitionTask>> allTasks = null;

	/**
	 * All tasks for each prince
	 */
	public static Map<Integer, FcfsQueue<CompetitionTask>> submittedTasks = new HashMap<Integer, FcfsQueue<CompetitionTask>>();

	/**
	 * block all princes before wizard creates all tasks.
	 */
	public static boolean tasksReady = false;

	/**
	 * for mountain task
	 */
	public static boolean[] readyArray = null;

	/**
	 * for mountain task
	 */
	public static int countArrivers = 0;

	/**
	 * for River task
	 */
	public static int riverCount = 0;

	/**
	 * for Thinking task
	 */
	public static boolean wizardBusy = false;

	/**
	 * notify wizard which price is ready to calculate score and release wizard
	 */
	public static int readyPrince = 0;

	/**
	 * Before prince go home, he must wait for his report.
	 */
	public static boolean reportReady = false;

	/**
	 * arrive mountain order
	 *
	 * @return
	 */
	public static synchronized int getArriveOrder() {
		return countArrivers++;
	}

	/**
	 * increase arrived river number
	 */
	public static synchronized void arriverRiver() {
		riverCount++;
	}

	/**
	 * all submitted tasks
	 *
	 * @param task
	 */
	public static synchronized void submitTask(CompetitionTask task) {
		FcfsQueue<CompetitionTask> queue = submittedTasks
				.get(task.getOwnerId());
		if (queue == null) {
			queue = new FcfsQueue<CompetitionTask>();
		}
		queue.add(task);
		submittedTasks.put(task.getOwnerId(), queue);
		if (CompetitionTask.TaskType.Thinking == task.getTaskType()) {
			// notify wizard to calculate prince total score
			readyPrince++;
		}
	}

	public synchronized static boolean meetWizard() {
		if (wizardBusy) {
			return false;
		}
		wizardBusy = true;
		return true;

	}

	/**
	 * release wizard
	 */
	public synchronized static void ReleaseWizard() {
		wizardBusy = false;

	}

}
