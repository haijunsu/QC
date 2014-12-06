package entity;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * Schedule
 * 
 * @author Haijun Su Date Dec 4, 2014
 *
 */

public class Schedule implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8487275810530147538L;

	private String username;

	private Set<Event> events = new TreeSet<Event>();

	/**
	 * Construct a schedule for user
	 * @param username
	 */
	public Schedule(String username) {
		super();
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	public boolean addEvent(Event event) {
		return this.events.add(event);
	}

	public boolean removeEvent(Event event) {
		return this.events.remove(event);
	}

	public boolean updateEvent(Event event) {
		if (!events.contains(event)) {
			return false; // there is no event which can be updated.
		}
		return this.events.add(event);
	}
	
	@Override
	public String toString() {
		return "Schedule [username=" + username + ", events=" + events + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	

}
