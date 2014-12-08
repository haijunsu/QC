package entity;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * User's schedule
 * 
 * Date Dec 4, 2014
 *
 * @author Haijun Su
 * @author Youchen Ren
 */

public class Schedule implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8487275810530147538L;

	/**
	 * User name
	 */
	private String username;

	/**
	 * User's schedules
	 */
	private Set<Event> events = new TreeSet<Event>();

	/**
	 * Construct a schedule for user
	 * 
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

	/**
	 * add an event
	 * 
	 * @param event
	 * @return
	 */
	public boolean addEvent(Event event) {
		return this.events.add(event);
	}

	/**
	 * remove an event
	 * 
	 * @param event
	 * @return
	 */
	public boolean removeEvent(Event event) {
		return this.events.remove(event);
	}

	/**
	 * Update an event
	 * 
	 * @param event
	 * @return
	 */
	public boolean updateEvent(Event event) {
		Event oldEvent = null;
		for (Event evt : events) {
			if (evt.getId() == event.getId()) {
				oldEvent = evt;
				break;
			}
		}
		if (oldEvent == null) {
			return false; // there is no event which can be updated.
		}
		this.events.remove(oldEvent);
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
