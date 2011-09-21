/**
 * 
 */
package app;

import java.util.Date;

/**
 * @author Lukas Keller
 *
 */
public class Event 
{
	private enum state 
	{
	 PUBLIC,
	 PRIVATE
	}
	
	private String name;
	private Date startDate;
	private Date endDate;
	private state state;
	
	/**
	 * Constructor for specific event with the state 'private'
	 * @param name
	 * @param startDate
	 * @param endDate
	 */
	public Event(String name, Date startDate, Date endDate)
	{
		this.name=name;
		this.startDate=startDate;
		this.endDate=endDate;
		
		this.state=state.PRIVATE;
	}
	
	/**
	 * Default constructor
	 */
	public Event()
	{
		this.name="unkown event";
		this.startDate=new Date();
		this.endDate=new Date();
		
		this.state=state.PRIVATE;
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
		return this.state==state.PRIVATE;
	}
	
	public boolean isPublic()
	{
		return this.state==state.PUBLIC;
	}
	
	
	///////////
	//SETTERS//
	///////////
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	public void setStartDate(Date startDate)
	{
		this.startDate=startDate;
	}
	
	public void setEndDate(Date endDate)
	{
		this.endDate=endDate;
	}
	
	public void setStatePublic()
	{
		this.state=state.PUBLIC;
	}
	
	public void setStatePrivate()
	{
		this.state=state.PRIVATE;
	}
}
