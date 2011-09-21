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
	
	private ArrayList<Event> events;
	
	public Calendar(User owner, String name)
	{
		this.owner=owner;
		this.name=name;
	}
	
	public void createPrivateEvent(String name, Date startDate, Date endDate, User user) throws NoAccessToCalendarException
	{
		if(this.owner.equals(user))
		{
			Event newEvent=new Event(name, startDate, endDate);
			
			this.events.add(newEvent);
		}
		else
		{
			throw new NoAccessToCalendarException(this);
		}
	}
	
	public void createPublicEvent(String name, Date startDate, Date endDate, User user) throws NoAccessToCalendarException
	{
		if(this.owner.equals(user))
		{
			Event newEvent=new Event(name, startDate, endDate);
			newEvent.setStatePublic();
			
			this.events.add(newEvent);
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
	
	private class NoAccessToCalendarException extends Exception
	{
		public NoAccessToCalendarException(Calendar calendar)
		{
			super(String.format("You are not allowed to create, edit or delete events of the calender %s!",calendar.getOwner().getName()));
		}
	}
}


