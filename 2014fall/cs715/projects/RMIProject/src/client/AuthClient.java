package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import service.AuthService;
import utils.Configuration;
import utils.Logger;
import utils.Utilities;
import entity.Event;
import entity.Schedule;
import entity.User;
import entity.User.UserRole;

/**
 * This is the client of RMI project. If it runs on different server,
 * server.name should be updated before running in resources/auth.properties.
 * 
 * Date Dec 7, 2014
 *
 * @author Haijun Su
 * @author Youchen Ren
 * 
 */
public class AuthClient {

	/**
	 * Logger adapter.
	 */
	private static Logger logger = Logger.getLogger(AuthClient.class);

	/**
	 * User information after logged on.
	 */
	private static User profile = null;

	/**
	 * User interactive console
	 */
	private static Scanner console;

	/**
	 * Remote authentication service
	 */
	private static AuthService service;

	/**
	 * Application main function
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			System.out.println(Configuration.appInfo());
			// initial environment
			Configuration.init(true);
			logger.debug("lookup registry...");
			Registry registry = LocateRegistry.getRegistry(
					Configuration.SERVER_NAME, Configuration.SERVER_PORT);
			service = (AuthService) registry.lookup(Configuration.SERVICE_NAME);
			// create user interactive console
			console = new Scanner(System.in);
			String username = inputString("Please input your username");
			String password = inputString("Please input your password");
			profile = service.login(username, password);
			logger.debug("authentication result: " + profile);
			if (profile == null) {
				logger.info("Login fail and exit.");
				System.out.println("Login fail.");
				System.exit(0);
			}
			while (true) {
				System.out.println("==================================");
				if (UserRole.ADMIN == profile.getRole()) {
					// show options
					displayAdminOptions();
					int option = inputInt("Please input your choice number");
					switch (option) {
					case 1:
						createAccount();
						break;
					case 2:
						deleteAccount();
						break;
					case 3:
						resetPassword();
						break;
					case 4:
						listAccounts();
						break;
					case 5:
						displayAccountSchedule();
						break;
					case 6:
						addEventForAccount();
						break;
					case 7:
						editEventForAccount();
						break;
					case 8:
						deleteEventFromAccount();
						break;
					case 9:
						logger.debug("exit system");
						System.out.println("Bye");
						System.exit(0);
						break;

					default:
						break;
					}
					// do user option

				} else {
					// show options
					displayRegularOptions();
					// do user option
					int option = inputInt("Please input your choice number");
					switch (option) {
					case 1:
						displaySchedule(profile);
						break;
					case 2:
						addEvent(profile);
						break;
					case 3:
						editEvent(profile);
						break;
					case 4:
						deleteEvent(profile);
						break;
					case 5:
						changePassword();
						break;
					case 6:
						logger.debug("exit system");
						System.out.println("Bye");
						System.exit(0);
						break;

					default:
						break;
					}

				}
			}
		} catch (Exception e) {
			logger.error("AuthService exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Create user account
	 * 
	 * @throws RemoteException
	 */
	private static void createAccount() throws RemoteException {
		String username = inputString("Please input account's name");
		String password = inputString("Please input account's password");
		if (service.createAccount(username, password)) {
			System.out.println("Create account success.");
		} else {
			System.out.println("Create account fail!");
		}

	}

