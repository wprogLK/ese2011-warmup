/**
 * 
 */
package app;

import java.util.Date;

/**
 * @author Lukas Keller
 *
 */
public abstract class AppExceptions
{
	
	///////////////
	//APP//
	///////
	public static class UnknownUserException extends Exception
	{
		public UnknownUserException(String userName)
		{
			super(String.format("The user %s doesn't exist!", userName));
		}
	}
	
	
	public static class UserNameAlreadyExistException extends Exception
	{
		public UserNameAlreadyExistException(String userName)
		{
			super(String.format("The username %s already exist! Please chose another username!", userName));
		}
	}
	
	public static class NoUserExistException extends Exception
	{
		public NoUserExistException()
		{
			super("No user exist!");
		}
	}
	
	
	
	
	/////////////////////
	//CALENDAR//
	////////////
	public static class NoAccessToCalendarException extends Exception
	{
		public NoAccessToCalendarException(Calendar calendar)
		{
			super(String.format("You are not allowed to create, edit or delete events of the calender %s!",calendar.getOwner().getName()));
		}
	}
	////////////
	//USER//
	//////////
	public static class UnknownCalendarException extends Exception
	{
		public UnknownCalendarException(String calendarName)
		{
			super(String.format("The calendar %s doesn't exist!", calendarName));
		}
	}
	
	////////////
	//EVENT///
	//////////
	
	public static class UnknownEventException extends Exception
	{
		public UnknownEventException(String eventName, Date startDate)
		{
			super(String.format("The event %s with the start date %c doesn't exist!", eventName, startDate));
		}
	}
}
