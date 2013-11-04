package by.epam.db.pool.exception;

public class ConnectionSQLException extends Exception 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ConnectionSQLException()
	{
		super();
	}
	
	public ConnectionSQLException(String s)
	{
		super(s);
	}
	
	public ConnectionSQLException(Throwable e)
	{
		super(e);
	}
	
	public ConnectionSQLException(String s, Throwable e)
	{
		super(s, e);
	}
	

}
