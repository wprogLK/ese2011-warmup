package interfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import app.AppExceptions.UnknownCalendarException;
import app.Calendar;
import app.OnlyForTesting;
import app.User;
import app.AppExceptions.*;

public interface IUser 
{
	////////////////////
	//CALENDAR ACTIONS//
	////////////////////
	
	/** Creates a new {@link Calendar} for the specified user.
	 * @param nameOfCalendar The title of the calendar to be created.
	 * @return The created calendar.
	 * */
	public void createNewCalendar(String nameOfCalendar);

	/** Removes the calendar including all events in it from the user.
	 * @param nameOfCalendar The title the calendar was given at creation time.
	 * @throws UnknownCalendarException If the {@code user} has no calendar with such a name.
	 */
	public void deleteCalendar(String nameOfCalendar) throws UnknownCalendarException;

	/** Provides an {@link ArrayList} with all calendar titles created by the user.
	 * @return The string literals of the calendars as {@link ArrayList}.
	 */
	public ArrayList<String> getAllMyCalendarNames();

	/** Tells, if the user has any calendars.
	 * @return If the {@link Calendar} {@link ArrayList} is empty, {@code true} is returned, {@code false} in all other cases.
	 */
	public boolean hasNoCalendar();

	public ArrayList<IEvent> getMyCalendarAllEventsDate(String calendarName, Date date) throws UnknownCalendarException, AccessDeniedException;

	public Iterator<IEvent> getMyCalendarAllEventsStarting(String calendarName, Date startDate) throws UnknownCalendarException, AccessDeniedException;

	public Iterator<IEvent> getMyCalendarPublicEventsStarting(String calendarName, Date startDate) throws UnknownCalendarException;

	public ArrayList<IEvent> getMyCalendarPublicEventsDate(String calendarName, Date date) throws UnknownCalendarException;
	
	/////////////////
	//EVENT ACTIONS//
	/////////////////
	
	/** Creates a new private event in the given {@link Calendar}.
	 * @param name Title of the event to identify it.
	 * @param startDate Date of the event to begin.
	 * @param endDate Date of the event to end.
	 * @param user The {@link User} who owns this calendar.
	 * @throws AccessDeniedException 
	 * @throws InvalidDateException If the end date is placed before the start date.
	 * @throws UnknownCalendarException 
	 */
	public void createPrivateEvent(String calendarName, String eventName, Date startDate, Date endDate) throws AccessDeniedException, InvalidDateException, UnknownCalendarException;

	/** Creates a new public event in the given {@link Calendar}.
	 * @param name Title of the event to identify it.
	 * @param startDate Date of the event to begin.
	 * @param endDate Date of the event to end.
	 * @param user {@link User} 
	 * @throws AccessDeniedException 
	 * @throws InvalidDateException If the end date is placed before the start date.
	 * @throws UnknownCalendarException 
	 */
	public void createPublicEvent(String calendarName, String eventName, Date startDate, Date endDate) throws AccessDeniedException, InvalidDateException, UnknownCalendarException;
	
	public void editEventName(String calendarName, String eventName, Date startDate, String newEventName) throws AccessDeniedException, UnknownEventException;

	public void editEventStartDate(String calendarName, String eventName, Date startDate, Date newStartDate) throws AccessDeniedException, UnknownEventException;

	public void editEventEndDate(String calendarName, String eventName, Date startDate, Date newEndDate) throws AccessDeniedException, UnknownEventException;
	
	public void editEventStateToPublic(String calendarName, String eventName, Date startDate) throws AccessDeniedException, UnknownEventException;

	public void editEventStateToPrivate(String calendarName, String eventName, Date startDate) throws AccessDeniedException, UnknownEventException;

	/*
	 * Usually this function would never throw an exception
	 * because is should not be possible to get a calendar object
	 * without having an user object which in turn should only be handed over when providing a password.
	 * Nevertheless this function was added as a precautionary measure against unpleasant surprises.
	 */

	/** Deletes the event from the calendar.
	 * @param eventName Title of the event to identify it.
	 * @param startDate Date when the event to be deleted starts.
	 * @throws UnknownEventException If the event is not in the calendar.
	 * @throws AccessDeniedException
	 */
	public void deleteEvent(String calendarName, String eventName, Date startDate) throws AccessDeniedException, UnknownEventException;
	
	/** Provides an {@link Iterator} with all (public and private) events from the specified calendar, that begin at the given date {@code startDate}.
	 * @param startDate Starting point of events.
	 * @param user The {@link User} object of the owner since private events are returned as well.
	 * @return All events that are set to start at {@code startDate}.
	 * @throws AccessDeniedException 
	 * @throws UnknownCalendarException 
	 */
	public Iterator<IEvent> getAllEventsStarting(String calendarName, Date startDate) throws AccessDeniedException, UnknownCalendarException;
	
	/** Provides all (public and private) events at a given {@code date} from the specified calendar as an {@link ArrayList}.
	 * @param date Date form which to list all events.
	 * @param user This function assumes that only the owner of the calendar has the {@link User} object
	 * at his / her disposal (not the user name as string) because
	 * in order to get the {@link User} object, a password is required.
	 * @return All events in the calendar.
	 * @throws AccessDeniedException If used from a {@link User}, that does not own the specified calendar. Use {@link Calendar#getAllPublicEventsDate(Date)} instead.
	 * @throws UnknownCalendarException 
	 * @see Authentication#getUser(String, String)
	 */
	public ArrayList<IEvent> getAllEventsDate(String calendarName, Date date) throws AccessDeniedException, UnknownCalendarException;

	String getName();
	
	
	////////////////////
	//ONLY FOR TESTING//
	////////////////////
	
	/** Provides a {@link Calendar} object. It can be used to perform further operations on the calendar.
	 * 
	 * <p>
	 * <u><b>Warning</u></b>: This method is only for testing in the IUser interface. Normally the visibility is public but not implemented in the IUser interface!
	 * </p>
	 * @param calendarName The title the calendar was given at creation time.
	 * @return The calendar with the corresponding {@code calendarName}.
	 * @throws UnknownCalendarException If the {@code user} has no calendar with such a name.
	 */
	 @OnlyForTesting
	public Calendar getCalendar(String calendarName) throws UnknownCalendarException;

}
