/**
 * Calendar framework
 */
package app;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import app.AppExceptions.*;

/**
 * @author Lukas Keller
 * @author Renato Corti
 *
 */

public class App 
{
	private Authentication auth;

	public App()
	{
		this.auth = new Authentication();
	}

	/* Functions for the user management */

	public User createUser(String username, String password) throws UserNameAlreadyExistException
	{
		return this.auth.createNewUser(username, password, this);
	}

	public void changePassword(String username, String oldPassword, String newPassword) throws UnknownUserException, AccessDeniedException
	{
		this.auth.setNewPassword(username, oldPassword, newPassword);
	}

	public void deleteUser(String userName, String password) throws UnknownUserException, AccessDeniedException
	{
		this.auth.deleteUser(userName, password);
	}

	/* Getters for calendars & events for users that are not owners of the calendars */

	/** Provides all calendar names as an ArrayList of strings of a specific users.
	 * The user object used here must not leave this function as it does not ask for a password for it.
	 * @param userName Owner of the calendars requested
	 * @return ArrayList with all calendar names of the user as represented as strings.
	 * @throws UnknownUserException if no valid user was provided.
	 * @see App#loginUser(String, String)
	 */
	public ArrayList<String> getAllCalendarsNamesFromUser(String userName) throws UnknownUserException
	{
		User user = this.auth.getUser(userName);
		return user.getAllMyCalendarNames();
	}

	/**
	 * Returns all public events from a named calendar that belong to the given user.
	 * The user object used here must not leave this function as it does not ask for a password for it.
	 * @param userName Owner of the calendar.
	 * @param calendarName Title of a calendar to identify it.
	 * @param date Self-explanatory!
	 * @return An ArrayList with all public events.
	 * @throws UnknownUserException if no valid user was provided.
	 * @throws UnknownCalendarException 
	 * @throws AccessDeniedException 
	 * @see App#loginUser(String, String)
	 */
	public ArrayList<Event> getUsersCalendarPublicEventsOverview(String userName, String calendarName, Date date) throws UnknownUserException, UnknownCalendarException, AccessDeniedException
	{
		User user = this.auth.getUser(userName);
		return user.getMyCalendarPublicEventsDate(calendarName, date);
	}

	/**
	 * Returns all public events from a named calendar that belong to the given user.
	 * The user object used here must not leave this function as it does not ask for a password for it.
	 * @param userName Owner of the calendar.
	 * @param calendarName Title of a calendar to identify it.
	 * @param startDate Self-explanatory!
	 * @return An iterator with all public events that occur at startDate or later.
	 * @throws UnknownUserException
	 * @throws UnknownCalendarException 
	 * @throws AccessDeniedException
	 */
	public Iterator<Event> getUsersCalendarPublicEvents(String userName, String calendarName, Date startDate) throws UnknownUserException, UnknownCalendarException, AccessDeniedException
	{
		User user = this.auth.getUser(userName);
		return user.getMyCalendarPublicEventsStarting(calendarName, startDate);
	}

	/**
	 * @param username Self-explanatory!
	 * @param password Self-explanatory!
	 * @return Returns the user object provided that the user exists and the corresponding
	 * password for the given user is correct.
	 * Not to be confused with the {@link Authentication#getUser(String, String)} function which is only
	 * available for internal processing.
	 * @throws UnknownUserException
	 * @throws AccessDeniedException
	 */
	public User loginUser(String username, String password) throws UnknownUserException, AccessDeniedException
	{
		return this.auth.getUser(username, password);
	}
}
