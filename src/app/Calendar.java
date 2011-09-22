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

	public Event createPrivateEvent(String name, Date startDate, Date endDate, User user) throws AppExceptions.NoAccessToCalendarException
	{
		this.checkUserIsOwner(user);

			Event newEvent = new Event(name, startDate, endDate);
			
		this.privateEvents.add(newEvent);
		return newEvent;
	}

	public Event createPublicEvent(String name, Date startDate, Date endDate, User user) throws AppExceptions.NoAccessToCalendarException
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

	//////////
	//EVENTS//
	//////////
	public Iterator<Event> getAllEventsStarting(Date startDate, User user) throws AppExceptions.NoAccessToCalendarException
	{
		this.checkUserIsOwner(user);
		
		ArrayList<Event> eventList = new ArrayList<Event>();
		
		eventList.addAll(this.privateEvents);
		eventList.addAll(this.publicEvents);
		
		eventList = this.getEventsWithStartDateOrMore(startDate, eventList);
		
//		this.sortEvents(eventList);
		
		return eventList.iterator();
	}
	
	
	public Iterator<Event> getAllPublicEventsStarting(Date startDate)
	{
		return this.getEventsWithStartDateOrMore(startDate, this.publicEvents).iterator();
	}
	
	
	public ArrayList<Event> getAllEventsDate(Date date, User user) throws AppExceptions.NoAccessToCalendarException
	{
		this.checkUserIsOwner(user);
		
		ArrayList<Event> eventList = new ArrayList<Event>();
		
		eventList.addAll(this.privateEvents);
		eventList.addAll(this.publicEvents);
		
//		this.sortEvents(eventList);
		
		return this.getEventsWithDate(date,eventList);
	}
	
	
	public ArrayList<Event> getAllPublicEventsDate(Date date)
	{
		return this.getEventsWithDate(date, this.publicEvents);
	}
	
	public Event getEvent(String eventName, Date startDate) throws NoAccessToCalendarException, UnknownEventException
	{
		Iterator<Event> iteratorAllEvents = this.getAllEventsStarting(startDate, this.owner);
		Event currentEvent = null;
		
		while(iteratorAllEvents.hasNext())
		{
			currentEvent=iteratorAllEvents.next();
			
			if(currentEvent.getName().equals(eventName) && currentEvent.getStartDate().equals(startDate))
			{
				return currentEvent;
			}
		}
		
		throw new AppExceptions.UnknownEventException(eventName, startDate);
	}
	///////////////
	//EDIT EVENTS//
	///////////////
	
	
	public void editEventName(String eventName,Date startDate, User user, String newName) throws NoAccessToCalendarException, UnknownEventException
	{
		this.checkUserIsOwner(user);
		this.getEvent(eventName, startDate).setName(newName);
	}	
	
	public void editEventStartDate(String eventName,Date startDate, User user, Date newStartDate) throws NoAccessToCalendarException, UnknownEventException
	{
		this.checkUserIsOwner(user);
		this.getEvent(eventName, startDate).setStartDate(newStartDate);
	}	
	
	public void editEventEndDate(String eventName,Date startDate, User user, Date newEndDate) throws NoAccessToCalendarException, UnknownEventException
	{
		this.checkUserIsOwner(user);
		this.getEvent(eventName, startDate).setStartDate(newEndDate);
	}	
	
	public void editEventStateToPublic(String eventName,Date startDate, User user) throws NoAccessToCalendarException, UnknownEventException
	{
		this.checkUserIsOwner(user);
		this.getEvent(eventName, startDate).setStatePublic();
	}	
	
	public void editEventStateToPrivate(String eventName,Date startDate, User user) throws NoAccessToCalendarException, UnknownEventException
	{
		this.checkUserIsOwner(user);
		this.getEvent(eventName, startDate).setStatePrivate();
	}	
	
	//////////////////
	//PRIVATE METHOD//
	//////////////////
	
	private ArrayList<Event> getEventsWithDate(Date date, ArrayList<Event> events)
	{
		//TODO: TEST THIS!
		
		ArrayList<Event> output = new ArrayList<Event>();
		
		for(Event event:events)
		{
			if(event.getEndDate().after(date))
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
		
		for(Event event:events)
		{
			if(event.getStartDate().after(startDate) || event.getStartDate().equals(startDate))
			{
				output.add(event);
			}
		}
		
		return output;
	}
	
	private ArrayList<Event> sortEvents(ArrayList<Event> inputList)
	{
		ArrayList<Event> output = new ArrayList<Event>();
		
		//TODO
		
		return output;
	}
	

	
	private void checkUserIsOwner(User user) throws AppExceptions.NoAccessToCalendarException
	{
		if(this.owner != user)
		{
			throw new AppExceptions.NoAccessToCalendarException(this);
		}
	}
}
