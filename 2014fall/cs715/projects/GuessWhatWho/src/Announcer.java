import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author Haijun Su Date Nov 16, 2014
 *
 */
public class Announcer extends Base implements Runnable {

	/**
	 * Locks for all groups
	 */
	private List<Object> groupLocks = new ArrayList<Object>();

	/**
	 * Queue that Contestants are waiting for grade.
	 */
	private List<Contestant> waitGradeContestants = new ArrayList<Contestant>();

	/**
	 * Gruop size is the room capacity.
	 */
	private int roomCapacity;

	/**
	 * Total number of contestants.
	 */
	private int numContestants;

	/**
	 * Time for the exam.
	 */
	private int examTime;

	/**
	 * Contestants
	 */
	private Contestant[] contestants;

	/**
	 * Winners of exam
	 */
	private Contestant[] examWinners = new Contestant[4];

	/**
	 * Condition value for form group. Identity how many members are in current
	 * group
	 */
	private int currentGroupSize = 0;

	/**
	 * Condition value for form group. Identity which group is current.
	 */
	private int currentGroup = 0;

	/**
	 * Condition value. Identity how many contestants have joined group.
	 */
	private int readyGroups = 0;

	/**
	 * Condition value. Identity how many contestants have seats.
	 */
	private int seatCounter = 0;

	/**
	 * Condition value. Identity how many contestants have been introduced to
	 * Host.
	 */
	private int readyForGameCounter = 0;

	/**
	 * Condition value. Identity how many contestants have been introduced to
	 * Host.
	 */
	private int introducedCounter = -1;

	/**
	 * Condition value. Taking seat lock.
	 */
	private Object seats = new Object();

	/**
	 * 
	 * @param room_capacity
	 * @param num_contestants
	 */

	public Announcer(int room_capacity, int num_contestants) {
		debug("room_capacity=" + room_capacity + " / num_contestants="
				+ num_contestants);
		this.roomCapacity = room_capacity;
		this.numContestants = num_contestants;
		// setting time for exam
		this.examTime = num_contestants * 10;
		int totalGroupLocks = num_contestants / room_capacity;
		if (num_contestants % room_capacity != 0) {
			++totalGroupLocks; // the last group number is less then
								// room_capacity
		}
		debug("total group locks: " + totalGroupLocks);
		for (int i = 0; i < totalGroupLocks; i++) {
			// create group locks
			groupLocks.add(new Object());
		}
	}

	private Contestant getContestantById(int id) {
		// since contestants.length is small, linear search is ok
		for (int i = 0; i < contestants.length; i++) {
			if (contestants[i].getId() == id) {
				return contestants[i];
			}
		}
		return null;
	}
	
	public int getExamTime() {
		return examTime;
	}

	public void setContestants(Contestant[] contestants) {
		this.contestants = contestants;
	}

	@Override
	public String getName() {
		return "Announcer";
	}

	public void joinGroup() {
		Object group = groupLocks.get(currentGroup);
		synchronized (group) {
			++currentGroupSize;
			if (currentGroupSize == this.roomCapacity
					|| (roomCapacity * currentGroup + currentGroupSize == numContestants)) {
				// current group is ready.
				askEnterClassroom();
				currentGroupSize = 0;
				++currentGroup;
			}
			while (true) {
				try {
					group.wait();
					break;
				} catch (InterruptedException e) {
					warn("waiting to ender classroom is interrupted.");
					e.printStackTrace();
					// if miss signal, it needs to check condition again to
					// determine to exit or continue wait.
					if (readyGroups == groupLocks.size()) {
						break;
					}
				}
			}
		}
	}

	public synchronized void askEnterClassroom() {
		++readyGroups;
		info("Group " + readyGroups
				+ " is ready. Waiting for notify from Announcer.");
		if (readyGroups == groupLocks.size()) {
			notify();
		}
	}

	public synchronized void givePermissionEnterClassroom() {
		info("Wait for groups ...");
		if (readyGroups < groupLocks.size()) {
			while (true) {
				try {
					wait();
					break;
				} catch (InterruptedException e) {
					warn("Waiting group is interrupted.");
					e.printStackTrace();
					// if miss signal, it needs to check condition again to
					// determine to exit or continue wait.
					if (readyGroups == groupLocks.size()) {
						break;
					}
				}
			}
		}
		info("Notify groups...");
		for (int i = 0; i < groupLocks.size(); i++) {
			Object group = groupLocks.get(i);
			synchronized (group) {
				group.notifyAll();
			}
		}
	}

	public void askForSeat() {
		synchronized (seats) {
			seatCounter++;
			if (seatCounter < numContestants) {
				while (true) {
					try {
						seats.wait();
						break;
					} catch (InterruptedException e) {
						warn("waiting for exam is interrupted.");
						e.printStackTrace();
						// if miss signal, it needs to check condition again to
						// determine to exit or continue wait.
						if (seatCounter == numContestants) {
							break;
						}
					}
				}
			} else {
				debug("All contestants have seats.");
				synchronized (this) {
					notify();
				}
			}
		}
	}

