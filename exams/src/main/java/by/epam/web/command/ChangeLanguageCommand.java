package by.epam.web.command;

import javax.servlet.http.Cookie;

import by.epam.resources.RequestConstants;
import by.epam.resources.SessionConstants;
import by.epam.util.Wrapper;
import by.epam.web.command.exception.CommandException;
/**
 * Changes the locale attribute in session (and cookies also).<p>
 * Executes the cached command by CommandFactory.cached.getCommand(wrapper).execute(wrapper);
 * @author Dmitry Shanko
 *
 */
public class ChangeLanguageCommand extends Command 
{
	@Override
	public void execute(Wrapper wrapper) throws CommandException 
	{
		String lang = wrapper.getRequest().getParameter(RequestConstants.LANG_PARAMETER.getContent());
		if (null != lang)
		{
			lang = lang.toLowerCase().trim();
			if (null != lang && (lang.matches("ru") || lang.matches("en")))
			{
				switch(lang)
				{
				case "ru":
					lang = lang.concat("_RU");
					break;
				case "en":
					lang = lang.concat("_US");
					break;
				}
				int day = 60 * 60 * 24;
				wrapper.getRequest().getSession().setAttribute(SessionConstants.LANG_ATTRIBUTE.getContent(), lang);
				Cookie cookie = new Cookie(SessionConstants.LANG_ATTRIBUTE.getContent(), lang);
				cookie.setMaxAge(day);
				wrapper.getResponse().addCookie(cookie);
			}
		}		
		CommandFactory.cached.getCommand(wrapper).execute(wrapper);
	}
}