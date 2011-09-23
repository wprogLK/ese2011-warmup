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

	public Event createPrivateEvent(String name, Date startDate, Date endDate, User user) throws AccessDeniedException, InvalidDateException
	{
		this.checkUserIsOwner(user);
		Event newEvent = new Event(name, startDate, endDate);
		this.privateEvents.add(newEvent);
		return newEvent;
	}

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

	////////////
	// EVENTS //
	////////////

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

	/** Used to get a list with public events from the specified calendar
	 * @param startDate Self-explanatory!
	 * @return All public events from a specified calendar that belong to the very same user.
	 * other than this one using this function.
	 * 
	 */
	public Iterator<Event> getAllPublicEventsStarting(Date startDate)
	{
		return this.getEventsWithStartDateOrMore(startDate, this.publicEvents).iterator();
	}

	/** Returns all (public and private) events from the specified calendar.
	 * It is not intended being used from a user, that is not the owner of the specified calendar
	 * since that will trigger an error. Use getAllPublicEventsDate instead.
	 * @param date Self-explanatory!
	 * @param user 
	 * @return All events in the calendar.
	 * @throws AccessDeniedException 
	 * 
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
	
	/** Used to get a list with public events occurring on a given date.
	 * @param date Self-explanatory!
	 * @return All public events from a specified calendar.
	 */
	public ArrayList<Event> getAllPublicEventsDate(Date date)
	{
		return this.getEventsWithDate(date, this.publicEvents);
	}

	/** Returns a single event.
	 * @param eventName Creates an ArrayList depending of the given events.
	 * @param startDate Self-explanatory!
	 * @return The desired event object to read, mess up (edit) or trash.
	 * @throws AccessDeniedException 
	 * @throws UnknownEventException 
	 * 
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

	/////////////////
	// EDIT EVENTS //
	/////////////////

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

	/////////////////////
	// PRIVATE METHODS //
	/////////////////////

	/** Creates an ArrayList depending of the given events.*/
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

	/** Creates an ArrayList depending of the given events.*/
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

	private void checkUserIsOwner(User user) throws AccessDeniedException
	{
		if (this.owner != user)
		{
			throw new AccessDeniedException(this);
		}
	}

	/**
	 * @param eventName Title of an event to identify it.
	 * @param startDate Self-explanatory!
	 * @throws UnknownEventException
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
