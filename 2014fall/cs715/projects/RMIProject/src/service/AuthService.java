package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import entity.User;

/**
 * Authentication service
 * 
 * @author Haijun Su Date Dec 5, 2014
 *
 */
public interface AuthService {

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
	 * RMI 2: Handle request to change password.
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
	 * RMI 2: Handle request to change password.
	 * 
	 * @param userName
	 * @param password
	 * @return success is true. fail is false
	 * @throws RemoteException
	 */
	public boolean resetPassword(User user, String userName, String newPass)
			throws RemoteException;

}
