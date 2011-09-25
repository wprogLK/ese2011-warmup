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

public class User
{
	private ArrayList<Calendar> calendars;
	private String name;

	/** Constructor for an user object. It contains the {@link Calendar} object.
	 * @param name The user name must be unique.
	 * @param app  
	 */
	public User(String name, final App app)
	{
		this.name = name;
		this.calendars = new ArrayList<Calendar>();
	}

	public String getName()
	{
		return this.name;
	}

	/* Operations on calendars */

	/** Cretes a new {@link Calendar} for the specified user.
	 * @param nameOfCalendar The title of the calendar to be created.
	 * @return The created calendar.
	 * */
	public Calendar createNewCalendar(String nameOfCalendar)
	{
		Calendar newCalendar = new Calendar(this, nameOfCalendar);
		this.calendars.add(newCalendar);
		return newCalendar;
	}

	/** Removes the calendar including all events in it from the user.
	 * @param nameOfCalendar The title the calendar was given at creation time.
	 * @throws UnknownCalendarException If the {@code user} has no calendar with such a name.
	 */
	public void deleteCalendar(String nameOfCalendar) throws UnknownCalendarException
	{
		Calendar calendarToDelete = this.getCalendar(nameOfCalendar);
		this.calendars.remove(calendarToDelete);
	}

	/** Provides a {@link Calendar} object. It can be used to perform further operations on the calendar.
	 * @param calendarName The title the calendar was given at creation time.
	 * @return The calendar with the corresponding {@code calendarName}.
	 * @throws UnknownCalendarException If the {@code user} has no calendar with such a name.
	 */
	public Calendar getCalendar(String calendarName) throws UnknownCalendarException
	{
		Iterator<Calendar> iteratorCalendar = this.calendars.iterator();
		while (iteratorCalendar.hasNext())
		{
			Calendar currentCalendar = iteratorCalendar.next();
			if (currentCalendar.getName().equals(calendarName))
			{
				return currentCalendar;
			}
		}
		throw new UnknownCalendarException(calendarName);
	}

	/** Provides an {@link ArrayList} with all calendar titles created by the user.
	 * @return The string literals of the calendars as {@link ArrayList}.
	 */
	public ArrayList<String> getAllMyCalendarNames()
	{
		ArrayList<String> allCalendarNames = new ArrayList<String>();
		
		for (Calendar c : this.calendars)
		{
			allCalendarNames.add(c.getName());
		}
		return allCalendarNames;
	}

	/** Tells, if the user has any calendars.
	 * @return If the {@link Calendar} {@link ArrayList} is empty, {@code true} is returned, {@code false} in all other cases.
	 */
	public boolean hasNoCalendar()
	{
		return this.calendars.isEmpty();
	}

	public ArrayList<Event> getMyCalendarAllEventsDate(String calendarName, Date date) throws UnknownCalendarException, AccessDeniedException
	{
		Calendar calendar = this.getCalendar(calendarName);
		
		return calendar.getAllEventsDate(date, this);
	}

	public Iterator<Event> getMyCalendarAllEventsStarting(String calendarName, Date startDate) throws UnknownCalendarException, AccessDeniedException
	{
		Calendar calendar = this.getCalendar(calendarName);
		
		return calendar.getAllEventsStarting(startDate, this);
	}

	public Iterator<Event> getMyCalendarPublicEventsStarting(String calendarName, Date startDate) throws UnknownCalendarException
	{
		Calendar calendar = this.getCalendar(calendarName);
		
		return calendar.getAllPublicEventsStarting(startDate);
	}

	public ArrayList<Event> getMyCalendarPublicEventsDate(String calendarName, Date date) throws UnknownCalendarException
	{
		Calendar calendar = this.getCalendar(calendarName);
		
		return calendar.getAllPublicEventsDate(date);
	}

	/* Operation on events */

	public Event createPublicEvent(String calendarName, String eventName, Date startDate, Date endDate) throws UnknownCalendarException, AccessDeniedException, InvalidDateException
	{
		Calendar calendar = this.getCalendar(calendarName);
		
		return calendar.createPublicEvent(eventName, startDate, endDate, this);
	}

	public Event createPrivateEvent(String calendarName, String eventName, Date startDate, Date endDate) throws UnknownCalendarException, AccessDeniedException, InvalidDateException
	{
		Calendar calendar = this.getCalendar(calendarName);
		
		return calendar.createPrivateEvent(eventName, startDate, endDate, this);
	}
}
