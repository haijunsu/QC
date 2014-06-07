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
	private long totalTime = -1;

	private int id;

	private long ownerAge;

	private boolean result;

	/*
	 * (non-Javadoc)
	 *
	 * @see CompetitionTask#totalTime()
	 */
	@Override
	public long totalTime() {
		return this.totalTime;
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
			this.totalTime = System.currentTimeMillis() - beginTime;
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
	 * @see CompetitionTask#setOwnerAge(long)
	 */
	public void setOwnerAge(long age) {
		this.ownerAge = age;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see CompetitionTask#getOwnerAge()
	 */
	@Override
	public long getOwnerAge() {
		return this.ownerAge;
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