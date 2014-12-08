package entity;

import java.io.Serializable;

/**
 * User information
 * 
 * Date Dec 4, 2014
 *
 * @author Haijun Su
 * @author Youchen Ren
 */
public class User implements Serializable, Comparable<User> {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -3363120264501521428L;

	/**
	 * User's role type
	 * 
	 * @author Haijun Su
	 * @author Youchen Ren Date Dec 7, 2014
	 *
	 */
	public static enum UserRole {
		ADMIN, REGULAR
	};

	/**
	 * User name
	 */
	private String username;

	/**
	 * User password
	 */
	private String password;

	/**
	 * User role
	 */
	private UserRole role;

	/**
	 * Default construct
	 */
	public User() {
	}

	/**
	 * Construct with username
	 * 
	 * @param username
	 */
	public User(String username) {
		super();
		this.username = username;
	}

	/**
	 * Construct with username and role
	 * 
	 * @param username
	 * @param role
	 */
	public User(String username, UserRole role) {
		super();
		this.username = username;
		this.role = role;
	}

	/**
	 * Construct with username, password and role
	 * 
	 * @param username
	 * @param password
	 * @param role
	 */
	public User(String username, String password, UserRole role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
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
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", role=" + role + "]";
	}

	@Override
	public int compareTo(User o) {
		return username.compareTo(o.getUsername());
	}

}
