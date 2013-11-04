package by.epam.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import by.epam.buisnesslogicbean.FacultiesCompiler;
import by.epam.buisnesslogicbean.exception.LogicException;
import by.epam.db.dao.ExamDao;
import by.epam.db.dao.FacultyDao;
import by.epam.db.dao.UserDao;
import by.epam.db.dao.factory.DAOFactory;
import by.epam.db.exception.DaoException;
import by.epam.entity.Faculty;
import by.epam.entity.User;
import by.epam.web.service.exception.ServiceException;
/**
 * This signletone class connects Dao Faculty layer with web layer represented by command package.
 * @author Dmitry Shanko
 *
 */
public class FacultyService 
{
	private static FacultyService instance;
	private static Lock singletoneLock = new ReentrantLock();
	private FacultyDao fDao;
	/**
	 * Private constructor, do only this.fDao = this.getFacultyDao(), where getFacultyDao() takes DAOFactory.MYSQL.getFacultyDao() or throws ServiceException if can't.
	 * @throws ServiceException
	 */
	private FacultyService() throws ServiceException
	{
		this.fDao = this.getFacultyDao();
	}
	/**
	 * Method that uses lock to prevent threads from creating many instances of this class
	 * @return the singletone instance of FacultyService class
	 * @throws ServiceException
	 */
	public static FacultyService getInstance() throws ServiceException
	{
		singletoneLock.lock();
		if (null == FacultyService.instance)
		{
			FacultyService.instance = new FacultyService();
		}
		singletoneLock.unlock();
		return FacultyService.instance;
	}
	/**
	 * Method to collect all existing faculties in database.<p>
	 * Uses FacultyDao.collectFaculties();
	 * @return List with all existing faculties
	 * @throws ServiceException
	 */
	public List<Faculty> collectPossibleFacultiesForNewExam() throws ServiceException
	{
		List<Faculty> faculties = new ArrayList<Faculty>(0);
		try 
		{
			faculties = fDao.collectFaculties();
		} 
		catch (DaoException e) 
		{
			throw new ServiceException("Can't collect faculties from " + fDao.getClass(), e);
		}
		return faculties;
	}
	/**
	 * Method to collect all possible idfaculties in database.<p>
	 * Uses FacultyDao.collectIdFaculties();
	 * @return List with all possible idfaculties.
	 * @throws ServiceException
	 */
	public List<Integer> collectPossibleIdFacultiesForNewExam() throws ServiceException
	{
		List<Integer> idfaculties = new ArrayList<Integer>(0);
		try
		{
			idfaculties = fDao.collectIdFaculties();
		}
		catch (DaoException e)
		{
			throw new ServiceException("Can't collect IdFaculties from " + fDao.getClass(), e);
		}
		return idfaculties;
	}
	/**
	 * Creates Logic Bean that contains User and logic of collecting all students that have passed their exams.
	 * @param user If user has entered to any faculty, this variable will make possible to mark this student in list.
	 * @return FacultyCompiler object that contains logic of collecting all students that have passed their exams.
	 * @throws ServiceException 
	 */
	public FacultiesCompiler getFacultyCompiler(User user) throws ServiceException
	{
		try 
		{
			return new FacultiesCompiler(user, getExamDao(), getFacultyDao(), getUserDao());
		} 
		catch (LogicException e) 
		{
			throw new ServiceException("Can't create new FacultiesCompiler", e);
		} 
		catch (ServiceException e) 
		{
			throw e;
		}
	}
	/**
	 * Gets MYSQL FacultyDao from DAOFactory. To change used dao you must change used DAOFactory enum in this method.
	 * @return FacultyDao from DAOFactory
	 * @throws ServiceException
	 */
	private FacultyDao getFacultyDao() throws ServiceException
	{
		try 
		{
			return DAOFactory.MYSQL.getFacultyDao();
		} 
		catch (DaoException e) 
		{
			throw new ServiceException("Can't get MYSQL FacultyDao", e);
		}
	}
	/**
	 * Gets MYSQL ExamDao from DAOFactory. To change used dao you must change used DAOFactory enum in this method.
	 * @return ExamDao from DAOFactory
	 * @throws ServiceException
	 */
	private ExamDao getExamDao() throws ServiceException
	{
		try 
		{
			return DAOFactory.MYSQL.getExamDao();
		} 
		catch (DaoException e) 
		{
			throw new ServiceException("Can't get MYSQL ExamDao", e);
		}
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
