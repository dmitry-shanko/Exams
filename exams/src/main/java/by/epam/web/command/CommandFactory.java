package by.epam.web.command;

import by.epam.entity.User;
import by.epam.resources.SessionConstants;
import by.epam.util.Wrapper;
/**
 * Enum factory to get proper command by commands names.
 * @author Dmitry Shanko
 *
 */
public enum CommandFactory
{
	redirect(1), login(2), mainpage(3), logout(4), check(5), hasChecked(6), registerExams(7), setFaculty(8), createNewExam(9), createwrite(10), language(11),
	pass(12), addContent(13), showPassed(14), adminpage(15), cached(16);
	private int id;
	private CommandFactory(int id)
	{
		this.id = id;
	}
	/**
	 * Returns Command for the chosen command's name (it uses id for each command enum).
	 * @return new Command.
	 */
	public Command getCommand(Wrapper wrapper)
	{
		User user = null;
		try
		{
			user = (User) wrapper.getSession().getAttribute(SessionConstants.LOGIN_ATTRIBUTE.getContent());
		}
		catch (Exception t)
		{
			return new RedirectComand();
		}
		if (null != user)
		{
			int idroles = user.getIdroles();
			switch (idroles)
			{			
			case 1:
			case 2:
				switch(id)
				{
				case 1:
					return new RedirectComand();
				case 2:
					return new LoginCommand();
				case 3: 
					return new MainPageRedirect();
				case 4:
					return new LogoutCommand();
				case 5:
					return new CheckCommand();
				case 6:
					return new HasCheckedCommand();
				case 9:
					return new CreateNewExamCommand();
				case 10:
					return new CreateWriteCommand();
				case 11:
					return new ChangeLanguageCommand();
				case 14:
					return new ShowPassedCommand();
				case 15:
					return new AdminpageCommand();
				case 16:
					return new ExecuteCachedCommand();
				default:
					return new RedirectComand();
				}
			case 3:
				switch(id)
				{
				case 1:
					return new RedirectComand();
				case 2:
					return new LoginCommand();
				case 3: 
					return new MainPageRedirect();
				case 4:
					return new LogoutCommand();
				case 7:
					return new RegisterExamsCommand();
				case 8:
					return new SetFacultyCommand();
				case 11:
					return new ChangeLanguageCommand();
				case 12:
					return new PassExamCommand();
				case 13:
					return new AddContentCommand();
				case 14:
					return new ShowPassedCommand();
				case 16:
					return new ExecuteCachedCommand();
				default:
					return new RedirectComand();
				}				
			}			
			return new RedirectComand();
		}
		else
		{
			switch(id)
			{
			case 1:
				return new RedirectComand();
			case 2:
				return new LoginCommand();
			case 3:
				return new MainPageRedirect();
			case 4:
				return new LogoutCommand();
			case 11:
				return new ChangeLanguageCommand();
			case 16:
				return new ExecuteCachedCommand();
			default:
				return new RedirectComand();
			}			
		}
	}
}