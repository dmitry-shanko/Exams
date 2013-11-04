package by.epam.buisnesslogicbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.epam.buisnesslogicbean.exception.LogicException;
import by.epam.db.dao.ExamDao;
import by.epam.entity.Exam;
import by.epam.entity.User;
import by.epam.entity.factory.EntitityFactory;
import by.epam.resources.Resources;
/**
 * Abstract class that contains data collected for logged in user.<p>
 * Can recollect all data for selected User by calling method refresh().
 * @author Dmitry Shanko
 *
 */
public abstract class Compiler implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Resources res = Resources.getInstance();
	private static final Logger log = res.getLogger(by.epam.buisnesslogicbean.Compiler.class);
	private ExamDao examDao;
	private User user;
	private List<Exam> exams;

	public Compiler()
	{
		this.user = EntitityFactory.getInstance().createNewUser();
		this.exams = new ArrayList<Exam>();		
	}

	public Compiler(User user, ExamDao examDao) throws LogicException
	{
		if (null != user)
		{
			this.user = user;	
			this.exams = new ArrayList<Exam>();
		}
		else
		{
			log.error("Can't create Decorator for null User");
			throw new LogicException("Can't create Decorator for null User");
		}
		if (null != examDao)
		{
			this.examDao = examDao;
		}
		else
		{
			log.error("Can't create propper Compiler without any Dao");
			throw new LogicException("Can't create propper Compiler without any Dao");
		}
	}

	/**
	 * @return the user
	 */
	public User getUser() 
	{
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) 
	{
		if (null != user)
		{
			this.user = user;
		}
	}

	/**
	 * @return the examDao
	 */
	public ExamDao getExamDao() 
	{
		return examDao;
	}

	/**
	 * @param examDao the examDao to set
	 */
	public void setExamDao(ExamDao examDao) 
	{
		if (null != examDao)
		{
			this.examDao = examDao;
		}
	}

	/**
	 * @return the exams
	 */
	public List<Exam> getExams() 
	{
		return exams;
	}

	/**
	 * @param exams the exams to set
	 */
	public void setExams(List<Exam> exams) 
	{
		if (null != exams)
		{
			this.exams = exams;
		}
	}

	protected final Logger getLogger()
	{
		return Compiler.log;
	}

	abstract public void refresh() throws LogicException;
	/**
	 * Overridden Object.toString()<p>
	 * @return user.toString().concat(exams.toString());
	 */
	@Override
	public String toString() 
	{
		return user.toString().concat(exams.toString());
	}
	/**
	 * Overridden Object.equals()<p>
	 * @return ((null != obj) && (obj instanceof Compiler) <p>
	 *		&& (((Compiler)obj).getUser().equals(this.getUser())) <p>
	 *		&& (((Compiler)obj).getExams().equals(this.getExams())));<p>
	 */
	@Override
	public boolean equals(Object obj) 
	{
		return ((null != obj) && (obj instanceof Compiler) 
				&& (((Compiler)obj).getUser().equals(this.getUser())) 
				&& (((Compiler)obj).getExams().equals(this.getExams())));
	}
	/**
	 * Overridden Object.hashCode()<p>
	 *	final int hash = 101;<p>
	 *	int result = 19;<p>
	 *	result = hash * result + ((null != user) ? user.hashCode() : 1);<p>
	 *	result = hash * result + ((null != exams) ? exams.hashCode() : 1);<p>
	 * @return result;
	 */
	@Override
	public int hashCode() 
	{
		final int hash = 101;
		int result = 19;
		result = hash * result + ((null != user) ? user.hashCode() : 1);
		result = hash * result + ((null != exams) ? exams.hashCode() : 1);
		return result;
	}
}
