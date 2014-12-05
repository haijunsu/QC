package entity;

import java.io.Serializable;
import java.util.Date;

import utils.Utilities;

/**
 * Event
 * 
 * @author Haijun Su Date Dec 4, 2014
 *
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
	 * Event description
	 */
	private String description;

	/**
	 * Event date
	 */
	private Date eventDate;

	/**
	 * Event start time
	 */
	private String startTime;

	/**
	 * Event end time
	 */
	private String endTime;

	/**
	 * All day time for the event
	 */
	private boolean allDay;

	/**
	 * Construct an event with time value
	 * 
	 * @param description
	 * @param eventDate
	 * @param statTime
	 * @param endTime
	 */
	public Event(int id, String description, Date eventDate, String statTime,
			String endTime) {
		super();
		this.id = id;
		this.description = description;
		this.eventDate = eventDate;
		this.startTime = statTime;
		this.endTime = endTime;
	}

	/**
	 * Construct an event with all day value
	 * 
	 * @param description
	 * @param eventDate
	 * @param allDay
	 */
	public Event(int id, String description, Date eventDate, boolean allDay) {
		super();
		this.id = id;
		this.description = description;
		this.eventDate = eventDate;
		this.allDay = allDay;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getStatrTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	@Override
	public int compareTo(Event evt) {
		// parameter should not be a null object
		if (evt == null) {
			return -1;
		}
		int result = eventDate.compareTo(evt.eventDate);
		if (result != 0) {
			return result;
		}
		// two dates are equal and compare start time.
		if (!allDay) {
			// all day event, ol
			result = startTime.compareTo(evt.getStatrTime());
		}
		return result;
	}

	@Override
	public String toString() {
		String evtString = "EventId=" + this.id;
		if (allDay) {
			evtString = " eventDate=" + Utilities.formatDate(eventDate)
					+ ", allDay=" + allDay + ", description=" + description;
		} else {
			evtString = "eventDate=" + Utilities.formatDate(eventDate)
					+ ", startTime=" + startTime + ", endTime=" + endTime
					+ ", description=" + description;
		}
		return evtString;
	}

}
