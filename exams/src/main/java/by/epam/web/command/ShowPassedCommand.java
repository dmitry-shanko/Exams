package by.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.buisnesslogicbean.FacultiesCompiler;
import by.epam.entity.User;
import by.epam.resources.PageConstants;
import by.epam.resources.RequestConstants;
import by.epam.resources.SessionConstants;
import by.epam.util.Wrapper;
import by.epam.web.command.exception.CommandException;
import by.epam.web.service.FacultyService;
import by.epam.web.service.exception.ServiceException;
/**
 * Adds FacultyCompiler to request and forwards to the PageConstants.STUDENTS_DISPATCHER.getContent().<p>
 * In case of any problem calls CommandFactory.mainpage.getCommand(wrapper).execute(wrapper);<p>
 * This command caches itself.
 * @author Dmitry Shanko
 *
 */
public class ShowPassedCommand extends Command
{	
	@Override
	public void execute(Wrapper wrapper) throws CommandException 
	{
		super.execute(wrapper);
		HttpSession session = wrapper.getSession();
		HttpServletRequest request = wrapper.getRequest();
		User user = (User) session.getAttribute(SessionConstants.LOGIN_ATTRIBUTE.getContent());
		try 
		{
			FacultiesCompiler fc = FacultyService.getInstance().getFacultyCompiler(user);
			request.setAttribute(RequestConstants.FACULTIESCOMPILER_ATTRIBUTE.getContent(), fc);
			request.getRequestDispatcher(PageConstants.STUDENTS_DISPATCHER.getContent()).forward(request, wrapper.getResponse());
			setCommand(getClass());
			return;
		} 
		catch (ServiceException e) 
		{
			log.warn("Can't get FacultiesCompiler. Can't dispatcher.forward to students.jsp");
		} 
		catch (ServletException e) 
		{
			log.error("ServletException in " + getClass() + " in trying to dispatcher.forward", e);
			throw new CommandException("ServletException in " + getClass() + " in trying to dispatcher.forward", e);
		} 
		catch (IOException e) 
		{
			log.error("IOException in " + getClass() + " in trying to dispatcher.forward", e);
			throw new CommandException("IOException in " + getClass() + " in trying to dispatcher.forward", e);
		}
		CommandFactory.mainpage.getCommand(wrapper).execute(wrapper);
	}
}