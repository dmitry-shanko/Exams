package by.epam.web.command;

import javax.servlet.http.HttpSession;

import by.epam.resources.SessionConstants;
import by.epam.util.Wrapper;
import by.epam.web.command.exception.CommandException;
/**
 * Clears all session attributes.<p>
 * Sets cached command as null.
 * @author Dmitry Shanko.
 *
 */
public class LogoutCommand extends Command
{
	@Override
	public void execute(Wrapper wrapper) throws CommandException
	{
		super.execute(wrapper);
		HttpSession session = wrapper.getRequest().getSession();
		session.removeAttribute(SessionConstants.LOGIN_ATTRIBUTE.getContent());
		session.removeAttribute(SessionConstants.EXAMTOCHECK_ATTRIBUTE.getContent());
		session.removeAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent());
		session.removeAttribute(SessionConstants.EXAMTOPASS_ATTRIBUTE.getContent());
		/*
		 * since 15.06.2013
		 */
		setCommand(null);
		CommandFactory.redirect.getCommand(wrapper).execute(wrapper);
	}
}