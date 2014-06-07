/**
 *
 */

/**
 * @author Haijun Su
 *
 */
public class MountainTask extends BaseTask {

	private long sleepValue = 0l;

	public MountainTask(long sleepValue) {
		this.sleepValue = sleepValue;
	}

	/* (non-Javadoc)
	 * @see CompetitionTask#getTaskType()
	 */
	@Override
	public TaskType getTaskType() {
		return TaskType.Mountain;
	}

	/* (non-Javadoc)
	 * @see BaseTask#processTask()
	 */
	@Override
	public boolean processTask() {
		try {
			Thread.sleep(sleepValue);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}


}
