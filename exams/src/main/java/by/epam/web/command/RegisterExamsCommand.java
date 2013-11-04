package by.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.buisnesslogicbean.StudentCompiler;
import by.epam.resources.PageConstants;
import by.epam.resources.RequestConstants;
import by.epam.resources.SessionConstants;
import by.epam.util.Wrapper;
import by.epam.web.command.exception.CommandException;
import by.epam.web.service.FacultyService;
import by.epam.web.service.exception.ServiceException;
/**
 * Sets faculty to the user from StudentCompiler attribute.<p>
 * Adds exams to this user by choosen faculty.<p>
 * Then calls CommandFactory.mainpage.getCommand(wrapper).execute(wrapper);
 * @author Dmitry Shanko
 *
 */
public class RegisterExamsCommand extends Command
{
	@Override
	public void execute(Wrapper wrapper) throws CommandException
	{
		super.execute(wrapper);
		HttpSession session = wrapper.getSession();
		HttpServletRequest request = wrapper.getRequest();
		if ((null != session.getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent()))
				&& (session.getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent()) instanceof StudentCompiler))
		{			
			StudentCompiler student = (StudentCompiler) session.getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent());
			if (!student.isRegistred())
			{
				try 
				{
					request.setAttribute(RequestConstants.FACULTIES_ATTRIBUTE.getContent(), FacultyService.getInstance().collectPossibleFacultiesForNewExam());
					request.getRequestDispatcher(PageConstants.EXAMS_DISPATCHER.getContent()).forward(request, wrapper.getResponse());
					return;
				} 
				catch (ServletException | IOException e) 
				{
					log.error("Can't forward to " + PageConstants.EXAMS_DISPATCHER.getContent() + " student " + student.getUser(), e);
					throw new CommandException("Can't forward to " + PageConstants.EXAMS_DISPATCHER.getContent() + " student " + student.getUser(), e);
				} 
				catch (ServiceException e) 
				{
					log.error("Can't collect Faculties in FacultyService", e);
				} 
			}
		}
		CommandFactory.mainpage.getCommand(wrapper).execute(wrapper);
	}
}