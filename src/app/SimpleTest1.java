/**
 * 
 */
package app;

import java.util.Date;

import org.junit.*;

import ch.unibe.jexample.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
/**
 * @author Lukas Keller
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
	
	@Given("userAlphaNameShouldBeAlpha")
	public Calendar calendarOwnerShouldBeUserAlpha(User user)
	{
		Calendar myCalendar=new Calendar(user,"My calendar");
		
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
	public User userBetaShouldNotAddAnEventToCalendarAlpha(Calendar calendarAlpha)
	{
		User userBeta=new User("Beta");
		
		Event betaEvent =null;
		
		try
		{
			betaEvent=calendarAlpha.createPrivateEvent("Not my event", new Date(), new Date(), userBeta);
		}
		catch(Exception e)
		{
			//do nothing
		}
		finally
		{
			assertEquals(null, betaEvent);
		}
		
		return userBeta;
	}
}






	
	