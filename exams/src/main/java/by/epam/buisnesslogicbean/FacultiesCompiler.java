package by.epam.buisnesslogicbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.epam.buisnesslogicbean.exception.LogicException;
import by.epam.db.dao.ExamDao;
import by.epam.db.dao.FacultyDao;
import by.epam.db.dao.UserDao;
import by.epam.db.exception.DaoException;
import by.epam.entity.Faculty;
import by.epam.entity.User;
/**
 * Example of abstract Compiler. Is used to keep students by faculties.
 * Contains logic of collecting important data for dividing students by faculties and marks.
 * @author Dmitry Shanko
 *
 */
public class FacultiesCompiler extends Compiler
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FacultyDao facultyDao;
	private UserDao userDao;
	private List<Faculty> faculties;
	private Map<Integer, List<StudentCompiler>> students;

	public FacultiesCompiler()
	{
		super();
	}

	public FacultiesCompiler(User user, ExamDao examDao, FacultyDao facultyDao, UserDao userDao) throws LogicException
	{
		super(user, examDao);
		if (null == facultyDao)
		{
			super.getLogger().error("Can't create new FacultiesCompiler with null FacultyDao");
			throw new LogicException("Can't create new FacultiesCompiler with null FacultyDao");
		}
		else
		{
			this.facultyDao = facultyDao;
		}
		if (null == userDao)
		{
			super.getLogger().error("Can't create new FacultiesCompiler with null UserDao");
			throw new LogicException("Can't create new FacultiesCompiler with null UserDao");
		}
		else
		{
			this.userDao = userDao;
		}
		this.faculties = new ArrayList<Faculty>();
		this.students = new HashMap<Integer, List<StudentCompiler>>();
		this.refresh();
	}
	/**
	 * @return the facultyDao
	 */
	public FacultyDao getFacultyDao() 
	{
		return facultyDao;
	}

	/**
	 * @param facultyDao the facultyDao to set
	 */
	public void setFacultyDao(FacultyDao facultyDao) 
	{
		if (null != facultyDao)
		{
			this.facultyDao = facultyDao;
		}
	}
	/**
	 * Private setter for faculties from database.
	 */
	private void setFaculties()
	{
		try 
		{
			this.faculties = facultyDao.collectFaculties();
		} 
		catch (DaoException e) 
		{
			super.getLogger().warn("Can't get faculties from " + facultyDao.getClass() + " in " + getClass());
		}
	}
	/**
	 * Getter for faculties field
	 * @return faculties
	 */
	public List<Faculty> getFaculties()
	{
		return faculties;
	}
	/**
	 * Private method to divide students by faculties. Collects all students from database, then decorates them with StudentCompiler,<p>
	 * then sorts them it the collection and puts to Map with idfaculty as key.
	 */
	private void setStudentsByFaculty()
	{
		for (Faculty f : faculties)
		{
			try 
			{
				List<User> studentsByFaculty = userDao.getUsersByIdfaculty(f.getId());
				List<StudentCompiler> lsc = new ArrayList<StudentCompiler>();
				for (User u : studentsByFaculty)
				{
					try 
					{
						lsc.add(new StudentCompiler(u, getExamDao()));
					} 
					catch (LogicException e) 
					{
						getLogger().warn("Can't create StudentCompiler for " + u.toString());
					}
				}
				Collections.sort(lsc);
				students.put(f.getId(), lsc);
			} 
			catch (DaoException e) 
			{
				getLogger().warn("Can't get students from " + userDao.getClass() + " for " + f.toString());
			}
		}
	}
	/**
	 * Getter for map students.
	 * @return students
	 */
	public Map<Integer, List<StudentCompiler>> getStudents()
	{
		return students;
	}
	/**
	 * Method that refreshed all data contained in this Compiler
	 */
	@Override
	public void refresh() throws LogicException 
	{
		this.setFaculties();
		this.setStudentsByFaculty();
	}
	/**
	 * Overridden Object.toString()<p>
	 * @return super.toString() + " " + students;
	 */
	@Override
	public String toString() 
	{
		return super.toString() + " " + students;
	}
	/**
	 * Overridden Object.equals()<p>
	 * @return (super.equals(obj)<p>
	 *			&& (obj instanceof FacultiesCompiler) <p>
	 *			&& (((FacultiesCompiler)obj).getStudents().equals(this.getStudents())));<p>
	 */
	@Override
	public boolean equals(Object obj) 
	{
		return (super.equals(obj)
				&& (obj instanceof FacultiesCompiler) 
				&& (((FacultiesCompiler)obj).getStudents().equals(this.getStudents())));
	}
	/**
	 * Overridden Object.hashCode()<p>
	 *	final int hash = 1111;<p>
	 *	int result = 1009;<p>
	 *	result = hash * result + super.hashCode();<p>
	 *	result = hash * result + ((null != students) ? students.hashCode() : 1);<p>
	 * @return result;
	 */
	@Override
	public int hashCode() 
	{
		final int hash = 1111;
		int result = 1009;
		result = hash * result + super.hashCode();
		result = hash * result + ((null != students) ? students.hashCode() : 1);
		return result;
	}
}
