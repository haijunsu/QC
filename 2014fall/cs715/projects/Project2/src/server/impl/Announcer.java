package server.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import common.Logger;

/**
 * Announcer's tasks: 1. holds the written exam. 2. creates the host and gives
 * signal to host to start game.
 * 
 * @author Haijun Su Date Nov 16, 2014
 *
 */
public class Announcer extends Base implements Runnable {

	private static Logger logger = Logger.getLogger(Announcer.class);

	/**
	 * Locks for all groups
	 */
	private List<GroupLock> groupLocks = new ArrayList<GroupLock>();

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
	private int readyGroupsNum = 0;

	/**
	 * Condition value. Ready groups.
	 */
	private List<GroupLock> readyGroups = new ArrayList<GroupLock>();

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
	 * Construct Announcer. Init the group locks base on the room_capacity and
	 * num_contestants
	 * 
	 * @param room_capacity
	 * @param num_contestants
	 */

	public Announcer(int room_capacity, int num_contestants) {
		logger.debug("room_capacity=" + room_capacity + " / num_contestants="
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
		logger.debug("total group locks: " + totalGroupLocks);
		for (int i = 0; i < totalGroupLocks; i++) {
			// create group locks
			GroupLock lock = new GroupLock();
			lock.setId(i);
			groupLocks.add(lock);
		}
		this.contestants = new Contestant[num_contestants];
	}

	/**
	 * Get Contestant by ID
	 * 
	 * @param id
	 * @return
	 */
	private Contestant getContestantById(int id) {
		// since contestants.length is small, linear search is ok
		for (int i = 0; i < contestants.length; i++) {
			if (contestants[i].getId() == id) {
				return contestants[i];
			}
		}
		return null;
	}

	/**
	 * Written exam time
	 * 
	 * @return
	 */
	public int getExamTime() {
		return examTime;
	}

	/**
	 * Contestants objects
	 * 
	 * @param contestants
	 */
	public void setContestants(Contestant[] contestants) {
		this.contestants = contestants;
	}
	/**
	 * add a contestant
	 * @param contestant
	 */
	public void addContestant(Contestant contestant) {
		contestants[contestant.getId()] = contestant;
	}

	@Override
	public String getName() {
		return "Announcer";
	}

	/**
	 * Let contestants to join a group
	 */
	public void joinGroup() {
		GroupLock group = null;
		synchronized (this) {
			group = groupLocks.get(currentGroup);
			++currentGroupSize;
			if (currentGroupSize == this.roomCapacity
					|| (roomCapacity * currentGroup + currentGroupSize == numContestants)) {
				logger.info("Last member in group " + group.getId());
				// current group is ready.
				readyGroups.add(group);
				notify();
				currentGroupSize = 0;
				++currentGroup;
			}
		}
		synchronized (group) {
			if (!group.isNotified()) {
				while (true) {
					try {
						group.wait();
						break;
					} catch (InterruptedException e) {
						logger.warn("waiting to ender classroom is interrupted.");
						e.printStackTrace();
						// if miss signal, it needs to check condition again to
						// determine to exit or continue wait.
						if (group.isNotified()) {
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Notify groups to enter classroom
	 */
	public synchronized void givePermissionEnterClassroom() {
		logger.info("Wait for groups ...");
		while (readyGroupsNum < groupLocks.size()) {
			if (readyGroups.isEmpty()) {
				while (true) {
					try {
						wait();
						break;
					} catch (InterruptedException e) {
						logger.warn("Waiting group is interrupted.");
						e.printStackTrace();
						// if miss signal, it needs to check condition again to
						// determine to exit or continue wait.
						if (!readyGroups.isEmpty()) {
							break;
						}
					}
				}
			}
			logger.debug("Notify group(s)...");
			int readyGroupsSize = readyGroups.size();
			for (int i = 0; i < readyGroupsSize; i++) {
				GroupLock group = readyGroups.remove(0);
				synchronized (group) {
					++readyGroupsNum;
					group.setNotified(true);
					logger.info("Notify group " + group.getId()
							+ " ready to enter classroom.");
					group.notifyAll();
				}
			}

		}
	}

	/**
	 * Contestants enter classroom and take seats. The last one notifies the
	 * announcer that they are ready to start the written exam.
	 */
	public void askForSeat() {
		synchronized (this) {
			seatCounter++;
			if (seatCounter == numContestants) {
				logger.info("All contestants have seats.");
				notify();
			}
		}
		synchronized (seats) {
			if (seatCounter < numContestants) {
				while (true) {
					try {
						seats.wait();
						break;
					} catch (InterruptedException e) {
						logger.warn("waiting for exam is interrupted.");
						e.printStackTrace();
						// if miss signal, it needs to check condition again to
						// determine to exit or continue wait.
						if (seatCounter == numContestants) {
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Announcer announces the exam starts.
	 */
	public synchronized void startExam() {
		logger.info("Wait for having seats of all contestants");
		if (seatCounter < numContestants) {
			while (true) {
				try {
					wait();
					break;
				} catch (InterruptedException e) {
					logger.warn("Waiting for having seats of all contestants is interrupted.");
					e.printStackTrace();
					// if miss signal, it needs to check condition again to
					// determine to exit or continue wait.
					if (seatCounter == numContestants) {
						break;
					}
				}
			}
		}
		logger.info("Start exam...");
		synchronized (seats) {
			seats.notifyAll();
		}
	}

	/**
	 * Contestant submits his/her answer of the written exam.
	 * 
	 * @param id
	 */
	public void submitAnswers(int id) {
		Contestant ctt = getContestantById(id);
		synchronized (this) {
			waitGradeContestants.add(ctt);
			logger.info(ctt.getName() + " has submitted answers.");
			if (waitGradeContestants.size() == numContestants) {
				notify();
			}
		}
		synchronized (ctt) {
			if (ctt.getExamScore() == 0) {
				while (true) {
					try {
						ctt.wait();
						break;
					} catch (InterruptedException e) {
						logger.warn("waiting for grade is interrupted.");
						if (ctt.getExamScore() > 0) {
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Announcer grades the exam and finds the winners. Then he notifies
	 * contestants one by one in FCFS order.
	 */
	public void gradeExam() {
		logger.info("Waiting for submission...");
		synchronized (this) {
			if (waitGradeContestants.size() < numContestants) {
				while (true) {
					try {
						wait();
						break;
					} catch (InterruptedException e) {
						logger.warn("waiting for submission is interrupted");
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
			logger.debug(Arrays.toString(contestants));

		}
		// notify all contestants by submit order
		for (int i = 0; i < waitGradeContestants.size(); i++) {
			Contestant ctt = waitGradeContestants.get(i);
			synchronized (ctt) {
				if (ctt.isWinExam()) {
					logger.info("Congs, " + ctt.getName()
							+ ". You win the exam");
				} else {
					logger.info("Sorry, " + ctt.getName()
							+ ". You lost the exam");
				}
				ctt.notify();
			}
		}

	}

	/**
	 * Winners claim that they are ready for the game
	 * 
	 * @param id
	 */
	public void readyForGame(int id) {
		synchronized (this) {
			++readyForGameCounter;
			logger.debug("readyForGameCounter = " + readyForGameCounter);
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
						logger.debug("contestant " + id
								+ " is waiting for being introduced.");
						ctt.wait();
						break;
					} catch (InterruptedException e) {
						logger.warn("waiting for being introduced is interrupted.");
						// check whether the announcer has introduced him/her.
						if (introducedCounter >= pos) {
							break;
						}
					}
				}
			}
		}
	}
	
	public void prepareHost() {
		Host host = GuessWhatWho.getHost();
		host.setContestants(examWinners);
		for (int i = 0; i < examWinners.length; i++) {
			examWinners[i].setHost(host);
		}
	}
	
	public void introduceWinners() {
		logger.info("Waiting for all winners to start the game.");
		synchronized (this) {
			if (readyForGameCounter < examWinners.length) {
				while (true) {
					try {
						wait();
						break;
					} catch (InterruptedException e) {
						logger.warn("Waiting for winners is interrupted.");
						if (readyForGameCounter == examWinners.length) {
							break;
						}
					}
				}
			}
		}

		// greeting for winners and announce game.
		logger.info("Cong winners. Let's start the game. blablalba....");

		// introduce contestants
		for (int i = 0; i < examWinners.length; i++) {
			synchronized (examWinners[i]) {
				logger.info("I am glade to introduce contestant "
						+ examWinners[i].getId() + " to join the game.");
				++introducedCounter;
				examWinners[i].notify();
			}
		}

	}
	
	public void startGame() {
		Host host = GuessWhatWho.getHost();
		host.startGame();
		logger.info("I am done and exit.");
	}

	/**
	 * Thread run method.
	 */
	@Override
	public void run() {
		logger.debug("Start...");

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

		logger.info("Waiting for all winners to start the game.");
		synchronized (this) {
			if (readyForGameCounter < examWinners.length) {
				while (true) {
					try {
						wait();
						break;
					} catch (InterruptedException e) {
						logger.warn("Waiting for winners is interrupted.");
						if (readyForGameCounter == examWinners.length) {
							break;
						}
					}
				}
			}
		}

		// greeting for winners and announce game.
		logger.info("Cong winners. Let's start the game. blablalba....");

		// introduce contestants
		for (int i = 0; i < examWinners.length; i++) {
			synchronized (examWinners[i]) {
				logger.info("I am glade to introduce contestant "
						+ examWinners[i].getId() + " to join the game.");
				++introducedCounter;
				examWinners[i].notify();
			}
		}
		host.startGame();
		logger.info("I am done and exit.");

	}

}
