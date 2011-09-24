/**
 * Calendar framework
 */
package app;
import java.util.Date;
import app.AppExceptions.*;

/**
 * @author Lukas Keller
 * @author Renato Corti
 *
 */

public class Event
{
	private enum State
	{
	 PUBLIC,
	 PRIVATE
	}

	private String eventName;
	private Date startDate;
	private Date endDate;
	private State visibility;

	/**
	 * Constructor for specific event with the state 'private'
	 * @param eventName A precise name / description for the event
	 * @param startDate The begin of the mentioned event
	 * @param endDate The end of the mentioned event
	 * @throws InvalidDateException 
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
		this.visibility = State.PRIVATE;
	}

	/////////////
	// GETTERS //
	/////////////

	public String getEventName()
	{
		return this.eventName;
	}
	
	public Date getStartDate()
	{
		return this.startDate;
	}

	public Date getEndDate()
	{
		return this.endDate;
	}

	public boolean isPrivate()
	{
		return (this.visibility == State.PRIVATE);
	}

	public boolean isPublic()
	{
		return (this.visibility == State.PUBLIC);
	}

	/////////////
	// SETTERS //
	/////////////

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
		this.visibility = State.PUBLIC;
	}

	public void setStatePrivate()
	{
		this.visibility = State.PRIVATE;
	}
}
