package tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestTemplate 
{
	protected Date stringParseToDate(String strDate) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
		return sdf.parse(strDate);
		
	}
}
