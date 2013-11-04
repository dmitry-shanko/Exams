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
 * Example of abstract Compiler. Is used as decorator for User as student (idroles=3).<p>
 * Contains logic of collecting important data for users. Can be refreshed.
 * @author Dmitry Shanko
 *
 */
public class StudentCompiler extends Compiler implements Comparable<StudentCompiler>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Resources res = Resources.getInstance();
	private static final Logger log = res.getLogger(by.epam.buisnesslogicbean.StudentCompiler.class);
	private double mark;
	private boolean registred;
	private List<Exam> notPassedExams;
	private List<Exam> passedExams;

	public StudentCompiler()
	{
		super();
		this.notPassedExams = new ArrayList<Exam>();
		this.passedExams = new ArrayList<Exam>();
	}

	public StudentCompiler(User user, ExamDao examDao) throws LogicException
	{
		super(user, examDao);
		this.notPassedExams = new ArrayList<Exam>();
		this.passedExams = new ArrayList<Exam>();
		this.collectExams();
		this.setNotPassedExams();
		this.setPassedExams();
		this.setRegistred();
		this.countMark();
	}
	/**
	 * Getter for the field registred.
	 * @return registred.
	 */
	public boolean isRegistred()
	{
		return registred;
	}
	/**
	 * Getter for the field mark. Calls this.refresh() to collect all marks.
	 * @return mark.
	 */
	public double getMark()
	{
		try
		{
			this.refresh();
		}
		catch (LogicException e)
		{
			log.error("Exception in trying to refresh from getMark() method", e); 
		}
		return this.mark;		
	}
	/**
	 * Getter for the list of exams that are not passed yet.
	 * @return notPassedExams
	 */
	public List<Exam> getNotPassedExams()
	{
		return notPassedExams;
	}
	/**
	 * Private setter of exams that are not passed. Recollects all exams from this.getExams() if exam has null studentcontent.
	 */
	private void setNotPassedExams()
	{
		this.notPassedExams.clear();
		for (Exam exam : this.getExams())
		{
			if (null == exam.getStudentcontent())
			{
				this.notPassedExams.add(exam);
			}
		}
	}
	/**
	 * Getter for the field passedExams
	 * @return passedExans
	 */
	public List<Exam> getPassedExams()
	{
		return passedExams;
	}
	/**
	 * Private setter of exams that are passed and already checked. Recollects all exams from this.getExams() if exam is already checked(exam.isStatus()).
	 */
	private void setPassedExams()
	{
		this.passedExams.clear();
		for (Exam exam : this.getExams())
		{
			if (exam.isStatus())
			{
				this.passedExams.add(exam);
			}
		}
	}
	/**
	 * Private setter for the field registred. If this compiler has any exam in this.getExams(), so this.registred = true else this.registred = false.
	 */
	private void setRegistred()
	{
		if (getExams().size() > 0)
		{
			this.registred = true;
		}
		else
		{
			this.registred = false;
		}
	}
	/**
	 * Collects all exams for this User from studentsexams.
	 * @throws LogicException if catches any DaoException.
	 */
	private void collectExams() throws LogicException
	{
		if (this.getUser().getIdusers() > 0)
		{
			try 
			{
				this.setExams(this.getExamDao().collectExamsForStudent(this.getUser().getIdusers()));
			} 
			catch (DaoException e) 
			{
				throw new LogicException("Can't get Exams for StudentCompiler class", e);
			}
		}
	}
	/**
	 * Counts total mark by all exams. this.mark = allMarks / getExams().size();
	 */
	private void countMark()
	{
		double allMarks = 0;
		for (Exam exam : this.getExams())
		{
			allMarks += exam.getMark();
		}
		if (getExams().size() != 0)
		{
			this.mark = allMarks / getExams().size();
		}
		else
		{
			this.mark = 0;
		}
	}
	/**
	 * Refreshes data of this compiler from database.
	 */
	public void refresh() throws LogicException
	{
		this.collectExams();
		this.setNotPassedExams();
		this.setPassedExams();
		this.setRegistred();
		this.countMark();
	}
	/**
	 * Is used to sort StudentCompiler by resulted mark.
	 * @return (int)(10000*(o.getMark() - this.getMark()));
	 */
	@Override
	public int compareTo(StudentCompiler o) 
	{
		if (null == o)
		{
			return Integer.MIN_VALUE;
		}
		return (int)(10000*(o.getMark() - this.getMark()));
	}
	/**
	 * Overridden Object.toString()<p>
	 * @return super.toString() + " " + mark + " " + registred;
	 */
	@Override
	public String toString() 
	{
		return super.toString() + " " + mark + " " + registred;
	}
	/**
	 * Overridden Object.equals()<p>
	 * @return (super.equals(obj)<p>
	 *		&& (obj instanceof StudentCompiler)<p> 
	 *		&& (((StudentCompiler)obj).getMark() == this.getMark()) <p>
	 *		&& (((StudentCompiler)obj).getPassedExams().equals(this.getPassedExams()))<p>
	 *		&& (((StudentCompiler)obj).getNotPassedExams().equals(this.getNotPassedExams())));<p>
	 */
	@Override
	public boolean equals(Object obj) 
	{
		return (super.equals(obj)
				&& (obj instanceof StudentCompiler) 
				&& (((StudentCompiler)obj).getMark() == this.getMark()) 
				&& (((StudentCompiler)obj).getPassedExams().equals(this.getPassedExams()))
				&& (((StudentCompiler)obj).getNotPassedExams().equals(this.getNotPassedExams())));
	}
	/**
	 * Overridden Object.hashCode()<p>
	 *	final int hash = 111;<p>
	 *	int result = 17;<p>
	 *	result = hash * result + super.hashCode();<p>
	 *	result = hash * result + ((null != passedExams) ? passedExams.hashCode() : 1);<p>
	 *	result = hash * result + ((null != notPassedExams) ? notPassedExams.hashCode() : 1);<p>
	 *	result = hash * result + (registred ? 2 : 1);<p>
	 *	result = hash * result + (int)(mark*100);<p>
	 * @return result;
	 */
	@Override
	public int hashCode() 
	{
		final int hash = 111;
		int result = 17;
		result = hash * result + super.hashCode();
		result = hash * result + ((null != passedExams) ? passedExams.hashCode() : 1);
		result = hash * result + ((null != notPassedExams) ? notPassedExams.hashCode() : 1);
		result = hash * result + (registred ? 2 : 1);
		result = hash * result + (int)(mark*100);
		return result;
	}
}
