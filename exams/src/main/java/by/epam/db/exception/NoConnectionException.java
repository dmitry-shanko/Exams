package by.epam.db.exception;

public class NoConnectionException extends Exception 
{

	private static final long serialVersionUID = 1L;
	
	public NoConnectionException()
	{
		super();
	}
	
	public NoConnectionException(String s)
	{
		super(s);
	}
	
	public NoConnectionException(Throwable e)
	{
		super(e);
	}
	
	public NoConnectionException(String s, Throwable e)
	{
		super(s, e);
	}

}
