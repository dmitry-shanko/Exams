package by.epam.resources;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
/**
 * Class that provides the possibility of localization java classes by common resources.
 * @author Dmitry Shanko
 *
 */
public class ResourceLocalizator implements Localizator
{
	private static final Resources res = Resources.getInstance();
	private static final Logger log = res.getLogger(by.epam.resources.ResourceLocalizator.class);
	private ResourceBundle bundle; //= getBundle(stringResources, Locale.getDefault());
	private ResourceBundle enumBundle = getBundle(res.getEnumResources(), Locale.getDefault());

	private ResourceLocalizator()
	{
	}	
	/**
	 * Inner Static class. Provides Singleton pattern.
	 * @author Dmitry Shanko
	 *
	 */
	private static class SingletonHolder
	{
		public static final ResourceLocalizator instance = new ResourceLocalizator();
	}
	/**
	 * Method to get singleton instance of this class
	 * @return Reference to the singleton object of this class
	 */
	public static Localizator getLocalizator()
	{
		return SingletonHolder.instance;
	}

	private ResourceBundle getBundle(String baseName, Locale locale)
	{
		try
		{
			return ResourceBundle.getBundle(baseName, locale);
		}
		catch (MissingResourceException e)
		{
			log.error("Missing resource", e);
			return null;
		}
	}

	public void setNewBundle(Locale locale)
	{
		if (locale != null)
		{
			ResourceBundle b = getBundle(res.getStringResources(), locale);
			if (b != null)
			{
				bundle = b;
			}
			b = getBundle(res.getEnumResources(), locale);
			if (b != null)
			{
				enumBundle = b;
			}
		}
	}

	public void setNewStringResBundle(String baseName, Locale locale)
	{
		if (baseName != null && locale != null)
		{
			ResourceBundle b = getBundle(baseName, locale);
			if (b != null)
			{
				bundle = b;
			}
		}
	}
	
	public void setNewEnumResBundle(String baseName, Locale locale)
	{
		if (baseName != null && locale != null)
		{
			ResourceBundle b = getBundle(baseName, locale);
			if (b != null)
			{
				enumBundle = b;
			}
		}
	}

	public String getString(String key)
	{
		if (bundle == null || key == null)
		{
			return "";
		}
		else
		{
			try
			{			
				return bundle.getString(key);
			}
			catch (MissingResourceException e)
			{
				log.error("Incorrect key at resource file", e);
				return "";
			}
		}
	}
	
	public String getEnum(Enum<? extends Enum<?>> en)
	{
		if (enumBundle == null || en == null)
		{
			return "";
		}
		else
		{
			try
			{			
				return enumBundle.getString(en.name());
			}
			catch (MissingResourceException e)
			{
				log.error("Incorrect key at resource file", e);
				return "";
			}
		}
	}
}
