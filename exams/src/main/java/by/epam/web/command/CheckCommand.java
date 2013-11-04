package by.epam.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.buisnesslogicbean.AdminCompiler;
import by.epam.buisnesslogicbean.Compiler;
import by.epam.entity.Exam;
import by.epam.resources.PageConstants;
import by.epam.resources.RequestConstants;
import by.epam.resources.SessionConstants;
import by.epam.util.Wrapper;
import by.epam.web.command.exception.CommandException;
/**
 * This command forwards to the page of checking students answer for exam. Forwards only in compiler attribute in session is instanceof AdminCompiler and Compiler.<p>
 * Puts exam to check in session.<p>
 * In case of any exception or missing any important attributes calls CommandFactory.mainpage.getCommand(wrapper).execute(wrapper)<p>
 * or CommandFactory.adminpage.getCommand(wrapper).execute(wrapper) if compiler is instanceof AdminCompiler
 * @author Dmitry Shanko
 *
 */
public class CheckCommand extends Command
{
	@Override
	public void execute(Wrapper wrapper) throws CommandException 
	{
		super.execute(wrapper);
		HttpSession session = wrapper.getSession();
		HttpServletRequest request = wrapper.getRequest();
		HttpServletResponse response = wrapper.getResponse();

		Object obj = session.getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent());
		if (obj instanceof AdminCompiler && obj instanceof Compiler)
		{
			try
			{

				int idexam = Integer.parseInt(request.getParameter(RequestConstants.IDEXAM_PARAMETER.getContent()));
				int idstudent = Integer.parseInt(request.getParameter(RequestConstants.IDSTUDENT_PARAMETER.getContent()));
				List<Exam> exams = ((Compiler)obj).getExams();
				Exam exam = null;
				for (Exam e : exams)
				{
					if (e.getIdexam() == idexam && e.getIdstudent() == idstudent)
					{
						exam = e;						
					}
				}
				if (null != exam)
				{
					try 
					{
						session.setAttribute(SessionConstants.EXAMTOCHECK_ATTRIBUTE.getContent(), exam);
						request.getRequestDispatcher(PageConstants.CHECK_DISPATCHER.getContent()).forward(request, response);
						return;
					} 
					catch (IOException e1) 
					{
						log.error("Error in dispatcher.forward to " + PageConstants.CHECK_DISPATCHER.getContent() + " from CheckCommand", e1);
						throw new CommandException("Error in dispatcher.forward to " + PageConstants.CHECK_DISPATCHER.getContent() + " from CheckCommand", e1);
					} 
					catch (ServletException e1) 
					{
						log.error("Error in dispatcher.forward to " + PageConstants.CHECK_DISPATCHER.getContent() + " from CheckCommand", e1);
						throw new CommandException("Error in dispatcher.forward to " + PageConstants.CHECK_DISPATCHER.getContent() + " from CheckCommand", e1);
					}
				}
				else
				{
					CommandFactory.adminpage.getCommand(wrapper).execute(wrapper);
					return;
				}
			}
			catch (NumberFormatException e)
			{
				log.error("No values for " + RequestConstants.IDEXAM_PARAMETER.getContent() + " and " + RequestConstants.IDSTUDENT_PARAMETER.getContent());
			}
			CommandFactory.adminpage.getCommand(wrapper).execute(wrapper);
			return;
		}
		else
		{
			log.error("Incorrect Compiler object in session. Can't forward to check.jsp");
		}
		CommandFactory.mainpage.getCommand(wrapper).execute(wrapper);
	}
}