/**
 * Calendar framework
 */
package app;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Lukas Keller
 * @author Renato Corti
 *
 */
public class User 
{
	private ArrayList<Calendar> calendars;
	private String name;

	/**
	 * Default constructor
	 * @param name The name of the user
	 */
	public User(String name)
	{
		// TODO: Prevent creating an user if the name is already used
		this.name = name;
		this.calendars = new ArrayList<Calendar>();
	}

	public Calendar createNewCalendar(String nameOfCalendar)
	{
		Calendar newCalendar = new Calendar(this,nameOfCalendar);
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

	class UserAlreadyExistsException extends Exception
	{
		public UserAlreadyExistsException(String user)
		{
			super(String.format("User %s already exists!", user));
		}
	}
}
