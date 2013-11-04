package by.epam.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.resources.Resources;

import org.apache.log4j.Logger;
/**
 * Wrapper for HttpServletRequest and HttpServletResponse.
 * Grants easy ways of getting Session, Request and Response.
 * Can't be created with null Request or Response.
 * @author Dmitry Shanko
 *
 */
public class HttpRequestResponseWrapper implements Wrapper
{
	private static final Resources res = Resources.getInstance();
	private static Logger log = res.getLogger(by.epam.util.HttpRequestResponseWrapper.class);
	private HttpServletRequest request;
	private HttpServletResponse response;

	public HttpRequestResponseWrapper(HttpServletRequest request, HttpServletResponse response) throws WrapperException
	{
		if (null != request)
		{
			this.request = request;
		}
		else
		{
			log.error("Attempt to create empty RequestResponseWrapper (with null in HttpServletRequest request parameter)");
			throw new WrapperException("Attempt to create empty RequestResponseWrapper (with null in HttpServletRequest request parameter)");
		}
		if (null != response)
		{
			this.response = response;
		}
		else
		{
			log.error("Attempt to create empty RequestResponseWrapper (with null in HttpServletResponse response parameter)");
			throw new WrapperException("Attempt to create empty RequestResponseWrapper (with null in HttpServletResponse response parameter)");
		}		
	}	

	public void setRequest(HttpServletRequest request)
	{
		if (null != request)
		{
			this.request = request;
		}			
	}

	public void setResponse(HttpServletResponse response)
	{
		if (null != response)
		{
			this.response = response;
		}
	}
	
	public HttpServletRequest getRequest()
	{
		return request;
	}
	
	public HttpServletResponse getResponse()
	{
		return response;
	}
	
	public HttpSession getSession()
	{
		return request.getSession();
	}
	
}
