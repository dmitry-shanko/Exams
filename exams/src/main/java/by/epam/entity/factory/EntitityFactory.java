package by.epam.entity.factory;

import by.epam.entity.Exam;
import by.epam.entity.Faculty;
import by.epam.entity.User;

/**
 * Factory pattern for User, Exam and Faculty entities.
 * @author Dmitry Shanko
 *
 */
public class EntitityFactory 
{
	
	private EntitityFactory()
	{
		
	}
	/**
	 * Class that provides singleton patter for EntitityFactory class
	 * @author Dmitry Shanko
	 *
	 */
	private static class SingletonHolder
	{
		public static final EntitityFactory instance = new EntitityFactory();
	}
	/**
	 * Return the singleton instance of EntitityFactory class
	 * @return Reference to the singleton EntitityFactory object
	 */
	public static EntitityFactory getInstance()
	{
		return SingletonHolder.instance;
	}
	
	public User createNewUser()
	{
		return new User();
	}
	public Exam createNewExam()
	{
		return new Exam();
	}
	
	public Faculty createNewFaculty()
	{
		return new Faculty();
	}

}
