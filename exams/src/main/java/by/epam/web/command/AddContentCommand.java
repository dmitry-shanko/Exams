package by.epam.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
 * Adds studentcontent from request to exam from session. Then writes this exam in database as passed using ExamService.
 * @author Dmitry Shanko
 *
 */
public class AddContentCommand extends Command
{
	@Override
	public void execute(Wrapper wrapper) throws CommandException 
	{
		super.execute(wrapper);
		HttpSession session = wrapper.getSession();
		HttpServletRequest request = wrapper.getRequest();
		Exam exam = (Exam) session.getAttribute(SessionConstants.EXAMTOPASS_ATTRIBUTE.getContent());
		session.removeAttribute(SessionConstants.EXAMTOPASS_ATTRIBUTE.getContent());
		String content = request.getParameter(RequestConstants.CONTENT_PARAMETER.getContent());
		try 
		{
			ExamService.getInstance().passExam(exam, session.getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent()), content);
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
		catch (ServiceException e) 
		{
			log.error("Attempt to write exam failed", e);
		}		
		CommandFactory.mainpage.getCommand(wrapper).execute(wrapper);
	}
}