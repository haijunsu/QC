package service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import utils.Logger;
import entity.Event;
import entity.Schedule;
import entity.User;
import entity.User.UserRole;

/**
 * Authentication service implementation Date
 * 
 * 
 * Dec 5, 2014
 * 
 * @author Haijun Su
 * @author Youchen Ren
 */
public class AuthServiceImpl implements AuthService {

	/**
	 * logger adapter
	 */
	private static Logger logger = Logger.getLogger(AuthServiceImpl.class);

	/**
	 * Id generator
	 */
	private static final AtomicInteger idGen = new AtomicInteger(0);

	/**
	 * User map
	 */
	private static Map<String, User> userMap = new HashMap<String, User>();

	/**
	 * User schedule map
	 */
	private static Map<String, Schedule> userSchedule = new HashMap<String, Schedule>();

	/**
	 * Construct authentication service
	 */
	public AuthServiceImpl() {
		// initial admin user
		User admin = new User("admin", "admin", UserRole.ADMIN);
		userMap.put(admin.getUsername(), admin);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see service.AuthService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public User login(String userName, String password) throws RemoteException {
		logger.debug("login() - username=" + userName);
		User user = userMap.get(userName);
		if (user != null) {
			logger.debug("login() - Checking password...");
			if (user.getPassword().equals(password)) {
				logger.debug("login() - " + userName + " logged on.");
				return user;
			}
		}
		logger.debug("login() - " + userName + " login fail.");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see service.AuthService#createAccount(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean createAccount(String userName, String password)
			throws RemoteException {
		logger.debug("createAccount() - username=" + userName);
		if (userName == null || userName.trim().equals("")) {
			logger.error("createAccount() - Cannot create user without username.");
			return false;
		}
		if (userMap.get(userName) != null) {
			logger.error("createAccount() - User already exists.");
			return false;
		}
		User user = new User(userName, password, UserRole.USER);
		userMap.put(user.getUsername(), user);
		logger.debug("createAccount() - Create user success. username="
				+ userName);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see service.AuthService#deleteAccount(java.lang.String)
	 */
	@Override
	public boolean deleteAccount(String userName) throws RemoteException {
		logger.debug("deleteAccount() - username=" + userName);
		if (userName == null) {
			logger.error("deleteAccount() - Can't delete account because username is null.");
			return false;
		}
		User user = userMap.get(userName);
		if (user == null) {
			logger.error("deleteAccount() - Can't delete account because user doesn't exist.");
			return false;
		}
		userMap.remove(user.getUsername());
		logger.debug("deleteAccount() - User has been removed. username = "
				+ userName);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see service.AuthService#changePassword(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean changePassword(String userName, String oldPass,
			String newPass) throws RemoteException {
		logger.debug("changePassword() - username=" + userName);
		if (userName == null) {
			logger.error("changePassword() - Can't change password because username is null.");
			return false;
		}
		User user = userMap.get(userName);
		if (user == null) {
			logger.error("changePassword() - Can't change password because user doesn't exist.");
			return false;
		}
		if (user.getPassword().equals(oldPass)) {
			logger.debug("changePassword() - User password has be changed.");
			user.setPassword(newPass);
			return true;
		} else {
			logger.error("changePassword() - Can't change password because user password doesn't match.");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see service.AuthService#resetPassword(entity.User, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean resetPassword(User user, String userName, String newPass)
			throws RemoteException {
		logger.debug("resetPassword() - username=" + userName);
		if (user == null || user.getRole() != UserRole.ADMIN) {
			logger.error("resetPassword() - Can't reset password because it needs admin role.");
			return false;
		}
		if (userName == null) {
			logger.error("resetPassword() - Can't reset password because username is null.");
			return false;
		}
		User profile = userMap.get(userName);
		if (profile == null) {
			logger.error("resetPassword() - Can't reset password because user doesn't exist.");
			return false;
		}
		logger.debug("resetPassword() - User password has be resetted.");
		profile.setPassword(newPass);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see service.AuthService#listAccounts(entity.User)
	 */
	@Override
	public List<User> listAccounts(User user) throws RemoteException {
		logger.debug("listAccounts() - user=" + user);
		if (user == null || user.getRole() != UserRole.ADMIN) {
			logger.error("listAccounts() - Can't list accounts because it needs admin role.");
			return null;
		}
		List<User> accounts = new ArrayList<User>(userMap.values());
		Collections.sort(accounts);
		return accounts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see service.AuthService#getScheduleByUser(entity.User)
	 */
	@Override
	public Schedule getScheduleByUser(User user) throws RemoteException {
		logger.debug("getScheduleByUser() - user=" + user);
		if (user == null) {
			logger.error("getScheduleByUser() - Can't get user's schedule because user is null.");
			return null;
		}
		return userSchedule.get(user.getUsername());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see service.AuthService#addEvent(entity.User, entity.Event)
	 */
	@Override
	public boolean addEvent(User user, Event event) throws RemoteException {
		logger.debug("addEvent() - user=" + user);
		if (user == null) {
			logger.error("addEvent() - Can't add user's event because user is null.");
			return false;
		}
		User tu = userMap.get(user.getUsername());
		if (tu == null) {
			logger.error("addEvent() - Can't add user's event because user doesn't exist.");
			return false;
		}
		Schedule schedule = userSchedule.get(user.getUsername());
		if (schedule == null) {
			schedule = new Schedule(user.getUsername());
			userSchedule.put(user.getUsername(), schedule);
		}
		event.setId(idGen.incrementAndGet());
		logger.debug("addEvent() - event=" + event);
		schedule.addEvent(event);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see service.AuthService#updateEvent(entity.User, entity.Event)
	 */
	@Override
	public boolean updateEvent(User user, Event event) throws RemoteException {
		logger.debug("updateEvent() - user=" + user);
		if (user == null) {
			logger.error("updateEvent() - Can't update user's event because user is null.");
			return false;
		}
		Schedule schedule = userSchedule.get(user.getUsername());
		if (schedule == null) {
			logger.error("updateEvent() - Can't update user's event because user's schedule is null.");
			return false;
		}
		schedule.updateEvent(event);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see service.AuthService#removeEvent(entity.User, entity.Event)
	 */
	@Override
	public boolean removeEvent(User user, Event event) throws RemoteException {
		logger.debug("removeEvent() - user=" + user);
		if (user == null) {
			logger.error("removeEvent() - Can't remove user's event because user is null.");
			return false;
		}
		Schedule schedule = userSchedule.get(user.getUsername());
		if (schedule == null) {
			logger.error("updateEvent() - Can't update user's event because user's schedule is null.");
			return false;
		}
		schedule.removeEvent(event);
		return true;
	}

	@Override
	public User getUser(String username) throws RemoteException {
		return userMap.get(username);
	}

}
