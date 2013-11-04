package by.epam.xml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import by.epam.xml.sax.SAXContentHandler;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


public class SAXXMLReader implements MyXMLReader 
{
	private static final String defaultFile = "";
	private SAXContentHandler myHandler;
	private XMLReader reader;
	/**
	 * Default constructor. Calls this(SAXXMLReader.defaultFile).
	 * @throws SAXException
	 * @throws IOException
	 */
	public SAXXMLReader() throws SAXException, IOException
	{
		this(SAXXMLReader.defaultFile);
	}
	/**
	 * Constructor with identified XML file to read. Sets to XMLReader reader = XMLReaderFactory.createXMLReader( ) my SAXContentHandler with very important method getData().
	 * @param s Path to XML File to parse.
	 * @throws SAXException
	 * @throws IOException
	 */
	public SAXXMLReader(String s) throws SAXException, IOException
	{
		try 
		{
			reader = XMLReaderFactory.createXMLReader( );
			myHandler = new SAXContentHandler(); 
			reader.setContentHandler(myHandler); 
			try
			{
				InputSource inputSource = new InputSource(s); 
				reader.parse(inputSource);
			}
			catch (IOException e)
			{
				throw e;
			} 
		} 
		catch (SAXException e) 
		{
			throw e;
		} 

	}
	/**
	 * Overridden parse method. Collects all data from my SAXContentHandler and returns it to Logic layer.
	 * @return List with Maps with collected data from XML Parser
	 */
	@Override
	public Map<XMLEnums, String> parse() 
	{
		if (myHandler != null)
		{
			return myHandler.getData();
		}
		else
		{
			return new HashMap<XMLEnums,String>();
		}
	}
}
