package by.epam.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.buisnesslogicbean.Compiler;
import by.epam.buisnesslogicbean.StudentCompiler;
import by.epam.buisnesslogicbean.exception.LogicException;
import by.epam.resources.RequestConstants;
import by.epam.resources.SessionConstants;
import by.epam.util.Wrapper;
import by.epam.web.command.exception.CommandException;
import by.epam.web.service.ExamService;
import by.epam.web.service.exception.ServiceException;
/**
 * Adds connection of such StudentCompiler from session to the faculty from request.<p>
 * Calls ExamService.addExamsTStudent(faculty (idfaculty from request), student (StudentCompiler from session))<p>
 * Then calls CommandFactory.mainpage.getCommand(wrapper).execute(wrapper);
 * @author Dmitry Shanko
 *
 */
public class SetFacultyCommand extends Command
{
	@Override
	public void execute(Wrapper wrapper) throws CommandException
	{
		super.execute(wrapper);
		HttpSession session = wrapper.getSession();
		HttpServletRequest request = wrapper.getRequest();
		try
		{
			if ((null != session.getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent())) 
					&& (session.getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent()) instanceof StudentCompiler))
			{
				StudentCompiler student = (StudentCompiler) session.getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent());
				String faculty = request.getParameter(RequestConstants.FACULTY_PARAMETER.getContent());
				try 
				{
					if (!ExamService.getInstance().addExamsToStudent(faculty, student))
					{
						log.warn("Can't add exams to such student " + student.getUser());
					}
				} 
				catch (ServiceException e) 
				{
					log.warn("Can't add exams to such student " + student.getUser(), e);
				}	
				Compiler com = (Compiler)(session.getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent()));
				try 
				{
					com.refresh();
				} 
				catch (LogicException e) 
				{
					log.error("Can't refresh Compiler in " + getClass(), e);
				}
			}			
		}
		finally
		{
			CommandFactory.mainpage.getCommand(wrapper).execute(wrapper);
		}
	}
}