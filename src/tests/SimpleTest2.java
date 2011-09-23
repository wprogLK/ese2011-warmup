/**
 * Calendar framework
 */
package tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.*;
import app.*;
import app.AppExceptions.AccessDeniedException;
import app.AppExceptions.InvalidDateException;
import app.AppExceptions.UnknownCalendarException;
import app.AppExceptions.UnknownEventException;
import app.AppExceptions.*;
import ch.unibe.jexample.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;

/**
 * @author Lukas Keller
 * @author Renato Corti
 */

@RunWith(JExample.class)
public class SimpleTest2 
{
	private User userAlpha;

	@Test
	public App oneUserTest() throws UserNameAlreadyExistException
	{
		App app = new App();
		this.userAlpha = app.createUser("UserAlpha", "123");
		return app;
	}

	@Given("oneUserTest")
	public App userAlphaNameShouldBeUserAlpha(App app)
	{
		assertEquals(this.userAlpha.getName(),"UserAlpha");
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
		Calendar calendarAlpha = this.userAlpha.createNewCalendar("CalendarAlpha");
		assertEquals(calendarAlpha.getName(), "CalendarAlpha");
		return app;
	}

	@Given("alphaCalendarNameShouldBeCalendarAlpha")
	public App alphaCalendarOwnerShouldBeUserAlpha(App app) throws UnknownCalendarException
	{
		Calendar calendarAlpha = this.userAlpha.getCalendar("CalendarAlpha");
		assertEquals(calendarAlpha.getOwner(),this.userAlpha);
		return app;
	}

	@Given("alphaCalendarOwnerShouldBeUserAlpha")
	public App alphaCalendarShouldBeEmpty(App app) throws AccessDeniedException, UnknownCalendarException
	{
		Calendar calendarAlpha = this.userAlpha.getCalendar("CalendarAlpha");
		assertTrue(calendarAlpha.getAllEventsDate(this.stringParseToDate("1.1.1970"),this.userAlpha).isEmpty());
		return app;
	}

	@Given("alphaCalendarNameShouldBeCalendarAlpha")
	public App userAlphaShouldHaveOneCalendar(App app)
	{
		assertFalse(this.userAlpha.hasNoCalendar());
		return app;
	}

	@Given("alphaCalendarShouldBeEmpty")
	public App eventNameShouldBeParty(App app) throws AccessDeniedException, UnknownCalendarException, UnknownEventException, InvalidDateException
	{
		Calendar calendarAlpha = this.userAlpha.getCalendar("CalendarAlpha");
		calendarAlpha.createPrivateEvent("Party", this.stringParseToDate("22.09.2011"), this.stringParseToDate("29.09.2011"), this.userAlpha);
		assertFalse(calendarAlpha.getAllEventsDate(new Date(), this.userAlpha).isEmpty());
		Event eventParty = calendarAlpha.getEvent("Party", this.stringParseToDate("22.09.2011"));
		assertEquals(eventParty.getEventName(),"Party");
		return app;
	}

	@Given("eventNameShouldBeParty")
	public App eventStartDateShouldBe22_09_2011(App app) throws AccessDeniedException, UnknownCalendarException, UnknownEventException, InvalidDateException
	{
		Calendar calendarAlpha = this.userAlpha.getCalendar("CalendarAlpha");
		calendarAlpha.createPrivateEvent("Party", this.stringParseToDate("22.09.2011"), this.stringParseToDate("29.09.2011"), this.userAlpha);
		Event eventParty = calendarAlpha.getEvent("Party", this.stringParseToDate("22.09.2011"));
		assertEquals(eventParty.getStartDate(), this.stringParseToDate("22.09.2011"));
		return app;
	}

	@Given("eventStartDateShouldBe22_09_2011")
	public App eventEndDateShouldBe29_09_2011(App app) throws AccessDeniedException, UnknownCalendarException, UnknownEventException
	{
		Calendar calendarAlpha = this.userAlpha.getCalendar("CalendarAlpha");
		Event eventParty = calendarAlpha.getEvent("Party", this.stringParseToDate("22.09.2011"));
		assertEquals(eventParty.getEndDate(), this.stringParseToDate("29.09.2011"));
		return app;
	}

	@Given("alphaCalendarShouldBeEmpty")
	public App startDateShouldNotBeAfterEndDate(App app) throws AccessDeniedException, UnknownCalendarException
	{
		Calendar calendarAlpha = this.userAlpha.getCalendar("CalendarAlpha");
		try
		{
			calendarAlpha.createPrivateEvent("BackToTheFutureEvent", this.stringParseToDate("23.09.2011"), this.stringParseToDate("22.09.2011"), this.userAlpha);
			fail("InvalidDateException expected!");
		}
		catch(InvalidDateException e)
		{
			assertNotNull(e);
		}
		assertTrue(calendarAlpha.getAllEventsDate(this.stringParseToDate("1.1.1970"),this.userAlpha).isEmpty());
		return app;
	}

	@Given("eventNameShouldBeParty")
	public App deleteEventTest(App app) throws AccessDeniedException, UnknownEventException, UnknownCalendarException
	{
		// TODO: Not implemented
		userAlpha.getCalendar("CalendarAlpha").deleteEvent("Party", this.stringParseToDate("22.09.2011"));
		return app;
	}

	@Given("alphaCalendarNameShouldBeCalendarAlpha")
	public App createEventWithZeroDurationTest(App app)
	{
		// TODO: Not implemented
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
