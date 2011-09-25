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

public class Calendar
{
	private String name;
	private User owner;

	private ArrayList<Event> privateEvents;
	private ArrayList<Event> publicEvents;

	public Calendar(User owner, String name)
	{
		this.owner = owner;
		this.name = name;
		this.privateEvents = new ArrayList<Event>();
		this.publicEvents = new ArrayList<Event>();
	}

	/** Creates a new private event in the given {@link Calendar}.
	 * @param name Title of the event to identify it.
	 * @param startDate Date of the event to begin.
	 * @param endDate Date of the event to end.
	 * @param user The {@link User} who owns this calendar.
	 * @return The created event.
	 * @throws AccessDeniedException 
	 * @throws InvalidDateException If the end date is placed before the start date.
	 */
	public Event createPrivateEvent(String name, Date startDate, Date endDate, User user) throws AccessDeniedException, InvalidDateException
	{
		this.checkUserIsOwner(user);
		Event newEvent = new Event(name, startDate, endDate);
		this.privateEvents.add(newEvent);
		return newEvent;
	}

	/** Creates a new public event in the given {@link Calendar}.
	 * @param name Title of the event to identify it.
	 * @param startDate Date of the event to begin.
	 * @param endDate Date of the event to end.
	 * @param user {@link User} 
	 * @return The created event.
	 * @throws AccessDeniedException 
	 * @throws InvalidDateException If the end date is placed before the start date.
	 */
	public Event createPublicEvent(String name, Date startDate, Date endDate, User user) throws AccessDeniedException, InvalidDateException
	{
		this.checkUserIsOwner(user);
		Event newEvent = new Event(name, startDate, endDate);
		newEvent.setStatePublic();
		this.publicEvents.add(newEvent);
		return newEvent;
	}

	public User getOwner()
	{
		return this.owner;
	}

	public String getName()
	{
		return this.name;
	}

	/* Functions to get particular events from calendars */

	/** Provides an {@link Iterator} with all (public and private) events from the specified calendar, that begin at the given date {@code startDate}.
	 * @param startDate Starting point of events.
	 * @param user The {@link User} object of the owner since private events are returned as well.
	 * @return All events that are set to start at {@code startDate}.
	 * @throws AccessDeniedException 
	 */
	public Iterator<Event> getAllEventsStarting(Date startDate, User user) throws AccessDeniedException
	{
		this.checkUserIsOwner(user);

		ArrayList<Event> eventList = new ArrayList<Event>();
		eventList.addAll(this.privateEvents);
		eventList.addAll(this.publicEvents);
		eventList = this.getEventsWithStartDateOrMore(startDate, eventList);
//		this.sortEvents(eventList);
		return eventList.iterator();
	}

	/** Used to get a list with public events only from the specified calendar, that are set to happen at the date {@code startDate}.
	 * @param startDate Date when events take place.
	 * @return All public events with {@code startDate} as starting point.
	 */
	public Iterator<Event> getAllPublicEventsStarting(Date startDate)
	{
		return this.getEventsWithStartDateOrMore(startDate, this.publicEvents).iterator();
	}

	/** Provides all (public and private) events at a given {@code date} from the specified calendar as an {@link ArrayList}.
	 * @param date Date form which to list all events.
	 * @param user This function assumes that only the owner of the calendar has the {@link User} object
	 * at his / her disposal (not the user name as string) because
	 * in order to get the {@link User} object, a password is required.
	 * @return All events in the calendar.
	 * @throws AccessDeniedException If used from a {@link User}, that does not own the specified calendar. Use {@link Calendar#getAllPublicEventsDate(Date)} instead.
	 * @see Authentication#getUser(String, String)
	 */
	public ArrayList<Event> getAllEventsDate(Date date, User user) throws AccessDeniedException
	{
		this.checkUserIsOwner(user);
		ArrayList<Event> eventList = new ArrayList<Event>();
		eventList.addAll(this.privateEvents);
		eventList.addAll(this.publicEvents);
//		this.sortEvents(eventList);
		return this.getEventsWithDate(date, eventList);
	}
	
	/** Used to get an {@link ArrayList} with public events occurring on a given {@code date}.
	 * @param date Date form which all public events should be listed.
	 * @return All public events from a specified calendar.
	 */
	public ArrayList<Event> getAllPublicEventsDate(Date date)
	{
		return this.getEventsWithDate(date, this.publicEvents);
	}

