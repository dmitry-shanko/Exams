package by.epam.xml.sax;

import by.epam.resources.Resources;

import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
/**
 * Default ErrorHandler for this application.
 * @author Dmitry Shanko
 *
 */
public class SAXErrorHandler implements ErrorHandler
{
	private static final Resources res = Resources.getInstance();
	private static final Logger log = res.getLogger(by.epam.xml.sax.SAXErrorHandler.class);

	public SAXErrorHandler() 
	{

	}

	private String getLineAddress(SAXParseException e) 
	{
		return e.getLineNumber() + " : " + e.getColumnNumber();
	}
	/**
	 * Overridden method to log some error in parsing
	 */
	@Override
	public void error(SAXParseException e) throws SAXParseException 
	{
		log.error(getLineAddress(e) + " - " + e.getMessage());	
		throw e;
	}
	/**
	 * Overridden method to log some Fatal error in parsing
	 */
	@Override
	public void fatalError(SAXParseException e) throws SAXParseException 
	{
		log.fatal(getLineAddress(e) + " - "	+ e.getMessage());	
		throw e;
	}
	/**
	 * Overridden method to log some warning in parsing
	 */
	@Override
	public void warning(SAXParseException e) throws SAXParseException  
	{		
		log.warn(getLineAddress(e) + "-" + e.getMessage());		
		throw e;
	}

}
