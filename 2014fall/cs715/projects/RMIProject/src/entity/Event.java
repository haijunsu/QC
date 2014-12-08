package entity;

import java.io.Serializable;
import java.util.Date;

import utils.Utilities;

/**
 * Event entity
 * 
 * Date Dec 4, 2014
 *
 * @author Haijun Su
 * @author Youchen Ren
 */
public class Event implements Comparable<Event>, Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5382294636513575754L;

	/**
	 * Event id.
	 */
	private int id;

	/**
	 * Event title
	 */
	private String title;

	/**
	 * Event date
	 */
	private Date eventDate;

	/**
	 * default construct
	 */
	public Event() {
	}

	/**
	 * construct with id
	 * 
	 * @param id
	 */
	public Event(int id) {
		super();
		this.id = id;
	}

	/**
	 * Construct an event with title and time values
	 * 
	 * @param title
	 * @param eventDate
	 */
	public Event(String title, Date eventDate) {
		super();
		this.title = title;
		this.eventDate = eventDate;
	}

	/**
	 * construct with id, title and time values
	 * 
	 * @param id
	 * @param title
	 * @param eventDate
	 */
	public Event(int id, String title, Date eventDate) {
		super();
		this.id = id;
		this.title = title;
		this.eventDate = eventDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	@Override
	public int compareTo(Event evt) {
		// parameter should not be a null object
		if (evt == null) {
			return -1;
		}
		return eventDate.compareTo(evt.eventDate);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Event other = (Event) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", title=" + title + ", eventDate="
				+ Utilities.formatDate(eventDate) + "]";
	}

}
