package by.epam.web.command;

import by.epam.resources.Resources;

import by.epam.util.Wrapper;
import by.epam.web.command.exception.CommandException;

import org.apache.log4j.Logger;

/**
 * Common abstract class of Commands. Provides public void execute to subclasses. Uses pattern Command.
 * @author Dmitry Shanko
 *
 */
public abstract class Command
{
	private static final Resources res = Resources.getInstance();
	protected final Logger log = res.getLogger(getClass());
	private static Class<? extends Command> command;
	protected Command()
	{

	}	

	public void execute(Wrapper wrapper) throws CommandException
	{
		if ((null == wrapper) || (null == wrapper.getRequest()) || (null == wrapper.getResponse()))
		{
			log.error(getClass() + " has been called with null wrapper");
			throw new CommandException(getClass() + " has been called with null wrapper");
		}
	}

	protected Class<? extends Command> getCommand()
	{
		return Command.command;
	}

	protected void setCommand(Class<? extends Command> cachedCommand)
	{
		Command.command = cachedCommand;
	}


}