/**
 * 
 */
package app;

import java.util.Date;

/**
 * @author Lukas Keller
 * @author Renato Corti
 *
 */
public abstract class AppExceptions
{

	/**
	 * If an username is not in the database.
	 */
	public static class UnknownUserException extends Exception
	{
		public UnknownUserException(String userName)
		{
			super(String.format("The user \"%s\" does not exist!", userName));
		}
	}

	/**
	 * If an username is already in the database.
	 */
	public static class UserNameAlreadyExistException extends Exception
	{
		public UserNameAlreadyExistException(String userName)
		{
			super(String.format("The user \"%s\" already exists! Please choose another name!", userName));
		}
	}

	/**
	 * If the database is empty.
	 */
	public static class NoUserExistException extends Exception
	{
		public NoUserExistException()
		{
			super("No user exist! Start creating a new user!");
		}
	}

	/**
	 * When passwords do not match up
	 */
	public static class AccessDeniedException extends Exception
	{
		public AccessDeniedException(Calendar calendar)
		{
			super(String.format("You are not allowed to create, edit or delete events in the calender \"%s\"!", calendar.getOwner().getName()));
		}

		public AccessDeniedException(String username)
		{
			super(String.format("You provided user \"%s\" with an invalid password!", username));
		}
	}

	/**
	 * If the calendar is inexistent.
	 */
	public static class UnknownCalendarException extends Exception
	{
		public UnknownCalendarException(String calendarName)
		{
			super(String.format("The calendar \"%s\" does not exist!", calendarName));
		}
	}

	/**
	 * If the event is not in the calendar.
	 */
	public static class UnknownEventException extends Exception
	{
		public UnknownEventException(String eventName, Date startDate)
		{
			super(String.format("The event \"%s\" with the start date \"%tc\" does not exist!", eventName, startDate));
		}
	}

	/**
	 * If the end date is placed before the start date.
	 */
	public static class InvalidDateException extends Exception
	{
		public InvalidDateException(Date startDate, Date endDate)
		{
			super(String.format("The start date \"%tc\" cannot begin after end date \"%tc\"!", startDate, endDate));
		}
	}
}
