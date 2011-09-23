/**
 * 
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
	private ArrayList<User> users;

	public App()
	{
		this.users = new ArrayList<User>();
	}

	///////////
	// USERS //
	///////////

	public User createUser(String userName) throws UserNameAlreadyExistException
	{
		try 
		{
			this.getUser(userName);
			throw new UserNameAlreadyExistException(userName);
		} 
		catch (UnknownUserException e) 
		{
			User newUser = new User(userName, this);
			this.users.add(newUser);
			return newUser;
		}
	}

	public void deleteUser(String userName) throws UnknownUserException
	{
		User userToDelete = getUser(userName);
		if (userToDelete == null)
		{
			throw new UnknownUserException(userName);
		}
		else
		{
			this.users.remove(userToDelete);
		}
	}

	/////////////////////////////////////
	// GET CALENDARS & EVENTS & EVENTS //
	/////////////////////////////////////

	//****************//
	//***OTHER USER***//
	//****************//

	public String getUsersCalendarsNames(String userName) throws UnknownUserException
	{
		User user = this.getUser(userName);
		return user.getAllMyCalendarNames();
	}

	public ArrayList<Event> getUsersCalendarPublicEventsOverview(String userName, String calendarName, Date date) throws UnknownUserException, UnknownCalendarException, NoAccessToCalendarException
	{
		User user = this.getUser(userName);
		return user.getMyCalendarPublicEventsDate(calendarName, date);
	}

	public Iterator<Event> getUsersCalendarPublicEvents(String userName, String calendarName, Date startDate) throws UnknownUserException, UnknownCalendarException, NoAccessToCalendarException
	{
		User user = this.getUser(userName);
		return user.getMyCalendarPublicEventsStarting(calendarName, startDate);
	}

	//*********//
	//***ALL***//
	//*********//

	public void getAllCalendarsPublicEvents(Date startDate) throws NoUserExistException
	{
		if (this.users.isEmpty())
		{
			throw new NoUserExistException();
		}
	}
	public User getUser(String userName) throws UnknownUserException
	{
		Iterator<User> iteratorUsers = this.users.iterator();
		User currentUser = null;
		while (iteratorUsers.hasNext())
		{
			currentUser = iteratorUsers.next();
			if (currentUser.getName().equals(userName))
			{
				return currentUser;
			}
		}
		throw new UnknownUserException(userName);
	}
}
