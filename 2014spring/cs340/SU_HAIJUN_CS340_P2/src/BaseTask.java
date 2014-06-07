/**
 *
 */

/**
 * @author Haijun Su
 *
 */
public abstract class BaseTask implements CompetitionTask {

	/**
	 * Total time to finish the task
	 */
	private long taskTime = -1;

	private int id;

	private long rest;

	private boolean result;

	/*
	 * (non-Javadoc)
	 *
	 * @see CompetitionTask#totalTime()
	 */
	@Override
	public long totalTime() {
		return this.taskTime + this.rest;
	}

	protected void msg(String message) {
		ProjectMainClass.msg("Task-" + getTaskType() + "-Prince-" + getOwnerId(),
				message);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see CompetitionTask#process()
	 */
	@Override
	public void process() {
		long beginTime = System.currentTimeMillis();
		try {
			msg("Enter " + getTaskType());
			result = processTask();
			msg("Finish " + getTaskType() + " Task");
		} catch (Exception e) {
			msg(e.getMessage());
			e.printStackTrace();
		} finally {
			this.taskTime = System.currentTimeMillis() - beginTime;
		}
	}

	public abstract boolean processTask();

	/*
	 * (non-Javadoc)
	 *
	 * @see CompetitionTask#setOwnerId(int)
	 */
	public void setOwnerId(int id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see CompetitionTask#getOwnerId()
	 */
	public int getOwnerId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see CompetitionTask#setRestTime(long)
	 */
	public void setRestTime(long rest) {
		this.rest = rest;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see CompetitionTask#taskTime()
	 */
	@Override
	public long taskTime() {
		return this.taskTime;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see CompetitionTask#result()
	 */
	public boolean result() {
		return this.result;
	}

}