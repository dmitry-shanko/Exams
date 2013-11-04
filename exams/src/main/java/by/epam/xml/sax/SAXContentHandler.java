package by.epam.xml.sax;

import java.util.HashMap;
import java.util.Map;

import by.epam.xml.XMLEnums;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * My ContentHandler with some already overridden methods in DefaultHandler ancestor.
 * Is used to collect some data in SAXXMLReader or as validator of dtd files. 
 * @author Dmitry Shanko
 *
 */
public class SAXContentHandler extends DefaultHandler
{
	private Map<XMLEnums, String> data;
	private XMLEnums currentEnum;
	/**
	 * Default constructor. Creates a default object of this class
	 */
	public SAXContentHandler()
	{

	}
	/**
	 * Very necessary method of collecting Data. Provides known interface method of getting some collected data.
	 * @return Map with collected data from XML file
	 */
	public Map<XMLEnums, String> getData()
	{
		return data;
	}
	/**
	 * Initializes data Map<XMLEnums, String> when see start of document.
	 */
	@Override
	public void startDocument() throws SAXException 
	{
		data = new HashMap<XMLEnums, String>();

	}
	/**
	 * Collects main tags names and takes some attributes values from this tags.
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException 
	{
		if (null != qName)
		{
			if (!qName.equalsIgnoreCase(XMLEnums.PROPERTIESLIST.toString().trim()))
			{
				try
				{
				currentEnum = XMLEnums.valueOf(qName.trim().toUpperCase());
				}
				catch (Exception e)
				{
					currentEnum = null;
				}
			}
			else
			{
				currentEnum = null;
			}

		}
	}
	/**
	 * Method to collect some String data between opening and closing tags
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException 
	{
		if (ch != null)
		{
			String s = new String(ch, start, length).trim();
			if(currentEnum == null || s.length() < 1) 
			{
				return;
			}
			else
			{
				data.put(currentEnum, s);
			}
		}
	}
	/**
	 * Can contain some logic of closing. Is not used in this application
	 */
	@Override
	public void endDocument() throws SAXException 
	{
		return;
	}
	/**
	 * Can contain some logic when see end tag but is not used in this application because method characters already has necessary logic.
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{
		return;
	}
}