	/**
	 * Delete a user account
	 * 
	 * @throws RemoteException
	 */
	private static void deleteAccount() throws RemoteException {
		System.out.println("Availiable accounts:");
		listAccounts();
		String username = inputString("Please input account's name");
		User user = service.getUser(username);
		if (user == null) {
			System.out.println("User doesn't exist.");
			return;
		}
		String confirm = inputString("Are you sure to delete account '"
				+ username + "'?[N]");
		if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("Yes")) {
			if (service.deleteAccount(username)) {
				System.out.println("Delete account success.");
			} else {
				System.out.println("Delete account fail!");
			}
		} else {
			System.out.println("Nothing is changed.");
		}
	}

	/**
	 * Reset user password
	 * 
	 * @throws RemoteException
	 */
	private static void changePassword() throws RemoteException {
		String oldPass = inputString("Please input old pasword");
		String password = inputString("Please input new pasword");
		String confirm = inputString("Please re-input new pasword");
		while (!password.equals(confirm)) {
			System.out.println("Passwords are not match. Please try again.");
			password = inputString("Please input account's new pasword");
			confirm = inputString("Please re-input account's new pasword");
		}

		if (service.changePassword(profile.getUsername(), oldPass, password)) {
			System.out.println("User password has been changed.");
		} else {
			System.out.println("Change password fail!");
		}

	}

	/**
	 * Reset user password
	 * 
	 * @throws RemoteException
	 */
	private static void resetPassword() throws RemoteException {
		System.out.println("Availiable accounts:");
		listAccounts();
		String username = inputString("Please input account's name");
		User user = service.getUser(username);
		if (user == null) {
			System.out.println("User doesn't exist.");
			return;
		}
		String password = inputString("Please input account's new pasword");
		String confirm = inputString("Please re-input account's new pasword");
		while (!password.equals(confirm)) {
			System.out.println("Passwords are not match. Please try again.");
			password = inputString("Please input account's new pasword");
			confirm = inputString("Please re-input account's new pasword");
		}

		if (service.resetPassword(profile, username, password)) {
			System.out.println("User password has been changed.");
		} else {
			System.out.println("Reset user password fail!");
		}

	}

	/**
	 * List all user accounts
	 * 
	 * @throws RemoteException
	 */
	private static void listAccounts() throws RemoteException {
		List<User> users = service.listAccounts(profile);
		int index = 0;
		for (User user : users) {
			System.out.println(++index + ". " + user.getUsername());
		}
	}

	/**
	 * Display schedule associated with specific user
	 * 
	 * @throws RemoteException
	 */
	private static void displayAccountSchedule() throws RemoteException {
		System.out.println("Availiable accounts:");
		listAccounts();
		String username = inputString("Please input account's name");
		User user = service.getUser(username);
		if (user == null) {
			System.out.println("User doesn't exist.");
			return;
		}
		displaySchedule(user);
	}

	/**
	 * Display user schedule
	 * 
	 * @param user
	 * @throws RemoteException
	 */
	private static void displaySchedule(User user) throws RemoteException {
		Schedule schedule = service.getScheduleByUser(user);
		if (schedule == null) {
			System.out.println("There is no event.");
			return;
		}
		Set<Event> events = schedule.getEvents();
		if (events == null || events.size() == 0) {
			System.out.println("There is no event.");
			return;
		}
		int index = 0;
		for (Event event : events) {
			System.out.println(++index + ". Event ID: " + event.getId()
					+ ", Date: " + Utilities.formatDate(event.getEventDate())
					+ ", Title: " + event.getTitle());
		}
	}

	/**
	 * Add an event to selected user's schedule
	 * 
	 * @throws RemoteException
	 * @throws ParseException
	 */
	private static void addEventForAccount() throws RemoteException,
			ParseException {
		System.out.println("Availiable accounts:");
		listAccounts();
		String username = inputString("Please input account's name");
		User user = service.getUser(username);
		if (user == null) {
			System.out.println("User doesn't exist.");
			return;
		}
		addEvent(user);
	}

	/**
	 * Edit an event from selected user's schedule
	 * 
	 * @throws RemoteException
	 * @throws ParseException
	 */
	private static void editEventForAccount() throws RemoteException,
			ParseException {
		System.out.println("Availiable accounts:");
		listAccounts();
		String username = inputString("Please input account's name");
		User user = service.getUser(username);
		if (user == null) {
			System.out.println("User doesn't exist.");
			return;
		}
		editEvent(user);
	}

	/**
	 * Delete an event from selected user's schedule
	 * 
	 * @throws RemoteException
	 */
	private static void deleteEventFromAccount() throws RemoteException {
		System.out.println("Availiable accounts:");
		listAccounts();
		String username = inputString("Please input account's name");
		User user = service.getUser(username);
		if (user == null) {
			System.out.println("User doesn't exist.");
			return;
		}
		deleteEvent(user);
	}

	/**
	 * Add an event for user
	 * 
	 * @param user
	 * @throws RemoteException
	 * @throws ParseException
	 */
	private static void addEvent(User user) throws RemoteException,
			ParseException {
		String strDate = inputString("Please input event date [MM/dd/yyyy]");
		String title = inputString("Please input event title");
		Event event = new Event(title, Utilities.parseDate(strDate));
		if (service.addEvent(user, event)) {
			System.out.println("Add event success");
		} else {
			System.out.println("Add event fail");
		}

	}

	/**
	 * Edit event for user
	 * 
	 * @param user
	 * @throws RemoteException
	 * @throws ParseException
	 */
	private static void editEvent(User user) throws RemoteException,
			ParseException {
		Schedule schedule = service.getScheduleByUser(user);
		if (schedule == null) {
			System.out.println("There is no event of user "
					+ user.getUsername());
			return;
		}
		Set<Event> events = schedule.getEvents();
		if (events == null || events.size() == 0) {
			System.out.println("There is no event of user "
					+ user.getUsername());
			return;
		}
		System.out.println("Available events: ");
		int index = 0;
		for (Event event : events) {
			System.out.println(++index + ". Event ID: " + event.getId()
					+ ", Date: " + Utilities.formatDate(event.getEventDate())
					+ ", Title: " + event.getTitle());
		}
		int id = inputInt("Please input event ID");
		// find old event
		Event oldEvent = null;
		for (Event event : events) {
			if (event.getId() == id) {
				oldEvent = event;
				break;
			}
		}
		if (oldEvent == null) {
			System.out.println("Nothing is changed. There is no such a event.");
			return;
		}
		String strDate = inputString(
				"Please input event date ["
						+ Utilities.formatDate(oldEvent.getEventDate()) + "]",
				true);
		String title = inputString(
				"Please input event title [" + oldEvent.getTitle() + "]", true);
		boolean isModified = false;
		if (!strDate.equals("")) {
			oldEvent.setEventDate(Utilities.parseDate(strDate));
			isModified = true;
		}
		if (!title.equals("")) {
			oldEvent.setTitle(title);
			isModified = true;
		}
		if (isModified) {
			if (service.updateEvent(user, oldEvent)) {
				System.out.println("Edit event success.");
			} else {
				System.out.println("Edit event fail.");
			}
		} else {
			System.out.println("Nothings is changed.");
		}
	}

	/**
	 * Delete an user's event
	 * 
	 * @param user
	 * @throws RemoteException
	 */
	private static void deleteEvent(User user) throws RemoteException {
		Schedule schedule = service.getScheduleByUser(user);
		if (schedule == null) {
			System.out.println("There is no event of user "
					+ user.getUsername());
			return;
		}
		Set<Event> events = schedule.getEvents();
		if (events == null || events.size() == 0) {
			System.out.println("There is no event of user "
					+ user.getUsername());
			return;
		}
		System.out.println("Available events: ");
		int index = 0;
		for (Event event : events) {
			System.out.println(++index + ". Event ID: " + event.getId()
					+ ", Date: " + Utilities.formatDate(event.getEventDate())
					+ ", Title: " + event.getTitle());
		}
		int id = inputInt("Please input event ID");
		// find old event
		Event oldEvent = null;
		for (Event event : events) {
			if (event.getId() == id) {
				oldEvent = event;
				break;
			}
		}
		if (oldEvent == null) {
			System.out.println("Nothing is changed. There is no such a event.");
			return;
		}
		String confirm = inputString("Are you sure to delete the event '"
				+ oldEvent.getTitle() + "'?[N]");
		if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("Yes")) {
			if (service.removeEvent(user, oldEvent)) {
				System.out.println("Delete event success");
			} else {
				System.out.println("Delete event fail");
			}
		} else {
			System.out.println("Noting is changed.");
		}
	}

	/**
	 * Read a string from console
	 * 
	 * @param label
	 * @return
	 */
	private static String inputString(String label) {
		return inputString(label, false);
	}

	/**
	 * Read a string from console
	 * 
	 * @param label
	 * @param allownEmptyLine
	 * @return
	 */
	private static String inputString(String label, boolean allownEmptyLine) {
		System.out.print(label + ": ");
		String value = null;
		do {
			value = console.nextLine();
		} while (value.equals("") && !allownEmptyLine);
		return value.trim();
	}

	/**
	 * Read an integer from console. If the value is Integer.MIN_VALUE, it mays
	 * no input from user.
	 * 
	 * @param label
	 * @param allowEmptyLine
	 * @return
	 */
	private static int inputInt(String label, boolean allowEmptyLine) {
		int value = Integer.MIN_VALUE;
		do {
			try {
				String strValue = inputString(label, allowEmptyLine);
				if (strValue.equals("") && allowEmptyLine)
					break; // accept empty input
				value = Integer.parseInt(strValue);
				break;
			} catch (NumberFormatException e) {
				System.out.println("Nubmer format is wrong. Please try again.");
			}
		} while (value == Integer.MIN_VALUE && !allowEmptyLine);
		return value;
	}

	/**
	 * Read an integer from console. If the value is Integer.MIN_VALUE, it mays
	 * no input from user.
	 * 
	 * @param label
	 * @return
	 */

	private static int inputInt(String label) {
		return inputInt(label, false);
	}

	/**
	 * Display regular user's options
	 */
	private static void displayRegularOptions() {
		System.out.println("1. Display schedule");
		System.out.println("2. Add an event");
		System.out.println("3. Edit an event");
		System.out.println("4. Delete an event");
		System.out.println("5. Change password");
		System.out.println("6. Exit Program");
	}

	/**
	 * Display admin's options
	 */
	private static void displayAdminOptions() {
		System.out.println("1. Create an account");
		System.out.println("2. Delete an account");
		System.out.println("3. Reset user's password");
		System.out.println("4. List all accounts");
		System.out.println("5. Display schedule associated with specific user");
		System.out.println("6. Add an event to selected user's schedule");
		System.out.println("7. Edit an event from selected user's schedule");
		System.out.println("8. Delete an event from selected user's schedule");
		System.out.println("9. Exit Program");
	}

}
