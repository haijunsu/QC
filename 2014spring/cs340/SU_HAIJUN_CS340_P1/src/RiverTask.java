/**
 *
 */

/**
 * @author Haijun Su
 *
 */
public class RiverTask extends BaseTask {

	private long crossingTime;

	private boolean lastone = false;
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
		if (lastone) {
			msg("I am the last one. I wakeuped all other princes ^_^");
			return true;
		}
		try {
			Thread.sleep(1000000000l);
		} catch (InterruptedException e) {
			msg("Interrupted by other prince");
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
	/*
	 * (non-Javadoc)
	 * @see BaseTask#totalTime()
	 */
	public long totalTime() {
		return crossingTime;

	}

	/**
	 * @param b
	 */
	public void setLastOne(boolean lastone) {
		this.lastone = lastone;

	}
}
