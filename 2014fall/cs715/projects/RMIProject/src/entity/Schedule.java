package entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Schedule
 * @author Haijun Su
 * Date Dec 4, 2014
 *
 */

public class Schedule implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8487275810530147538L;
	
	private String username;
	
	private Set<Event> events = new HashSet<>();
	
	
}
