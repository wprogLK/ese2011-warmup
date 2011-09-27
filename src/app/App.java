/**
 * Calendar framework
 */
package app;

import interfaces.IApp;
import interfaces.IEvent;
import interfaces.IUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import app.AppExceptions.*;

/**
 * @author Lukas Keller
 * @author Renato Corti
 *
 */

public class App implements IApp
{
	private Authentication auth;

	public App()
	{
		this.auth = new Authentication();
	}

	@Override
	public void createUser(String username, String password) throws UsernameAlreadyExistException
	{
		this.auth.createNewUser(username, password, this);
	}

	@Override
	public void changePassword(String username, String oldPassword, String newPassword) throws UnknownUserException, AccessDeniedException
	{
		this.auth.setNewPassword(username, oldPassword, newPassword);
	}

	@Override
	public void deleteUser(String username, String password) throws UnknownUserException, AccessDeniedException
	{
		this.auth.deleteUser(username, password);
	}

	@Override
	public ArrayList<String> getAllCalendarsNamesFromUser(String username) throws UnknownUserException
	{
		User user = this.auth.getUser(username);
		return user.getAllMyCalendarNames();
	}

	@Override
	public ArrayList<IEvent> getUsersCalendarPublicEventsOverview(String username, String calendarName, Date date) throws UnknownUserException, UnknownCalendarException, AccessDeniedException
	{
		User user = this.auth.getUser(username);
		return user.getMyCalendarPublicEventsDate(calendarName, date);
	}

	@Override
	public Iterator<IEvent> getUsersCalendarPublicEvents(String username, String calendarName, Date startDate) throws UnknownUserException, UnknownCalendarException, AccessDeniedException
	{
		User user = this.auth.getUser(username);
		return user.getMyCalendarPublicEventsStarting(calendarName, startDate);
	}

	@Override
	public IUser loginUser(String username, String password) throws UnknownUserException, AccessDeniedException
	{
		return this.auth.getUser(username, password);
	}
}
