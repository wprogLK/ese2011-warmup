/**
 * Calendar framework
 */
package app;

import java.util.Date;

import org.junit.*;
import org.junit.runner.RunWith;

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
	public User simpleTest1()
	{
		return new User("Alpha");
	}

	@Given("simpleTest1")
	public User userAlphaNameShouldBeAlpha(User user)
	{
		assertEquals(user.getName(),"Alpha");
		return user;
	}

	@Given("simpleTest1")
	public void preventCreatingUserWithAlreadyExistingNameTest()
	{
		User fakeUser = new User("Alpha");
		assertNotSame(fakeUser.getName(), "Alpha");
	}

	@Given("userAlphaNameShouldBeAlpha")
	public Calendar calendarOwnerShouldBeUserAlpha(User user)
	{
		Calendar myCalendar = new Calendar(user,"My calendar");
		assertEquals(myCalendar.getOwner(),user);
		return myCalendar;
	}

	@Given("calendarOwnerShouldBeUserAlpha")
	public Event eventShouldBePrivate (Calendar calendar)
	{
		Event myEvent = null;
		try 
		{
			myEvent = calendar.createPrivateEvent("My event", new Date(), new Date(), calendar.getOwner());
			assertTrue(myEvent.isPrivate());
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		return myEvent;
	}

	@Given("calendarOwnerShouldBeUserAlpha")
	public User userBetaShouldNotAddAnEventToForeignCalendar(Calendar calendarAlpha)
	{
		User userBeta = new User("Beta");
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
		return userBeta;
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
