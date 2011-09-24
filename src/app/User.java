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

	/**
	 * @param name 
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

	///////////////
	// CALENDARS //
	///////////////

	public Calendar createNewCalendar(String nameOfCalendar)
	{
		Calendar newCalendar = new Calendar(this, nameOfCalendar);
		this.calendars.add(newCalendar);
		return newCalendar;
	}

	public void deleteCalendar(String nameOfCalendar) throws UnknownCalendarException
	{
		Calendar calendarToDelete = this.getCalendar(nameOfCalendar);
		this.calendars.remove(calendarToDelete);
	}

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

	public ArrayList<String> getAllMyCalendarNames()
	{
		ArrayList<String> allCalendarNames = new ArrayList<String>();
		
		for (Calendar c : this.calendars)
		{
			allCalendarNames.add(c.getName());
		}
		return allCalendarNames;
	}

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

	////////////
	// EVENTS //
	////////////

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
