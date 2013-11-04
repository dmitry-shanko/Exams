package by.epam.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.buisnesslogicbean.AdminCompiler;
import by.epam.buisnesslogicbean.Compiler;
import by.epam.buisnesslogicbean.exception.LogicException;
import by.epam.entity.Exam;
import by.epam.resources.RequestConstants;
import by.epam.resources.SessionConstants;
import by.epam.util.Wrapper;
import by.epam.web.command.exception.CommandException;
import by.epam.web.service.ExamService;
import by.epam.web.service.exception.ServiceException;
/**
 * Takes important parameters from request to set exam in session SessionConstants.EXAMTOCHECK_ATTRIBUTE.getContent() checked by using ExamService.checkAndUpdateExam.<p>
 * Always calls CommandFactory.adminpage.getCommand(wrapper).execute(wrapper);<p>
 * If mark in request is not digit, sets it as 0.
 * @author Dmitry Shanko
 *
 */
public class HasCheckedCommand extends Command
{
	@Override
	public void execute(Wrapper wrapper) throws CommandException
	{
		super.execute(wrapper);
		HttpSession session = wrapper.getSession();
		HttpServletRequest request = wrapper.getRequest();
		if ((null != session.getAttribute(SessionConstants.EXAMTOCHECK_ATTRIBUTE.getContent())) 
				&& (null != session.getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent()))
				&& (session.getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent()) instanceof AdminCompiler))
		{
			try
			{
				Exam examToCheck = (Exam) (session.getAttribute(SessionConstants.EXAMTOCHECK_ATTRIBUTE.getContent()));
				session.removeAttribute(SessionConstants.EXAMTOCHECK_ATTRIBUTE.getContent());
				Compiler compiler = (Compiler) (session.getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent()));
				int mark = 0;
				try
				{
					mark = Integer.parseInt(request.getParameter(RequestConstants.MARK_PARAMETER.getContent()));
				}
				catch (NumberFormatException e)
				{
					log.error("Attempt to put unknown " + RequestConstants.MARK_PARAMETER.getContent() + " to exam", e);
					CommandFactory.adminpage.getCommand(wrapper).execute(wrapper);
					return;
				}
				try
				{
					ExamService.getInstance().checkAndUpdateExam(compiler, examToCheck, mark);
				}
				catch (ServiceException e)
				{
					log.error("Can't get ExamService", e);
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
			catch (Exception e)
			{
				log.error("Can't check exam", e);
			}	
			CommandFactory.adminpage.getCommand(wrapper).execute(wrapper);
			return;
		}
		else
		{
			log.error("No exam to check in session");
		}
		CommandFactory.adminpage.getCommand(wrapper).execute(wrapper);
	}
}