package by.epam.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Interface for any wrapper of HttpServletRequest and HttpServletResponse.
 * @author Dmitry Shanko.
 *
 */
public interface Wrapper 
{
	HttpServletRequest getRequest();
	HttpServletResponse getResponse();
	HttpSession getSession();


}
