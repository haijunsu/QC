/**
 *
 */

/**
 * @author Haijun Su
 *
 */
public class ForestTask extends BaseTask {

	private Compass compass;

	private Forest forest;

	private String magicWord;

	public ForestTask(Forest forest, String magicWord) {
		this.forest = forest;
		this.magicWord = magicWord;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see CompetitionTask#getTaskType()
	 */
	@Override
	public TaskType getTaskType() {
		return TaskType.Forest;
	}

	public void setCompass(Compass compass) {
		this.compass = compass;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see BaseTask#processTask()
	 */
	@Override
	public boolean processTask() {
		compass.setForest(this.forest);
		compass.setMagicword(this.magicWord);
		return compass.find();

	}

}
