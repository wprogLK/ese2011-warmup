/**
 * Calendar framework
 */
package tests;

import interfaces.IEvent;
import interfaces.IUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.junit.*;
import ch.unibe.jexample.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;

import app.*;
import app.AppExceptions.*;

/**
 * @author Lukas Keller
 * @author Renato Corti
 */

//FIXME: Rename SimpleTest2 to something like Calendar-specific tests
@RunWith(JExample.class)
public class SimpleTest2
{
	private IUser userAlpha;
	private Calendar calendarAlpha;

	@Test
	public App oneUserTest() throws UsernameAlreadyExistException, UnknownUserException, AccessDeniedException
	{
		App app = new App();
		app.createUser("Alpha", "123");
		this.userAlpha= app.loginUser("Alpha", "123");
		return app;
	}

	@Given("oneUserTest")
	public App userAlphaNameShouldBeUserAlpha(App app)
	{
		assertEquals(this.userAlpha.getName(), "Alpha");
		return app;
	}

	@Given("userAlphaNameShouldBeUserAlpha")
	public App userAlphaShouldHaveNoCalendars(App app)
	{
		assertTrue(this.userAlpha.hasNoCalendar());
		return app;
	}

	@Given("userAlphaShouldHaveNoCalendars")
	public App alphaCalendarNameShouldBeCalendarAlpha(App app) throws UnknownCalendarException
	{
		this.userAlpha.createNewCalendar("CalendarAlpha");
		this.calendarAlpha=this.userAlpha.getCalendar("CalendarAlpha");
		assertEquals(calendarAlpha.getName(), "CalendarAlpha");
		return app;
	}

	@Given("alphaCalendarNameShouldBeCalendarAlpha")
	public App alphaCalendarOwnerShouldBeUserAlpha(App app)
	{
		assertEquals(calendarAlpha.getOwner(), this.userAlpha);
		return app;
	}

	@Given("alphaCalendarOwnerShouldBeUserAlpha")
	public App alphaCalendarShouldBeEmpty(App app) throws AccessDeniedException, UnknownCalendarException
	{
		ArrayList<IEvent> allEvents=this.userAlpha.getAllEventsDate("CalendarAlpha",this.stringParseToDate("1.1.1970")); 
		assertTrue(allEvents.isEmpty());
		return app;
	}

	@Given("alphaCalendarNameShouldBeCalendarAlpha")
	public App userAlphaShouldHaveOneCalendar(App app)
	{
		assertFalse(this.userAlpha.hasNoCalendar());
		return app;
	}

	@Given("alphaCalendarShouldBeEmpty")
	public App eventNameShouldBeParty(App app) throws AccessDeniedException, UnknownEventException, InvalidDateException, UnknownCalendarException
	{
		
		this.userAlpha.createPrivateEvent("CalendarAlpha", "Party", this.stringParseToDate("22.09.2011"),  this.stringParseToDate("29.09.2011"));
		
		assertFalse(calendarAlpha.getAllEventsDate(this.stringParseToDate("1.1.1970")).isEmpty());
		Event eventParty = calendarAlpha.getEvent("Party", this.stringParseToDate("22.09.2011"));
		assertEquals(eventParty.getEventName(), "Party");
		return app;
	}

	@Given("eventNameShouldBeParty")
	public App eventStartDateShouldBe22_09_2011(App app) throws AccessDeniedException, UnknownEventException
	{
		Event eventParty = calendarAlpha.getEvent("Party", this.stringParseToDate("22.09.2011"));
		assertEquals(eventParty.getStartDate(), this.stringParseToDate("22.09.2011"));
		return app;
	}

	@Given("eventStartDateShouldBe22_09_2011")
	public App eventEndDateShouldBe29_09_2011(App app) throws AccessDeniedException, UnknownEventException
	{
		Event eventParty = calendarAlpha.getEvent("Party", this.stringParseToDate("22.09.2011"));
		assertEquals(eventParty.getEndDate(), this.stringParseToDate("29.09.2011"));
		return app;
	}

	@Given("alphaCalendarShouldBeEmpty")
	public App startDateShouldNotBeAfterEndDate(App app) throws AccessDeniedException, UnknownCalendarException
	{
		try
		{
			this.userAlpha.createPrivateEvent("CalendarAlpha","BackToTheFutureEvent", this.stringParseToDate("23.09.2011"), this.stringParseToDate("22.09.2011"));
			fail("InvalidDateException expected!");
		}
		catch(InvalidDateException e)
		{
			assertNotNull(e);
		}
		assertTrue(calendarAlpha.getAllEventsDate(this.stringParseToDate("1.1.1970")).isEmpty());
		return app;
	}

	@Given("userAlphaShouldHaveOneCalendar")
	public App deleteEventsTest(App app) throws AccessDeniedException, UnknownEventException, InvalidDateException, UnknownCalendarException
	{
		this.userAlpha.createPrivateEvent("CalendarAlpha", "My Event", this.stringParseToDate("23.09.2011"), this.stringParseToDate("23.09.2011"));
		this.userAlpha.createPublicEvent("CalendarAlpha", "Our Event", this.stringParseToDate("23.09.2011"), this.stringParseToDate("23.09.2011"));

		calendarAlpha.deleteEvent("My Event", this.stringParseToDate("23.09.2011"));
		calendarAlpha.deleteEvent("Our Event", this.stringParseToDate("23.09.2011"));

		// alphaCalendarShouldBeEmpty
		assertTrue(calendarAlpha.getAllEventsDate(this.stringParseToDate("1.1.1970")).isEmpty());

		return app;
	}

