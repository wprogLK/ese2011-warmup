/**
 * 
 */
package app;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Lukas Keller
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
		this.owner=owner;
		this.name=name;
		
		this.privateEvents=new ArrayList<Event>();
		this.publicEvents=new ArrayList<Event>();
	}
	
	public Event createPrivateEvent(String name, Date startDate, Date endDate, User user) throws NoAccessToCalendarException
	{
		if(this.owner.equals(user))
		{
			Event newEvent=new Event(name, startDate, endDate);
			
			this.privateEvents.add(newEvent);
			return newEvent;
		}
		else
		{
			throw new NoAccessToCalendarException(this);
		}
	}
	
	public Event createPublicEvent(String name, Date startDate, Date endDate, User user) throws NoAccessToCalendarException
	{
		if(this.owner.equals(user))
		{
			Event newEvent=new Event(name, startDate, endDate);
			newEvent.setStatePublic();
			
			this.publicEvents.add(newEvent);
			return newEvent;
		}
		else
		{
			throw new NoAccessToCalendarException(this);
		}
	}
	
	public User getOwner()
	{
		return this.owner;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	private class NoAccessToCalendarException extends Exception
	{
		public NoAccessToCalendarException(Calendar calendar)
		{
			super(String.format("You are not allowed to create, edit or delete events of the calender %s!",calendar.getOwner().getName()));
		}
	}
}


