/**
 * Calendar framework
 */
package app;

import java.util.ArrayList;
import java.util.Iterator;

import app.AppExceptions.*;

/**
 * @author Lukas Keller
 * @author Renato Corti
 *
 */

public class Authentication
{
	private ArrayList<Tuple> userDatabase;

	public Authentication()
	{
		this.userDatabase = new ArrayList<Tuple>();
	}

	private class Tuple
	{
		private User user;
		private String password;
		public Tuple(User user, String password)
		{
			this.user = user;
			this.password = password;
		}
		public String getUsername()
		{
			return this.user.getName();
		}
		public User getUser()
		{
			return this.user;
		}
		public String getPassword()
		{
			return this.password;
		}
		public void setPassword(String newPassword)
		{
			this.password = newPassword;
		}
	}

	public User createNewUser(String username, String password, App app) throws UserNameAlreadyExistException
	{
		isUsernameUnused(username);

		User newUser = new User(username, app);
		this.userDatabase.add(new Tuple(newUser, password));
		return newUser;
	}

	private void isUsernameUnused(String username) throws UserNameAlreadyExistException
	{
		for (Tuple t : userDatabase)
		{
			if (t.getUsername().equals(username))
			{
				throw new UserNameAlreadyExistException(username); 
			}
		}
	}

	/** Used to get the User object which is required to work with calendar objects (add, modify, delete events).
	 * This are actions, that should only be performed by the owner of the calendar.
	 * It is intended that the User Object reaches the external user interface
	 * and the user has access to the user object and its methods (add, modify, delete events).
	 * So this function requires a password.
	 * For access to read only functions and public events
	 * there is the {@link Authentication#getUser(String)} funcion available, which does not ask for a password.
	 * @param username 
	 * @param password 
	 * @return Provides the user object.
	 * @throws UnknownUserException 
	 * @throws AccessDeniedException 
	 */
	public User getUser(String username, String password) throws UnknownUserException, AccessDeniedException
	{
		Iterator<Tuple> iteratorUsers = this.userDatabase.iterator();
		while (iteratorUsers.hasNext())
		{
			Tuple t = iteratorUsers.next();
			if (t.getUsername().equals(username))
			{
				if (t.getPassword().equals(password))
				{
					return t.getUser();
				}
				else
				{
					throw new AccessDeniedException(username);
				}
			}
		}
		throw new UnknownUserException(username);
	}

	/** This is a more permissive function to get the user object.
	 * It does not ask for a password.
	 * The difference to the overloaded {@link Authentication#getUser(String, String)} function is,
	 * that the user object does not reach the outer interface and can therefore not be used
	 * to provide write access to the calendar (add, modify, delete events)
	 * @param username 
	 * @return User object.
	 * @throws UnknownUserException 
	 */
	public User getUser(String username) throws UnknownUserException
	{
		Iterator<Tuple> iteratorUsers = this.userDatabase.iterator();
		while (iteratorUsers.hasNext())
		{
			Tuple t = iteratorUsers.next();
			if (t.getUsername().equals(username))
			{
				return t.getUser();
			}
		}
		throw new UnknownUserException(username);
	}

	public void setNewPassword(String username, String oldPassword, String newPassword) throws UnknownUserException, AccessDeniedException
	{
		this.getUser(username, oldPassword);
		for (Tuple t : this.userDatabase)
		{
			if (t.getUsername().equals(username))
			{
				t.setPassword(newPassword);
				break;
			}
		}
	}

	/**
	 * @param username 
	 * @param password 
	 * @throws UnknownUserException 
	 * @throws AccessDeniedException 
	 */
	public void deleteUser(String username, String password) throws UnknownUserException, AccessDeniedException
	{
		this.getUser(username, password);
		for (Tuple t : this.userDatabase)
		{
			if (t.getUsername().equals(username))
			{
				this.userDatabase.remove(t);
				break;
			}
		}
	}
}
