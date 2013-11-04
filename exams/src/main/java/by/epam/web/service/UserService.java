package by.epam.web.service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import by.epam.buisnesslogicbean.LecturerCompiler;
import by.epam.buisnesslogicbean.MainadminCompiler;
import by.epam.buisnesslogicbean.Compiler;
import by.epam.buisnesslogicbean.StudentCompiler;
import by.epam.buisnesslogicbean.exception.LogicException;
import by.epam.db.dao.UserDao;
import by.epam.db.dao.factory.DAOFactory;
import by.epam.db.exception.DaoException;
import by.epam.entity.User;
import by.epam.web.service.exception.ServiceException;
/**
 * This signletone class connects Dao layer of UserDao with web layer represented by command package.
 * @author Dmitry Shanko
 *
 */
public class UserService
{
	private static UserService instance;
	private static Lock singletoneLock = new ReentrantLock();
	private UserDao uDao;
	/**
	 * Private constructor, do only this.uDao = this.getUserDao(), where getUserDao() takes DAOFactory.MYSQL.getUserDao() or throws ServiceException if can't.
	 * @throws ServiceException
	 */
	private UserService() throws ServiceException
	{
		this.uDao = this.getUserDao();
	}
	/**
	 * Method that uses lock to prevent threads from creating many instances of this class
	 * @return the singletone instance of UserService class
	 * @throws ServiceException
	 */
	public static UserService getInstance() throws ServiceException
	{
		singletoneLock.lock();
		if (null == UserService.instance)
		{
			UserService.instance = new UserService();
		}
		singletoneLock.unlock();
		return UserService.instance;
	}
	/**
	 * Method to get User from UserDao by key fields password and email.
	 * @param password password of user to look for
	 * @param email email of user to look for
	 * @return User got by password and email.
	 * @throws ServiceException
	 */
	public User getUser(String password, String email) throws ServiceException
	{
		try 
		{					
			return uDao.getUserByEmail(email, password);
		} 
		catch (DaoException e) 
		{
			throw new ServiceException("Can't get user from " + uDao.getClass(), e);
		}				
	}
	/**
	 * Creates proper business logic bean for User bean. Business logic beans can be found in package by.epam.buisnesslogicbean;
	 * @param user User to be converted to Compiler logic bean
	 * @return Compiler for user.
	 * @throws ServiceException
	 */
	public Compiler getCompilerForUser(User user) throws ServiceException
	{
		if (null != user)
		{
			try
			{
				switch (user.getIdroles())
				{
				case 1:	
					return new MainadminCompiler(user, DAOFactory.MYSQL.getExamDao());
				case 2:
					return new LecturerCompiler(user, DAOFactory.MYSQL.getExamDao());
				case 3:
					return new StudentCompiler(user, DAOFactory.MYSQL.getExamDao());
				default:
					break;
				}
			}
			catch (LogicException | DaoException e)
			{
				throw new ServiceException("Error in trying to create decorator for " + user + " user.", e);
			}
		}
		else
		{
			throw new ServiceException("Attempt to create decorator for null User");
		}
		return null;
	}
	/**
	 * Gets MYSQL UserDao from DAOFactory. To change used dao you must change used DAOFactory enum in this method.
	 * @return UserDao from DAOFactory
	 * @throws ServiceException
	 */
	private UserDao getUserDao() throws ServiceException
	{
		try 
		{
			return DAOFactory.MYSQL.getUserDao();
		} 
		catch (DaoException e) 
		{
			throw new ServiceException("Can't get MYSQL UserDao", e);
		}
	}
}
