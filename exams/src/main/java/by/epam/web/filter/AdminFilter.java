package by.epam.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.entity.User;
import by.epam.resources.PageConstants;
import by.epam.resources.SessionConstants;
/**
 * This filter prevent access from pages that requires any admin login by checking existing login attribute in session.
 * @author Dmitry Shanko
 *
 */
public class AdminFilter implements Filter
{

	@Override
	public void init(FilterConfig arg0) throws ServletException 
	{

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,	FilterChain next) throws IOException, ServletException 
	{
		if ((null != request) && (request instanceof HttpServletRequest)
				&& ((((HttpServletRequest)request).getSession().getAttribute(SessionConstants.LOGIN_ATTRIBUTE.getContent())) instanceof User))
		{
			User user = (User) (((HttpServletRequest)request).getSession().getAttribute(SessionConstants.LOGIN_ATTRIBUTE.getContent()));
			if ((null != user) && ((user.getIdroles() == 1) || (user.getIdroles() == 2)))
			{
				next.doFilter(request, response);
			}
			else
			{
				((HttpServletResponse)response).sendRedirect(PageConstants.MAINPAGE_REDIRECT.getContent());
			}
		}
		else
		{
			((HttpServletResponse)response).sendRedirect(PageConstants.MAINPAGE_REDIRECT.getContent());
		}
	}


	@Override
	public void destroy() 
	{

	}

}