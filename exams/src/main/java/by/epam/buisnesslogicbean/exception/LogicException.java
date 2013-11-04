package by.epam.buisnesslogicbean.exception;

public class LogicException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LogicException()
	{
		super();
	}
	
	public LogicException(String s)
	{
		super(s);
	}
	
	public LogicException(Throwable e)
	{
		super(e);
	}
	
	public LogicException(String s, Throwable e)
	{
		super(s, e);
	}

}
