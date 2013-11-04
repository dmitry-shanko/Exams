package by.epam.xml;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import by.epam.resources.Resources;
import by.epam.xml.exception.NoXMLReaderException;
import by.epam.xml.exception.NotValidXMLException;
import by.epam.xml.sax.SAXErrorHandler;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class XMLParser
{
	private static final Resources res = Resources.getInstance();
	private static final String pathString = "path";
	private static final String schemaNameString = "schemaName";
	private static final String defaultFileName = "";
	private static final String defaultXSDFileName = "";	
	private static final String xsdEnd = "xsd";
	private static final Logger log = res.getLogger(by.epam.xml.XMLParser.class);
	private static final Lock lock1 = new ReentrantLock();
	private static String fileName;
	private static String schemaName;
	private static XMLParser SAMXMLParser;	
	private MyXMLReader reader;
	private Map<XMLEnums, String> data;
	static
	{
		Properties p = new Properties();
		try 
		{
			p.load(new FileReader(res.getPath().concat(res.getPropertiesFile())));
			fileName = res.getPath().concat(p.getProperty(pathString));
			schemaName = res.getPath().concat(p.getProperty(schemaNameString));
		} 
		catch (IOException e) 
		{
			log.error("ERROR in XML Parses");
			log.error("IOException", e);
			fileName = defaultFileName;
			schemaName = defaultXSDFileName;
		}
	}
	/**
	 * Attempts to create new XMLParser to validate existing XML files. Can be created only for valid XML files.<p>
	 * Validates XMLFile by known XSD or DTS file.<p>
	 * Uses default Schema Validator to XSD files or DOMParser with ErrorHandler to DTD validation.
	 * @param r Any XMLReader
	 * @throws NoXMLReaderException
	 * @throws NotValidXMLException
	 */
	private XMLParser(MyXMLReader r) throws NoXMLReaderException, NotValidXMLException
	{
		if (r != null)
		{
			this.reader = r;
			try 
			{
				if (schemaName != null && schemaName.toLowerCase().endsWith(xsdEnd))
				{
					SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);	        
					Schema schema = factory.newSchema(new File(schemaName));		    
					Validator validator = schema.newValidator();	
					Source source = new StreamSource(fileName);		        
					validator.validate(source);
				}
				else
				{
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					dbf.setNamespaceAware(true);
					dbf.setValidating(true);
					DocumentBuilder db;
					try 
					{
						db = dbf.newDocumentBuilder();
						db.setErrorHandler(new SAXErrorHandler());
						db.parse(fileName);
					} 
					catch (ParserConfigurationException e) 
					{
						throw new NotValidXMLException("Can't validate XML file because of ParserConfigurationException", e);
					}
				}
			}
			catch (SAXException ex) 
			{
				log.error("XML file is not valid", ex);
				throw new NotValidXMLException("XML file is not valid, can't create parser to work with it", ex);
			} 
			catch (IOException e) 
			{
				throw new NotValidXMLException("Can't validate XML file because of IOException", e);
			}  
		}
		else
		{
			throw new NoXMLReaderException();
		}
	}
	/**
	 * Creates SAXXMLReader to parse XMLFile with path got from properties file.
	 * @return Object of XMLParser with SAXXMLReader object.
	 * @throws NoXMLReaderException
	 * @throws NotValidXMLException
	 */
	private static XMLParser createLogicForSAXXMLReader() throws NoXMLReaderException, NotValidXMLException
	{
		MyXMLReader reader;
		try 
		{
			reader = new SAXXMLReader(fileName);
		} 
		catch (SAXException e) 
		{
			log.error("SAXException in trying to create DOMXMLReader", e);
			throw new NoXMLReaderException("SAXXMLReader couldn't be created", e);
		} 
		catch (IOException e) 
		{
			log.error("IOException in trying to create DOMXMLReader", e);
			throw new NoXMLReaderException("SAXXMLReader couldn't be created", e);
		} 
		return new XMLParser(reader);
	}
	/**
	 * Getter for the singletone istance of XMLParser for SAX XML Reader.
	 * @return Singletone instance of SAX XML Parser.
	 * @throws NoXMLReaderException
	 * @throws NotValidXMLException
	 */
	public static XMLParser getSAMXMLReader() throws NoXMLReaderException, NotValidXMLException
	{
		lock1.lock();
		if (null == SAMXMLParser)
		{
			XMLParser.SAMXMLParser = XMLParser.createLogicForSAXXMLReader();
			lock1.unlock();
		}
		return XMLParser.SAMXMLParser;
	}
	/**
	 * Collects all data from XML file.
	 * @return Collected data from XML file
	 */
	public Map<XMLEnums, String> parse()
	{
		lock1.lock();
		if (null == data)
		{
			data = reader.parse();
			lock1.unlock();
		}
		return data;
	}

}
