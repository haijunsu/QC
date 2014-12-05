package entity;

import java.io.Serializable;

/**
 * User information
 * 
 * @author Haijun Su Date Dec 4, 2014
 *
 */
public class User implements Serializable {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -3363120264501521428L;

	public static enum UserRole {
		ADMIN, USER
	};

	private String username;

	private String password;

	private UserRole role;

	public User() {

	}

	public User(String username, UserRole role) {
		super();
		this.username = username;
		this.role = role;
	}

	public User(String username, String password, UserRole role) {
		super();
		this.username = username;
		this.password = password;
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

}
