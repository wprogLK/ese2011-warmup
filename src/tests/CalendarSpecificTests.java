/**
 * Calendar framework
 */
package tests;

import interfaces.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.*;
import ch.unibe.jexample.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;

import app.*;
import app.AppExceptions.UnknownUserException;
import app.AppExceptions.*;

/**
 * @author Lukas Keller
 * @author Renato Corti
 *
 */

@RunWith(JExample.class)
public class CalendarSpecificTests extends TestTemplate
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
	public App alphaCalendarNameShouldBeCalendarAlpha(App app) throws UnknownCalendarException, CalendarIsNotUniqueException
	{
		this.userAlpha.createNewCalendar("CalendarAlpha");
		this.calendarAlpha = this.userAlpha.getCalendar("CalendarAlpha");
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
	public App alphaCalendarShouldBeEmpty(App app) throws AccessDeniedException, UnknownCalendarException, ParseException
	{
		ArrayList<IEvent> allEvents = this.userAlpha.getMyCalendarAllEventsAtDate("CalendarAlpha", this.stringParseToDate("1.1.1970"));
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
	public App eventNameShouldBeParty(App app) throws AccessDeniedException, UnknownEventException, InvalidDateException, UnknownCalendarException, ParseException
	{

		this.userAlpha.createPrivateEvent("CalendarAlpha", "Party", this.stringParseToDate("22.09.2011"),  this.stringParseToDate("29.09.2011"));

		assertFalse(calendarAlpha.getAllEventsAtDate(this.stringParseToDate("1.1.1970")).isEmpty());
		Event eventParty = calendarAlpha.getEvent("Party", this.stringParseToDate("22.09.2011"));
		assertEquals(eventParty.getEventName(), "Party");
		return app;
	}

	@Given("eventNameShouldBeParty")
	public App eventStartDateShouldBe22_09_2011(App app) throws UnknownEventException, ParseException
	{
		Event eventParty = calendarAlpha.getEvent("Party", this.stringParseToDate("22.09.2011"));
		assertEquals(eventParty.getStartDate(), this.stringParseToDate("22.09.2011"));
		return app;
	}

	@Given("eventStartDateShouldBe22_09_2011")
	public App eventEndDateShouldBe29_09_2011(App app) throws UnknownEventException, ParseException
	{
		Event eventParty = calendarAlpha.getEvent("Party", this.stringParseToDate("22.09.2011"));
		assertEquals(eventParty.getEndDate(), this.stringParseToDate("29.09.2011"));
		return app;
	}

	@Given("alphaCalendarShouldBeEmpty")
	public App startDateShouldNotBeAfterEndDate(App app) throws AccessDeniedException, UnknownCalendarException, ParseException
	{
		try
		{
			this.userAlpha.createPrivateEvent("CalendarAlpha", "BackToTheFutureEvent", this.stringParseToDate("23.09.2011"), this.stringParseToDate("22.09.2011"));
			fail("InvalidDateException expected!");
		}
		catch(InvalidDateException e)
		{
			assertNotNull(e);
		}
		assertTrue(calendarAlpha.getAllEventsAtDate(this.stringParseToDate("1.1.1970")).isEmpty());
		return app;
	}

	@Given("userAlphaShouldHaveOneCalendar")
	public App deleteEventsTest(App app) throws AccessDeniedException, UnknownEventException, InvalidDateException, UnknownCalendarException, ParseException
	{
		this.userAlpha.createPrivateEvent("CalendarAlpha", "My Event", this.stringParseToDate("23.09.2011"), this.stringParseToDate("23.09.2011"));
		this.userAlpha.createPublicEvent("CalendarAlpha", "Our Event", this.stringParseToDate("23.09.2011"), this.stringParseToDate("23.09.2011"));

		calendarAlpha.deleteEvent("My Event", this.stringParseToDate("23.09.2011"));
		calendarAlpha.deleteEvent("Our Event", this.stringParseToDate("23.09.2011"));

		// alphaCalendarShouldBeEmpty
		assertTrue(calendarAlpha.getAllEventsAtDate(this.stringParseToDate("1.1.1970")).isEmpty());

		return app;
	}

	@Given("alphaCalendarOwnerShouldBeUserAlpha")
	public App caledarNameShouldBeUnique(App app)
	{
		try
		{
			this.userAlpha.createNewCalendar("CalendarAlpha");
			fail("CalendarIsNotUniqueException expected!");
		}
		catch (CalendarIsNotUniqueException e)
		{
			assertNotNull(e);
		}
		return app;
	}

	@Given("userAlphaShouldHaveNoCalendars")
	public App shouldGiveCalendarListOfUserAlphaTest(App app) throws AccessDeniedException, InvalidDateException, UnknownUserException, UnknownCalendarException, CalendarIsNotUniqueException, ParseException
	{
		this.userAlpha.createNewCalendar("New calendar");
		this.userAlpha.createPrivateEvent("New calendar", "Private Event", this.stringParseToDate("21.9.2011"), this.stringParseToDate("21.9.2011"));
		this.userAlpha.createPublicEvent("New calendar", "Public Event", this.stringParseToDate("21.9.2011"), this.stringParseToDate("21.9.2011"));

		this.userAlpha.createNewCalendar("Second calendar");
		this.userAlpha.createPrivateEvent("Second calendar", "Private Event", this.stringParseToDate("22.9.2011"), this.stringParseToDate("22.9.2011"));
		this.userAlpha.createPublicEvent("Second calendar", "Public Event", this.stringParseToDate("22.9.2011"), this.stringParseToDate("22.9.2011"));

		this.userAlpha.createNewCalendar("Other calendar");
		this.userAlpha.createPrivateEvent("Other calendar", "Private Event", this.stringParseToDate("23.9.2011"), this.stringParseToDate("23.9.2011"));
		this.userAlpha.createPublicEvent("Other calendar", "Public Event", this.stringParseToDate("23.9.2011"), this.stringParseToDate("23.9.2011"));

		ArrayList<String> alphasCalendarListViaUser = userAlpha.getAllMyCalendarNames();
		ArrayList<String> alphasCalendarListViaApp = app.getAllCalendarsNamesFromUser("Alpha");
		assertEquals(alphasCalendarListViaUser, alphasCalendarListViaApp);

		assertEquals(3, alphasCalendarListViaUser.size());
		assertEquals(3, alphasCalendarListViaApp.size());
		assertEquals("New calendar", alphasCalendarListViaUser.get(0));
		assertEquals("New calendar", alphasCalendarListViaApp.get(0));
		assertEquals("Second calendar", alphasCalendarListViaUser.get(1));
		assertEquals("Second calendar", alphasCalendarListViaApp.get(1));
		assertEquals("Other calendar", alphasCalendarListViaUser.get(2));
		assertEquals("Other calendar", alphasCalendarListViaApp.get(2));

		return app;
	}

	@Given("alphaCalendarOwnerShouldBeUserAlpha")
	public App shouldRetrieveArrayListEventsContainingInputDateFromCalendar(App app) throws UnknownCalendarException, UnknownUserException, AccessDeniedException, InvalidDateException, ParseException
	{
		// Create calendar entries
		userAlpha.createPrivateEvent("CalendarAlpha", "My private one-day event", this.stringParseToDate("23.09.2011"), this.stringParseToDate("23.09.2011"));
		userAlpha.createPublicEvent("CalendarAlpha", "My public one-day event", this.stringParseToDate("23.09.2011"), this.stringParseToDate("23.09.2011"));
		userAlpha.createPrivateEvent("CalendarAlpha", "My private long event", this.stringParseToDate("22.09.2011"), this.stringParseToDate("24.09.2011"));
		userAlpha.createPublicEvent("CalendarAlpha", "My public night event", this.stringParseToDate("22.09.2011"), this.stringParseToDate("24.09.2011"));

		// Retrieve via user object
			// Retrieve all 4 events with ArrayList
				ArrayList<IEvent> userAllEventsDate = userAlpha.getMyCalendarAllEventsAtDate("CalendarAlpha", this.stringParseToDate("23.09.2011"));
				assertEquals(4, userAllEventsDate.size());

			// Retrieve 2 public events with ArrayList
				ArrayList<IEvent> userPublicEventsDate = userAlpha.getMyCalendarPublicEventsAtDate("CalendarAlpha", this.stringParseToDate("23.09.2011"));
				assertEquals(2, userPublicEventsDate.size());
				assertEquals("My public one-day event", userPublicEventsDate.get(0).getEventName());
				assertEquals("My public night event", userPublicEventsDate.get(1).getEventName());

		// Retrieve via app
			// Retrieve 2 public events with ArrayList
				ArrayList<IEvent> appPublicEventsDate = app.getUsersCalendarPublicEventsOverview("Alpha", "CalendarAlpha", this.stringParseToDate("23.09.2011"));
				assertEquals(2, appPublicEventsDate.size());
				assertEquals("My public one-day event", appPublicEventsDate.get(0).getEventName());
				assertEquals("My public night event", appPublicEventsDate.get(1).getEventName());

		return app;
	}

	@Given("alphaCalendarOwnerShouldBeUserAlpha")
	public App shouldRetrieveIteratorEventsContainingInputDateFromCalendar(App app) throws UnknownCalendarException, UnknownUserException, AccessDeniedException, InvalidDateException, ParseException
	{
		// Create calendar entries
		userAlpha.createPrivateEvent("CalendarAlpha", "My private one-day event", this.stringParseToDate("23.09.2011"), this.stringParseToDate("23.09.2011"));
		userAlpha.createPublicEvent("CalendarAlpha", "My public one-day event", this.stringParseToDate("23.09.2011"), this.stringParseToDate("23.09.2011"));
		userAlpha.createPrivateEvent("CalendarAlpha", "My private past event", this.stringParseToDate("22.09.2011"), this.stringParseToDate("24.09.2011"));
		userAlpha.createPublicEvent("CalendarAlpha", "My public past event", this.stringParseToDate("22.09.2011"), this.stringParseToDate("24.09.2011"));

		// Retrieve via user object
			// Retrieve 2 events with iterator
				Iterator<IEvent> userAllEventsStarting = userAlpha.getMyCalendarAllEventsStartingFrom("CalendarAlpha", this.stringParseToDate("23.09.2011"));
				int i = 0;
				while (userAllEventsStarting.hasNext())
				{
					userAllEventsStarting.next();
					i++;
				}
				assertEquals(2, i);

			// Retrieve 1 public event with iterator
				Iterator<IEvent> userPublicEventsStarting = userAlpha.getMyCalendarPublicEventsStartingFrom("CalendarAlpha", this.stringParseToDate("23.09.2011"));
				i = 0;
				while (userPublicEventsStarting.hasNext())
				{
					userPublicEventsStarting.next();
					i++;
				}
				assertEquals(1, i);

		// Retrieve via app
			// Retrieve 1 public event with iterator
				Iterator<IEvent> appPublicEventsStarting = app.getUsersCalendarPublicEvents("Alpha", "CalendarAlpha", this.stringParseToDate("23.09.2011"));
				i = 0;
				while (appPublicEventsStarting.hasNext())
				{
					appPublicEventsStarting.next();
					i++;
				}
				assertEquals(1, i);

		return app;
	}

	@Given("alphaCalendarOwnerShouldBeUserAlpha")
	public App shouldRetrieveArrayListEventsNotEqualInputDateFromCalendar(App app) throws UnknownCalendarException, UnknownUserException, AccessDeniedException, InvalidDateException, ParseException
	{
		// Create calendar entries
		userAlpha.createPrivateEvent("CalendarAlpha", "My private one-day event", this.stringParseToDate("23.09.2011"), this.stringParseToDate("24.09.2011"));
		userAlpha.createPublicEvent("CalendarAlpha", "My public one-day event", this.stringParseToDate("23.09.2011"), this.stringParseToDate("24.09.2011"));
		userAlpha.createPrivateEvent("CalendarAlpha", "My private long event", this.stringParseToDate("22.09.2011"), this.stringParseToDate("26.09.2011"));
		userAlpha.createPublicEvent("CalendarAlpha", "My public night event", this.stringParseToDate("22.09.2011"), this.stringParseToDate("26.09.2011"));

		// Retrieve via user object
			// Retrieve all 4 events with ArrayList
				ArrayList<IEvent> userAllEventsDate = userAlpha.getMyCalendarAllEventsAtDate("CalendarAlpha", this.stringParseToDate("23.09.2011"));
				assertEquals(4, userAllEventsDate.size());

			// Retrieve 2 public events with ArrayList
				ArrayList<IEvent> userPublicEventsDate = userAlpha.getMyCalendarPublicEventsAtDate("CalendarAlpha", this.stringParseToDate("23.09.2011"));
				assertEquals(2, userPublicEventsDate.size());
				assertEquals("My public one-day event", userPublicEventsDate.get(0).getEventName());
				assertEquals("My public night event", userPublicEventsDate.get(1).getEventName());

		// Retrieve via app
			// Retrieve 2 public events with ArrayList
				ArrayList<IEvent> appPublicEventsDate = app.getUsersCalendarPublicEventsOverview("Alpha", "CalendarAlpha", this.stringParseToDate("23.09.2011"));
				assertEquals(2, appPublicEventsDate.size());
				assertEquals("My public one-day event", appPublicEventsDate.get(0).getEventName());
				assertEquals("My public night event", appPublicEventsDate.get(1).getEventName());

		return app;
	}

	@Given("alphaCalendarOwnerShouldBeUserAlpha")
	public App shouldRetrieveIteratorEventsNotEqualInputDateFromCalendar(App app) throws UnknownCalendarException, UnknownUserException, AccessDeniedException, InvalidDateException, ParseException
	{
		// Create calendar entries
		userAlpha.createPrivateEvent("CalendarAlpha", "My private one-day event", this.stringParseToDate("24.09.2011"), this.stringParseToDate("24.09.2011"));
		userAlpha.createPublicEvent("CalendarAlpha", "My public one-day event", this.stringParseToDate("24.09.2011"), this.stringParseToDate("24.09.2011"));
		userAlpha.createPrivateEvent("CalendarAlpha", "My private past event", this.stringParseToDate("20.09.2011"), this.stringParseToDate("22.09.2011"));
		userAlpha.createPublicEvent("CalendarAlpha", "My public past event", this.stringParseToDate("20.09.2011"), this.stringParseToDate("22.09.2011"));

		// Retrieve via user object
			// Retrieve 2 events with iterator
				Iterator<IEvent> userAllEventsStarting = userAlpha.getMyCalendarAllEventsStartingFrom("CalendarAlpha", this.stringParseToDate("23.09.2011"));
				int i = 0;
				while (userAllEventsStarting.hasNext())
				{
					userAllEventsStarting.next();
					i++;
				}
				assertEquals(2, i);

			// Retrieve 1 public event with iterator
				Iterator<IEvent> userPublicEventsStarting = userAlpha.getMyCalendarPublicEventsStartingFrom("CalendarAlpha", this.stringParseToDate("23.09.2011"));
				i = 0;
				while (userPublicEventsStarting.hasNext())
				{
					userPublicEventsStarting.next();
					i++;
				}
				assertEquals(1, i);

		// Retrieve via app
			// Retrieve 1 public event with iterator
				Iterator<IEvent> calendarPublicEventsStarting = app.getUsersCalendarPublicEvents("Alpha", "CalendarAlpha", this.stringParseToDate("23.09.2011"));
				i = 0;
				while (calendarPublicEventsStarting.hasNext())
				{
					calendarPublicEventsStarting.next();
					i++;
				}
				assertEquals(1, i);

		return app;
	}
}
