package by.epam.resources;
/**
 * Enum with constants that are used with HttpServletRequest.
 * @author Dmitry Shanko
 *
 */
public enum RequestConstants 
{
	
	COMMAND_PARAMETER("command"),
	IDEXAM_PARAMETER("idexam"),
	IDSTUDENT_PARAMETER("idstudent"),
	FACULTY_PARAMETER("faculty"),
	EXAMNAME_PARAMETER("examname"),
	CONTENT_PARAMETER("content"),
	MARK_PARAMETER("mark"),
	PASSWORD_PARAMETER("pass"),
	EMAIL_PARAMETER("email"),
	LANG_PARAMETER("lang"),
	REFERER_HEADER("referer"),
	CONTROLLER_HEADER("MainServlet"),
	FACULTIES_ATTRIBUTE("faculties"),
	FACULTIESCOMPILER_ATTRIBUTE("facultiesCompiler"),
	ERROR_ATTRIBUTE("error_key"),
	SUCCESS_ATTRIBUTE("success"),
	;
	private String content;
	private RequestConstants(String content)
	{
		this.content = content;
	}
	
	public String getContent()
	{
		return content;
	}


}
