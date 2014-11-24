/**
 * Used for build group
 * 
 * @author Haijun Su Date Nov 13, 2014
 *
 */
public class GroupLock {
	private int id;
	private boolean isNotified;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isNotified() {
		return isNotified;
	}

	public void setNotified(boolean isNotified) {
		this.isNotified = isNotified;
	}

}
