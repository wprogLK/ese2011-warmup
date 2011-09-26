package interfaces;

import java.util.Date;

public interface IEvent 
{
	public String getEventName();
	
	public Date getStartDate();

	public Date getEndDate();

	public boolean isPrivate();

	public boolean isPublic();
}
