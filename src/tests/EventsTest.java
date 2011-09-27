/**
 * Calendar framework
 */
package tests;

import interfaces.IUser;

import java.text.ParseException;
import org.junit.*;
import ch.unibe.jexample.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;

import app.*;
import app.AppExceptions.*;

/**
 * @author Lukas Keller
 * @author Renato Corti
 * 
 */

@RunWith(JExample.class)
public class EventsTest extends TestTemplate
{
	private IUser userAlpha;

	@Test
	public App eventTest2() throws UsernameAlreadyExistException, UnknownUserException, AccessDeniedException
	{
		App app = new App();
		app.createUser("Alpha", "123");
		this.userAlpha= app.loginUser("Alpha", "123");
		return app;
	}

	@Given("eventTest2")
	public App newNameOfEventShouldBeAreWeDead(App app) throws AccessDeniedException, InvalidDateException, UnknownCalendarException, UnknownEventException, ParseException, CalendarIsNotUniqueException
	{
		this.userAlpha.createNewCalendar("MyCalendar");
		this.userAlpha.createPrivateEvent("MyCalendar", "What happen after year 1999?", this.stringParseToDate("01.01.2000"), this.stringParseToDate("2.01.2000"));

		Calendar myCalendar=this.userAlpha.getCalendar("MyCalendar");

		this.userAlpha.editEventName("MyCalendar", "What happen after year 1999?", this.stringParseToDate("01.01.2000"), "Are we dead?");

		Event milleniumEvent=myCalendar.getEvent("Are we dead?", this.stringParseToDate("01.01.2000"));

		assertEquals(milleniumEvent.getEventName(),"Are we dead?");

		return app;
	}

	@Given("eventTest2")
	public App newStartDateOfEventShouldBe01_08_2011(App app) throws AccessDeniedException, InvalidDateException, UnknownCalendarException, UnknownEventException, ParseException, CalendarIsNotUniqueException
	{
		this.userAlpha.createNewCalendar("MyCalendar");
		this.userAlpha.createPrivateEvent("MyCalendar", "NationalDay", this.stringParseToDate("01.07.2011"), this.stringParseToDate("01.08.2011"));

		Calendar myCalendar=this.userAlpha.getCalendar("MyCalendar");

		this.userAlpha.editEventStartDate("MyCalendar", "NationalDay", this.stringParseToDate("01.07.2011"), this.stringParseToDate("01.08.2011"));

		Event nationalDayEvent=myCalendar.getEvent("NationalDay", this.stringParseToDate("01.08.2011"));

		assertEquals(nationalDayEvent.getStartDate(),this.stringParseToDate("01.08.2011"));

		return app;
	}

	@Given("eventTest2")
	public App newEndDateOfEventShouldBe01_08_2011(App app) throws AccessDeniedException, InvalidDateException, UnknownCalendarException, UnknownEventException, ParseException, CalendarIsNotUniqueException
	{
		this.userAlpha.createNewCalendar("MyCalendar");
		this.userAlpha.createPrivateEvent("MyCalendar", "Holiday", this.stringParseToDate("01.07.2011"), this.stringParseToDate("01.08.2011"));

		Calendar myCalendar=this.userAlpha.getCalendar("MyCalendar");

		this.userAlpha.editEventEndDate("MyCalendar", "Holiday", this.stringParseToDate("01.07.2011"), this.stringParseToDate("01.09.2011"));

		Event holidayEvent=myCalendar.getEvent("Holiday", this.stringParseToDate("01.07.2011"));

		assertEquals(holidayEvent.getEndDate(),this.stringParseToDate("01.09.2011"));

		return app;
	}

	@Given("eventTest2")
	public App newVisibilityShouldBePublic(App app) throws AccessDeniedException, InvalidDateException, UnknownCalendarException, UnknownEventException, ParseException, CalendarIsNotUniqueException
	{
		this.userAlpha.createNewCalendar("MyCalendar");
		this.userAlpha.createPrivateEvent("MyCalendar", "Public Event", this.stringParseToDate("01.07.2011"), this.stringParseToDate("01.08.2011"));

		Calendar myCalendar=this.userAlpha.getCalendar("MyCalendar");

		this.userAlpha.editEventStateToPublic("MyCalendar", "Public Event", this.stringParseToDate("01.07.2011"));

		Event publicEvent=myCalendar.getEvent("Public Event", this.stringParseToDate("01.07.2011"));

		assertTrue(publicEvent.isPublic());

		return app;
	}

	@Given("eventTest2")
	public App newVisibilityShouldBePrivate(App app) throws AccessDeniedException, InvalidDateException, UnknownCalendarException, UnknownEventException, ParseException, CalendarIsNotUniqueException
	{
		this.userAlpha.createNewCalendar("MyCalendar");
		this.userAlpha.createPublicEvent("MyCalendar", "Secret mission", this.stringParseToDate("01.07.2011"), this.stringParseToDate("01.08.2011"));

		Calendar myCalendar=this.userAlpha.getCalendar("MyCalendar");

		this.userAlpha.editEventStateToPrivate("MyCalendar", "Secret mission", this.stringParseToDate("01.07.2011"));

		Event missionEvent=myCalendar.getEvent("Secret mission", this.stringParseToDate("01.07.2011"));

		assertTrue(missionEvent.isPrivate());

		return app;
	}
}
