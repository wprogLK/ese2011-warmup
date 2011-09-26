/**
 * Calendar framework
 */
package app;
import interfaces.IEvent;

import java.util.Date;
import app.AppExceptions.*;

/**
 * @author Lukas Keller
 * @author Renato Corti
 *
 */

public class Event implements IEvent
{
	private String eventName;
	private Date startDate;
	private Date endDate;
	private boolean isPrivate;
	
	/**
	 * Constructor for specific event with the state 'private'
	 * @param eventName A precise name / description for the event
	 * @param startDate The begin of the mentioned event
	 * @param endDate The end of the mentioned event
	 * @throws InvalidDateException If the end date is placed before the start date.
	 */
	public Event(String eventName, Date startDate, Date endDate) throws InvalidDateException
	{
		if (startDate.after(endDate))
		{
			throw new InvalidDateException(startDate, endDate);
		}

		this.eventName = eventName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isPrivate = true;
	}
	
	///////////
	//GETTERS//
	///////////
	
	@Override
	public String getEventName() 
	{
		return this.eventName;
	}

	@Override
	public Date getStartDate() 
	{
		return this.startDate;
	}

	@Override
	public Date getEndDate() 
	{
		return this.endDate;
	}

	@Override
	public boolean isPrivate() 
	{
		return this.isPrivate;
	}

	@Override
	public boolean isPublic() 
	{
		return !this.isPrivate;
	}

	///////////
	//SETTERS//
	///////////

	public void setEventName(String name)
	{
		this.eventName = name;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public void setStatePublic()
	{
		this.isPrivate=false;
	}

	public void setStatePrivate()
	{
		this.isPrivate=true;
	}
}
