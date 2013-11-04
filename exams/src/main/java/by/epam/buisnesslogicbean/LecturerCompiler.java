package by.epam.buisnesslogicbean;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.epam.buisnesslogicbean.exception.LogicException;
import by.epam.db.dao.ExamDao;
import by.epam.db.exception.DaoException;
import by.epam.entity.Exam;
import by.epam.entity.User;
import by.epam.resources.Resources;
/**
 * Example of abstract Compiler. Is used to keep idExams that can check user decorated by this LecturerCompiler.
 * Contains logic of collecting idexams (as it is LecturerCompiler, collects idexams that belongs to this Lecturer).
 * @author Dmitry Shanko
 *
 */
public class LecturerCompiler extends Compiler implements AdminCompiler
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Resources res = Resources.getInstance();
	private static final Logger log = res.getLogger(by.epam.buisnesslogicbean.LecturerCompiler.class);
	private List<Integer> idExams;

	public LecturerCompiler()
	{
		super();
		this.idExams = new ArrayList<Integer>();
	}

	public LecturerCompiler(User user, ExamDao examDao) throws LogicException
	{
		super(user, examDao);
		this.idExams = new ArrayList<Integer>();
		this.collectIdExams();
		this.collectExams();
	}	
	/**
	 * @return the idExams
	 */
	public List<Integer> getIdExams() 
	{
		return idExams;
	}

	/**
	 * @param idExams the idExams to set
	 */
	public void setIdExams(List<Integer> idExams) 
	{
		if (null != idExams)
		{
			this.idExams = idExams;
		}
	}
	/**
	 * Collects all idexams that can be checked by this Lecturer. Uses this.getExamDao().collectIdExamsForLecturer(this.getUser().getIdusers())
	 * @throws LogicException if catches any DaoException
	 */
	private void collectIdExams() throws LogicException
	{	
		if (this.getUser().getIdusers() > 0)
		{
			try 
			{
				this.setIdExams(this.getExamDao().collectIdExamsForLecturer(this.getUser().getIdusers()));
			} 
			catch (DaoException e) 
			{
				log.error("DaoException in " + this.getExamDao().getClass() + ". Can't collect Exams for " + this.getUser().toString(), e);
				throw new LogicException("DaoException in " + this.getExamDao().getClass() + ". Can't collect Exams for " + this.getUser().toString(), e);
			}
		}
	}
	/**
	 * Collects all exams that can be checked by mainadmin role. this.getExamDao().collectExamsByIdNotChecked(i), where i must be kept in idexams collection
	 * @throws LogicException if catches any DaoException
	 */
	private void collectExams() throws LogicException
	{
		List<Exam> exams = new ArrayList<Exam>();
		if (this.getUser().getIdusers() > 0)
		{
			for (Integer i : this.getIdExams())
			{
				try 
				{	
					exams.addAll(this.getExamDao().collectExamsByIdNotChecked(i));				
				} 
				catch (DaoException e) 
				{
					log.error("DaoException in " + this.getExamDao().getClass() + ". Can't collect Exams for " + this.getUser(), e);
					throw new LogicException("DaoException in " + this.getExamDao().getClass() + ". Can't collect Exams for " + this.getUser().toString(), e);
				}
			}
		}
		this.setExams(exams);
	}
	/**
	 * Refreshes data by recollecting it from database
	 */
	@Override
	public void refresh() throws LogicException 
	{
		this.collectIdExams();
		this.collectExams();
	}
	/**
	 * Overridden Object.toString()<p>
	 * @return super.toString() + " " + idExams;
	 */
	@Override
	public String toString() 
	{
		return super.toString() + " " + idExams;
	}
	/**
	 * Overridden Object.equals()<p>
	 * @return (super.equals(obj)<p>
	 *			&& (obj instanceof LecturerCompiler) <p>
	 *			&& (((LecturerCompiler)obj).getIdExams().equals(this.getIdExams())));<p>
	 */
	@Override
	public boolean equals(Object obj) 
	{
		return (super.equals(obj)
				&& (obj instanceof LecturerCompiler) 
				&& (((LecturerCompiler)obj).getIdExams().equals(this.getIdExams())));
	}
	/**
	 * Overridden Object.hashCode()<p>
	 *	final int hash = 179;<p>
	 *	int result = 15;<p>
	 *	result = hash * result + super.hashCode();<p>
	 *	result = hash * result + ((null != idExams) ? idExams.hashCode() : 1);<p>
	 * @return result;
	 */
	@Override
	public int hashCode() 
	{
		final int hash = 179;
		int result = 15;
		result = hash * result + super.hashCode();
		result = hash * result + ((null != idExams) ? idExams.hashCode() : 1);
		return result;
	}
}