	/** Returns a single event.
	 * @param eventName Title of the event to return.
	 * @param startDate The date when the event begins.
	 * @return The desired event object for reading or messsing up (editing) with.
	 * @throws AccessDeniedException 
	 * @throws UnknownEventException If the event is not in the calendar.
	 */
	public Event getEvent(String eventName, Date startDate) throws AccessDeniedException, UnknownEventException
	{
		Iterator<Event> iteratorAllEvents = this.getAllEventsStarting(startDate, this.owner);
		Event currentEvent = null;
		while (iteratorAllEvents.hasNext())
		{
			currentEvent = iteratorAllEvents.next();
			if (currentEvent.getEventName().equals(eventName) && currentEvent.getStartDate().equals(startDate))
			{
				return currentEvent;
			}
		}
		throw new UnknownEventException(eventName, startDate);
	}

	/* Functions used to modify events */

	//TODO: Write tests
	public void editEventName(String eventName, Date startDate, User user, String newEventName) throws AccessDeniedException, UnknownEventException
	{
		this.checkUserIsOwner(user);
		this.getEvent(eventName, startDate).setEventName(newEventName);
	}	

	public void editEventStartDate(String eventName, Date startDate, User user, Date newStartDate) throws AccessDeniedException, UnknownEventException
	{
		this.checkUserIsOwner(user);
		this.getEvent(eventName, startDate).setStartDate(newStartDate);
	}	

	public void editEventEndDate(String eventName, Date startDate, User user, Date newEndDate) throws AccessDeniedException, UnknownEventException
	{
		this.checkUserIsOwner(user);
		this.getEvent(eventName, startDate).setStartDate(newEndDate);
	}	

	public void editEventStateToPublic(String eventName, Date startDate, User user) throws AccessDeniedException, UnknownEventException
	{
		this.checkUserIsOwner(user);
		this.getEvent(eventName, startDate).setStatePublic();
	}	

	public void editEventStateToPrivate(String eventName, Date startDate, User user) throws AccessDeniedException, UnknownEventException
	{
		this.checkUserIsOwner(user);
		this.getEvent(eventName, startDate).setStatePrivate();
	}	

	/* Private methods */

	private ArrayList<Event> getEventsWithDate(Date date, ArrayList<Event> events)
	{
		//TODO: TEST THIS!
		ArrayList<Event> output = new ArrayList<Event>();
		for (Event event : events)
		{
			if (event.getEndDate().after(date))
			{
				output.add(event);
			}
		}
		return output;
	}

	private ArrayList<Event> getEventsWithStartDateOrMore(Date startDate, ArrayList<Event> events)
	{
		//TODO: TEST THIS!
		ArrayList<Event> output = new ArrayList<Event>();
		for (Event event : events)
		{
			if (event.getStartDate().after(startDate) || event.getStartDate().equals(startDate))
			{
				output.add(event);
			}
		}
		return output;
	}

//	private ArrayList<Event> sortEvents(ArrayList<Event> inputList)
//	{
//		ArrayList<Event> output = new ArrayList<Event>();
//		//TODO: TEST THIS!
//		return output;
//	}

	/*
	 * Usually this function would never throw an exception
	 * because is should not be possible to get a calendar object
	 * without having an user object which in turn should only be handed over when providing a password.
	 * Nevertheless this function was added as a precautionary measure against unpleasant surprises.
	 */
	private void checkUserIsOwner(User user) throws AccessDeniedException
	{
		if (this.owner != user)
		{
			throw new AccessDeniedException(this);
		}
	}

	/** Deletes the event from the calendar.
	 * @param eventName Title of the event to identify it.
	 * @param startDate Date when the event to be deleted starts.
	 * @throws UnknownEventException If the event is not in the calendar.
	 * @throws AccessDeniedException
	 */
	public void deleteEvent(String eventName, Date startDate) throws AccessDeniedException, UnknownEventException
	{
		Event eventToDelete = this.getEvent(eventName, startDate);
		if (eventToDelete.isPrivate())
		{
			this.privateEvents.remove(eventToDelete);
		}
		else
		{
			this.publicEvents.remove(eventToDelete);
		}
	}
}
