/**
 *
 */

/**
 * @author Haijun Su
 *
 */
public class RiverTask extends BaseTask {

	private long crossingTime;

	/* (non-Javadoc)
	 * @see CompetitionTask#getTaskType()
	 */
	@Override
	public TaskType getTaskType() {
		return TaskType.River;
	}

	/* (non-Javadoc)
	 * @see BaseTask#processTask()
	 */
	@Override
	public boolean processTask() {
		try {
			Thread.sleep(crossingTime);
		} catch (InterruptedException e) {
			msg("Interrupted should not happen!!!");
		}
		return true;
	}

	/**
	 * set crossing time
	 * @param crossingTime
	 */
	public void setCrossingTime(long crossingTime) {
		this.crossingTime = crossingTime;
	}

}
