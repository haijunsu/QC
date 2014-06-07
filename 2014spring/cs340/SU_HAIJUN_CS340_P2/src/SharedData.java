import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * @author Haijun Su
 * 
 */
public class SharedData {

	/**
	 * A counter which is used by Competitor thread
	 */
	public static int princeCount = 0;

	/**
	 * Binary semaphore. It make sure there is no more than one thread to change
	 * the princeCount value.
	 */
	public static Semaphore mutex = new Semaphore(1);

	/**
	 * Binary semaphore. It make sure there is no more than one thread to change
	 * the riverCount value.
	 */
	public static Semaphore mutexRiver = new Semaphore(1);

	/**
	 * All princes will be block by this binary semaphore when they try to get
	 * their task. The wizard releases it after he prepares all tasks. Prince
	 * releases it after he gets his tasks.
	 */
	public static Semaphore taskReady = new Semaphore(0);

	/**
	 * All prince will be block by this binary semaphore before executing task
	 * until the wizard release it. The last prince will notice wizard to
	 * release it.
	 */
	public static Semaphore allPrienceReadyForNextTask = new Semaphore(0);

	/**
	 * All prince will be block by this binary semaphore before executing Forest
	 * task until the wizard release it. The last prince will notice wizard to
	 * release it.
	 */
	public static Semaphore startForestTask = new Semaphore(0);

	/**
	 * All princes will be block by this binary semaphore before executing Mount
	 * task until the wizard release it.
	 */
	public static Semaphore startMountTask = new Semaphore(0);

	/**
	 * Wizard will be block by this binary semaphore before a prince has done
	 * mount task. After this semaphore is released, wizard releases
	 * startMountTask for another prince.
	 */
	public static Semaphore oneMountTaskDone = new Semaphore(0);

	/**
	 * All princes will be block by this binary semaphore after executing Mount
	 * task Wizard will release it after all princes have done Mount tasks.
	 */
	public static Semaphore wizardReadyForRiverTask = new Semaphore(0);

	/**
	 * All prince will be block by this binary semaphore before executing River
	 * task until the wizard release it. The last prince will notice wizard to
	 * release it.
	 */
	public static Semaphore startRiverTask = new Semaphore(0);

	/**
	 * Wizard will be block by this binary semaphore before a group is ready.
	 * After this semaphore is released, wizard releases startRiverTask for
	 * princes in the group.
	 */
	public static Semaphore groupReady = new Semaphore(0);

	/**
	 * All prince will be block by this binary semaphore before executing
	 * dismiss until the wizard present the report and announce the winner.
	 */
	public static Semaphore dismiss = new Semaphore(0);

	/**
	 * Make sure only one prince can submit his task at same time
	 */
	public static Semaphore sumbitTaskLock = new Semaphore(1);

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
	 * for River task
	 */
	public static int riverCount = 0;

	/**
	 * all submitted tasks
	 * 
	 * @param task
	 */
	public static void submitTask(CompetitionTask task) {
		try {
			sumbitTaskLock.acquire();

			FcfsQueue<CompetitionTask> queue = submittedTasks.get(task
					.getOwnerId());
			if (queue == null) {
				queue = new FcfsQueue<CompetitionTask>();
			}
			queue.add(task);
			submittedTasks.put(task.getOwnerId(), queue);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			sumbitTaskLock.release();
		}
	}

}
