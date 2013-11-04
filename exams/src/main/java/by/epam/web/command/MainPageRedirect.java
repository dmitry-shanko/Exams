package by.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;

import by.epam.entity.User;
import by.epam.resources.PageConstants;
import by.epam.resources.SessionConstants;
import by.epam.util.Wrapper;
import by.epam.web.command.exception.CommandException;
import by.epam.buisnesslogicbean.Compiler;
import by.epam.buisnesslogicbean.exception.LogicException;
/**
 * Forwards to the mainpage.jsp or calls CommandFactory.redirect.getCommand(wrapper).execute(wrapper) if Login attribute in session is null or not instanceof User.<p>
 * Is used if command parameter is null or there is no such command.
 * This command caches itself.
 * @author Dmitry Shanko
 *
 */
public class MainPageRedirect extends Command
{
	/**
	 * Forwards to the mainpage.jsp.<p>
	 * @throws CommandException 
	 */
	@Override
	public void execute(Wrapper wrapper) throws CommandException
	{
		super.execute(wrapper);
		try 
		{
			if ((null != wrapper.getSession().getAttribute(SessionConstants.LOGIN_ATTRIBUTE.getContent()))
					&& (wrapper.getSession().getAttribute(SessionConstants.LOGIN_ATTRIBUTE.getContent()) instanceof User))
			{
				try 
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
					wrapper.getRequest().getRequestDispatcher(PageConstants.MAINPAGE_DISPATCHER.getContent()).forward(wrapper.getRequest(), wrapper.getResponse());
					setCommand(getClass());
					return;
				}
				catch (ServletException e) 
				{
					log.error("Can't dispatcher.forward in " + getClass() + " to mainpage.jsp", e);
				}				
			}
		} 
		catch (IOException e) 
		{
			log.error("Can't redirect to " + PageConstants.MAINPAGE_REDIRECT.getContent() + " in RedirectCommand", e);
		}
		CommandFactory.redirect.getCommand(wrapper).execute(wrapper);
	}
}
