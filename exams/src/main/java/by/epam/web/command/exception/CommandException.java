package by.epam.web.command.exception;

public class CommandException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CommandException()
	{
		super();
	}
	
	public CommandException(String s)
	{
		super(s);
	}
	
	public CommandException(String s, Throwable t)
	{
		super(s, t);
	}
	
	public CommandException(Throwable t)
	{
		super(t);
	}

}
