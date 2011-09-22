/**
 * 
 */
package app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.*;

import app.AppExceptions.NoAccessToCalendarException;
import app.AppExceptions.UnknownCalendarException;
import app.AppExceptions.UnknownUserException;
import app.AppExceptions.UserNameAlreadyExistException;
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
	public App simpleTest1()
	{
		App app=new App();
	
		return app;
	}
	
	@Given("simpleTest1")
	public App userAlphaNameShouldBeAlpha(App app) throws UserNameAlreadyExistException
	{
		User userAlpha=app.createUser("Alpha");
		
		assertEquals(userAlpha.getName(),"Alpha");
		
		return app;
	}
	
	@Given("userAlphaNameShouldBeAlpha")
	public App calendarOwnerShouldBeUserAlpha(App app) throws UnknownUserException
	{
		User userAlpha=app.getUser("Alpha");
		
		Calendar myCalendar=userAlpha.createNewCalendar("My calendar");
		
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
	public App userBetaShouldNotAddAnEventToCalendarAlpha(App app) throws UserNameAlreadyExistException, UnknownUserException, UnknownCalendarException
	{
		User userBeta=app.createUser("Beta");
		User userAlpha=app.getUser("Alpha");
		
		Calendar calendarAlpha=userAlpha.getCalendar("My calendar");
		
		
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






	
	