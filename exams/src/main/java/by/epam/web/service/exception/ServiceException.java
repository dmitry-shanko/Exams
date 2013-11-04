package by.epam.web.service.exception;

public class ServiceException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ServiceException()
	{
		super();
	}
	
	public ServiceException(String s)
	{
		super(s);
	}
	
	public ServiceException(String s, Throwable t)
	{
		super(s, t);
	}
	
	public ServiceException(Throwable t)
	{
		super(t);
	}

}
