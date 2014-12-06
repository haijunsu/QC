package service;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Logger;
import entity.Event;
import entity.Schedule;
import entity.User;
import entity.User.UserRole;

/**
 * @author Haijun Su Date Dec 5, 2014
 *
 */
public class AuthServiceImpl implements AuthService {
	
	private static Logger logger = Logger.getLogger(AuthServiceImpl.class);
	
	private static Map<String, User> userMap = new HashMap<String, User>();

	/**
	 * 
	 */
	public AuthServiceImpl() {
		// init admin user
		User admin = new User("admin", "admin", UserRole.ADMIN);
		userMap.put(admin.getUsername().toUpperCase(), admin);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see service.AuthService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public User login(String userName, String password) throws RemoteException {
		logger.debug("username=" + userName);
		User user = userMap.get(userName.toUpperCase());
		if (user != null) {
			logger.debug("Checking password...");
			if (user.getPassword().equals(password)) {
				return user;
			}
		}
		return user;
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
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see service.AuthService#deleteAccount(java.lang.String)
	 */
	@Override
	public boolean deleteAccount(String userName) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see service.AuthService#listAccounts(entity.User)
	 */
	@Override
	public List<User> listAccounts(User user) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see service.AuthService#getScheduleByUser(entity.User)
	 */
	@Override
	public Schedule getScheduleByUser(User user) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see service.AuthService#addEvent(entity.User, entity.Event)
	 */
	@Override
	public boolean addEvent(User user, Event event) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see service.AuthService#updateEvent(entity.User, entity.Event)
	 */
	@Override
	public boolean updateEvent(User user, Event event) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see service.AuthService#removeEvent(entity.User, entity.Event)
	 */
	@Override
	public boolean removeEvent(User user, Event event) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

}
