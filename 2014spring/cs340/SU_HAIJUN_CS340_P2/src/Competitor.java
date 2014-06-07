/**
 * 
 * @author Haijun Su
 * 
 */

public class Competitor extends BaseProject implements Runnable {

	private int id;

	public Competitor(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		try {
			msg("start...");
			// msg("Thread priority is " +
			// Thread.currentThread().getPriority());
			FcfsQueue<CompetitionTask> tasks = getTasks(id);
			// need to wait wizard command
			SharedData.startForestTask.acquire();
			CompetitionTask task = tasks.pop();
			while (task != null) {
				CompetitionTask.TaskType type = task.getTaskType();
				int restTime = getRandomNumber(40, 60);
				// stop for rest and food
				msg("Stop for rest and food before task[" + type
						+ "]. Sleep time is " + restTime + "ms");
				Thread.sleep(restTime);
				task.setRestTime(restTime);
				switch (type) {
				case Forest:
					doForestTask((ForestTask) task);
					// Thread.currentThread().setPriority(5);
					// msg("Thread priority is "
					// + Thread.currentThread().getPriority());
					submitTask(task);
					notifyWizard();
					// next task need to wait wizard command
					SharedData.startMountTask.acquire();
					task = tasks.pop();
					break;
				case Mountain:
					doMountainTask((MountainTask) task);
					submitTask(task);
					// notify wizard that the mount task has been done.
					SharedData.oneMountTaskDone.release();
					// wait wizard for river task
					SharedData.wizardReadyForRiverTask.acquire();
					// group
					SharedData.mutexRiver.acquire();
					SharedData.riverCount++;

					if ((SharedData.riverCount % ProjectMainClass.GROUP_SIZE != 0)
							&& (SharedData.riverCount != ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES)) {
						// release mutex
						SharedData.mutexRiver.release();
						// block by startRiverTask
						SharedData.startRiverTask.acquire();
					} else {
						SharedData.mutexRiver.release();
						// release group ready
						SharedData.groupReady.release();
					}
					task = tasks.pop();
					break;
				case River:
					doRiverTask((RiverTask) task);
					submitTask(task);
					notifyWizard();
					// exit loop
					task = null;
					break;

				default:
					msg("Error. Code should not reach here.");
				}

			}
			// wait wizard command for dismiss
			SharedData.dismiss.acquire();

			msg("dismissed");
			msg("end!");
		} catch (InterruptedException e) {
			msg(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 *
	 */
	private void doRiverTask(RiverTask task) {
		// River crossing time
		task.setCrossingTime(getRandomNumber(40, 200));
		task.process();
	}

	/**
	 * @param task
	 * 
	 */
	private void doMountainTask(MountainTask task) {
		task.process();
	}

	/**
	 * @param task
	 * 
	 */
	private void doForestTask(ForestTask task) {
		Compass compass = new Compass(id);
		task.setCompass(compass);
		task.process();
		if (!task.result()) {
			// not finding is penalized
			Thread.yield();
			Thread.yield();
		}
	}

	private void notifyWizard() {
		try {
			SharedData.mutex.acquire();
			SharedData.princeCount--;
			if (SharedData.princeCount == 0) {
				// set princeCount for next use
				SharedData.princeCount = ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES;
				// the last prince notify wizard that all princes are ready for
				// next task.
				SharedData.allPrienceReadyForNextTask.release();
			}

		} catch (InterruptedException e) {
			msg("I was interrupted and I didn't get my task! It should not happens.");
			e.printStackTrace();
		} finally {
			// let other princes to update the princeCount value.
			SharedData.mutex.release();
		}
	}

	@Override
	public String getName() {
		return "Prince-" + this.id;
	}

	public int getId() {
		return this.id;
	}

	/**
	 * Submit task to wizard.
	 * 
	 * @param task
	 * @return
	 */
	public void submitTask(CompetitionTask task) {
		SharedData.submitTask(task);
	}

	/**
	 * CS codes. Need to wait Wizard thread build tasks.
	 * 
	 * @param id
	 * @return
	 */
	public FcfsQueue<CompetitionTask> getTasks(int id) {
		try {
			SharedData.mutex.acquire();
			SharedData.princeCount++;
			// busy wait for task ready, it will cause that all others are
			// blocked by mutex but it is not an issue because all prince must
			// wait taskReady released by the wizard
			SharedData.taskReady.acquire();
			FcfsQueue<CompetitionTask> mytasks = SharedData.allTasks.get(id);
			msg("Get competition tasks successfully.");
			if (SharedData.princeCount == ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES) {
				// all princes get their tasks. the last one notify wizard
				SharedData.allPrienceReadyForNextTask.release();
			}
			return mytasks;

		} catch (InterruptedException e) {
			msg("I was interrupted and I didn't get my task! It should not happens.");
			e.printStackTrace();
			return null;
		} finally {
			// let other princes to get their task.
			SharedData.taskReady.release();
			// let other princes to update the princeCount value.
			SharedData.mutex.release();
		}
	}

	/**
	 * Thanking task answer
	 * 
	 * @param question
	 * @return
	 */
	public int answer(int question) {
		return (question * getRandomNumber(1, 300)) % 10;
	}

}
