package by.epam.xml.exception;

public class NoXMLReaderException extends Exception 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoXMLReaderException()
	{
		super();
	}
	
	public NoXMLReaderException(String st, Throwable e)
	{
		super(st, e);
	}
	
	public NoXMLReaderException(Throwable e)
	{
		super(e);
	}
	
	public NoXMLReaderException(String st)
	{
		super(st);
	}
	

}
