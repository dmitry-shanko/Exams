package by.epam.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.entity.User;
import by.epam.resources.RequestConstants;
import by.epam.resources.SessionConstants;
import by.epam.util.Wrapper;
import by.epam.web.command.exception.CommandException;
import by.epam.web.service.UserService;
import by.epam.web.service.exception.ServiceException;
import by.epam.buisnesslogicbean.Compiler;
/**
 * Uses Login logic of this application. Puts proper user bean to session or null if there is no such user.<p>
 * Uses request parameters: pass, email.<p>
 * Uses session attributes: login (puts User bean here).<p>
 * Sends redirect to mainpage.jsp or signin.jsp.
 * 
 * @author Dmitry Shanko
 *
 */
public class LoginCommand extends Command
{
	/**
	 * Uses Login logic of this application. Puts proper user bean to session or null if there is no such user.<p>
	 * Uses request parameters: pass, email.<p>
	 * Uses session attributes: login (puts User bean here).<p>
	 */
	@Override
	public void execute(Wrapper wrapper) throws CommandException
	{
		super.execute(wrapper);	
		HttpSession session = wrapper.getSession();
		HttpServletRequest request = wrapper.getRequest();
		session.removeAttribute(SessionConstants.LOGIN_ATTRIBUTE.getContent());
		session.removeAttribute(SessionConstants.EXAMTOCHECK_ATTRIBUTE.getContent());
		session.removeAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent());
		session.removeAttribute(SessionConstants.EXAMTOPASS_ATTRIBUTE.getContent());
		String pass = request.getParameter(RequestConstants.PASSWORD_PARAMETER.getContent());
		String email = request.getParameter(RequestConstants.EMAIL_PARAMETER.getContent());		
		try
		{
			UserService us = UserService.getInstance();
			User user = us.getUser(pass, email);
			if (null != user)
			{
				session.setAttribute(SessionConstants.LOGIN_ATTRIBUTE.getContent(), user);
				Compiler compiler = us.getCompilerForUser(user);
				if (null != compiler)
				{
					session.setAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent(), compiler);
				}
			}
			else
			{
				request.setAttribute(RequestConstants.ERROR_ATTRIBUTE.getContent(), "error_key");
			}
		}
		catch (ServiceException e)
		{
			log.error("Can't get User from UserService", e);			
		}
		CommandFactory.mainpage.getCommand(wrapper).execute(wrapper);
	}
}
