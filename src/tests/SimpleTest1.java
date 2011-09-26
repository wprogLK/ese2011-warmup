/**
 * Calendar framework
 */
package tests;

import interfaces.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.*;
import org.junit.runner.RunWith;

import app.App;
import app.AppExceptions.*;
import app.AppExceptions;
import app.Calendar;
import app.Event;
import ch.unibe.jexample.*;
import static org.junit.Assert.*;

/**
 * @author Lukas Keller
 * @author Renato Corti
 *
 */

//FIXME: Rename SimpleTest1 to something like User-specific Tests
@RunWith(JExample.class)
public class SimpleTest1 extends TestTemplate
{

	IUser userAlpha;
	IUser userBeta;

	@Test
	public App simpleTest1()
	{
		App app = new App();
		return app;
	}
	
	@Test
	/**
	 * Only for a better coverage ;-)
	 */
	public void createAppExceptionsClass()
	{
		new AppExceptions();
	}

	@Given("simpleTest1")
	public App userAlphaNameShouldBeAlpha(App app) throws UsernameAlreadyExistException, UnknownUserException, AccessDeniedException
	{
		app.createUser("Alpha", "123");
		this.userAlpha=app.loginUser("Alpha", "123");
		
		assertEquals(userAlpha.getName(), "Alpha");
		return app;
	}

	@Given("userAlphaNameShouldBeAlpha")
	public App preventCreatingUserWithAlreadyExistingNameTest(App app) throws UnknownUserException
	{
		try
		{
			app.createUser("Alpha", "456");
			fail("UsernameAlreadyExistException expected!");
		}
		catch (UsernameAlreadyExistException e)
		{
			assertNotNull(e);
		}
		
		try 
		{
			app.loginUser("Alpha", "456");
			fail("AccessDeniedException expected!");
		} 
		catch (AccessDeniedException e) 
		{
			assertNotNull(e);
		}
		
		return app;
	}

	@Given("userAlphaNameShouldBeAlpha")
	public App shouldNotRetrieveNonExistentUser(App app) throws AccessDeniedException
	{
		IUser nonExistentUser = null;
		try
		{
			nonExistentUser = app.loginUser("Beta", "abc");
			fail("UnknownUserException expected!");
		}
		catch (UnknownUserException e)
		{
			assertNotNull(e);
		}
		assertNull(nonExistentUser);
		return app;
	}

	@Given("userAlphaNameShouldBeAlpha")
	public App shouldNotRetrieveCalendarOrEventsFromFictitiousUser(App app) throws UnknownCalendarException, AccessDeniedException, ParseException
	{
		ArrayList<String> fictiousCalendarNames = null;

		try
		{
			fictiousCalendarNames = app.getAllCalendarsNamesFromUser("Beta");
			fail("UnknownUserException expected!");
		}
		catch (UnknownUserException e)
		{
			assertNotNull(e);
		}
		assertNull(fictiousCalendarNames);

		try
		{
			app.getUsersCalendarPublicEvents("Beta", "Fictitious Calendar", stringParseToDate("23.9.2011"));
			fail("UnknownUserException expected!");
		}
		catch (UnknownUserException e)
		{
			assertNotNull(e);
		}
		assertNull(fictiousCalendarNames);

		try
		{
			app.getUsersCalendarPublicEventsOverview("Beta", "Fictitious Calendar", stringParseToDate("23.9.2011"));
			fail("UnknownUserException expected!");
		}
		catch (UnknownUserException e)
		{
			assertNotNull(e);
		}
		assertNull(fictiousCalendarNames);

		return app;
	}

	@Given("userAlphaNameShouldBeAlpha")
	public App calendarOwnerShouldBeUserAlpha(App app) throws UnknownCalendarException
	{
		userAlpha.createNewCalendar("My calendar");
		Calendar myCalendar;
		
		myCalendar = this.userAlpha.getCalendar("My calendar");

		assertEquals(myCalendar.getOwner(), userAlpha);
		return app;
	}

	@Given("calendarOwnerShouldBeUserAlpha")
	public App deleteCalendarFromUserAlpha(App app) throws UnknownCalendarException, AccessDeniedException, InvalidDateException, ParseException
	{
		userAlpha.createNewCalendar("Short-living calendar");
		userAlpha.createPrivateEvent("Short-living calendar", "My private event", this.stringParseToDate("22.01.2011"), this.stringParseToDate("22.08.2011"));
		userAlpha.createPublicEvent("Short-living calendar", "My public event", this.stringParseToDate("23.01.2011"), this.stringParseToDate("23.08.2011"));

		userAlpha.deleteCalendar("Short-living calendar");
		
		try
		{
			userAlpha.getCalendar("Short-living calendar");
			fail("UnknownCalendarException expected!");
		}
		catch (UnknownCalendarException e)
		{
			assertNotNull(e);
		}

		assertEquals(1, userAlpha.getAllMyCalendarNames().size());
		return app;
	}

