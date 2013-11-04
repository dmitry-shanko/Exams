package by.epam.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import by.epam.resources.Resources;
import by.epam.resources.SessionConstants;
/**
 * This filter sets request.setCharacterEncoding("utf-8") and preferred language from cookies.
 * @author Dmitry Shanko
 *
 */
public class CharsetFilter implements Filter
{
	private static final Resources res = Resources.getInstance();
	private final String content = "utf-8";
	private static boolean init = false;	

	@Override
	public void init(FilterConfig config) throws ServletException 
	{

	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response,	FilterChain next) throws IOException, ServletException 
	{
		if (!CharsetFilter.init)
		{
			res.setPath(((HttpServletRequest)request).getSession().getServletContext().getRealPath(""));
		}
		CharsetFilter.init = true;
		request.setCharacterEncoding(content);
		if (request instanceof HttpServletRequest)
		{
			HttpServletRequest thisRequest = ((HttpServletRequest)request);
			Cookie[] cookies = thisRequest.getCookies();
			if (null != cookies)
			{
				for (Cookie cookie : cookies)
				{
					if (SessionConstants.LANG_ATTRIBUTE.getContent().equals(cookie.getName()))
					{
						String lang = cookie.getValue();
						if (null != lang && (lang.matches("ru_RU") || lang.matches("en_US")))
						{						
							thisRequest.getSession().setAttribute(SessionConstants.LANG_ATTRIBUTE.getContent(), lang);
						}
					}
				}
			}
		}
		response.setCharacterEncoding(content);
		next.doFilter(request, response);		
	}

	@Override
	public void destroy() 
	{

	}
}
