package by.epam.xml;

import java.util.Map;
/**
 * Interface that provides parse method to connect parsers with logic. Logic connects parsers with builder.
 * @author Dmitry Shanko
 *
 */
public interface MyXMLReader 
{
	Map<XMLEnums, String> parse();

}
