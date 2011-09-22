/**
 * Calendar framework
 */
package app;

import java.util.Date;

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

	private String name;
	private Date startDate;
	private Date endDate;
	private State visibility;

	/**
	 * Constructor for specific event with the state 'private'
	 * @param name A precise name / description for the event
	 * @param startDate The begin of the mentioned event
	 * @param endDate The end of the mentioned event
	 */
	public Event(String name, Date startDate, Date endDate)
	{
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		
		this.visibility = State.PRIVATE;
	}

	/**
	 * Default constructor
	 */
	public Event()
	{
		this.name = "unkown event";
		this.startDate = new Date();
		this.endDate = new Date();
		
		this.visibility = State.PRIVATE;
	}

	///////////
	//GETTERS//
	///////////

	public String getName()
	{
		return this.name;
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

	///////////
	//SETTERS//
	///////////

	public void setName(String name)
	{
		this.name = name;
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
