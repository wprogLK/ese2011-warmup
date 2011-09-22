/**
 * Calendar framework
 */
package app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.*;
import org.junit.runner.RunWith;

import app.AppExceptions.NoAccessToCalendarException;
import app.AppExceptions.UnknownCalendarException;
import app.AppExceptions.UnknownUserException;
import app.AppExceptions.UserNameAlreadyExistException;
import ch.unibe.jexample.*;
import static org.junit.Assert.*;

/**
 * @author Lukas Keller
 * @author Renato Corti
 *
 */

@RunWith(JExample.class)
public class SimpleTest1 
{

	@Test
	public App simpleTest1()
	{
		App app = new App();
	
		return app;
	}

	@Given("simpleTest1")
	public App userAlphaNameShouldBeAlpha(App app) throws UserNameAlreadyExistException
	{
		User userAlpha=app.createUser("Alpha");
		assertEquals(userAlpha.getName(),"Alpha");
		
		return app;
	}

	@Given("simpleTest1")
	public void preventCreatingUserWithAlreadyExistingNameTest()
	{
		User fakeUser = new User("Alpha");
		assertNotSame(fakeUser.getName(), "Alpha");
	}

	@Given("userAlphaNameShouldBeAlpha")
	public App calendarOwnerShouldBeUserAlpha(App app) throws UnknownUserException
	{
		User userAlpha=app.getUser("Alpha");
		Calendar myCalendar = userAlpha.createNewCalendar("My calendar");
		assertEquals(myCalendar.getOwner(),userAlpha);
		
		return app;
	}

	@Given("calendarOwnerShouldBeUserAlpha")
	public App eventShouldBePrivate (App app) throws UnknownUserException, UnknownCalendarException, NoAccessToCalendarException
	{
		User userAlpha=app.getUser("Alpha");
		Event myEvent=userAlpha.createPrivateEvent("My calendar", "My event", this.stringParseToDate("22.01.2011"), this.stringParseToDate("22.08.2011"));
		assertTrue(myEvent.isPrivate());
		return app;
	}

	@Given("calendarOwnerShouldBeUserAlpha")
	public App userBetaShouldNotAddAnEventToForeignCalendar(App app) throws UserNameAlreadyExistException, UnknownUserException, UnknownCalendarException
	{
		User userBeta = app.createUser("Beta");
		User userAlpha = app.getUser("Alpha");
		
		Calendar calendarAlpha=userAlpha.getCalendar("My calendar");
		
		Event betaEvent = null;
		try
		{
			betaEvent = calendarAlpha.createPrivateEvent("Not my event", new Date(), new Date(), userBeta);
			fail("Expected NoAccessToCalendarException!");
		}
		catch(Calendar.NoAccessToCalendarException e)
		{
			assertNotNull(e);
		}
		assertNull(betaEvent);
		return app;
	}
	
	
	private Date stringParseToDate(String strDate)
	{
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
 
        try 
        {
			return sdf.parse(strDate);
		} 
        catch (ParseException e) 
		{
			e.printStackTrace();
			return null;
		}
	}

	@Given("calendarOwnerShouldBeUserAlpha")
	public void createNewEventTest()
	{
		// TODO: Not implemented
	}

	@Given("calendarOwnerShouldBeUserAlpha")
	public void deleteEventTest()
	{
		// TODO: Not implemented
	}

	/**
	 * Test duration from 23:00 to 1:00 = 2 hours
	 */
	@Given("calendarOwnerShouldBeUserAlpha")
	public void createEventThatSpansOverMidnightTest()
	{
		// TODO: Not implemented
	}

	/**
	 * Test transformation from Sunday to Monday weekday
	 */
	@Given("calendarOwnerShouldBeUserAlpha")
	public void createEventThatSpansOverWeekendTest()
	{
		// TODO: Not implemented
	}

	/**
	 * End of the month (28|29|30|31). - 1.
	 */
	@Given("calendarOwnerShouldBeUserAlpha")
	public void createEventThatSpansOverEndOfMonthTest()
	{
		// TODO: Not implemented
	}

	/**
	 * 31.12.x to 01.01.x+1
	 */
	@Given("calendarOwnerShouldBeUserAlpha")
	public void createEventThatSpansOverSylvesterTest()
	{
		// TODO: Not implemented
	}
	/**
	 * Start time: 12:00, End time: 12:00
	 */
	@Given("calendarOwnerShouldBeUserAlpha")
	public void createEventWithZeroDurationTest()
	{
		// TODO: Not implemented
	}

	@Given("calendarOwnerShouldBeUserAlpha")
	public void createPrivateEventTest()
	{
		// TODO: Not implemented
	}

	@Given("calendarOwnerShouldBeUserAlpha")
	public void createPublicEventTest()
	{
		// TODO: Not implemented
	}

}
