package by.epam.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import by.epam.buisnesslogicbean.StudentCompiler;
import by.epam.buisnesslogicbean.exception.LogicException;
import by.epam.resources.SessionConstants;
/**
 * Is not user in this version of the project.<p>
 * Refreshes instance of Compiler in Session if it is the instance of StudentCompiler
 * @author Dmitry Shanko
 *
 */
@Deprecated
public class StudentContentRefreshFilter implements Filter
{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException 
	{
		if ((null != request) && (request instanceof HttpServletRequest)
				&& (null != ((HttpServletRequest)request).getSession().getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent()))
				&& (((HttpServletRequest)request).getSession().getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent()) instanceof StudentCompiler))
		{
			try 
			{
				((StudentCompiler)((HttpServletRequest)request).getSession().getAttribute(SessionConstants.COMPILER_ATTRIBUTE.getContent())).refresh();
			} 
			catch (LogicException e) 
			{	
				throw new ServletException("Can't refresh \"student\" attribute from session", e);
			}
		}
		next.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