	public synchronized void startExam() {
		info("Wait for having seats of all contestants");
		if (seatCounter < numContestants) {
			while (true) {
				try {
					wait();
					break;
				} catch (InterruptedException e) {
					warn("Waiting for having seats of all contestants is interrupted.");
					e.printStackTrace();
					// if miss signal, it needs to check condition again to
					// determine to exit or continue wait.
					if (seatCounter == numContestants) {
						break;
					}
				}
			}
		}
		info("Start exam...");
		synchronized (seats) {
			seats.notifyAll();
		}
	}

	public void submitAnswers(int id) {
		Contestant ctt = getContestantById(id);
		synchronized (this) {
			waitGradeContestants.add(ctt);
			info(ctt.getName() + " has submitted answers.");
			if (waitGradeContestants.size() == numContestants) {
				notify();
			}
		}
		synchronized (ctt) {
			while (true) {
				try {
					ctt.wait();
					break;
				} catch (InterruptedException e) {
					warn("waiting for grade is interrupted.");
					if (ctt.getExamScore() > 0) {
						break;
					}
				}
			}
		}
	}

	public void gradeExam() {
		info("Waiting for submission...");
		synchronized (this) {
			if (waitGradeContestants.size() < numContestants) {
				while (true) {
					try {
						wait();
						break;
					} catch (InterruptedException e) {
						warn("waiting for submission is interrupted");
						e.printStackTrace();
						if (waitGradeContestants.size() == numContestants) {
							break;
						}
					}
				}
			}
			// generate grades
			int[] grades = getRandomNumberSequence(10, 10 + numContestants,
					numContestants);
			// assign grade
			for (int i = 0; i < grades.length; i++) {
				contestants[i].setExamScore(grades[i]);
			}
			// find winners
			Arrays.sort(contestants, new Comparator<Contestant>() {

				@Override
				public int compare(Contestant o1, Contestant o2) {
					if (o1.getExamScore() == o2.getExamScore())
						return 0;
					return o1.getExamScore() > o2.getExamScore() ? -1 : 1;
				}
			});
			for (int i = 0; i < examWinners.length; i++) {
				contestants[i].setWinExam(true);
				examWinners[i] = contestants[i];
			}
			debug(Arrays.toString(contestants));

		}
		// notify all contestants by submit order
		for (int i = 0; i < waitGradeContestants.size(); i++) {
			Contestant ctt = waitGradeContestants.get(i);
			synchronized (ctt) {
				if (ctt.isWinExam()) {
					info("Congs, " + ctt.getName() + ". You win the exam");
				} else {
					info("Sorry, " + ctt.getName() + ". You lost the exam");
				}
				ctt.notify();
			}
		}

	}

	public void readyForGame(int id) {
		synchronized (this) {
			++readyForGameCounter;
			debug("readyForGameCounter = " + readyForGameCounter);
			if (readyForGameCounter == examWinners.length) {
				notify();
			}
		}
		Contestant ctt = null;
		int pos = -1;
		for (int i = 0; i < examWinners.length; i++) {
			if (id == examWinners[i].getId()) {
				ctt = examWinners[i];
				pos = i;
				break;
			}
		}
		synchronized (ctt) {
			if (introducedCounter < pos) {
				while (true) {
					try {
						debug("contestant " + id
								+ " is waiting for being introduced.");
						ctt.wait();
						break;
					} catch (InterruptedException e) {
						warn("waiting for being introduced is interrupted.");
						if (introducedCounter >= pos) {
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public void run() {
		debug("Start...");

		givePermissionEnterClassroom();

		startExam();

		gradeExam();

		// create host
		Host host = new Host();
		host.setContestants(examWinners);
		for (int i = 0; i < examWinners.length; i++) {
			examWinners[i].setHost(host);
		}
		Thread hostT = new Thread(host, host.getName());
		hostT.start();

		info("Waiting for all winners to start the game.");
		synchronized (this) {
			if (readyForGameCounter < examWinners.length) {
				while (true) {
					try {
						wait();
						break;
					} catch (InterruptedException e) {
						warn("Waiting for winners is interrupted.");
						if (readyForGameCounter == examWinners.length) {
							break;
						}
					}
				}
			}
		}

		// greeting for winners and announce game.
		info("Cong winners. Let's start the game. blablalba....");

		// introduce contestants
		for (int i = 0; i < examWinners.length; i++) {
			synchronized (examWinners[i]) {
				info("I am glade to introduce contestant "
						+ examWinners[i].getId() + " to join the game.");
				++introducedCounter;
				examWinners[i].notify();
			}
		}
		host.startGame();
		info("I am done and exit.");

	}

}
