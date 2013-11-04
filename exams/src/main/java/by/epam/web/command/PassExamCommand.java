package by.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.entity.Exam;
import by.epam.resources.PageConstants;
import by.epam.resources.RequestConstants;
import by.epam.resources.SessionConstants;
import by.epam.util.Wrapper;
import by.epam.web.command.exception.CommandException;
import by.epam.web.service.ExamService;
import by.epam.web.service.exception.ServiceException;
/**
 * Forwards to the page of passing (only if compiler in session is istanceof StudentCompiler).<p>
 * Or CommandFactory.mainpage.getCommand(wrapper).execute(wrapper);<p>
 * @author Dmitry Shanko
 *
 */
public class PassExamCommand extends Command
{
	@Override
	public void execute(Wrapper wrapper) throws CommandException 
	{
		super.execute(wrapper);
		HttpSession session = wrapper.getSession();
		HttpServletRequest request = wrapper.getRequest();
		try
		{
			int idexam = Integer.parseInt(request.getParameter(RequestConstants.IDEXAM_PARAMETER.getContent()));
			int idstudent = Integer.parseInt(request.getParameter(RequestConstants.IDSTUDENT_PARAMETER.getContent()));
			try 
			{
				Exam examToPass = ExamService.getInstance().getExamToPass(idstudent, idexam, session.getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent()));
				session.setAttribute(SessionConstants.EXAMTOPASS_ATTRIBUTE.getContent(), examToPass);
				try 
				{
					request.getRequestDispatcher(PageConstants.PASS_DISPATCHER.getContent()).forward(request, wrapper.getResponse());
					return;
				} 
				catch (ServletException | IOException e) 
				{
					log.error("Can't forward to " + PageConstants.PASS_DISPATCHER.getContent() + " in " + getClass(), e);
					throw new CommandException("Can't forward to " + PageConstants.PASS_DISPATCHER.getContent() + " in " + getClass(), e);
				}
			} 
			catch (ServiceException e) 
			{
				log.warn("Can't get any ExamToPass in " + getClass(), e);	
			}
		}
		catch (NumberFormatException e)
		{
			log.warn("Incorrect values in request in " + getClass());			
		}
		CommandFactory.mainpage.getCommand(wrapper).execute(wrapper);
	}
}
