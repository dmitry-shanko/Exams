package by.epam.xml.exception;

public class NotValidXMLException extends Exception 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NotValidXMLException()
	{
		super();
	}
	
	public NotValidXMLException(String st, Throwable e)
	{
		super(st, e);
	}
	
	public NotValidXMLException(Throwable e)
	{
		super(e);
	}
	
	public NotValidXMLException(String st)
	{
		super(st);
	}
}
