package by.epam.db.dao.factory;

import by.epam.db.dao.ExamDao;
import by.epam.db.dao.FacultyDao;
import by.epam.db.dao.UserDao;
import by.epam.db.dao.mysql.MySQLExamDao;
import by.epam.db.dao.mysql.MySQLFacultyDao;
import by.epam.db.dao.mysql.MySQLUserDao;
import by.epam.db.exception.DaoException;
/**
 * Enum Factory for Dao.<p>
 * Uses special id to each RDBMS.
 * @author Dmitry Shanko
 *
 */
public enum DAOFactory
{
	MYSQL(1), ORACLE(2), COMMON(3);
	private int id;
	private static DAOFactory e = DAOFactory.COMMON;
	private DAOFactory(int id)
	{
		this.id = id;
	}
	/**
	 * Gets StudentDao for DaoFactory enum. Enum must be chosen to get instance of Dao.
	 * @return Instance of proper UserDao
	 * @throws DaoException
	 */
	public UserDao getUserDao() throws DaoException 
	{
		switch(id)
		{
		case 1:
			return MySQLUserDao.getInstance();
		case 2:
			break;
		default:
			break;
		}
		throw new DaoException("No StudentDao can be created for such Dao Factory enum");		
	}
	/**
	 * Gets ExamDao for DaoFactory enum. Enum must be chosen to get instance of Dao.
	 * @return Instance of proper ExamDao
	 * @throws DaoException
	 */
	public ExamDao getExamDao() throws DaoException 
	{
		switch(id)
		{
		case 1:
			return MySQLExamDao.getInstance();
		case 2:
			break;
		default:
			break;
		}
		throw new DaoException("No ExamDao can be created for such Dao Factory enum");	
	}
	/**
	 * Gets FacultyDao for DaoFactory enum. Enum must be chosen to get instance of Dao.
	 * @return Instance of proper FacultyDao
	 * @throws DaoException
	 */
	public FacultyDao getFacultyDao() throws DaoException
	{
		switch(id)
		{
		case 1:
			return MySQLFacultyDao.getInstance();
		case 2:
			break;
		default:
			break;
		}
		throw new DaoException("No FacultyDao can be created for such Dao Factory enum");	
	}
	/**
	 * This method must be used before calling any static method with "Initialized" in its names.<p>
	 * Takes id of DaoFactory enum and sets its value to private static variable. It makes possible to get concrete Dao without choosing enum.
	 * @param e DaoFactory enum to be set as static initializing variable
	 */
	public static void init(DAOFactory e)
	{
		if (null != e)
		{
			DAOFactory.e = e;
		}
	}
	/**
	 * Gets UserDao for initialized DaoFactory enum. Enum must be initialized in DaoFactory to get instance of Dao.
	 * @return Instance of proper UserDao
	 * @throws DaoException
	 */
	public static UserDao getInitializedUserDao() throws DaoException 
	{
		switch(DAOFactory.e.id)
		{
		case 1:
			return MySQLUserDao.getInstance();
		case 2:
			break;
		default:
			break;
		}
		throw new DaoException("No StudentDao can be created for such initialized Dao Factory enum. Or no StudentDao has been initialized yet.");		
	}
	/**
	 * Gets ExamDao for initialized DaoFactory enum. Enum must be initialized in DaoFactory to get instance of Dao.
	 * @return Instance of proper ExamDao
	 * @throws DaoException
	 */
	public static ExamDao getInitializedExamDao() throws DaoException 
	{
		switch(DAOFactory.e.id)
		{
		case 1:
			return MySQLExamDao.getInstance();
		case 2:
			break;
		default:
			break;
		}
		throw new DaoException("No ExamDao can be created for such initialized Dao Factory enum. Or no ExamDao has been initialized yet.");	
	}
	/**
	 * Gets FacultyDao for initialized DaoFactory enum. Enum must be initialized in DaoFactory to get instance of Dao.
	 * @return Instance of proper FacultyDao
	 * @throws DaoException
	 */
	public static FacultyDao getInitializedFacultyDao() throws DaoException
	{
		switch(DAOFactory.e.id)
		{
		case 1:
			return MySQLFacultyDao.getInstance();
		case 2:
			break;
		default:
			break;
		}
		throw new DaoException("No FacultyDao can be created for such initialized Dao Factory enum. Or no FacultyDao has been initialized yet.");	
	}
}
