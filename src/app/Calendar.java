/**
 * Calendar framework
 */
package app;

import interfaces.ICalendar;
import interfaces.IEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import app.AppExceptions.InvalidDateException;
import app.AppExceptions.*;

/**
 * @author Lukas Keller
 * @author Renato Corti
 *
 */

public class Calendar implements ICalendar
{
	private String name;
	private User owner;

	private ArrayList<IEvent> privateEvents;
	private ArrayList<IEvent> publicEvents;

	public Calendar(User owner, String name)
	{
		this.owner = owner;
		this.name = name;
		this.privateEvents = new ArrayList<IEvent>();
		this.publicEvents = new ArrayList<IEvent>();
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public Iterator<IEvent> getAllPublicEventsStarting(Date startDate)
	{
		return this.getEventsWithStartDateOrMore(startDate, this.publicEvents).iterator();
	}

	@Override
	public ArrayList<IEvent> getAllPublicEventsDate(Date date)
	{
		return this.getEventsWithDate(date, this.publicEvents);
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
	public Event createPrivateEvent(String name, Date startDate, Date endDate) throws AccessDeniedException, InvalidDateException
	{
		Event newEvent = new Event(name, startDate, endDate);
		this.privateEvents.add(newEvent);
		return newEvent;
	}

	/** Creates a new public event in the given {@link Calendar}.
	 * @param name Title of the event to identify it.
	 * @param startDate Date of the event to begin.
	 * @param endDate Date of the event to end.
	 * @return The created event.
	 * @throws AccessDeniedException 
	 * @throws InvalidDateException If the end date is placed before the start date.
	 */
	public Event createPublicEvent(String name, Date startDate, Date endDate) throws AccessDeniedException, InvalidDateException
	{
		Event newEvent = new Event(name, startDate, endDate);
		newEvent.setPrivateVisibility(false);
		this.publicEvents.add(newEvent);
		return newEvent;
	}

	public User getOwner()
	{
		return this.owner;
	}

	/* Functions to get particular events from calendars */

	/** Provides an {@link Iterator} with all (public and private) events from the specified calendar, that begin at the given date {@code startDate}.
	 * @param startDate Starting point of events.
	 * @return All events that are set to start at {@code startDate}.
	 * @throws AccessDeniedException 
	 */
	public Iterator<IEvent> getAllEventsStarting(Date startDate) throws AccessDeniedException
	{
		ArrayList<IEvent> eventList = new ArrayList<IEvent>();
		eventList.addAll(this.privateEvents);
		eventList.addAll(this.publicEvents);
		eventList = this.getEventsWithStartDateOrMore(startDate, eventList);
		this.sortEvents(eventList);
		return eventList.iterator();
	}

	/** Provides all (public and private) events at a given {@code date} from the specified calendar as an {@link ArrayList}.
	 * @param date Date form which to list all events.
	 * at his / her disposal (not the user name as string) because
	 * in order to get the {@link User} object, a password is required.
	 * @return All events in the calendar.
	 * @throws AccessDeniedException If used from a {@link User}, that does not own the specified calendar. Use {@link Calendar#getAllPublicEventsDate(Date)} instead.
	 * @see Authentication#getUser(String, String)
	 */
	public ArrayList<IEvent> getAllEventsDate(Date date) throws AccessDeniedException
	{
		ArrayList<IEvent> eventList = new ArrayList<IEvent>();
		eventList.addAll(this.privateEvents);
		eventList.addAll(this.publicEvents);
		this.sortEvents(eventList);
		return this.getEventsWithDate(date, eventList);
	}

	/** Returns a single event.
	 * @param eventName Title of the event to return.
	 * @param startDate The date when the event begins.
	 * @return The desired event object for reading or messsing up (editing) with.
	 * @throws AccessDeniedException 
	 * @throws UnknownEventException If the event is not in the calendar.
	 */
	@Override
	public Event getEvent(String eventName, Date startDate) throws AccessDeniedException, UnknownEventException
	{
		Iterator<IEvent> iteratorAllEvents = this.getAllEventsStarting(startDate);
		IEvent currentEvent = null;
		while (iteratorAllEvents.hasNext())
		{
			currentEvent = iteratorAllEvents.next();
			if (currentEvent.getEventName().equals(eventName) && currentEvent.getStartDate().equals(startDate))
			{
				return (Event) currentEvent;
			}
		}
		throw new UnknownEventException(eventName, startDate);
	}

	/* Functions used to modify events */

	public void editEvent(String eventName, Date startDate, String newEventName, Date newStartDate, Date newEndDate, Boolean newPrivateVisible) throws AccessDeniedException, UnknownEventException, InvalidDateException
	{
		Event e=this.getEvent(eventName, startDate);

		if(newEventName!=null)
		{
			e.setEventName(newEventName);
		}

		if(newStartDate != null)
		{
			e.setStartDate(newStartDate);
		}

		if(newEndDate != null)
		{
			e.setEndDate(newEndDate);
		}

		if(newPrivateVisible != null)
		{
			e.setPrivateVisibility(newPrivateVisible);
		}
	}

	/* Private methods */

	private ArrayList<IEvent> getEventsWithDate(Date date, ArrayList<IEvent> events)
	{
		//TODO: TEST THIS!
		ArrayList<IEvent> output = new ArrayList<IEvent>();
		for (IEvent event : events)
		{
			if (event.getEndDate().after(date) || event.getEndDate().equals(date))
			{
				output.add(event);
			}
		}
		return output;
	}

	private ArrayList<IEvent> getEventsWithStartDateOrMore(Date startDate, ArrayList<IEvent> events)
	{
		//TODO: TEST THIS!
		ArrayList<IEvent> output = new ArrayList<IEvent>();
		for (IEvent event : events)
		{
			if (event.getStartDate().after(startDate) || event.getStartDate().equals(startDate)) // = if (! event.getStartDate().before(startDate))
			{
				output.add(event);
			}
		}
		return output;
	}

	private void sortEvents(ArrayList<IEvent> inputList)
	{
		IEvent[] arrayOfEvents = new IEvent[inputList.size()];
		inputList.toArray(arrayOfEvents);

		Arrays.sort(arrayOfEvents);

		inputList.clear();

		for(IEvent e: arrayOfEvents)
		{
			inputList.add(e);
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
