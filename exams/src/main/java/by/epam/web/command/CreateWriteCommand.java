package by.epam.web.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.buisnesslogicbean.MainadminCompiler;
import by.epam.resources.PageConstants;
import by.epam.resources.RequestConstants;
import by.epam.resources.SessionConstants;
import by.epam.util.Wrapper;
import by.epam.web.command.exception.CommandException;
import by.epam.web.service.ExamService;
import by.epam.web.service.exception.ServiceException;
/**
 * Takes important parameters from request to create new Exan in database.<p>
 * If exam has been created, redirects to empty page to prevent double committing of exam writing.
 * @author Dmitry Shanko
 *
 */
public class CreateWriteCommand extends Command
{
	@Override
	public void execute(Wrapper wrapper) throws CommandException
	{
		super.execute(wrapper);
		HttpSession session = wrapper.getSession();
		HttpServletRequest request = wrapper.getRequest();
		if (session.getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent()) instanceof MainadminCompiler)
		{
			if (null != request.getParameterValues(RequestConstants.FACULTY_PARAMETER.getContent()))
			{
				try 
				{
					
					List<Integer> idfaculties = new ArrayList<Integer>();
					for (String s : request.getParameterValues(RequestConstants.FACULTY_PARAMETER.getContent()))
					{
						try
						{
							idfaculties.add(Integer.parseInt(s));
						}
						catch (NumberFormatException e)
						{
							log.warn("Unknown " + RequestConstants.FACULTY_PARAMETER.getContent() + " in request", e);
						}
					}
					String examName = request.getParameter(RequestConstants.EXAMNAME_PARAMETER.getContent());
					String content =  request.getParameter(RequestConstants.CONTENT_PARAMETER.getContent());
					if (ExamService.getInstance().saveNewExam(idfaculties, examName, content))
					{
						request.setAttribute(RequestConstants.SUCCESS_ATTRIBUTE.getContent(), "yes");
						log.info("New exam has been created in database");
					}
					else
					{
						log.warn("Attemp to create incorrect exam in database");
					}
				} 
				catch (ServiceException e) 
				{
					log.error("Can't create new exam", e);
				}	
			}
			try 
			{
				try 
				{
					request.getRequestDispatcher(PageConstants.NOPAGE_REDIRECT.getContent()).forward(request, wrapper.getResponse());
				} 
				catch (ServletException e) 
				{
					throw new CommandException("Can't forward in " + getClass(), e);
				}
				return;
			} 
			catch (IOException e) 
			{
				log.error("IOException in trying to forward to " + getClass(), e);
			}
			CommandFactory.adminpage.getCommand(wrapper).execute(wrapper);
			return;
		}
		else
		{
			CommandFactory.mainpage.getCommand(wrapper).execute(wrapper);
		}
	}
}