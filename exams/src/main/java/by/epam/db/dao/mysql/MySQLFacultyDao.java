package by.epam.db.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import by.epam.db.dao.FacultyDao;
import by.epam.db.dao.mysql.constant.FacultyFields;
import by.epam.db.exception.DaoException;
import by.epam.db.exception.NoConnectionException;
import by.epam.db.pool.ConnectionPool;
import by.epam.db.pool.PooledConnection;
import by.epam.db.pool.exception.ConnectionSQLException;
import by.epam.entity.Faculty;
import by.epam.entity.factory.EntitityFactory;
import by.epam.resources.Resources;

public class MySQLFacultyDao implements FacultyDao
{

	/**
	 * 
	 */
	private static final Resources res = Resources.getInstance();
	private static final Logger log = res.getLogger(by.epam.db.dao.mysql.MySQLFacultyDao.class);
	private static final Lock singletonLock = new ReentrantLock();
	private static final EntitityFactory factory = EntitityFactory.getInstance();
	private static MySQLFacultyDao instance;
	private static ConnectionPool cp;
	private Faculty faculty;
	/**
	 * Private constructor. References this MySQLStudentDao to existing ConnectionPool
	 * @throws DaoException
	 */
	private MySQLFacultyDao() throws DaoException
	{
		try 
		{
			MySQLFacultyDao.cp = ConnectionPool.getInstance();
		} 
		catch (NoConnectionException | ConnectionSQLException e) 
		{
			log.error("Exception in trying to initialize ConnectionPool. Can't create MySQLFacultyDao", e);
			throw new DaoException("Exception in trying to initialize ConnectionPool. Can't create MySQLFacultyDao", e);
		}
	}	
	/**
	 * Method to get singleton instance of MySQLStudentDao class.
	 * @return Singleton instance of MySQLStudentDao.
	 * @throws DaoException
	 */
	public static MySQLFacultyDao getInstance() throws DaoException
	{
		singletonLock.lock();
		if (MySQLFacultyDao.instance == null)
		{
			MySQLFacultyDao.instance = new MySQLFacultyDao();			
		}
		singletonLock.unlock();
		return instance;
	}
	/**
	 * Collects all faculties from MYSQL database.
	 * @return List with all faculties.
	 */
	@Override
	public List<Faculty> collectFaculties() throws DaoException 
	{
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		try 
		{
			connection = (PooledConnection) MySQLFacultyDao.cp.takeConnection();
			pStatement = connection.prepareStatement(MySQLFacultyDaoStatement.collectAllFaculties.getStatement());
			return parseFaculties(pStatement.executeQuery());
		} 
		catch (NoConnectionException | ConnectionSQLException e) 
		{
			log.error("Can't take connection from ConnectionPool", e);
			throw new DaoException("Can't take connection from ConnectionPool", e);
		} 
		catch (SQLException e) 
		{
			log.error("Error in SQL statement " + MySQLFacultyDaoStatement.collectAllFaculties.getStatement(), e);
			throw new DaoException("Error in SQL statement " + MySQLFacultyDaoStatement.collectAllFaculties.getStatement(), e);
		}
		finally
		{
			if (null != connection)
			{
				try 
				{
					this.closeStatement(pStatement);
					cp.releaseConnection(connection);
				} 
				catch (ConnectionSQLException e) 
				{
					log.error("Can't release connection back", e);
					throw new DaoException("Can't release connection back", e);
				}
			}
		}
	}
	/**
	 * Collects only idfaculties from MYSQL database
	 * @return List with all idfaculties.
	 */
	@Override
	public List<Integer> collectIdFaculties() throws DaoException 
	{
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		try 
		{
			connection = (PooledConnection) MySQLFacultyDao.cp.takeConnection();
			pStatement = connection.prepareStatement(MySQLFacultyDaoStatement.collectIdFaculties.getStatement());
			return parseIdFaculties(pStatement.executeQuery());
		} 
		catch (NoConnectionException | ConnectionSQLException e) 
		{
			log.error("Can't take connection from ConnectionPool", e);
			throw new DaoException("Can't take connection from ConnectionPool", e);
		} 
		catch (SQLException e) 
		{
			log.error("Error in SQL statement " + MySQLFacultyDaoStatement.collectIdFaculties.getStatement(), e);
			throw new DaoException("Error in SQL statement " + MySQLFacultyDaoStatement.collectIdFaculties.getStatement(), e);
		}
		finally
		{
			if (null != connection)
			{
				try 
				{
					this.closeStatement(pStatement);
					cp.releaseConnection(connection);
				} 
				catch (ConnectionSQLException e) 
				{
					log.error("Can't release connection back", e);
					throw new DaoException("Can't release connection back", e);
				}
			}
		}
	}
	
	/**
	 * Parses list of Faculties from the ResultSet using enums from FacultyFields.
	 * @param rs ResultSet to be parsed.
	 * @return List of compiled Faculty beans.
	 * @throws DaoException
	 */
	private List<Faculty> parseFaculties(ResultSet rs) throws DaoException
	{
		List<Faculty> faculties = new ArrayList<Faculty>();
		if (rs != null)
		{
			try 
			{
				while(rs.next())
				{
					faculty = factory.createNewFaculty();
					faculty.setId(rs.getInt(FacultyFields.idfaculties.name()));
					faculty.setName(rs.getString(FacultyFields.name.name()));
					faculty.setMaxstudents(rs.getInt(FacultyFields.maxstudents.name()));
					faculties.add(faculty);				
				}			
			} 
			catch (SQLException e) 
			{
				log.error("Error in trying to parse Facultues through resultSet", e);
				throw new DaoException("Error in trying to parse Faculties through resultSet", e);
			}
		}
		return faculties;
	}
	/**
	 * Parses list of Integer from the ResultSet using enums from FacultyFields idfaculties.
	 * @param rs ResultSet to be parsed.
	 * @return List of Idfaculties.
	 * @throws DaoException
	 */
	private List<Integer> parseIdFaculties(ResultSet rs) throws DaoException
	{
		List<Integer> idfaculties = new ArrayList<Integer>();
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					idfaculties.add(rs.getInt(FacultyFields.idfaculties.name()));
				}
			}
			catch (SQLException e)
			{
				log.error("Error in trying to parse IdFaculties through resultSet", e);
				throw new DaoException("Error in trying to parse IdFaculties through resultSet", e);
			}
		}
		return idfaculties;
	}
	/**
	 * Method that contains logic of closing PreparedStatement.
	 * @param pStatement the PreparedStatement to be closed.
	 */
	private void closeStatement(PreparedStatement pStatement)
	{
		try 
		{
			if (null != pStatement && !pStatement.isClosed())
			{
				pStatement.close();
			}
		} 
		catch (SQLException e) 
		{
			log.error("Error during trying to close pStatement", e);
		}
	}
}
