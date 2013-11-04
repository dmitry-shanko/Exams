package by.epam.db.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import by.epam.db.dao.ExamDao;
import by.epam.db.dao.mysql.constant.ExamConstants;
import by.epam.db.exception.DaoException;
import by.epam.db.exception.NoConnectionException;
import by.epam.db.pool.ConnectionPool;
import by.epam.db.pool.PooledConnection;
import by.epam.db.pool.exception.ConnectionSQLException;
import by.epam.entity.Exam;
import by.epam.entity.ExamWriteBean;
import by.epam.entity.Faculty;
import by.epam.entity.User;
import by.epam.entity.factory.EntitityFactory;
import by.epam.resources.Resources;

public class MySQLExamDao implements ExamDao
{
	/**
	 * 
	 */
	private static final Resources res = Resources.getInstance();
	private static final Logger log = res.getLogger(by.epam.db.dao.mysql.MySQLExamDao.class);
	private static final Lock singletonLock = new ReentrantLock();
	private static final EntitityFactory factory = EntitityFactory.getInstance();
	private static MySQLExamDao instance;
	private static ConnectionPool cp;
	private static Lock lock1 = new ReentrantLock();
	/**
	 * Private constructor. References this Dao to existing ConnectionPool
	 * @throws DaoException
	 */
	private MySQLExamDao() throws DaoException
	{
		try 
		{
			MySQLExamDao.cp = ConnectionPool.getInstance();
		} 
		catch (NoConnectionException | ConnectionSQLException e) 
		{
			log.error("Exception in trying to initialize ConnectionPool. Can't create MySQLExamDao", e);
			throw new DaoException("Exception in trying to initialize ConnectionPool. Can't create MySQLExamDao", e);
		}
	}	
	/**
	 * Method to get singleton instance of MySQLExamDao class
	 * @return Instance of MySQLExamDao.
	 * @throws DaoException
	 */
	public static MySQLExamDao getInstance() throws DaoException
	{
		singletonLock.lock();
		if (null == MySQLExamDao.instance)
		{
			MySQLExamDao.instance = new MySQLExamDao();
		}
		singletonLock.unlock();
		return MySQLExamDao.instance;
	}	
	/**
	 * Collects idexams for idusers that belongs to Lecturer role in database
	 * @param id Idusers of any Lecturer
	 * @return List of idexams that can be checked by this Lecturer
	 */
	@Override
	public List<Integer> collectIdExamsForLecturer(int id) throws DaoException
	{
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		if (id > 0)
		{
			try 
			{
				connection = (PooledConnection) MySQLExamDao.cp.takeConnection();
				pStatement = connection.prepareStatement(MySQLExamDaoStatement.collectIdExamsForLecturer.getStatement());
				pStatement.setInt(1, id);
				return parseInteger(pStatement.executeQuery());
			} 
			catch (NoConnectionException | ConnectionSQLException e) 
			{
				log.error("Can't take connection from ConnectionPool", e);
				throw new DaoException("Can't take connection from ConnectionPool", e);
			} 
			catch (SQLException e) 
			{
				log.error("Error in SQL statement " + MySQLExamDaoStatement.collectIdExamsForLecturer.getStatement(), e);
				throw new DaoException("Error in SQL statement " + MySQLExamDaoStatement.collectIdExamsForLecturer.getStatement(), e);
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
			return new ArrayList<Integer>();
		}
	}
	/**
	 * Collect all idexams (id useful for mainadmin role)
	 * @return List with all idexams.
	 */
	@Override
	public List<Integer> collectIdExamsForMainadmin() throws DaoException 
	{
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		try 
		{
			connection = (PooledConnection) MySQLExamDao.cp.takeConnection();
			pStatement = connection.prepareStatement(MySQLExamDaoStatement.collectIdExamsForMainadmin.getStatement());
			return parseInteger(pStatement.executeQuery());
		} 
		catch (NoConnectionException | ConnectionSQLException e) 
		{
			log.error("Can't take connection from ConnectionPool", e);
			throw new DaoException("Can't take connection from ConnectionPool", e);
		} 
		catch (SQLException e) 
		{
			log.error("Error in SQL statement " + MySQLExamDaoStatement.collectIdExamsForMainadmin.getStatement(), e);
			throw new DaoException("Error in SQL statement " + MySQLExamDaoStatement.collectIdExamsForMainadmin.getStatement(), e);
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
	 * Collects exams by idexams that have not been checked yet.
	 * @param id idexam
	 * @return List with not checked exams.
	 */
	@Override
	public List<Exam> collectExamsByIdNotChecked(int id) throws DaoException 
	{
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		if (id > 0)
		{
			try 
			{
				connection = (PooledConnection) MySQLExamDao.cp.takeConnection();
				pStatement = connection.prepareStatement(MySQLExamDaoStatement.collectExamsByIdNotChecked.getStatement());
				pStatement.setInt(1, id);
				return parseExams(pStatement.executeQuery());
			} 
			catch (NoConnectionException | ConnectionSQLException e) 
			{
				log.error("Can't take connection from ConnectionPool", e);
				throw new DaoException("Can't take connection from ConnectionPool", e);
			} 
			catch (SQLException e) 
			{
				log.error("Error in SQL statement " +MySQLExamDaoStatement.collectExamsByIdNotChecked.getStatement(), e);
				throw new DaoException("Error in SQL statement " + MySQLExamDaoStatement.collectExamsByIdNotChecked.getStatement(), e);
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
			return new ArrayList<Exam>();
		}
	}
	/**
	 * Updates existing exam in database.
	 * @param exam Exam to be updated.
	 * @return true if any row in database has been changed.
	 */
	@Override
	public boolean updateExam(Exam exam) throws DaoException 
	{
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		if (null != exam)
		{
			try 
			{
				connection = (PooledConnection) MySQLExamDao.cp.takeConnection();
				pStatement = connection.prepareStatement(MySQLExamDaoStatement.updateExam.getStatement());
				pStatement.setInt(1, (exam.isStatus() ? 1 : 0));
				pStatement.setInt(2, exam.getMark());
				pStatement.setInt(3, exam.getIdexam());
				pStatement.setInt(4, exam.getIdfaculty());
				pStatement.setInt(5,  exam.getIdstudent());
				return (pStatement.executeUpdate() > 0 ? true : false);
			} 
			catch (NoConnectionException | ConnectionSQLException e) 
			{
				log.error("Can't take connection from ConnectionPool", e);
				throw new DaoException("Can't take connection from ConnectionPool", e);
			} 
			catch (SQLException e) 
			{
				log.error("Error in SQL statement " + MySQLExamDaoStatement.updateExam.getStatement(), e);
				throw new DaoException("Error in SQL statement " + MySQLExamDaoStatement.updateExam.getStatement(), e);
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
			return false;
		}
	}
	/**
	 * Collects all exams from studentexams table for selected idstudents.
	 * @param id idstudents from studentsexams.
	 * @return List with exams for selected user.
	 */
	@Override
	public List<Exam> collectExamsForStudent(int id) throws DaoException 
	{
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		if (id > 0)
		{
			try 
			{
				connection = (PooledConnection) MySQLExamDao.cp.takeConnection();
				pStatement = connection.prepareStatement(MySQLExamDaoStatement.collectExamsForStudent.getStatement());
				pStatement.setInt(1, id);
				return parseExams(pStatement.executeQuery());
			} 
			catch (NoConnectionException | ConnectionSQLException e) 
			{
				log.error("Can't take connection from ConnectionPool", e);
				throw new DaoException("Can't take connection from ConnectionPool", e);
			} 
			catch (SQLException e) 
			{
				log.error("Error in SQL statement " + MySQLExamDaoStatement.collectExamsForStudent.getStatement(), e);
				throw new DaoException("Error in SQL statement " + MySQLExamDaoStatement.collectExamsForStudent.getStatement(), e);
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
			return new ArrayList<Exam>();
		}
	}
	/**
	 * Collects all exams from studentexams table for selected idstudents that have not been passed yet.
	 * @param id idstudents from studentsexams.
	 * @return List with exams for selected user that have not been passed yet.
	 */
	@Override
	public List<Exam> collectExamsForStudentNotPassed(int id) throws DaoException 
	{
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		if (id > 0)
		{
			try 
			{
				connection = (PooledConnection) MySQLExamDao.cp.takeConnection();
				pStatement = connection.prepareStatement(MySQLExamDaoStatement.collectExamsForStudentNotPassed.getStatement());
				pStatement.setInt(1, id);
				return parseExams(pStatement.executeQuery());
			} 
			catch (NoConnectionException | ConnectionSQLException e) 
			{
				log.error("Can't take connection from ConnectionPool", e);
				throw new DaoException("Can't take connection from ConnectionPool", e);
			} 
			catch (SQLException e) 
			{
				log.error("Error in SQL statement " + MySQLExamDaoStatement.collectExamsForStudentNotPassed.getStatement(), e);
				throw new DaoException("Error in SQL statement " +MySQLExamDaoStatement.collectExamsForStudentNotPassed.getStatement(), e);
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
			return new ArrayList<Exam>();
		}
	}
	/**
	 * Collects all exams from studentexams table for selected idstudents.
	 * @param id idstudents from studentsexams.
	 * @return List with exams for selected user.
	 */
	@Override
	public List<Exam> collectAllExamsForStudent(int id) throws DaoException 
	{
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		if (id > 0)
		{
			try 
			{
				connection = (PooledConnection) MySQLExamDao.cp.takeConnection();
				pStatement = connection.prepareStatement(MySQLExamDaoStatement.collectAllExamsForStudent.getStatement());
				pStatement.setInt(1, id);
				return parseExams(pStatement.executeQuery());
			} 
			catch (NoConnectionException | ConnectionSQLException e) 
			{
				log.error("Can't take connection from ConnectionPool", e);
				throw new DaoException("Can't take connection from ConnectionPool", e);
			} 
			catch (SQLException e) 
			{
				log.error("Error in SQL statement " + MySQLExamDaoStatement.collectAllExamsForStudent.getStatement(), e);
				throw new DaoException("Error in SQL statement " + MySQLExamDaoStatement.collectAllExamsForStudent.getStatement(), e);
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
			return new ArrayList<Exam>();
		}
	}
	/**
	 * Collects all exams from studentexams table. Is useful for the role mainadmin.
	 * @return List with all exams.
	 */
	@Override
	public List<Exam> collectAllExamsForMainadmin() throws DaoException 
	{
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		try 
		{
			connection = (PooledConnection) MySQLExamDao.cp.takeConnection();
			pStatement = connection.prepareStatement(MySQLExamDaoStatement.collectAllExamsForMainadmin.getStatement());
			return parseExams(pStatement.executeQuery());
		} 
		catch (NoConnectionException | ConnectionSQLException e) 
		{
			log.error("Can't take connection from ConnectionPool", e);
			throw new DaoException("Can't take connection from ConnectionPool", e);
		} 
		catch (SQLException e) 
		{
			log.error("Error in SQL statement " + MySQLExamDaoStatement.collectAllExamsForMainadmin.getStatement(), e);
			throw new DaoException("Error in SQL statement " + MySQLExamDaoStatement.collectAllExamsForMainadmin.getStatement(), e);
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
	 * Creates new exam in exams table.
	 * @param exam Valid ExamWriteBean to be written in database.
	 * @return true if any row has been created.
	 */
	@Override
	public boolean createNewExam(ExamWriteBean exam) throws DaoException 
	{
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		try 
		{
			connection = (PooledConnection) MySQLExamDao.cp.takeConnection();


			pStatement = connection.prepareStatement(MySQLExamDaoStatement.createNewExam.getStatement());
			pStatement.setString(1, exam.getName());
			pStatement.setString(2, exam.getContent());
			lock1.lock();
			boolean write = (pStatement.executeUpdate() > 0 ? true : false);

			//int id = parseKey(pStatement.getGeneratedKeys());
			if (write)
			{
				pStatement = connection.prepareStatement(MySQLExamDaoStatement.getMaxIdexam.getStatement());
				int id = getMaxIdexam(pStatement.executeQuery());
				lock1.unlock();
				return (createFacultyExams(id, exam));
			}
			else
			{
				lock1.unlock();
				return false;
			}
		} 
		catch (NoConnectionException | ConnectionSQLException e) 
		{
			log.error("Can't take connection from ConnectionPool", e);
			throw new DaoException("Can't take connection from ConnectionPool", e);
		} 
		catch (SQLException e) 
		{
			log.error("Error in SQL statement " + MySQLExamDaoStatement.createNewExam.getStatement(), e);
			throw new DaoException("Error in SQL statement " + MySQLExamDaoStatement.createNewExam.getStatement(), e);
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
	 * Creates new rows in facultyexams for new exam from ExamWriteBean.
	 * @param id idfaculty value
	 * @param exam Valid ExamWriteBean
	 * @return true if any row has been created in facultyexams table.
	 * @throws DaoException
	 */
	private boolean createFacultyExams(int id, ExamWriteBean exam) throws DaoException
	{
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		try 
		{
			connection = (PooledConnection) MySQLExamDao.cp.takeConnection();
			List<Integer> idfaculties = exam.getIdfaculties();
			int count = 0;
			for (Integer i : idfaculties)
			{
				pStatement = connection.prepareStatement(MySQLExamDaoStatement.createNewFacultyExam.getStatement());
				pStatement.setInt(1, i);
				pStatement.setInt(2, id);
				count += pStatement.executeUpdate();
			}
			return ((count > 0 ? true : false));
		} 
		catch (NoConnectionException | ConnectionSQLException e) 
		{
			log.error("Can't take connection from ConnectionPool", e);
			throw new DaoException("Can't take connection from ConnectionPool", e);
		} 
		catch (SQLException e) 
		{
			log.error("Error in SQL statement " + MySQLExamDaoStatement.createNewFacultyExam.getStatement(), e);
			throw new DaoException("Error in SQL statement " + MySQLExamDaoStatement.createNewFacultyExam.getStatement(), e);
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
	 * Creates new rows in studentsexams table for selected User using exams from selected Faculty.
	 * @param faculty Faculty from what exams should be connected to such user.
	 * @param student User that should be connected with new studentsexams.
	 * @return true if any row has been created in studentsexams table.
	 */
	@Override
	public boolean writeExamsForStudent(Faculty faculty, User student) throws DaoException 
	{
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		try 
		{
			connection = (PooledConnection) MySQLExamDao.cp.takeConnection();
			List<Integer> idfacultyexams = getIdfacultyExamsForIdexam(faculty.getId());
			int count = 0;
			for (Integer i : idfacultyexams)
			{
				pStatement = connection.prepareStatement(MySQLExamDaoStatement.writeExamsForStudent.getStatement());
				pStatement.setInt(1, student.getIdusers());
				pStatement.setInt(2, i);
				count += pStatement.executeUpdate();
			}
			return (count > 0 ? true : false);
		} 
		catch (NoConnectionException | ConnectionSQLException e) 
		{
			log.error("Can't take connection from ConnectionPool", e);
			throw new DaoException("Can't take connection from ConnectionPool", e);
		} 
		catch (SQLException e) 
		{
			log.error("Error in SQL statement " + MySQLExamDaoStatement.writeExamsForStudent.getStatement(), e);
			throw new DaoException("Error in SQL statement " + MySQLExamDaoStatement.writeExamsForStudent.getStatement(), e);
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
	 * Collects all idfacultyexams for idfaculty. Is used to collect all exams by faculty to create new studentsexams.
	 * @param idfaculty id of faculty
	 * @return List with idfacultyexams values.
	 * @throws DaoException
	 */
	private List<Integer> getIdfacultyExamsForIdexam(int idfaculty) throws DaoException
	{
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		List<Integer> results = new ArrayList<Integer>();
		try 
		{
			if (idfaculty > 0)
			{
				connection = (PooledConnection) MySQLExamDao.cp.takeConnection();
				pStatement = connection.prepareStatement(MySQLExamDaoStatement.collectExamsByFaculty.getStatement());
				pStatement.setInt(1, idfaculty);
				results.addAll(parseIntegers(pStatement.executeQuery()));
			}
		} 
		catch (NoConnectionException | ConnectionSQLException e) 
		{
			log.error("Can't take connection from ConnectionPool", e);
			throw new DaoException("Can't take connection from ConnectionPool", e);
		} 
		catch (SQLException e) 
		{
			log.error("Error in SQL statement " + MySQLExamDaoStatement.collectExamsByFaculty.getStatement(), e);
			throw new DaoException("Error in SQL statement " + MySQLExamDaoStatement.collectExamsByFaculty.getStatement(), e);
		}
		finally
		{
			if (null != connection)
			{
				try 
				{
					cp.releaseConnection(connection);
				} 
				catch (ConnectionSQLException e) 
				{
					log.error("Can't release connection back", e);
					throw new DaoException("Can't release connection back", e);
				}
			}
		}
		return results;
	}
	/**
	 * Updates existing studentexams with student answer.
	 * @param exam Exam that should be updated.
	 * @return true if any row in database has been changed.
	 */
	@Override
	public boolean writeExamAsPassed(Exam exam) throws DaoException
	{
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		try 
		{
			List<Integer> ids = getIdfacultyexamsByIdstudentsIdexamIdfaculty(exam.getIdstudent(), exam.getIdexam(), exam.getIdfaculty());
			if (ids.size() != 1)
			{
				return false;
			}
			else
			{
				int idfacultyexams = ids.get(0);
				connection = (PooledConnection) MySQLExamDao.cp.takeConnection();
				pStatement = connection.prepareStatement(MySQLExamDaoStatement.writeExamAsPassed.getStatement());
				pStatement.setString(1, exam.getStudentcontent());
				pStatement.setInt(2, exam.getIdstudent());
				pStatement.setInt(3, idfacultyexams);
				return (pStatement.executeUpdate() > 0 ? true : false);
			}			
		} 
		catch (NoConnectionException | ConnectionSQLException e) 
		{
			log.error("Can't take connection from ConnectionPool", e);
			throw new DaoException("Can't take connection from ConnectionPool", e);
		} 
		catch (SQLException e) 
		{
			log.error("Error in SQL statement " + MySQLExamDaoStatement.writeExamsForStudent.getStatement(), e);
			throw new DaoException("Error in SQL statement " + MySQLExamDaoStatement.writeExamsForStudent.getStatement(), e);
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
	 * Gets idfacultyexams in studentsexams table. Is used to check if true exam is trying to be updated with student answer. Is not necessary.
	 * @param idstudents idstudents value from studentsexams
	 * @param idexam idexam value in exams table.
	 * @param idfaculty idfaculty value in faculties table.
	 * @return List with idfacultyexams values.
	 * @throws DaoException
	 */
	private List<Integer> getIdfacultyexamsByIdstudentsIdexamIdfaculty(int idstudents, int idexam, int idfaculty) throws DaoException
	{
		PooledConnection connection = null;
		PreparedStatement pStatement = null;
		try 
		{
			connection = (PooledConnection) MySQLExamDao.cp.takeConnection();
			pStatement = connection.prepareStatement(MySQLExamDaoStatement.getIdfacultyexamsByIdstudentsIdexamIdfaculty.getStatement());
			pStatement.setInt(1, idstudents);
			pStatement.setInt(2, idexam);
			pStatement.setInt(3, idfaculty);
			return parseIntegers(pStatement.executeQuery());
		} 
		catch (NoConnectionException | ConnectionSQLException e) 
		{
			log.error("Can't take connection from ConnectionPool", e);
			throw new DaoException("Can't take connection from ConnectionPool", e);
		} 
		catch (SQLException e) 
		{
			log.error("Error in SQL statement " + MySQLExamDaoStatement.getIdfacultyexamsByIdstudentsIdexamIdfaculty.getStatement(), e);
			throw new DaoException("Error in SQL statement " + MySQLExamDaoStatement.getIdfacultyexamsByIdstudentsIdexamIdfaculty.getStatement(), e);
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
	 * Parses list of Integer from the ResultSet using ExamConstants.IDFACULTYEXAMS
	 * @param rs ResultSet to be parsed.
	 * @return List of ExamConstants.IDFACULTYEXAMS values from the ResultSet
	 * @throws DaoException
	 */
	private List<Integer> parseIntegers(ResultSet rs) throws DaoException
	{

		List<Integer> results = new ArrayList<Integer>();
		if (rs != null)
		{
			try 
			{
				while(rs.next())
				{
					results.add(new Integer(rs.getInt(ExamConstants.IDFACULTYEXAMS.getContent())));				
				}			
			} 
			catch (SQLException e) 
			{
				log.error("Error in trying to parse Integer through resultSet", e);
				throw new DaoException("Error in trying to parse Integer through resultSet", e);
			}
		}
		return results;
	}
	/**
	 * Parses list of Integer from the ResultSet. Gets only 1st column from the ResultSet
	 * @param rs ResultSet to be parsed.
	 * @return List of int values.
	 * @throws DaoException
	 */
	private List<Integer> parseInteger(ResultSet rs) throws DaoException
	{
		List<Integer> examsIds = new ArrayList<Integer>();
		if (rs != null)
		{
			try 
			{
				while(rs.next())
				{
					examsIds.add(new Integer(rs.getInt(1)));				
				}			
			} 
			catch (SQLException e) 
			{
				log.error("Error in trying to parse Integer through resultSet", e);
				throw new DaoException("Error in trying to parse Integer through resultSet", e);
			}
		}
		return examsIds;
	}
	/**
	 * Parses list of Exams from the ResultSet using enums from ExamConstants.
	 * @param rs ResultSet to be parsed.
	 * @return List of compiled Exams beans.
	 * @throws DaoException
	 */
	private List<Exam> parseExams(ResultSet rs) throws DaoException
	{
		List<Exam> exams = new ArrayList<Exam>();
		if (rs != null)
		{
			try 
			{
				while(rs.next())
				{
					Exam exam = factory.createNewExam();
					exam.setIdexam(rs.getInt(ExamConstants.FACULTYEXAMS_IDEXAM.getContent()));
					exam.setIdstudent(rs.getInt(ExamConstants.IDSTUDENTS.getContent()));
					exam.setStudentcontent(rs.getString(ExamConstants.STUDENTCONTENT.getContent()));
					exam.setMark(rs.getInt(ExamConstants.MARK.getContent()));
					exam.setStatus((rs.getInt(ExamConstants.STATUS.getContent()) == 1 ? true : false));
					exam.setIdfaculty(rs.getInt(ExamConstants.FACULTYEXAMS_IDFACULTY.getContent()));
					exam.setContent(rs.getString(ExamConstants.EXAMS_CONTENT.getContent()));
					exams.add(exam);				
				}			
			} 
			catch (SQLException e) 
			{
				log.error("Error in trying to parse Integer through resultSet", e);
				throw new DaoException("Error in trying to parse Integer through resultSet", e);
			}
		}
		return exams;		
	}
	/**
	 * Parses max int value from the 1st column
	 * @param rs ResultSet to be parsed.
	 * @return parsed int value
	 * @throws DaoException
	 */
	private int getMaxIdexam(ResultSet rs) throws DaoException
	{
		int id = -1;
		if (rs != null)
		{
			try 
			{				
				while(rs.next())
				{
					id = (rs.getInt(1) > id ? rs.getInt(1) : id);			
				}			
				return id;
			} 
			catch (SQLException e) 
			{
				log.error("Error in trying to parse Integer through resultSet", e);
				throw new DaoException("Error in trying to parse Integer through resultSet", e);
			}
		}
		return id;	
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