/**
 * Calendar framework
 */
package tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
	private User userAlpha;
	private Calendar calendarAlpha;

	@Test
	public App oneUserTest() throws UserNameAlreadyExistException
	{
		App app = new App();
		this.userAlpha = app.createUser("Alpha", "123");
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
	public App alphaCalendarNameShouldBeCalendarAlpha(App app)
	{
		this.calendarAlpha = this.userAlpha.createNewCalendar("CalendarAlpha");
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
	public App alphaCalendarShouldBeEmpty(App app) throws AccessDeniedException
	{
		assertTrue(this.calendarAlpha.getAllEventsDate(this.stringParseToDate("1.1.1970"), this.userAlpha).isEmpty());
		return app;
	}

	@Given("alphaCalendarNameShouldBeCalendarAlpha")
	public App userAlphaShouldHaveOneCalendar(App app)
	{
		assertFalse(this.userAlpha.hasNoCalendar());
		return app;
	}

	@Given("alphaCalendarShouldBeEmpty")
	public App eventNameShouldBeParty(App app) throws AccessDeniedException, UnknownEventException, InvalidDateException
	{
		calendarAlpha.createPrivateEvent("Party", this.stringParseToDate("22.09.2011"), this.stringParseToDate("29.09.2011"), this.userAlpha);
		assertFalse(calendarAlpha.getAllEventsDate(new Date(), this.userAlpha).isEmpty());
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
	public App startDateShouldNotBeAfterEndDate(App app) throws AccessDeniedException
	{
		try
		{
			calendarAlpha.createPrivateEvent("BackToTheFutureEvent", this.stringParseToDate("23.09.2011"), this.stringParseToDate("22.09.2011"), this.userAlpha);
			fail("InvalidDateException expected!");
		}
		catch(InvalidDateException e)
		{
			assertNotNull(e);
		}
		assertTrue(calendarAlpha.getAllEventsDate(this.stringParseToDate("1.1.1970"), this.userAlpha).isEmpty());
		return app;
	}

	@Given("userAlphaShouldHaveOneCalendar")
	public App deleteEventsTest(App app) throws AccessDeniedException, UnknownEventException, InvalidDateException
	{
		calendarAlpha.createPrivateEvent("My Event", this.stringParseToDate("23.09.2011"), this.stringParseToDate("23.09.2011"), this.userAlpha);
		calendarAlpha.createPublicEvent("Our Event", this.stringParseToDate("23.09.2011"), this.stringParseToDate("23.09.2011"), this.userAlpha);

		calendarAlpha.deleteEvent("My Event", this.stringParseToDate("23.09.2011"));
		calendarAlpha.deleteEvent("Our Event", this.stringParseToDate("23.09.2011"));

		// alphaCalendarShouldBeEmpty
		assertTrue(calendarAlpha.getAllEventsDate(this.stringParseToDate("1.1.1970"), this.userAlpha).isEmpty());

		return app;
	}

	@Given("alphaCalendarNameShouldBeCalendarAlpha")
	public App createEventWithZeroDurationTest(App app)
	{
		// TODO: Not implemented
		return app;
	}

	@Given("userAlphaShouldHaveNoCalendars")
	public App shouldGiveCalendarListOfUserAlphaTest(App app) throws AccessDeniedException, InvalidDateException, UnknownUserException
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
