package by.epam.web.command;

import by.epam.entity.User;
import by.epam.resources.SessionConstants;
import by.epam.util.Wrapper;
import by.epam.web.command.exception.CommandException;
/**
 * Executes cached command using reflection API.<p>
 * If cachedCommand is null, executes command by switching idroles parameter of Login attribute from session.<p>
 * As default executes CommandFactory.redirect.getCommand(wrapper).execute(wrapper);
 * @author Dmitry Shanko
 *
 */
public class ExecuteCachedCommand extends Command
{
	@Override
	public void execute(Wrapper wrapper) throws CommandException 
	{
		super.execute(wrapper);
		try 
		{		
			if (null != getCommand())
			{
				Command cachedCommand = getCommand().newInstance();
				cachedCommand.execute(wrapper);
				return;
			}
		} 
		catch (InstantiationException e1) 
		{
			log.error("Can't execute cachedCommand in " + getClass(), e1);
		} 
		catch (IllegalAccessException e1) 
		{
			log.error("Can't execute cachedCommand in " + getClass(), e1);
		}
		if (wrapper.getSession().getAttribute(SessionConstants.LOGIN_ATTRIBUTE.getContent()) instanceof User)
		{
			int idroles = ((User)(wrapper.getRequest().getSession().getAttribute(SessionConstants.LOGIN_ATTRIBUTE.getContent()))).getIdroles();
			switch (idroles)
			{
			case 1:
			case 2:
				CommandFactory.adminpage.getCommand(wrapper).execute(wrapper);
				return;
			case 3:
				CommandFactory.mainpage.getCommand(wrapper).execute(wrapper);
				return;
			default:
				CommandFactory.redirect.getCommand(wrapper).execute(wrapper);
				return;
			}
		}
		CommandFactory.redirect.getCommand(wrapper).execute(wrapper);
	}
}