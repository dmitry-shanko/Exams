package by.epam.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.resources.RequestConstants;
import by.epam.util.HttpRequestResponseWrapper;
import by.epam.util.Wrapper;
import by.epam.util.WrapperException;
import by.epam.web.command.Command;
import by.epam.web.command.CommandFactory;
import by.epam.web.command.exception.CommandException;
/**
 * Servlet implementation class MainServlet
 */
public class MainServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
/**
 * Inits the servlet and sets path to the resources in Resources class with getServletContext().getRealPath("")
 */
	@Override
	public void init() throws ServletException 
	{
		super.init();
//		pathclass.setPath(getServletContext().getRealPath(""));
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	/**
	 * Takes command parameter from request (RequestConstants.COMMAND_PARAMETER) and then executes the Command from CommandFactory by this command parameter.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String command = request.getParameter(RequestConstants.COMMAND_PARAMETER.getContent());
		Command com = null;
		Wrapper wrapper = null;
		try 
		{
			wrapper = new HttpRequestResponseWrapper(request, response);
		} 
		catch (WrapperException e) 
		{
			throw new ServletException("Null in request/response in MainServlet. Can't create HttpRequestResponseWrapper", e);
		}
		if (null != command)
		{
			for (CommandFactory cf : CommandFactory.values())
			{
				if (cf.toString().equals(command))
				{
					com = cf.getCommand(wrapper);
				}
			}
		}
		if (null == com)
		{
			com = CommandFactory.mainpage.getCommand(wrapper);
		}
		try
		{
			com.execute(wrapper);
		} 
		catch (CommandException e) 
		{
			response.sendError(500);
		}
	}
}
