package by.epam.db.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import by.epam.db.dao.UserDao;
import by.epam.db.dao.mysql.constant.UserFields;
import by.epam.db.exception.DaoException;
import by.epam.db.exception.NoConnectionException;
import by.epam.db.pool.ConnectionPool;
import by.epam.db.pool.PooledConnection;
import by.epam.db.pool.exception.ConnectionSQLException;
import by.epam.entity.User;
import by.epam.entity.factory.EntitityFactory;
import by.epam.resources.Resources;

public class MySQLUserDao implements UserDao
{
	/**
	 * 
	 */
	private static final Resources res = Resources.getInstance();
	private static final Logger log = res.getLogger(by.epam.db.dao.mysql.MySQLUserDao.class);
	private static final Lock singletonLock = new ReentrantLock();
	private static final EntitityFactory factory = EntitityFactory.getInstance();
	private static MySQLUserDao instance;
	private static ConnectionPool cp;
	/**
	 * Private constructor. References this MySQLStudentDao to existing ConnectionPool
	 * @throws DaoException
	 */
	private MySQLUserDao() throws DaoException
	{
		try 
		{
			MySQLUserDao.cp = ConnectionPool.getInstance();
		} 
		catch (NoConnectionException | ConnectionSQLException e) 
		{
			log.error("Exception in trying to initialize ConnectionPool. Can't create MySQLStudentDao", e);
			throw new DaoException("Exception in trying to initialize ConnectionPool. Can't create MySQLStudentDao", e);
		}
	}	
	/**
	 * Method to get singleton instance of MySQLStudentDao class.
	 * @return Singleton instance of MySQLStudentDao.
	 * @throws DaoException
	 */
	public static MySQLUserDao getInstance() throws DaoException
	{
		singletonLock.lock();
		if (null == MySQLUserDao.instance)
		{
			MySQLUserDao.instance = new MySQLUserDao();			
		}
		singletonLock.unlock();
		return instance;
	}
	/**
	 * Takes user by email and password from mysql database.
	 * @param email email for the search
	 * @param password password for the search
	 * @return User from the result.
	 */
	@Override
	public User getUserByEmail(String email, String password) throws DaoException 
	{		
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		if (null != email && null != password)
		{
			try 
			{
				connection = (PooledConnection) MySQLUserDao.cp.takeConnection();
				pStatement = connection.prepareStatement(MySQLUserDaoStatement.getUserByEmail.getStatement());
				pStatement.setString(1, email);
				pStatement.setString(2, password);
				return parseUser(pStatement.executeQuery());
			} 
			catch (NoConnectionException | ConnectionSQLException e) 
			{
				log.error("Can't take connection from ConnectionPool", e);
				throw new DaoException("Can't take connection from ConnectionPool", e);
			} 
			catch (SQLException e) 
			{
				log.error("Error in SQL statement " + MySQLUserDaoStatement.getUserByEmail.getStatement(), e);
				throw new DaoException("Error in SQL statement " + MySQLUserDaoStatement.getUserByEmail.getStatement(), e);
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
		else
		{
			throw new DaoException("Can't make a request to database because of uncorrected request parameters");
		}
	}
	/**
	 * Collects all users that has any exam from such idfaculty.
	 * @param idfaculty id of the faculty to search
	 * @return List of users that has connections to such idfaculty 
	 */
	@Override
	public List<User> getUsersByIdfaculty(int idfaculty) throws DaoException 
	{
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		try 
		{
			connection = (PooledConnection) MySQLUserDao.cp.takeConnection();
			pStatement = connection.prepareStatement(MySQLUserDaoStatement.getUsersByIdfaculty.getStatement());
			pStatement.setInt(1, idfaculty);
			return parseUsers(pStatement.executeQuery());
		} 
		catch (NoConnectionException | ConnectionSQLException e) 
		{
			log.error("Can't take connection from ConnectionPool", e);
			throw new DaoException("Can't take connection from ConnectionPool", e);
		} 
		catch (SQLException e) 
		{
			log.error("Error in SQL statement " + MySQLUserDaoStatement.getUsersByIdfaculty.getStatement(), e);
			throw new DaoException("Error in SQL statement " + MySQLUserDaoStatement.getUsersByIdfaculty.getStatement(), e);
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
	 * Parses User from the ResultSet using enums from UserFields.
	 * @param rs ResultSet to be parsed.
	 * @return Compiled User bean.
	 * @throws DaoException
	 */
	private User parseUser(ResultSet rs) throws DaoException
	{

		if (rs != null)
		{
			try 
			{
				User user = null;
				while(rs.next())
				{
					user = factory.createNewUser();
					if (user != null)
					{
						user.setIdroles(rs.getInt(UserFields.idroles.name()));
						user.setIdusers(rs.getInt(UserFields.idusers.name()));
						user.setName(rs.getString(UserFields.name.name()));
						user.setSurname(rs.getString(UserFields.surname.name()));
						return user;
					}									
				}			
			} 
			catch (SQLException e) 
			{
				log.error("Error in trying to parse User through resultSet", e);
				throw new DaoException("Error in trying to parse User through resultSet", e);
			}
		}
		return null;
	}
	/**
	 * Parses list of Users from the ResultSet using enums from UserFields.
	 * @param rs ResultSet to be parsed.
	 * @return List of compiled User bean.
	 * @throws DaoException
	 */
	private List<User> parseUsers(ResultSet rs) throws DaoException
	{
		List<User> users = new ArrayList<User>();
		if (rs != null)
		{
			try 
			{
				User user;
				while(rs.next())
				{
					user = factory.createNewUser();
					if (user != null)
					{
						user.setIdroles(rs.getInt(UserFields.idroles.name()));
						user.setIdusers(rs.getInt(UserFields.idusers.name()));
						user.setName(rs.getString(UserFields.name.name()));
						user.setSurname(rs.getString(UserFields.surname.name()));
						users.add(user);
					}									
				}			
			} 
			catch (SQLException e) 
			{
				log.error("Error in trying to parse User through resultSet", e);
				throw new DaoException("Error in trying to parse User through resultSet", e);
			}
		}
		return users;
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
