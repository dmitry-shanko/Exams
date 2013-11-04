package by.epam.util;

public class WrapperException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WrapperException()
	{
		super();
	}
	
	public WrapperException(String s)
	{
		super(s);
	}

	public WrapperException(Throwable e)
	{
		super(e);
	}
	
	public WrapperException(String s, Throwable e)
	{
		super(s, e);
	}
}
