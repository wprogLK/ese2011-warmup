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

public abstract class AppExceptions
{

	/**
	 * If {@code username} is not in the database.
	 */
	public static class UnknownUserException extends Exception
	{
		public UnknownUserException(String username)
		{
			super(String.format("The user \"%s\" does not exist!", username));
		}
	}

	/**
	 * If {@code username} is already in the database.
	 */
	public static class UsernameAlreadyExistException extends Exception
	{
		public UsernameAlreadyExistException(String username)
		{
			super(String.format("The user \"%s\" already exists! Please choose another name!", username));
		}
	}

	/**
	 * When passwords do not match up.
	 */
	public static class AccessDeniedException extends Exception
	{
		public AccessDeniedException(Calendar calendar)
		{
			super(String.format("User \"%s\" did not give you permission to create, edit or delete events in the calendar \"%s\"!", calendar.getOwner().getName(), calendar.getName()));
		}

		public AccessDeniedException(String username)
		{
			super(String.format("You provided user \"%s\" with an invalid password!", username));
		}
	}

	/**
	 * If the {@code user} has no calendar with such a name.
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
