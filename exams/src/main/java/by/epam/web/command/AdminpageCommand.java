package by.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;

import by.epam.buisnesslogicbean.Compiler;
import by.epam.buisnesslogicbean.exception.LogicException;
import by.epam.entity.User;
import by.epam.resources.PageConstants;
import by.epam.resources.SessionConstants;
import by.epam.util.Wrapper;
import by.epam.web.command.exception.CommandException;
/**
 * Forwards to the PageConstants.ADMIN_DISPATCHER.getContent() if User from login has idroles == 1 || 2 and has compiler in session.<p>
 * If not, calls CommandFactory.mainpage.getCommand(wrapper).execute(wrapper);<p>
 * This command caches itself.
 * @author Dmitry Shanko
 *
 */
public class AdminpageCommand extends Command
{
	@Override
	public void execute(Wrapper wrapper) throws CommandException 
	{
		super.execute(wrapper);
		try 
		{
			User user = (User) wrapper.getSession().getAttribute(SessionConstants.LOGIN_ATTRIBUTE.getContent());
			if (null != user && (user.getIdroles() == 1 || user.getIdroles() == 2))
			{
				try 
				{
					((Compiler) wrapper.getSession().getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent())).refresh();
				} 
				catch (ClassCastException e)
				{
					CommandFactory.redirect.getCommand(wrapper).execute(wrapper);
					return;
				}
				catch (LogicException e) 
				{
					log.error("Can't refresh Compiler attribute in " + getClass(), e);
				}
				wrapper.getRequest().getRequestDispatcher(PageConstants.ADMIN_DISPATCHER.getContent()).forward(wrapper.getRequest(), wrapper.getResponse());
				setCommand(getClass());
				return;
			}
		} 
		catch (ServletException | IOException e) 
		{
			log.error("Error in dispatcher.forward in " + getClass(), e);
		}
		CommandFactory.mainpage.getCommand(wrapper).execute(wrapper);
	}
}