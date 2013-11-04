package by.epam.web.service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import by.epam.buisnesslogicbean.Compiler;
import by.epam.buisnesslogicbean.LecturerCompiler;
import by.epam.buisnesslogicbean.MainadminCompiler;
import by.epam.buisnesslogicbean.StudentCompiler;
import by.epam.buisnesslogicbean.exception.LogicException;

import by.epam.db.dao.ExamDao;
import by.epam.db.dao.FacultyDao;
import by.epam.db.dao.factory.DAOFactory;
import by.epam.db.exception.DaoException;
import by.epam.entity.Exam;
import by.epam.entity.ExamWriteBean;
import by.epam.entity.Faculty;
import by.epam.web.service.exception.ServiceException;
/**
 * This signletone class connects Dao Exam layer with web layer represented by command package.
 * @author Dmitry Shanko
 *
 */
public class ExamService 
{
	private static ExamService instance;
	private static Lock singletoneLock = new ReentrantLock();
	private static Lock writeLock = new ReentrantLock();
	private ExamDao eDao;
	/**
	 * Private constructor, do only this.eDao = this.getExamDao(), where getExamDao() takes DAOFactory.MYSQL.getExamDao() or throws ServiceException if can't.
	 * @throws ServiceException
	 */
	private ExamService() throws ServiceException
	{
		this.eDao = this.getExamDao();
	}
	/**
	 * Method that uses lock to prevent threads from creating many instances of this class
	 * @return the singletone instance of FacultyService class
	 * @throws ServiceException
	 */
	public static ExamService getInstance() throws ServiceException
	{
		singletoneLock.lock();
		if (null == ExamService.instance)
		{
			ExamService.instance = new ExamService();
		}
		singletoneLock.unlock();
		return ExamService.instance;
	}
	/**
	 * Puts mark to selected Exam if User from AdminCompiler has access to check it.<p>
	 * Uses ExamDao.updateExam(exam). 
	 * @param compiler AdminCompiler object that contains User that checks Exam.
	 * @param exam Exam to check
	 * @param mark Mark that User put to this Exam.
	 * @return true if exam has been successfully updated in database.
	 * @throws ServiceException
	 */
	public boolean checkAndUpdateExam(Compiler compiler, Exam exam, int mark) throws ServiceException
	{
		Exam checked = null;
		for (Exam e : compiler.getExams())
		{
			if (exam.equals(e))
			{
				checked = e;
			}
		}
		if (null != checked && ((compiler instanceof LecturerCompiler && !checked.isStatus()) || compiler instanceof MainadminCompiler))
		{
			if (mark < 0)
			{
				mark = 0;
			}
			if (mark > 10)
			{
				mark = 10;
			}
			checked.setMark(mark);
			checked.setStatus(true);
			try 
			{
				if (!eDao.updateExam(checked))
				{
					throw new ServiceException("Can't update Exam:\n" + checked);
				}
				else
				{
					compiler.refresh();
					return true;
				}				
			} 
			catch (DaoException e1) 
			{
				throw new ServiceException("Can't update exam in " + eDao.getClass(), e1);
			} 
			catch (LogicException e1) 
			{
				throw new ServiceException("Can't refresh compiler " + compiler, e1);
			}
		}
		return false;
	}
	/**
	 * Creates new Exam in database to selected faculties (list of idfaculties) with name and content (question of exam).<p>
	 * Uses ExamDao.createNewExam(exam) if (ExamValidator.validateExam(exam)).
	 * @param idfaculties list of idfaculties.
	 * @param name Name of exam.
	 * @param content Question of exam.
	 * @return true if exam has been successfully created in database.
	 * @throws ServiceException
	 */
	public boolean saveNewExam(List<Integer> idfaculties, String name, String content) throws ServiceException
	{
		if (null == name || null == content || null == idfaculties)
		{
			return false;
		}
		ExamWriteBean exam = new ExamWriteBean();
		content = content.replaceAll("<", "&lt;");
		content = content.replaceAll(">", "&gt");
		name = name.replaceAll("<", "&lt;");
		name = name.replaceAll(">", "&gt");
		exam.setIdfaculties(idfaculties);
		exam.setName(name);
		exam.setContent(content);
		try 
		{
			if (ExamValidator.validateExam(exam))
			{
				return eDao.createNewExam(exam);
			}
		} 
		catch (ServiceException e) 
		{
			throw e;
		}
		catch (DaoException e)
		{
			throw new ServiceException("Error in trying to write exam to the database", e);
		}
		return false;
	}
	/**
	 * Adds exams in database to User from StudentCompiler by chosen faculty.<p>
	 * Uses ExamDao.writeExamsForStudent(Faculty, User);
	 * @param faculty String with the id of selected faculty.
	 * @param student StudentCompiler with User that has chosen this faculty.
	 * @return true if there were no exams in database to such User and if Exams has been successfully added to this User.
	 * @throws ServiceException
	 */
	public boolean addExamsToStudent(String faculty, StudentCompiler student) throws ServiceException
	{
		writeLock.lock();
		try		
		{
			List<Faculty> faculties = getFacultyDao().collectFaculties();
			if (null != faculty && null != student && faculties.size() > 0 && !student.isRegistred())
			{
				int idfaculty = 0;
				try
				{
					idfaculty = Integer.parseInt(faculty);
				}
				catch (NumberFormatException e)
				{
					return false;
				}
				for (Faculty f : faculties)
				{
					if (f.getId() == idfaculty)
					{
						if (eDao.collectAllExamsForStudent(student.getUser().getIdusers()).size() < 1)
						{
							return eDao.writeExamsForStudent(f, student.getUser());
						}					
					}
				}
			}
			student.refresh();
		}
		catch (DaoException e)
		{
			throw new ServiceException("Can't get Dao in ExamService", e);
		}
		catch (LogicException e) 
		{
			throw new ServiceException("Error in refreshing Logical bean", e);
		}
		finally
		{
			writeLock.unlock();
		}
		return true;
	}
	/**
	 * Takes Exam from StudentCompiler compiler using StudentCompiler.getNotPassedExams() by idexam and idstudent.
	 * @param idstudent idstudent to be sure that this student has access to this Exam.
	 * @param idexam idexam of Exam to look for
	 * @param compiler StudentCompiler that contains possible exams to pass.
	 * @return Exam by idexam that has not been already passed.
	 * @throws ServiceException
	 */
	public Exam getExamToPass(int idstudent, int idexam, Object compiler) throws ServiceException
	{
		if ((idstudent < 1 || idexam < 1 || null == compiler) || !(compiler instanceof StudentCompiler))
		{
			throw new ServiceException("Can't get any Exam for such variables");
		}
		StudentCompiler student = (StudentCompiler) compiler;
		for (Exam exam : student.getNotPassedExams())
		{
			if (exam.getIdstudent() == idstudent && exam.getIdexam() == idexam && exam.getStudentcontent() == null)
			{
				return exam;
			}
		}
		throw new ServiceException("No such exam to pass for idexam=" + idexam + "; idstudent=" + idstudent + "; user: " + student.getUser());
	}
	/**
	 * Makes Exam passed by student in database. Uses ExamDao.writeExamAsPassed(exam).<p>
	 * Can't make Exam passed if it has been already passed. Or if it has any student answer.
	 * @param exam Exam that student tried to pass.
	 * @param compiler Compiler from HttpSession to prevent not authorized access to Exam.
	 * @param studentcontent Student answer on this exam.
	 * @return true if params are not null, User has access to passing this Exam and database has been successfully updated by this Exam.
	 * @throws ServiceException
	 */
	public boolean passExam(Exam exam, Object compiler, String studentcontent) throws ServiceException
	{
		if ((null == exam || null == studentcontent || null == compiler) || !(compiler instanceof StudentCompiler))
		{
			return false;
		}
		else
		{
			studentcontent = studentcontent.replaceAll("<", "&lt;");
			studentcontent = studentcontent.replaceAll(">", "&gt");
			StudentCompiler student = (StudentCompiler) compiler;
			for (Exam e : student.getNotPassedExams())
			{
				if (exam.getIdstudent() == e.getIdstudent() && exam.getIdexam() == e.getIdexam() && e.getStudentcontent() == null)
				{
					exam.setStudentcontent(studentcontent);
					try 
					{
						return eDao.writeExamAsPassed(exam);
					} 
					catch (DaoException e1) 
					{
						throw new ServiceException("Can't write such exam " + exam, e1);
					}
				}
			}
		}
		return false;
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
}
