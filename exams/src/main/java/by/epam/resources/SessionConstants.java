package by.epam.resources;
/**
 * Enum with constants that are used with HttpSession.
 * @author Dmitry Shanko
 *
 */
public enum SessionConstants 
{
	
	COMPILER_ATTRIBUTE("compiler"),
	LOGIN_ATTRIBUTE("login"),
	EXAMTOCHECK_ATTRIBUTE("examToCheck"),
	LANG_ATTRIBUTE("lang"),
	EXAMTOPASS_ATTRIBUTE("examToPass"),
	;
	private String content;
	private SessionConstants(String content)
	{
		this.content = content;
	}
	
	public String getContent()
	{
		return content;
	}

}
