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

import by.epam.util.HttpRequestResponseWrapper;
import by.epam.util.WrapperException;
import by.epam.web.command.CommandFactory;
import by.epam.web.command.exception.CommandException;
/**
 * This filter redirects request to the CommandFactory.cached.getCommand(wrapper).execute(wrapper).<p>
 * It is used to block any access to the pages to which this filter is set.
 * @author Dmitry Shanko
 *
 */
public class RedirectFilter implements Filter
{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,	FilterChain arg2) throws IOException, ServletException 
	{
		HttpRequestResponseWrapper wrapper;
		try 
		{
			wrapper = new HttpRequestResponseWrapper((HttpServletRequest) request, (HttpServletResponse)response);
			try 
			{
				CommandFactory.cached.getCommand(wrapper).execute(wrapper);
			} 
			catch (CommandException e) 
			{
				throw new ServletException(e);
			}
		} 
		catch (WrapperException e) 
		{
			throw new ServletException(e);
		}		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