	@Given("alphaCalendarNameShouldBeCalendarAlpha")
	public App createEventWithZeroDurationTest(App app)
	{
		// TODO: Not implemented
		return app;
	}

	@Given("userAlphaShouldHaveNoCalendars")
	public App shouldGiveCalendarListOfUserAlphaTest(App app) throws AccessDeniedException, InvalidDateException, UnknownUserException, UnknownCalendarException
	{
		Calendar newCalendar = this.userAlpha.createNewCalendar("New calendar");
		newCalendar.createPrivateEvent("Private Event", this.stringParseToDate("21.9.2011"), this.stringParseToDate("21.9.2011"), this.userAlpha);
		newCalendar.createPublicEvent("Public Event", this.stringParseToDate("21.9.2011"), this.stringParseToDate("21.9.2011"), this.userAlpha);

		Calendar secondCalendar = this.userAlpha.createNewCalendar("Second Calendar");
		secondCalendar.createPrivateEvent("Private Event", this.stringParseToDate("22.9.2011"), this.stringParseToDate("22.9.2011"), this.userAlpha);
		secondCalendar.createPublicEvent("Public Event", this.stringParseToDate("22.9.2011"), this.stringParseToDate("22.9.2011"), this.userAlpha);

		Calendar otherCalendar = this.userAlpha.createNewCalendar("Other calendar");
		otherCalendar.createPrivateEvent("Private Event", this.stringParseToDate("23.9.2011"), this.stringParseToDate("23.9.2011"), this.userAlpha);
		otherCalendar.createPublicEvent("Public Event", this.stringParseToDate("23.9.2011"), this.stringParseToDate("23.9.2011"), this.userAlpha);

		ArrayList<String> alphasCalendarListViaUser = userAlpha.getAllMyCalendarNames();
		ArrayList<String> alphasCalendarListViaApp = app.getAllCalendarsNamesFromUser("Alpha");
		assertEquals(alphasCalendarListViaUser, alphasCalendarListViaApp);

		assertEquals(3, alphasCalendarListViaUser.size());
		assertEquals(3, alphasCalendarListViaApp.size());
		assertEquals("New calendar", alphasCalendarListViaUser.get(0));
		assertEquals("New calendar", alphasCalendarListViaApp.get(0));
		assertEquals("Second Calendar", alphasCalendarListViaUser.get(1));
		assertEquals("Second Calendar", alphasCalendarListViaApp.get(1));
		assertEquals("Other calendar", alphasCalendarListViaUser.get(2));
		assertEquals("Other calendar", alphasCalendarListViaApp.get(2));

		this.userAlpha.createNewCalendar("New calendar");
		
		this.userAlpha.createPrivateEvent("New calendar", "Private Event", this.stringParseToDate("21.9.2011"), this.stringParseToDate("21.9.2011"));
		this.userAlpha.createPublicEvent("New calendar", "Public Event",this.stringParseToDate("21.9.2011"), this.stringParseToDate("21.9.2011"));

		
		this.userAlpha.createNewCalendar("Second Calendar");
		
		this.userAlpha.createPrivateEvent("Second Calendar", "Private Event",this.stringParseToDate("22.9.2011"), this.stringParseToDate("22.9.2011"));
		this.userAlpha.createPublicEvent("Second Calendar", "Public Event",this.stringParseToDate("22.9.2011"), this.stringParseToDate("22.9.2011"));
		

		this.userAlpha.createNewCalendar("Other calendar");
		
		this.userAlpha.createPrivateEvent("Other calendar", "Private Event", this.stringParseToDate("23.9.2011"), this.stringParseToDate("23.9.2011"));
		this.userAlpha.createPrivateEvent("Other calendar", "Public Event", this.stringParseToDate("23.9.2011"), this.stringParseToDate("23.9.2011"));

		
		ArrayList<String> AlphasCalendarListViaUser = userAlpha.getAllMyCalendarNames();
		ArrayList<String> AlphasCalendarListViaApp = app.getAllCalendarsNamesFromUser("Alpha");
		assertEquals(AlphasCalendarListViaUser, AlphasCalendarListViaApp);

		assertEquals(3, AlphasCalendarListViaUser.size());
		assertEquals(3, AlphasCalendarListViaApp.size());
		assertEquals("New calendar", AlphasCalendarListViaUser.get(0));
		assertEquals("New calendar", AlphasCalendarListViaApp.get(0));
		assertEquals("Second Calendar", AlphasCalendarListViaUser.get(1));
		assertEquals("Second Calendar", AlphasCalendarListViaApp.get(1));
		assertEquals("Other calendar", AlphasCalendarListViaUser.get(2));
		assertEquals("Other calendar", AlphasCalendarListViaApp.get(2));

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
}
