package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.Event;
import entity.Schedule;
import entity.User;

/**
 * Authentication service
 * 
 * Date Dec 5, 2014
 * 
 * @author Haijun Su
 * @author Youchen Ren
 *
 */
public interface AuthService extends Remote {

	/**
	 * Login function.
	 * 
	 * RMI 1: Handle authentication requests to verify if user name and password
	 * is correct.
	 * 
	 * @param userName
	 * @param password
	 * @return If failed, return null. Otherwise, return the user object
	 * @throws RemoteException
	 */
	public User login(String userName, String password) throws RemoteException;

	/**
	 * Create regular account
	 * 
	 * RMI 2: Handle account creation request to create and delete new accounts.
	 * (No need to worry about conflict account creation) *All accounts created
	 * will be regular accounts
	 * 
	 * @param userName
	 * @param password
	 * @return success is true. fail is false
	 * @throws RemoteException
	 */
	public boolean createAccount(String userName, String password)
			throws RemoteException;

	/**
	 * Remove regular account from system
	 * 
	 * RMI 2: Handle account creation request to create and delete new accounts.
	 * (No need to worry about conflict account creation) *All accounts created
	 * will be regular accounts
	 * 
	 * @param userName
	 * @param password
	 * @return success is true. fail is false
	 * @throws RemoteException
	 */
	public boolean deleteAccount(String userName) throws RemoteException;

	/**
	 * Change user password. This function can be called by admin and regular
	 * user.
	 * 
	 * RMI 3: Handle request to change password.
	 * 
	 * @param userName
	 * @param password
	 * @return success is true. fail is false
	 * @throws RemoteException
	 */
	public boolean changePassword(String userName, String oldPass,
			String newPass) throws RemoteException;

	/**
	 * Change user password. This function only can be called by admin.
	 * 
	 * RMI 3: Handle request to change password.
	 * 
	 * @param user
	 *            is used to verify user role.
	 * @param userName
	 * @param password
	 * @return success is true. fail is false
	 * @throws RemoteException
	 */
	public boolean resetPassword(User user, String userName, String newPass)
			throws RemoteException;

	/**
	 * RMI 4 Handle request to list all user accounts. Only can be called by
	 * admin.
	 * 
	 * @param user
	 *            is used to verify user role
	 * @return
	 * @throws RemoteException
	 */
	public List<User> listAccounts(User user) throws RemoteException;

	/**
	 * RMI 5 Handle request to display schedule
	 * 
	 * @param user
	 * @return
	 * @throws RemoteException
	 */
	public Schedule getScheduleByUser(User user) throws RemoteException;

	/**
	 * RMI 6 Handle request to add an event to the schedule.
	 * 
	 * @param user
	 * @param event
	 * @return
	 * @throws RemoteException
	 */
	public boolean addEvent(User user, Event event) throws RemoteException;

	/**
	 * RMI 7 Handle request to edit an event from the schedule.
	 * 
	 * @param user
	 * @param event
	 * @return
	 * @throws RemoteException
	 */
	public boolean updateEvent(User user, Event event) throws RemoteException;

	/**
	 * RMI 8 Handle request to delete an event from the schedule.
	 * 
	 * @param user
	 * @param event
	 * @return
	 * @throws RemoteException
	 */
	public boolean removeEvent(User user, Event event) throws RemoteException;

	/**
	 * Get user information.
	 * 
	 * @param username
	 * @return
	 * @throws RemoteException
	 */
	public User getUser(String username) throws RemoteException;

}
