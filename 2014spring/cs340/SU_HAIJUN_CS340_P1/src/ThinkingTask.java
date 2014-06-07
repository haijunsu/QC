/**
 *
 */

/**
 * @author Haijun Su
 *
 */
public class ThinkingTask extends BaseTask {

	private Competitor prince;

	private int score = 0;

	private int[] questions;
	public ThinkingTask(int[] questions) {
		this.questions = questions;
	}

	public void setPrince(Competitor prince) {
		this.prince = prince;
	}

	/* (non-Javadoc)
	 * @see CompetitionTask#getTaskType()
	 */
	@Override
	public TaskType getTaskType() {
		return TaskType.Thinking;
	}

	/* (non-Javadoc)
	 * @see BaseTask#processTask()
	 */
	@Override
	public boolean processTask() {
		for (int i = 0; i < this.questions.length; i++) {
			int answer = prince.answer(this.questions[i]);
			if (answer >= 4 && answer <= 6) {
				this.score += 2;
			} else if (answer >=7 && answer <=9) {
				this.score += 3;
			}
		}
		return true;
	}

	public int getScore() {
		return this.score;
	}

}
