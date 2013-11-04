package by.epam.resources;

import java.util.Locale;
/**
 * Interface of Localizator of any resources for java classes
 * @author Dmitry Shanko
 *
 */
public interface Localizator 
{
	String getString(String key);
	String getEnum(Enum<? extends Enum<?>> e);
	void setNewBundle(Locale locale);
	void setNewStringResBundle(String baseName, Locale locale);
	void setNewEnumResBundle(String baseName, Locale locale);
	
}
