/**
 * Calendar framework
 */
package app;

import interfaces.IEvent;
import interfaces.IUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import app.AppExceptions.InvalidDateException;
import app.AppExceptions.UnknownCalendarException;
import app.AppExceptions.*;

/**
 * @author Lukas Keller
 * @author Renato Corti
 *
 */

public class User implements IUser
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

	@Override
	public void createNewCalendar(String nameOfCalendar) 
	{
		Calendar newCalendar = new Calendar(this, nameOfCalendar);
		this.calendars.add(newCalendar);
	}

	@Override
	public void deleteCalendar(String nameOfCalendar) throws UnknownCalendarException 
	{
		Calendar calendarToDelete = this.getCalendar(nameOfCalendar);
		this.calendars.remove(calendarToDelete);
	}

	@Override
	public ArrayList<String> getAllMyCalendarNames() 
	{
		ArrayList<String> allCalendarNames = new ArrayList<String>();
		
		for (Calendar c : this.calendars)
		{
			allCalendarNames.add(c.getName());
		}
		return allCalendarNames;
	}

	@Override
	public boolean hasNoCalendar() 
	{
		return this.calendars.isEmpty();
	}

	@Override
	public ArrayList<IEvent> getMyCalendarAllEventsDate(String calendarName,Date date) throws UnknownCalendarException, AccessDeniedException 
	{
		Calendar calendar = this.getCalendar(calendarName);
		
		return calendar.getAllEventsDate(date);
	}

	@Override
	public Iterator<IEvent> getMyCalendarAllEventsStarting(String calendarName, Date startDate) throws UnknownCalendarException, AccessDeniedException 
	{
		Calendar calendar = this.getCalendar(calendarName);
		
		return calendar.getAllEventsStarting(startDate);
	}

	@Override
	public Iterator<IEvent> getMyCalendarPublicEventsStarting(String calendarName, Date startDate) throws UnknownCalendarException 
	{
		Calendar calendar = this.getCalendar(calendarName);
		
		return calendar.getAllPublicEventsStarting(startDate);
	}

	@Override
	public ArrayList<IEvent> getMyCalendarPublicEventsDate(String calendarName, Date date) throws UnknownCalendarException 
	{
		Calendar calendar = this.getCalendar(calendarName);
	
		return calendar.getAllPublicEventsDate(date);
	}

	@Override
	public void createPrivateEvent(String calendarName, String eventName, Date startDate, Date endDate) throws AccessDeniedException, InvalidDateException, UnknownCalendarException 
	{
		Calendar calendar = this.getCalendar(calendarName);
		
		calendar.createPrivateEvent(eventName, startDate, endDate);
	}

	@Override
	public void createPublicEvent(String calendarName, String eventName, Date startDate, Date endDate) throws AccessDeniedException, InvalidDateException, UnknownCalendarException 
	{
		Calendar calendar = this.getCalendar(calendarName);
		
		calendar.createPublicEvent(eventName, startDate, endDate);
	}

	@Override
	public void editEventName(String calendarName, String eventName, Date startDate, String newEventName) throws AccessDeniedException, UnknownEventException, UnknownCalendarException, InvalidDateException 
	{
		Calendar calendar = this.getCalendar(calendarName);
		calendar.editEvent(eventName, startDate, newEventName, null, null, null);
	}

	@Override
	public void editEventStartDate(String calendarName, String eventName, Date startDate, Date newStartDate) throws AccessDeniedException, UnknownEventException, UnknownCalendarException, InvalidDateException 
	{
		Calendar calendar = this.getCalendar(calendarName);
		calendar.editEvent(eventName, startDate, null, newStartDate, null, null);
	}

	@Override
	public void editEventEndDate(String calendarName, String eventName, Date startDate, Date newEndDate) throws AccessDeniedException, UnknownEventException, UnknownCalendarException, InvalidDateException 
	{
		Calendar calendar = this.getCalendar(calendarName);
		calendar.editEvent(eventName, startDate, null, null, newEndDate, null);
	}

	@Override
	public void editEventStateToPublic(String calendarName, String eventName, Date startDate) throws AccessDeniedException, UnknownEventException, UnknownCalendarException, InvalidDateException 
	{
		Calendar calendar = this.getCalendar(calendarName);
		calendar.editEvent(eventName, startDate, null, null, null, false);
	}

	@Override
	public void editEventStateToPrivate(String calendarName, String eventName, Date startDate) throws AccessDeniedException, UnknownEventException, UnknownCalendarException, InvalidDateException 
	{
		Calendar calendar = this.getCalendar(calendarName);
		calendar.editEvent(eventName, startDate, null, null, null, true);
	}

	@Override
	public void deleteEvent(String calendarName, String eventName,Date startDate) throws AccessDeniedException, UnknownEventException, UnknownCalendarException 
	{
		Calendar calendar = this.getCalendar(calendarName);
		calendar.deleteEvent(eventName, startDate);
	}

	//////////////
	//GET EVENTS//
	//////////////
	
	@Override
	public Iterator<IEvent> getAllEventsStarting(String calendarName, Date startDate) throws AccessDeniedException, UnknownCalendarException 
	{
		return this.getCalendar(calendarName).getAllEventsStarting(startDate);
	}

	@Override
	public ArrayList<IEvent> getAllEventsDate(String calendarName, Date date) throws AccessDeniedException, UnknownCalendarException 
	{
		return this.getCalendar(calendarName).getAllEventsDate(date);
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
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


}