	@Given("calendarOwnerShouldBeUserAlpha")
	public App eventShouldBePrivate (App app) throws UnknownCalendarException, AccessDeniedException, InvalidDateException, UnknownEventException, ParseException
	{
		this.userAlpha.createPrivateEvent("My calendar", "My private event", this.stringParseToDate("22.01.2011"), this.stringParseToDate("22.08.2011"));
		
		Event myPrivateEvent=this.userAlpha.getCalendar("My calendar").getEvent("My private event",this.stringParseToDate("22.01.2011"));
		
		assertTrue(myPrivateEvent.isPrivate());
		return app;
	}

	@Given("calendarOwnerShouldBeUserAlpha")
	public App eventShouldBePublic(App app) throws UnknownCalendarException, AccessDeniedException, InvalidDateException, UnknownEventException, ParseException
	{
		this.userAlpha.createPublicEvent("My calendar", "My public event", this.stringParseToDate("23.01.2011"), this.stringParseToDate("23.08.2011"));
		
		Event myPublicEvent=this.userAlpha.getCalendar("My calendar").getEvent("My public event",this.stringParseToDate("23.01.2011"));
		
		assertTrue(myPublicEvent.isPublic());
		return app;
	}

	@Given("userAlphaNameShouldBeAlpha")
	public App deleteUserAlpha(App app) throws AccessDeniedException
	{
		IUser ghostUser = null;
		try
		{
			app.deleteUser("Alpha", "123");
			ghostUser = app.loginUser("Alpha", "123");
			fail("UnknownUserException expected!");
		}
		catch (UnknownUserException e)
		{
			assertNotNull(e);
		}
		assertNull(ghostUser);
		return app;
	}

	@Given("eventShouldBePrivate")
	public App userBetaShouldNotAccessForeignUserAcount(App app) throws UsernameAlreadyExistException, UnknownUserException, AccessDeniedException
	{
		app.createUser("Beta", "abc");
		
		this.userBeta=app.loginUser("Beta", "abc");
		
		IUser hackedAccount = null;
		try
		{
			hackedAccount = app.loginUser("Alpha", "***");
			fail("Unauthorized access permitted!");
		}
		catch(Exception e)
		{
			assertNotNull(e);
		}
		assertNull(hackedAccount);
		return app;
	}

	@Given("userBetaShouldNotAccessForeignUserAcount")
	public App changeUserPasswordTest(App app) throws UnknownUserException, AccessDeniedException
	{
		app.changePassword("Beta", "abc", "xyz");
		IUser userBetaNew = app.loginUser("Beta", "xyz");
		assertEquals(this.userBeta, userBetaNew);
		return app;
	}

	@Given("eventShouldBePublic")
	public App userBetaShouldSeePublicEventsFromForeignCalendar(App app) throws UnknownUserException, UnknownCalendarException, AccessDeniedException, ParseException
	{
		Iterator<IEvent> iteratorPublicEvents = app.getUsersCalendarPublicEvents("Alpha", "My calendar", this.stringParseToDate("22.01.2011"));
		
		int i = 0;
		while(iteratorPublicEvents.hasNext())
		{
			IEvent eventInQuestion = iteratorPublicEvents.next();
			assertEquals(eventInQuestion.getEventName(), "My public event");
			assertEquals(eventInQuestion.getStartDate(), this.stringParseToDate("23.01.2011"));
			assertEquals(eventInQuestion.getEndDate(), this.stringParseToDate("23.08.2011"));
			i++;
		}
		assertEquals(1, i);

		ArrayList<IEvent> arrayListPublicEvents = app.getUsersCalendarPublicEventsOverview("Alpha", "My calendar", this.stringParseToDate("22.01.2011"));
		assertEquals(1, arrayListPublicEvents.size());
		return app;
	}

	@Given("eventShouldBePrivate")
	public App invalidCalendarTest(App app)
	{
		Calendar calendarZero = null;
		try
		{
			calendarZero = userAlpha.getCalendar("No calendar");
		}
		catch(UnknownCalendarException e)
		{
			assertNotNull(e);
		}
		assertNull(calendarZero);
		return app;
	}

	@Given("eventShouldBePrivate")
	public App invalidEventStringButValidDateTest(App app) throws UnknownCalendarException, AccessDeniedException, ParseException
	{
		Calendar calendarAlpha = userAlpha.getCalendar("My calendar");
		Event unrealEvent = null;
		try
		{
			unrealEvent = calendarAlpha.getEvent("No event", this.stringParseToDate("22.01.2011"));
			fail("Expected UnknownEventException!");
		}
		catch(UnknownEventException e)
		{
			assertNotNull(e);
		}
		assertNull(unrealEvent);
		return app;
	}

	@Given("eventShouldBePrivate")
	public App validEventStringButInvalidDateTest(App app) throws UnknownCalendarException, AccessDeniedException, ParseException
	{
		Calendar calendarAlpha = userAlpha.getCalendar("My calendar");
		Event phantomEvent = null;
		try
		{
			phantomEvent = calendarAlpha.getEvent("My public event", this.stringParseToDate("20.01.2011"));
			fail("Expected UnknownEventException!");
		}
		catch(UnknownEventException e)
		{
			assertNotNull(e);
		}
		assertNull(phantomEvent);
		return app;
	}


}
