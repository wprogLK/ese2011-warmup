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
public class User 
{
	private ArrayList<Calendar> calendars;
	private String name;
	
	/**
	 * Default constructor
	 * @param name, the name of the user
	 */
	public User(String name)
	{
		this.name=name;
		this.calendars=new ArrayList<Calendar>();
	}
	
	public Calendar createNewCalendar(String nameOfCalendar)
	{
		Calendar newCalendar=new Calendar(this,nameOfCalendar);
		this.calendars.add(newCalendar);
		
		return newCalendar;
	}

	public String getName()
	{
		return this.name;
	}
	
	public Calendar getCalendar(int indexOfCalendar)
	{
		return this.calendars.get(indexOfCalendar);
	}
	
	
	
}
