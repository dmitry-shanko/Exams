package by.epam.resources;
/**
 * Enum with constants that are used as URLs
 * @author Dmitry Shanko
 *
 */
public enum PageConstants 
{
	
	MAINPAGE_REDIRECT("/EpamFinalTask/mainpage.jsp"),
	SIGNIN_DISPATCHER("pages/signin.jsp"),
	NEWEXAM_DISPATCHER("pages/newexam.jsp"),
	CHECK_DISPATCHER("pages/check.jsp"),
	ADMIN_REDIRECT("/EpamFinalTask/admin.jsp"),
	EXAMS_DISPATCHER("pages/exams.jsp"),
	PASS_DISPATCHER("pages/pass.jsp"),
	STUDENTS_DISPATCHER("pages/students.jsp"), 
	ADMIN_DISPATCHER("pages/admin.jsp"),
	MAINPAGE_DISPATCHER("pages/mainpage.jsp"),
	NOPAGE_REDIRECT("pages/no.jsp");
	private String content;
	private PageConstants(String content)
	{
		this.content = content;
	}
	
	public String getContent()
	{
		return content;
	}

}
