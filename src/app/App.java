/**
 * 
 */
package app;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import app.AppExceptions.AccessDeniedException;
import app.AppExceptions.UnknownUserException;
import app.AppExceptions.UserNameAlreadyExistException;
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

	///////////
	// USERS //
	///////////

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

	////////////////////////////
	// GET CALENDARS & EVENTS //
	////////////////////////////

	//****************//
	//***OTHER USER***//
	//****************//

	/** Provides all calendar names as Strings on a specific users.
	 * @param userName Owner of the calendar
	 * @return String with all calendar names of the user.
	 * @throws UnknownUserException if no valid user was provided.
	 */
	public String getAllCalendarsNamesFromUser(String userName) throws UnknownUserException
	{
		User user = this.auth.getUser(userName);
		return user.getAllMyCalendarNames();
	}

	/**
	 * Returns all public events from a named calendar that belong to the given user.
	 * @param userName Owner of the calendar.
	 * @param calendarName Title of a calendar to identify it.
	 * @param date Self-explanatory!
	 * @return An ArrayList with all public events.
	 * @throws UnknownUserException if no valid user was provided.
	 * @throws UnknownCalendarException 
	 * @throws AccessDeniedException 
	 */
	public ArrayList<Event> getUsersCalendarPublicEventsOverview(String userName, String calendarName, Date date) throws UnknownUserException, UnknownCalendarException, AccessDeniedException
	{
		User user = this.auth.getUser(userName);
		return user.getMyCalendarPublicEventsDate(calendarName, date);
	}

	/**
	 * Returns all public events from a named calendar that belong to the given user.
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
	 * @return Provides the user object.
	 * @throws UnknownUserException
	 * @throws AccessDeniedException
	 */
	public User loginUser(String username, String password) throws UnknownUserException, AccessDeniedException
	{
		return this.auth.getUser(username, password);
	}
}
