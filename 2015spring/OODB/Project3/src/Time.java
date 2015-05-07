/**
 * 
 * @author suhaijun
 *
 */
@javax.jdo.annotations.PersistenceCapable
public class Time implements Comparable<Time> {
	/**
	 * minute
	 */
	int minute;

	/**
	 * hour
	 */
	int hour;

	/**
	 * Implements the inverse of Time: (arrivalTime) Schedule --> Time 0..1:*
	 */
	Schedule arrivalTimeSchedule;

	/**
	 * Implements the inverse of Time: (departureTime) Schedule --> Time 0..1:*
	 */
	Schedule departureTimeSchedule;

	/**
	 * Default constructor
	 */
	public Time() {
		
	}
	
	/**
	 * Constructor with hour and time
	 * @param hour
	 * @param minute
	 */
	public Time(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
	}
	@Override
	public int compareTo(Time o) {
		if (o == null)
			return 1;
		if (this.hour > o.hour)
			return 1;
		else if (this.hour < o.hour)
			return -1;
		else if (this.minute > o.minute)
			return 1;
		else if (this.minute < o.minute)
			return -1;
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Time [minute=" + minute + ", hour=" + hour
				+ ", arrivalTimeSchedule=" + arrivalTimeSchedule
				+ ", departureTimeSchedule=" + departureTimeSchedule + "]";
	}

	/**
	 * @return the minute
	 */
	public int getMinute() {
		return minute;
	}

	/**
	 * @param minute the minute to set
	 */
	public void setMinute(int minute) {
		this.minute = minute;
	}

	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * @param hour the hour to set
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}

	/**
	 * @return the arrivalTimeSchedule
	 */
	public Schedule getArrivalTimeSchedule() {
		return arrivalTimeSchedule;
	}

	/**
	 * @param arrivalTimeSchedule the arrivalTimeSchedule to set
	 */
	public void setArrivalTimeSchedule(Schedule arrivalTimeSchedule) {
		this.arrivalTimeSchedule = arrivalTimeSchedule;
	}

	/**
	 * @return the departureTimeSchedule
	 */
	public Schedule getDepartureTimeSchedule() {
		return departureTimeSchedule;
	}

	/**
	 * @param departureTimeSchedule the departureTimeSchedule to set
	 */
	public void setDepartureTimeSchedule(Schedule departureTimeSchedule) {
		this.departureTimeSchedule = departureTimeSchedule;
	}



}
