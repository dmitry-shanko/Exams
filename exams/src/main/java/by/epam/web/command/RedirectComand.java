package by.epam.web.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import by.epam.resources.PageConstants;
import by.epam.util.Wrapper;
import by.epam.web.command.exception.CommandException;
/**
 * Redirects to the page /signin.jsp using RequestDispatcher.
 * This command caches itself.
 * @author Dmitry Shanko
 *
 */
public class RedirectComand extends Command
{
	/**
	 * Redirects to the page /signin.jsp using RequestDispatcher.
	 */
	@Override
	public void execute(Wrapper wrapper) throws CommandException
	{
		super.execute(wrapper);
		RequestDispatcher dispatcher;
		dispatcher = wrapper.getRequest().getRequestDispatcher(PageConstants.SIGNIN_DISPATCHER.getContent()); 
		try 
		{
			dispatcher.forward(wrapper.getRequest(), wrapper.getResponse());
			setCommand(getClass());
			return;
		} 
		catch (ServletException | IOException e) 
		{
			log.error("Can't forward to " + PageConstants.SIGNIN_DISPATCHER.getContent() + " in RedirectCommand", e);
			throw new CommandException("Can't forward to " + PageConstants.SIGNIN_DISPATCHER.getContent() + " in RedirectCommand", e);
		}
	}
}