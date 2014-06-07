import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Haijun Su
 *
 */

public class Wizard extends BaseProject implements Runnable {

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		msg("Wizard builds froest");
		Forest forest = new Forest();
		int magicwordPos = getRandomNumber(0, forest.words.length);
		msg("MagicWord position: " + magicwordPos);
		Map<Integer, FcfsQueue<CompetitionTask>> tasks = new HashMap<Integer, FcfsQueue<CompetitionTask>>();
		SharedData.readyArray = new boolean[ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES];
		// build Forest task
		for (int i = 0; i < ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES; i++) {
			SharedData.readyArray[i] = false;
			FcfsQueue<CompetitionTask> queue = new FcfsQueue<CompetitionTask>();
			// Forest task
			ForestTask forestTask = new ForestTask(forest,
					forest.words[magicwordPos]);
			forestTask.setOwnerId(i);
			queue.add(forestTask);

			// MountainTask
			MountainTask mountainTask = new MountainTask(
					(long) getRandomNumber(10, 1000));
			mountainTask.setOwnerId(i);
			queue.add(mountainTask);

			// RiverTask
			RiverTask riverTask = new RiverTask();
			riverTask.setOwnerId(i);
			queue.add(riverTask);

			// ThinkingTask
			int[] questions = getRandomNumberSequence(100, 300, 3);
			ThinkingTask thinkingTask = new ThinkingTask(questions);
			thinkingTask.setOwnerId(i);
			queue.add(thinkingTask);

			// GoHomeTask
			GoHomeTask goHomeTask = new GoHomeTask();
			goHomeTask.setOwnerId(i);
			queue.add(goHomeTask);

			tasks.put(i, queue);
		}
		SharedData.allTasks = tasks;
		SharedData.tasksReady = true;
		CheckAllPrinceStatus();
		generateReports();

	}

	/**
	 * Print report
	 */
	private void generateReports() {
		msg("Generate report");
		PrinceScore[] scores = new PrinceScore[ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES];
		for (int i = 0; i < scores.length; i++) {
			scores[i] = new PrinceScore();
		}
		calcScore(scores);

		// report1
		msg("=================== REPORT 1 =====================");
		int winner = 0;
		long winnerTime = scores[0].getTurnaroundTime();
		for (int i = 0; i < scores.length; i++) {
			msg("Prince-" + i + "'s turnaround time: "
					+ scores[i].getTurnaroundTime());
			if (winnerTime > scores[i].getTurnaroundTime()) {
				winner = i;
				winnerTime = scores[i].getTurnaroundTime();
			}
		}

		msg("=================== REPORT 2 =====================");
		msg("Prince\t\tForest\tMountain\tRiver\tThinking\tBouns");
		for (int i = 0; i < scores.length; i++) {
			msg("Prince-" + i + "\t" + scores[i].getForest() + "\t"
					+ scores[i].getMountain() + "\t\t" + scores[i].getRiver()
					+ "\t" + scores[i].getThinking() + "\t\t"
					+ scores[i].getBouns());
		}

		msg("================== WINNER ======================");
		msg("\tPrince-" + winner);
		SharedData.reportReady = true;
	}

	private void calcScore(PrinceScore[] scores) {
		CompetitionTask[][] tasks = new CompetitionTask[ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES][4];
		for (int i = 0; i < ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES; i++) {
			FcfsQueue<CompetitionTask> queue = SharedData.submittedTasks.get(i);
			for (int j = 0; j < tasks[i].length; j++) {
				tasks[i][j] = queue.pop();
			}
		}
		for (int i = 0; i < 4; i++) {
			List<CompetitionTask> list = new ArrayList<CompetitionTask>();
			for (int j = 0; j < ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES; j++) {
				list.add(tasks[j][i]);
			}
			Collections.sort(list, new Comparator<CompetitionTask>() {

				@Override
				public int compare(CompetitionTask arg0, CompetitionTask arg1) {
					CompetitionTask.TaskType type = arg0.getTaskType();
					switch (type) {
					case Forest:
					case Mountain:
					case River:
						return arg0.totalTime() < arg1.totalTime() ? -1 : 1;

					case Thinking:
						return ((ThinkingTask) arg0).getScore() < ((ThinkingTask) arg1)
								.getScore() ? 1 : -1;

					default:
						break;
					}
					return 0;
				}

			});
			int index = 0;
			boolean noBouns = false;
			for (CompetitionTask competitionTask : list) {
				CompetitionTask.TaskType type = competitionTask.getTaskType();
				int id = competitionTask.getOwnerId();
				switch (type) {
				case Forest:
					if (competitionTask.result()) {
						scores[id]
								.setForest(ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES
										- index);
						if (!noBouns) { // first
							scores[id].addBouns(1000);
							noBouns = true;
						}
					} else {
						// failed
						scores[id].setForest(0);
					}
					break;
				case Mountain:
					switch (index) {
					case 0:
						scores[id].setMountain(8);
						scores[id].addBouns(1000);
						break;
					case 1:
						scores[id].setMountain(6);
						break;
					case 2:
						scores[id].setMountain(5);
						break;

					default:
						scores[id].setMountain(0);
						break;
					}
					break;
				case River:
					switch (index) {
					case 0:
						scores[id].setRiver(8);
						scores[id].addBouns(1000);
						break;
					case 1:
						scores[id].setRiver(6);
						break;
					case 2:
						scores[id].setRiver(5);
						break;

					default:
						scores[id].setRiver(0);
						break;
					}
					break;
				case Thinking:
					scores[id].setThinking(((ThinkingTask) competitionTask)
							.getScore());
					scores[id].setTurnaroundTime(competitionTask.getOwnerAge());
					if (!noBouns) { // first
						scores[id].addBouns(1000);
						noBouns = true;
					}
					break;
				default:
					break;
				}
				index++;
			}
		}

	}

	public void CheckAllPrinceStatus() {
		while (SharedData.readyPrince < ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES) {
			// busy wait
			Thread.yield();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see BaseProject#getName()
	 */
	@Override
	public String getName() {
		return "Wizard";
	}

}
