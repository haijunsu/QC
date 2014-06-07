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
		// build Forest task
		for (int i = 0; i < ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES; i++) {
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

			tasks.put(i, queue);
		}
		SharedData.allTasks = tasks;
//		SharedData.tasksReady = true;
		SharedData.taskReady.release();
		try {
			// wait all princes for executing the forest task
			SharedData.allPrienceReadyForNextTask.acquire();
			msg("================== Start Forest Race ======================");
			for (int i = 0; i < ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES; i++) {
				// release all princes to execute task. 
				SharedData.startForestTask.release();
			}
			// wait all princes for executing the Mountain task
			SharedData.allPrienceReadyForNextTask.acquire();
			msg("================== Start Mount Race ======================");
			for (int i = 0; i < ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES; i++) {
				// release all princes to execute task. 
				SharedData.startMountTask.release();
				// wait for a prince finish
				SharedData.oneMountTaskDone.acquire();
			}
			msg("================== Ready for Forest Race ======================");
			for (int i = 0; i < ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES; i++) {
				// release all princes to execute build groups.
				SharedData.wizardReadyForRiverTask.release();
			}
			
			int releasedRiverTaskCount = 0;
			while (releasedRiverTaskCount < ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES) {
				SharedData.groupReady.acquire();
				for (int i = 0; i < ProjectMainClass.GROUP_SIZE; i++) {
					if (releasedRiverTaskCount < ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES) {
						SharedData.startRiverTask.release();
						releasedRiverTaskCount ++;
					} else {
						break;
					}
				}
				msg("Released startRiverTask #: " + releasedRiverTaskCount);
			}
			SharedData.allPrienceReadyForNextTask.acquire();
			generateReports();
			// dismiss
			msg("================== Dismiss ======================");
			for (int i = 0; i < ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES; i++) {
				SharedData.dismiss.release();
			}
		} catch (InterruptedException e) {
			msg("Unbelivable! I was interrupted. Please press CRTL+C to stop.");
			e.printStackTrace();
		}


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

		msg("=================== REPORT 2 (score/time) =====================");
		msg("Prince\t\tForest\tMountain\tRiver");
		for (int i = 0; i < scores.length; i++) {
			msg("Prince-" + i + "\t" + scores[i].getForest() + "\t"
					+ scores[i].getMountain() + "\t\t" + scores[i].getRiver());
		}

		msg("================== WINNER ======================");
		msg("\tPrince-" + winner);
	}
	
	/**
	 * calculate prince's scores
	 * @param scores
	 */
	private void calcScore(PrinceScore[] scores) {
		CompetitionTask[][] tasks = new CompetitionTask[ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES][4];
		for (int i = 0; i < ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES; i++) {
			FcfsQueue<CompetitionTask> queue = SharedData.submittedTasks.get(i);
			for (int j = 0; j < tasks[i].length; j++) {
				tasks[i][j] = queue.pop();
			}
		}
		for (int i = 0; i < 3; i++) {
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
						return arg0.taskTime() < arg1.taskTime() ? -1 : 1;

					default:
						break;
					}
					return 0;
				}

			});
			int index = 0;
			for (CompetitionTask competitionTask : list) {
				CompetitionTask.TaskType type = competitionTask.getTaskType();
				int id = competitionTask.getOwnerId();
				scores[id].setTurnaroundTime(competitionTask.totalTime() + scores[id].getTurnaroundTime());
				switch (type) {
				case Forest:
					if (competitionTask.result()) {
						scores[id]
								.setForest((ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES
										- index) + "/" + competitionTask.taskTime());
					} else {
						// failed
						scores[id].setForest(0 + "/" + competitionTask.taskTime());
					}
					break;
				case Mountain:
					switch (index) {
					case 0:
						scores[id].setMountain(8 + "/" + competitionTask.taskTime());
						break;
					case 1:
						scores[id].setMountain(6 + "/" + competitionTask.taskTime());
						break;
					case 2:
						scores[id].setMountain(5 + "/" + competitionTask.taskTime());
						break;

					default:
						scores[id].setMountain(0 + "/" + competitionTask.taskTime());
						break;
					}
					break;
				case River:
					switch (index) {
					case 0:
						scores[id].setRiver(8 + "/" + competitionTask.taskTime());
						break;
					case 1:
						scores[id].setRiver(6 + "/" + competitionTask.taskTime());
						break;
					case 2:
						scores[id].setRiver(5 + "/" + competitionTask.taskTime());
						break;

					default:
						scores[id].setRiver(0 + "/" + competitionTask.taskTime());
						break;
					}
					break;
				default:
					break;
				}
				index++;
			}
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
