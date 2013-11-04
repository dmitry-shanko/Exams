package by.epam.resources;
/**
 * Reference to the class that localizes java classes.
 * @author Dmitry Shanko
 *
 */
public interface LocalizationRef 
{
	Localizator loc = ResourceLocalizator.getLocalizator();
}
