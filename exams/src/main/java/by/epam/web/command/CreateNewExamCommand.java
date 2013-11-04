package by.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.buisnesslogicbean.MainadminCompiler;
import by.epam.resources.PageConstants;
import by.epam.resources.RequestConstants;
import by.epam.resources.SessionConstants;
import by.epam.util.Wrapper;
import by.epam.web.command.exception.CommandException;
import by.epam.web.service.FacultyService;
import by.epam.web.service.exception.ServiceException;
/**
 * Forwards to the page of creating new exam (only if compiler in session is istanceof MainadminCompiler).<p>
 * Or CommandFactory.adminpage.getCommand(wrapper).execute(wrapper);<p>
 * This command caches itself.
 * @author Dmitry Shanko
 *
 */
public class CreateNewExamCommand extends Command
{
	@Override
	public void execute(Wrapper wrapper) throws CommandException
	{
		super.execute(wrapper);
		HttpSession session = wrapper.getSession();
		HttpServletRequest request = wrapper.getRequest();
		if (session.getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent()) instanceof MainadminCompiler)
		{
			try 
			{			
				request.setAttribute(RequestConstants.FACULTIES_ATTRIBUTE.getContent(), FacultyService.getInstance().collectPossibleFacultiesForNewExam());
				request.getRequestDispatcher(PageConstants.NEWEXAM_DISPATCHER.getContent()).forward(request, wrapper.getResponse());
				setCommand(getClass());
				return;
			} 
			catch (ServletException | IOException e) 
			{
				log.error("Can't forward from " + getClass() + " to " + PageConstants.NEWEXAM_DISPATCHER.getContent(), e);
				throw new CommandException("Can't forward from " + getClass() + " to " + PageConstants.NEWEXAM_DISPATCHER.getContent(), e);
			} 
			catch (ServiceException e) 
			{
				log.error("Can't collect possible faculties in " + getClass(), e);
			} 
		}
		CommandFactory.adminpage.getCommand(wrapper).execute(wrapper);
	}
}
