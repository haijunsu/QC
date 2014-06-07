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
			msg("Thread priority is " + Thread.currentThread().getPriority());
			FcfsQueue<CompetitionTask> tasks = getTasks(id);
			msg("Get competition tasks successfully.");
			CompetitionTask task = tasks.pop();
			while (task != null) {
				// stop for rest and food
				Thread.sleep(getRandomNumber(40, 60));
				CompetitionTask.TaskType type = task.getTaskType();
				switch (type) {
				case Forest:
					doForestTask((ForestTask) task);
					Thread.currentThread().setPriority(5);
					msg("Thread priority is "
							+ Thread.currentThread().getPriority());
					break;
				case Mountain:
					doMountainTask((MountainTask) task);
					break;
				case River:
					doRiverTask((RiverTask) task);
					break;
				case Thinking:
					doThinkingTask((ThinkingTask) task);
					break;
				case GoHome:
					doGoHomeTask((GoHomeTask) task);
					break;
				default:
					msg("Error. Code should not reach here.");
				}
				task.setOwnerAge(age());
				if (CompetitionTask.TaskType.GoHome != task.getTaskType()) {
					submitTask(task);
				}
				task = tasks.pop();
			}
			msg("end!");
		} catch (InterruptedException e) {
			msg(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 *
	 */
	private void doGoHomeTask(GoHomeTask task) {
		while (!SharedData.reportReady) {
			// busy wait
			Thread.yield();
		}
		if (id == (ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES - 1)) {
			task.process();
		} else {
			if (SharedData.princes[id + 1].isAlive()) {
				try {
					SharedData.princes[id + 1].join();
				} catch (InterruptedException e) {
					msg(e.getMessage());
					e.printStackTrace();
				}
			}
			task.process();
		}

	}

	/**
	 *
	 */
	private void doThinkingTask(ThinkingTask task) {
		while (!SharedData.meetWizard()) {
			// busy wait again
			Thread.yield();
		}
		task.setPrince(this);
		task.process();
		SharedData.ReleaseWizard();
	}

	/**
	 *
	 */
	private void doRiverTask(RiverTask task) {
		long arriveTime = System.currentTimeMillis();
		SharedData.arriverRiver();
		if (SharedData.riverCount == (ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES)) { // last
																					// one
			task.setLastOne(true);
			// interrupt other princes
			for (int i = 0; i < ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES; i++) {
				if (i != id) {
					SharedData.princes[i].interrupt();
				}
			}
		}
		task.process();
		// River crossing time
		task.setCrossingTime(arriveTime + getRandomNumber(100000, 100000000));
	}

	/**
	 * @param task
	 *
	 */
	private void doMountainTask(MountainTask task) {
		int order = SharedData.getArriveOrder();
		if (order == (ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES - 1)) { // last one
			// release first prince
			SharedData.readyArray[0] = true;
		}
		while (!SharedData.readyArray[order]) {
			// busy wait
			Thread.yield();
		}
		msg("order is " + order);
		task.process();
		if (order < (ProjectMainClass.INITIAL_NUMBER_OF_PRINCESSES - 1)) {
			// release next prince
			SharedData.readyArray[order + 1] = true;
		}

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
		while (!SharedData.tasksReady) {
			// busywait
			Thread.yield();
		}
		return SharedData.allTasks.get(id);
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
